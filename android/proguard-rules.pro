# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# kotlinx serialization
-keepattributes InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class io.github.droidkaigi.feeder.data.request.**$$serializer { *; }
-keepclassmembers class io.github.droidkaigi.feeder.data.request.** {
    *** Companion;
}
-keepclasseswithmembers class io.github.droidkaigi.feeder.data.request.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class io.github.droidkaigi.feeder.data.response.**$$serializer { *; }
-keepclassmembers class io.github.droidkaigi.feeder.data.response.** {
    *** Companion;
}
-keepclasseswithmembers class io.github.droidkaigi.feeder.data.response.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class io.github.droidkaigi.feeder.data.session.response.**$$serializer { *; }
-keepclassmembers class io.github.droidkaigi.feeder.data.session.response.** {
    *** Companion;
}
-keepclasseswithmembers class io.github.droidkaigi.feeder.data.session.response.** {
    kotlinx.serialization.KSerializer serializer(...);
}
