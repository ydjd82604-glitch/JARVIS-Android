package com.jarvis.assistant.commands

import android.content.Context
import android.util.Log

class CommandParser(private val context: Context) {

    fun parseCommand(input: String): Command? {
        val lowerInput = input.lowercase().trim()

        return when {
            isCallCommand(lowerInput) -> parseCallCommand(input)
            isTextCommand(lowerInput) -> parseTextCommand(input)
            isAlarmCommand(lowerInput) -> parseAlarmCommand(input)
            isAppCommand(lowerInput) -> parseAppCommand(input)
            isWeatherCommand(lowerInput) -> parseWeatherCommand(input)
            isSearchCommand(lowerInput) -> parseSearchCommand(input)
            else -> null
        }
    }

    private fun isCallCommand(input: String): Boolean {
        return input.contains("call") || input.contains("phone")
    }

    private fun isTextCommand(input: String): Boolean {
        return input.contains("text") || input.contains("message") || input.contains("sms")
    }

    private fun isAlarmCommand(input: String): Boolean {
        return input.contains("alarm") || input.contains("timer")
    }

    private fun isAppCommand(input: String): Boolean {
        return input.contains("open") || input.contains("launch")
    }

    private fun isWeatherCommand(input: String): Boolean {
        return input.contains("weather") || input.contains("temperature") || input.contains("forecast")
    }

    private fun isSearchCommand(input: String): Boolean {
        return input.contains("search") || input.contains("find") || input.contains("look up")
    }

    private fun parseCallCommand(input: String): CallCommand? {
        val contactName = extractContactName(input)
        return if (contactName.isNotEmpty()) CallCommand(contactName) else null
    }

    private fun parseTextCommand(input: String): TextCommand? {
        val contactName = extractContactName(input)
        return if (contactName.isNotEmpty()) TextCommand(contactName, "") else null
    }

    private fun parseAlarmCommand(input: String): AlarmCommand? {
        val timePattern = "(\\d+)\\s*(minute|second|hour)s?".toRegex()
        val match = timePattern.find(input)
        return if (match != null) {
            val time = match.groupValues[1].toIntOrNull() ?: 1
            val unit = match.groupValues[2]
            AlarmCommand(time, unit)
        } else null
    }

    private fun parseAppCommand(input: String): AppCommand? {
        val parts = input.split("\\s+".toRegex())
        val appName = if (parts.size > 1) parts.drop(1).joinToString(" ") else ""
        return if (appName.isNotEmpty()) AppCommand(appName) else null
    }

    private fun parseWeatherCommand(input: String): WeatherCommand? {
        return WeatherCommand()
    }

    private fun parseSearchCommand(input: String): SearchCommand? {
        val parts = input.split("\\s+".toRegex())
        val query = if (parts.size > 1) parts.drop(1).joinToString(" ") else ""
        return if (query.isNotEmpty()) SearchCommand(query) else null
    }

    private fun extractContactName(input: String): String {
        val parts = input.split("\\s+".toRegex())
        return if (parts.size > 1) parts.drop(1).joinToString(" ") else ""
    }
}

sealed class Command
data class CallCommand(val contactName: String) : Command()
data class TextCommand(val contactName: String, val message: String) : Command()
data class AlarmCommand(val duration: Int, val unit: String) : Command()
data class AppCommand(val appName: String) : Command()
data class WeatherCommand(val location: String = "current") : Command()
data class SearchCommand(val query: String) : Command()
