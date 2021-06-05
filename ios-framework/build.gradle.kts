import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import io.github.droidkaigi.feeder.Dep
import io.github.droidkaigi.feeder.Versions

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}

apply(rootProject.file("gradle/android.gradle"))

kotlin {
    android()

    val iosTargets = listOf(
        iosArm64("ios"),
        iosX64()
    )
    iosTargets.forEach {
        it.binaries {
            framework {
                baseName = "DroidKaigiMPP"
                export(project(":model"))
                export(project(":data:repository"))
                linkerOpts.add("-lsqlite3")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":model"))
                implementation(project(":data:api"))
                implementation(project(":data:db"))
                api(project(":data:repository"))

                implementation(Dep.Coroutines.bom)
                implementation(Dep.Coroutines.core) {
                    version {
                        strictly(Versions.coroutines)
                    }
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val iosX64Main by getting
        val iosMain by getting {
            dependencies {
                implementation(Dep.Koin.core)
            }
            iosX64Main.dependsOn(this)
        }
        val iosTest by getting
    }
}

// Workaround for issues where types defined in iOS native code cannot be referenced in Android Studio
tasks.getByName("preBuild").dependsOn(tasks.getByName("compileKotlinIos"))

multiplatformSwiftPackage {
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("14") }
    }
    outputDirectory(File(projectDir, "../ios/build/xcframeworks"))
    buildConfiguration {
        if (System.getenv("CONFIGURATION") != "Release") {
            debug()
        } else {
            release()
        }
    }
}
