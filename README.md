# JARVIS - Android Personal Voice Assistant

A feature-complete Android voice assistant using native Android architecture, supporting wake-word detection, voice commands, and integration with device features.

## Features

- **Voice Interaction**: "Hey JARVIS" wake-word and natural conversation
- **Phone Integration**: Make calls, answer/end calls, send SMS
- **Device Control**: Open apps, set alarms/timers, control media
- **Information**: Weather, web search, calendar, reminders
- **Navigation**: Maps integration via intents
- **Settings**: Voice customization, permissions, privacy controls
- **Multi-turn Conversations**: Context-aware command understanding
- **Futuristic UI**: Lightweight, responsive design

## Architecture

- **Kotlin & Modern Android**: Coroutines, LiveData, MVVM pattern
- **VoiceInteractionService**: Native Android voice assistant framework
- **AI Provider Interface**: Abstracted AI backend (no hardcoded keys)
- **Modular Structure**: Separate UI, voice, AI, command, phone, SMS, contacts, settings layers

## Building

### Prerequisites
- Android Studio Flamingo or later
- Android SDK 31+ (API 31, compileSdk 34)
- Kotlin 1.9.20+
- Gradle 8.2+

### Setup

1. Clone the repository
2. Create `local.properties` with your Android SDK path:
   ```
   sdk.dir=/path/to/android/sdk
   ```
3. Configure AI provider via Environment variables or settings:
   - `JARVIS_AI_PROVIDER`: API provider (e.g., openai, anthropic, groq)
   - `JARVIS_AI_KEY`: API key (set in runtime, not in source)

### Build Debug APK

```bash
./gradlew assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

### Build Release APK

```bash
./gradlew assembleRelease
```

## GitHub Actions

Automated builds are available via `.github/workflows/build.yml`:
- Builds debug APK on each push
- Artifact: `JARVIS-debug-apk`

## Permissions Required

- `RECORD_AUDIO`: Voice input
- `CALL_PHONE`: Make calls
- `SEND_SMS`: Send messages
- `READ_CONTACTS`: Contact lookup
- `READ_CALENDAR`: Calendar access
- `ACCESS_FINE_LOCATION`: Navigation
- `SCHEDULE_EXACT_ALARM`: Alarms/timers
- `INTERNET`: AI API and web services
- `MODIFY_AUDIO_SETTINGS`: Volume/sound control
- `ANSWER_PHONE_CALLS`: Answer incoming calls

## Android Restrictions

- **Background Execution**: Limited by Doze, App Standby; wake-word listening requires VoiceInteractionService
- **Lock Screen**: Only system assistant can perform actions on lock screen
- **SMS Sending**: Requires user confirmation for non-default SMS app
- **Call Screening**: Android 10+ requires call screening service
- **Permissions**: Must request at runtime (Android 6+)

## Configuration

1. **AI Provider Setup** (at runtime):
   - Navigate to Settings > AI Provider
   - Select provider (OpenAI, Anthropic, Groq, etc.)
   - Enter API key securely
   - Test connection

2. **Voice Settings**:
   - Wake word customization
   - Speech recognition language
   - TTS voice and speed

3. **Permissions**:
   - All permissions are optional and gracefully handled
   - User prompted for permissions on first use

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/jarvis/assistant/
│   │   │       ├── ui/              # UI screens and fragments
│   │   │       ├── voice/           # Speech recognition & TTS
│   │   │       ├── ai/              # AI provider interface
│   │   │       ├── commands/        # Command parsing & execution
│   │   │       ├── phone/           # Phone call integration
│   │   │       ├── sms/             # SMS integration
│   │   │       ├── contacts/        # Contacts management
│   │   │       ├── settings/        # Settings & preferences
│   │   │       ├── services/        # VoiceInteractionService, etc.
│   │   │       ├── util/            # Utilities & helpers
│   │   │       └── MainActivity.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   ├── drawable/
│   │   │   └── menu/
│   │   └── AndroidManifest.xml
│   └── test/
│       └── kotlin/                  # Unit tests
├── build.gradle.kts
└── proguard-rules.pro
```

## Testing

Run unit tests:
```bash
./gradlew test
```

Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## Installation

### From APK

1. Enable "Install from Unknown Sources" in Settings
2. Download `JARVIS-debug-apk.apk`
3. Tap to install

### From Android Studio

```bash
./gradlew installDebug
```

Or:
1. Build > Build Bundle(s)/APK(s) > Build APK(s)
2. Run > Run app

## Troubleshooting

### Build Errors
- Ensure `local.properties` has correct SDK path
- Run `./gradlew clean` before rebuilding
- Check Kotlin version matches (1.9.20)

### Runtime Issues
- Verify all permissions are granted
- Check Android version (API 31+)
- Enable Developer Mode for debugging

### Voice Recognition Not Working
- Ensure Google Mobile Services (GMS) is installed
- Check Android Language settings
- Verify internet connection

## Privacy & Security

- AI API keys are NOT stored in source code
- Keys configured at runtime in Settings
- All requests use HTTPS
- Permissions are request-based, not batch-granted
- Respects Android security model completely

## License

MIT License - See LICENSE file

## Contributing

Issues and pull requests welcome. Please follow Kotlin style guide and add tests for new features.

## Roadmap

- [ ] Custom wake word training
- [ ] Offline speech recognition
- [ ] Multiple device sync
- [ ] Gesture control
- [ ] Advanced context memory
- [ ] Multi-language support

---

Built with ❤️ for Android enthusiasts and privacy-conscious users.
