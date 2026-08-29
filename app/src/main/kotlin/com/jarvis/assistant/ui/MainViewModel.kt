package com.jarvis.assistant.ui

import android.app.Application
import android.speech.SpeechRecognizer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jarvis.assistant.voice.VoiceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val voiceManager = VoiceManager(application)

    private val _responseText = MutableLiveData<String>()
    val responseText: LiveData<String> = _responseText

    private val _listeningState = MutableLiveData<Boolean>(false)
    val listeningState: LiveData<Boolean> = _listeningState

    fun startListening() {
        _listeningState.value = true
        voiceManager.startListening { result ->
            _listeningState.value = false
            processVoiceInput(result)
        }
    }

    private fun processVoiceInput(input: String) {
        _responseText.value = "Processing: $input"
        GlobalScope.launch(Dispatchers.Main) {
            _responseText.value = "JARVIS: I understood '$input'"
        }
    }
}
