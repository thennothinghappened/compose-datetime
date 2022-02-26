import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream

plugins {
    id("com.diffplug.spotless") version "6.0.4"
    id("org.jetbrains.dokka") version ProjectConfig.DokkaVersion
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Kotlin.gradlePlugin)
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.17.0")
        classpath(Dependencies.Shot.core)
    }
}

val githubProperties = java.util.Properties()
runCatching { githubProperties.load(FileInputStream(rootProject.file("github.properties"))) }

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/codeckle/compose-datetime")

            runCatching {
                credentials {
                    /**Create github.properties in root project folder file with gpr.usr=GITHUB_USER_ID  & gpr.key=PERSONAL_ACCESS_TOKEN**/
                    username = (githubProperties["gpr.usr"] ?: System.getenv("GPR_USER")).toString()
                    password = (githubProperties["gpr.key"] ?: System.getenv("GPR_API_KEY")).toString()
                }
            }.onFailure { it.printStackTrace() }
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

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(rootProject.file("docs/api"))
}

subprojects {
    plugins.apply("com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            ktlint(Dependencies.Ktlint.version)
        }
    }

    tasks.withType<Test> {
        testLogging {
            showStandardStreams = true
        }
    }
}