package com.jarvis.assistant.phone

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager

class PhoneManager(private val context: Context) {

    fun canMakeCall(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun makeCall(phoneNumber: String) {
        if (!canMakeCall()) {
            return // Permission not granted
        }
        
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        ContextCompat.startActivity(context, intent, null)
    }

    fun endCall() {
        // EndCall requires MODIFY_PHONE_STATE permission which is system-only
        // Use ANSWER_PHONE_CALLS permission for answering incoming calls via Intent
    }
}
