package dev.deftu.ezrique

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File

val configFile: File
    get() {
        var fileName = System.getenv("CONFIG")
        fileName = if (fileName == null || fileName.isEmpty()) {
            "config.json"
        } else if (!fileName.endsWith(".json")) "$fileName.json" else fileName

        return File(fileName)
    }

private var configJson: JsonObject? = null
    get() {
        if (field == null) {
            config()
        }

        return field
    }

val config: JsonObject
    get() = configJson!!

val token: String
    get() {
        var token = System.getenv("TOKEN")
        if (token == null || token.isEmpty()) {
            token = config.get("token")?.asString
            if (token == null || token.isEmpty()) error("No token provided!")
        }

        return token
    }

private fun config() {
    if (!configFile.exists()) error("Config file \"${configFile.absolutePath}\" does not exist!")

    val text = configFile.readText()
    val json = JsonParser.parseString(text)
    if (!json.isJsonObject) error("Config file \"${configFile.absolutePath}\" is not a valid JSON object!")

    configJson = json.asJsonObject
}
