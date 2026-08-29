package com.jarvis.assistant.services

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

// Removed: JarvisVoiceInteractionService - Not compatible with simple voice apps
// Use VoiceManager's native SpeechRecognizer instead

// Removed: JarvisInCallService - Requires special permissions and telephony expertise
// For simple call making, use PhoneManager with ACTION_CALL Intent
