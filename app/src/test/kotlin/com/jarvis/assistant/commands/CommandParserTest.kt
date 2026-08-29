package com.jarvis.assistant.commands

import android.content.Context
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.mock

class CommandParserTest {

    private lateinit var parser: CommandParser
    private lateinit var mockContext: Context

    @Before
    fun setUp() {
        mockContext = mock(Context::class.java)
        parser = CommandParser(mockContext)
    }

    @Test
    fun testParseCallCommand() {
        val command = parser.parseCommand("Call John")
        assertTrue(command is CallCommand)
        assertEquals("John", (command as CallCommand).contactName)
    }

    @Test
    fun testParseCallCommandLowercase() {
        val command = parser.parseCommand("call mom")
        assertTrue(command is CallCommand)
        assertEquals("mom", (command as CallCommand).contactName)
    }

    @Test
    fun testParseTextCommand() {
        val command = parser.parseCommand("Text Sarah")
        assertTrue(command is TextCommand)
        assertEquals("Sarah", (command as TextCommand).contactName)
    }

    @Test
    fun testParseAlarmCommand() {
        val command = parser.parseCommand("Set a timer for 10 minutes")
        assertTrue(command is AlarmCommand)
        val alarm = command as AlarmCommand
        assertEquals(10, alarm.duration)
        assertEquals("minutes", alarm.unit)
    }

    @Test
    fun testParseAppCommand() {
        val command = parser.parseCommand("Open YouTube")
        assertTrue(command is AppCommand)
        assertEquals("YouTube", (command as AppCommand).appName)
    }

    @Test
    fun testParseWeatherCommand() {
        val command = parser.parseCommand("What's the weather")
        assertTrue(command is WeatherCommand)
    }

    @Test
    fun testParseSearchCommand() {
        val command = parser.parseCommand("Search for pizza")
        assertTrue(command is SearchCommand)
        assertEquals("for pizza", (command as SearchCommand).query)
    }

    @Test
    fun testParseUnknownCommand() {
        val command = parser.parseCommand("xyz123")
        assertNull(command)
    }
}
