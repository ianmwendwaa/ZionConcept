package com.example

import MemberService
import NewMemberData
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(val id: Int, val message: String)
fun Application.configureRouting() {
    val memberService = MemberService()
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
        get("/all_members"){
            try {
                val membersRegistered = memberService.getAllMembers()
                call.respond(HttpStatusCode.OK, membersRegistered)
            }catch (e: Exception){
                call.respond(HttpStatusCode.InternalServerError, "Encountered error: ${e.localizedMessage}")
            }
        }
        post("/members"){
            try {
                val newMemberPayload = call.receive<NewMemberData>()

                val insertedId = memberService.createMember(newMemberPayload)
                call.respond(HttpStatusCode.Created, ServerResponse(insertedId, "${newMemberPayload.receivedName} created successfully!"))
            } catch (e: BadRequestException){
                call.respond(HttpStatusCode.BadRequest, "Malformed JSON payload! Ascertain JSON payload src.")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, "Could not create new member: ${e.localizedMessage}")
            }
        }
        delete("/delete_member/{member_number}"){
            try {
                val redMemberNumber = call.parameters["member_number"]

                if (redMemberNumber == null) call.respond(HttpStatusCode.BadRequest)
                memberService.deleteMember(redMemberNumber.toString())
                call.respond(HttpStatusCode.OK, "Member: $redMemberNumber deleted successfully!")
            }catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Encountered error while deleting member: ${e.message}")
            }

        }
    }
}