plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}
dependencies {
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.config.yaml)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.netty)
    implementation(libs.exposed.core)
    implementation(libs.logback.classic)
    implementation("org.jetbrains.exposed:exposed-core:1.6.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:1.6.0")
    implementation("org.jetbrains.exposed:exposed-dao:1.6.0")
    implementation("com.mysql:mysql-connector-j:26.7.0")
    implementation("com.zaxxer:HikariCP:6.0.0")
    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}

configurations.all {
    resolutionStrategy {
        eachDependency {
            if (requested.group == "com.google.protobuf" && requested.name == "protobuf-java") {
                // Force upgrade to the secure, patched version line
                useVersion("3.25.5")
                because("Fixes CVE-2024-7254 DoS StackOverflow vulnerability")
            }
        }
    }
}
