app/proguard-rules.pro
# ProGuard rules for JARVIS
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.jarvis.** { *; }
-keep interface com.jarvis.** { *; }
-keep enum com.jarvis.** { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepnames class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
