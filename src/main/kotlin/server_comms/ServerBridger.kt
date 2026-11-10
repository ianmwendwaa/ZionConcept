package server_comms

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.*
import models.NewMemberData
import io.ktor.client.plugins.contentnegotiation.* // 👈 Make sure to import this
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*

class ServerBridger {
    private val client = HttpClient(CIO){
        // So that the server can explicitly identify the payload as JSON format
        install(ContentNegotiation){
            json()
        }
    }
    suspend fun postNewMemberData(newMemberData: NewMemberData) {
        val response = client.post("http://127.0.0.1:8080/members") {
            // So I actually don't need to serialize the payload. Let the server deserialize it automatically.
            contentType(ContentType.Application.Json)
            setBody(newMemberData)
        }
        println("Server response status: ${response.status}")
        println("Server response body: ${response.bodyAsText()}")
    }
    suspend fun fetchAllMembers(): List<NewMemberData> {
        return try {
            val response = client.get("http://127.0.0.1:8080/all_members")

            if (response.status == HttpStatusCode.OK) {
                // Automatically deserialize the server's JSON output into a standard List type object
                response.body<List<NewMemberData>>()
            } else {
                emptyList()
            }
        }catch (e: Exception){
            println("Encountered error while fetching members: ${e.localizedMessage}")
            emptyList()
        }
    }
    suspend fun deleteMember(memberNumber: String) {
        val response = client.delete("http://127.0.0.1:8080/delete_member/$memberNumber")

        println("Server responded: ${response.bodyAsText()}")
    }
}
