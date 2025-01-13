package dev.deftu.ezrique.backend

import dev.deftu.ezrique.buildJsonArray
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

open class BackendClient(
    protected val baseUrl: String,
    private val token: String,

    private val name: String,
    private val version: String
) {

    companion object {

        val emptyBody = ByteArray(0).toRequestBody(null)

        val jsonContentType = "application/json; charset=utf-8".toMediaType()

    }

    protected val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                .addHeader("User-Agent", "$name/$version")
                .addHeader("Authorization", "Bot $token")
                .build())
        }.build()

    fun start() {
        httpClient.newCall(
            Request.Builder()
                .url("${baseUrl}/bots/startup")
                .post(emptyBody)
                .build()
        ).execute()
    }

    fun shutdown() {
        httpClient.newCall(
            Request.Builder()
                .url("${baseUrl}/bots/shutdown")
                .post(emptyBody)
                .build()
        ).execute()
    }

    fun setupGuilds(guilds: Set<BackendGuild>) {
        val body = buildJsonArray {
            guilds.map(BackendGuild::toJson).forEach(::add)
        }.toString()
        httpClient.newCall(
            Request.Builder()
                .url("${baseUrl}/bots/guilds")
                .post(body.toRequestBody(jsonContentType))
                .build()
        ).execute()
    }

    fun setupGuild(guild: BackendGuild) {
        setupGuilds(setOf(guild))
    }

}
