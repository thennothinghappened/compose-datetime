import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    dependencies {
        classpath("com.karumi:shot:${property("shot.version")}")
    }
}

plugins {
    kotlin("jvm").apply(false)
    kotlin("multiplatform").apply(false)
    kotlin("android").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)
    id("org.jetbrains.dokka")
    id("com.diffplug.spotless").version("6.0.4")
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(rootProject.file("docs/api"))
}

val githubProperties = java.util.Properties()
try {
    githubProperties.load(FileInputStream(rootProject.file("github.properties")))
} catch (ex: Exception) {
    ex.printStackTrace()
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/Qawaz/compose-datetime")
            try {
                credentials {
                    username = (githubProperties["gpr.usr"] ?: System.getenv("GPR_USER")).toString()
                    password = (githubProperties["gpr.key"] ?: System.getenv("GPR_API_KEY")).toString()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
                "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-Xopt-in=androidx.compose.ui.test.ExperimentalTestApi",
                "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                "-Xopt-in=com.google.accompanist.pager.ExperimentalPagerApi"
            )
        }
    }
}

subprojects {
    plugins.apply("com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            ktlint(property("ktlint.version") as String)
        }
    }

    tasks.withType<Test> {
        testLogging {
            showStandardStreams = true
        }
    }
}