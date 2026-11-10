import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.v1.jdbc.Database

class DatabaseInit {
    fun initDb(){
        val config = HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = "jdbc:mysql://localhost:3306/elders_db?useSSL=false&allowPublicKeyRetrieval=true"
            username = "root"
            password = "Ianmwendwa8435!!"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        val dataSrc = HikariDataSource(config)
        Database.connect(dataSrc)
    }
}