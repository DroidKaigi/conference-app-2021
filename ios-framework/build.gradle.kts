import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import io.github.droidkaigi.feeder.Dep
import io.github.droidkaigi.feeder.Versions
import java.io.File

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

apply(rootProject.file("gradle/android.gradle"))

kotlin {
    android()

    val xcf = XCFramework("DroidKaigiMPP")
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
                xcf.add(this)
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

task("createXCFramework") {
    this.dependsOn(tasks.getByName("assembleDroidKaigiMPPXCFramework"))
    this.doLast {
        val buildDir = tasks.getByName("assembleDroidKaigiMPPXCFramework").project.buildDir.absolutePath
        val outputFile = File("$buildDir/XCFrameworks/debug/DroidKaigiMPP.xcframework")
        val targetFile = File("$buildDir/../../ios/build/xcframeworks/DroidKaigiMPP.xcframework")
        outputFile.copyRecursively(target = targetFile)
    }
}
