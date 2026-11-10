import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.v1.jdbc.transactions.suspendTransaction

@Serializable
data class Members(val name: String, val phoneNumber: String, val memberNumber: String)

object MembersTable: Table("zion_elders_members"){
    val memberID = integer("member_id").autoIncrement()
    val memberNumber = varchar("member_number", 20)
    val memberName = varchar("member_name", 30)
    val memberPhoneNumber = varchar("member_phone_number", 10)

    override val primaryKey: PrimaryKey = PrimaryKey(memberID)
}

object DatabaseFactory {
    fun init() {
        val config = HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = "jdbc:mysql://localhost:3306/zion_elders_db?useSSL=false&serverTimezone=UTC"
            username = "root"
            password = "Ianmwendwa8435!!"
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        Database.connect(HikariDataSource(config))
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
//        suspendTransaction { block() }
        newSuspendedTransaction(Dispatchers.IO) { block() }

}

// Member CRUD service
class MemberService {
    suspend fun createMember(name: String, phoneNumber: String, memberNumber: String): Int = DatabaseFactory.dbQuery{
        MembersTable.insert {
            it[MembersTable.memberName] = name
            it[MembersTable.memberPhoneNumber] = phoneNumber
            it[MembersTable.memberNumber] = memberNumber
        } [MembersTable.memberID]
    }
    suspend fun getAllMembers(): List<Members?> = DatabaseFactory.dbQuery{
        return@dbQuery MembersTable.selectAll().map {
            Members(
                it[MembersTable.memberName],
                it[MembersTable.memberPhoneNumber],
                it[MembersTable.memberNumber]
            )

        }
    }
}
