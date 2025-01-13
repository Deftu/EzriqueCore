package dev.deftu.ezrique

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dev.kord.common.entity.Snowflake

fun String.parseJson(): JsonElement {
    return JsonParser.parseString(this)
}

fun isValidJson(json: String): Boolean {
    return try {
        json.parseJson()
        true
    } catch (e: Exception) {
        false
    }
}

val JsonElement?.isJsonObject: Boolean
    get() = this != null && isJsonObject

fun buildJsonObject(init: JsonObject.() -> Unit): JsonObject {
    val obj = JsonObject()
    obj.init()
    return obj
}

fun JsonObject.add(key: String, value: String?) {
    addProperty(key, value)
}

fun JsonObject.add(key: String, value: Number?) {
    addProperty(key, value)
}

fun JsonObject.add(key: String, value: Boolean?) {
    addProperty(key, value)
}

fun buildJsonArray(init: JsonArray.() -> Unit): JsonArray {
    val array = JsonArray()
    array.init()
    return array
}

fun JsonObject.isNull(key: String): Boolean {
    return has(key) && get(key).isJsonNull
}

fun JsonObject.maybeGet(key: String): JsonElement? {
    return if (has(key)) get(key) else null
}

fun JsonObject.maybeGetString(key: String): String? {
    val element = maybeGet(key)
    return if (element != null && element.isJsonPrimitive) element.asString else null
}

fun JsonObject.maybeGetInteger(key: String): Int? {
    val element = maybeGet(key)
    return if (element != null && element.isJsonPrimitive) element.asInt else null
}

fun JsonObject.maybeGetLong(key: String): Long? {
    val element = maybeGet(key)
    return if (element != null && element.isJsonPrimitive) element.asLong else null
}

fun JsonObject.maybeGetSnowflake(key: String): Snowflake? {
    val element = maybeGet(key)
    return if (element != null && element.isJsonPrimitive) Snowflake(element.asLong) else null
}

fun JsonObject.maybeGetBoolean(key: String): Boolean? {
    val element = maybeGet(key)
    return if (element != null && element.isJsonPrimitive) element.asBoolean else null
}

fun JsonObject.maybeGetJsonObject(key: String): JsonObject? {
    val element = maybeGet(key)
    return if (element != null && element.isJsonObject) element.asJsonObject else null
}

fun JsonObject.maybeGetJsonArray(key: String): JsonArray? {
    val element = maybeGet(key)
    return if (element != null && element.isJsonArray) element.asJsonArray else null
}
