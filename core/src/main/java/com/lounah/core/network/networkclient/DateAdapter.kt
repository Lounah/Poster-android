package com.lounah.core.network.networkclient

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.internal.bind.util.ISO8601Utils
import com.lounah.core.logger.Logger
import java.lang.reflect.Type
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * We convert date like this <code>yyyy-MM-dd'T'HH:mm:ssZZZZZ</code>
 *
 * Ex.: <code>2018-10-21T12:00:00Z</code>
 */
class DateAdapter : JsonDeserializer<Date>, JsonSerializer<Date> {

    companion object {
        const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
    }

    private val dateFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
        if (json == null) return null
        try {
            return stringToDate(json.asJsonPrimitive.asString)
        } catch (e: ParseException) {
            throw JsonParseException(e.message, e)
        }
    }

    override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
        return src?.let { JsonPrimitive(dateToString(src)) }
    }

    fun stringToDate(date: String?): Date? {
        if (date == null) return null

        try {
            return ISO8601Utils.parse(date, ParsePosition(0))
        } catch (ignored: ParseException) {
            Logger.e(ignored)
        }

        return try {
            return dateFormat.parse(date)
        } catch (e: ParseException) {
            Logger.e(e)
            null
        }
    }

    fun dateToString(date: Date?): String? {
        if (date == null) return null
        return dateFormat.format(date)
    }
}