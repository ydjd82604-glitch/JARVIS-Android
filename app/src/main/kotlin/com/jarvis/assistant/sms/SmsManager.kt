package com.jarvis.assistant.sms

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager

class SmsManager(private val context: Context) {

    fun canSendSms(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun sendSms(phoneNumber: String, message: String) {
        if (!canSendSms()) {
            return // Permission not granted
        }
        
        // Use Intent to launch SMS app (compliant with Android restrictions)
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phoneNumber")
            putExtra("sms_body", message)
        }
        ContextCompat.startActivity(context, intent, null)
    }
}
