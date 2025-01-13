plugins {
    java
    kotlin("jvm") version("2.0.0")
    val dgt = "2.6.0"
    id("dev.deftu.gradle.tools") version(dgt)
    id("dev.deftu.gradle.tools.bloom") version(dgt)
    id("dev.deftu.gradle.tools.publishing.maven") version(dgt)
}

dependencies {
    // Discord
    api("dev.kord:kord-core:${libs.versions.kord.get()}")

    // Data handling
    api("com.google.code.gson:gson:${libs.versions.gson.get()}")
    api("com.squareup.okhttp3:okhttp:${libs.versions.okhttp.get()}")
    api("redis.clients:jedis:${libs.versions.jedis.get()}")

    // Healthcheck server
    api("io.ktor:ktor-server-core:${libs.versions.ktor.get()}")
    api("io.ktor:ktor-server-netty:${libs.versions.ktor.get()}")

    // Error handling
    api("io.sentry:sentry:${libs.versions.sentry.get()}")

    // Logging
    api("org.apache.logging.log4j:log4j-api:${libs.versions.log4j.get()}")
    api("org.apache.logging.log4j:log4j-core:${libs.versions.log4j.get()}")
    api("org.apache.logging.log4j:log4j-slf4j-impl:${libs.versions.log4j.get()}")
    api("org.apache.logging.log4j:log4j-slf4j2-impl:${libs.versions.log4j.get()}")
}
