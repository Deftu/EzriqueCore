import dev.deftu.gradle.utils.getDgtPublishingPassword
import dev.deftu.gradle.utils.getDgtPublishingUsername

plugins {
    java
    kotlin("jvm") version("2.0.0")
    val dgt = "2.2.3"
    id("dev.deftu.gradle.tools") version(dgt)
    id("dev.deftu.gradle.tools.publishing.maven") version(dgt)
}

dependencies {
    // Discord
    api("dev.kord:kord-core:${libs.versions.kord.get()}")

    // Data handling
    api("com.google.code.gson:gson:${libs.versions.gson.get()}")
    api("xyz.deftu:enhancedeventbus:${libs.versions.enhancedeventbus.get()}")
    api("com.squareup.okhttp3:okhttp:${libs.versions.okhttp.get()}")

    // SQL
    api("org.postgresql:postgresql:${libs.versions.postgres.get()}")
    api("org.jetbrains.exposed:exposed-core:${libs.versions.exposed.get()}")
    api("org.jetbrains.exposed:exposed-dao:${libs.versions.exposed.get()}")
    api("org.jetbrains.exposed:exposed-jdbc:${libs.versions.exposed.get()}")

    // Error handling
    api("io.sentry:sentry:${libs.versions.sentry.get()}")

    // Logging
    api("org.apache.logging.log4j:log4j-api:${libs.versions.log4j.get()}")
    api("org.apache.logging.log4j:log4j-core:${libs.versions.log4j.get()}")
    api("org.apache.logging.log4j:log4j-slf4j-impl:${libs.versions.log4j.get()}")
    api("org.apache.logging.log4j:log4j-slf4j2-impl:${libs.versions.log4j.get()}")
}

publishing {
    repositories {
        val publishingUsername = getDgtPublishingUsername()
        val publishingPassword = getDgtPublishingPassword()
        if (publishingUsername != null && publishingPassword != null) {
            fun MavenArtifactRepository.applyCredentials() {
                authentication.create<BasicAuthentication>("basic")
                credentials {
                    username = publishingUsername
                    password = publishingPassword
                }
            }

            maven {
                name = "DeftuInternalExposed"
                url = uri("https://maven.deftu.dev/internal-exposed")
                applyCredentials()
            }
        }
    }
}
