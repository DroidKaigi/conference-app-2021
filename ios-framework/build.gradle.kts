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
        iosArm64(),
        iosX64("ios")
    )
    iosTargets.forEach {
        it.binaries {
            framework {
                baseName = "DroidKaigiMPP"
                export(projects.model)
                export(projects.data.repository)
                export(Dep.datetime)
                linkerOpts.add("-lsqlite3")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.model)
                implementation(projects.data.api)
                implementation(projects.data.db)
                api(projects.data.repository)

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
        val iosMain by getting {
            dependencies {
                implementation(Dep.Koin.core)
            }
        }
        val iosArm64Main by getting {
            dependsOn(iosMain)
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
