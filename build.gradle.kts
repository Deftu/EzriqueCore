import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    kotlin("jvm") version("1.9.10")
    val dgt = "1.22.4"
    id("dev.deftu.gradle.tools") version(dgt)
    id("dev.deftu.gradle.tools.maven-publishing") version(dgt)
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
}

publishing {
    repositories {
        fun getPublishingUsername(): String? {
            val property = project.findProperty("deftu.publishing.username")
            return property?.toString() ?: System.getenv("DEFTU_PUBLISHING_USERNAME")
        }

        fun getPublishingPassword(): String? {
            val property = project.findProperty("deftu.publishing.password")
            return property?.toString() ?: System.getenv("DEFTU_PUBLISHING_PASSWORD")
        }

        val publishingUsername = getPublishingUsername()
        val publishingPassword = getPublishingPassword()
        if (publishingUsername != null && publishingPassword != null) {
            fun MavenArtifactRepository.applyCredentials() {
                authentication.create<BasicAuthentication>("basic")
                credentials {
                    username = publishingUsername
                    password = publishingPassword
                }
            }

            maven {
                name = "DeftuInternal"
                url = uri("https://maven.deftu.dev/internal")
                applyCredentials()
            }
        }
    }
}
