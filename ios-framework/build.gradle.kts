import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import io.github.droidkaigi.feeder.Dep

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

apply(rootProject.file("gradle/android.gradle"))

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {
        binaries {
            framework {
                baseName = "Shared"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":model"))
                implementation(project(":data:api"))
                implementation(project(":data:db"))
                implementation(project(":data:repository"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Dep.Koin.core)
            }
        }
        val iosTest by getting
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>("ios").binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)

// Workaround for issues where types defined in iOS native code cannot be referenced in Android Studio
tasks.getByName("preBuild").dependsOn(tasks.getByName("iosMainKlibrary"))
