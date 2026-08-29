package com.jarvis.assistant.services

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.voice.VoiceInteractionService
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.S)
class JarvisVoiceInteractionService : VoiceInteractionService() {

    companion object {
        private const val TAG = "JarvisVIS"
    }

    override fun onReady() {
        super.onReady()
        Log.d(TAG, "Voice Interaction Service Ready")
    }

    override fun onVoiceActivated(reason: Int) {
        Log.d(TAG, "Voice activated with reason: $reason")
        val intent = Intent(this, com.jarvis.assistant.MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    override fun onCancelVoiceInteraction() {
        Log.d(TAG, "Voice interaction cancelled")
    }

    override fun onTaskStarted(intent: Intent?, taskId: Int) {
        Log.d(TAG, "Task started: $taskId")
    }

    override fun onTaskFinished(intent: Intent?, taskId: Int) {
        Log.d(TAG, "Task finished: $taskId")
    }
}
