package com.jarvis.assistant

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.jarvis.assistant.databinding.ActivityMainBinding
import com.jarvis.assistant.ui.MainViewModel
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var speechRecognizer: SpeechRecognizer? = null
    private val requestCodeAudioPermission = 1001
    private val requestCodeAllPermissions = 1002

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Check and request permissions
        if (!allPermissionsGranted()) {
            requestPermissions()
        }

        // Initialize Speech Recognizer
        initializeSpeechRecognizer()

        // Set up UI listeners
        binding.micButton.setOnClickListener {
            startListening()
        }

        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, com.jarvis.assistant.ui.SettingsActivity::class.java))
        }

        // Observe view model
        viewModel.responseText.observe(this) { response ->
            binding.responseText.text = response
        }

        viewModel.listeningState.observe(this) { isListening ->
            updateListeningUI(isListening)
        }
    }

    private fun allPermissionsGranted(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET
        )
        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.INTERNET,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SCHEDULE_EXACT_ALARM,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.POST_NOTIFICATIONS
            } else {
                null
            }
        ).filterNotNull().toTypedArray()

        ActivityCompat.requestPermissions(this, permissions, requestCodeAllPermissions)
    }

    private fun initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        }
    }

    private fun startListening() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                requestCodeAudioPermission
            )
            return
        }

        viewModel.startListening()
    }

    private fun updateListeningUI(isListening: Boolean) {
        binding.micButton.isEnabled = !isListening
        binding.micButton.alpha = if (isListening) 0.5f else 1.0f
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            requestCodeAudioPermission -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startListening()
                }
            }
            requestCodeAllPermissions -> {
                // Permissions handled, proceed
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer?.destroy()
    }
}
