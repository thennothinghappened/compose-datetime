import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    kotlin("jvm").apply(false)
    kotlin("multiplatform").apply(false)
    kotlin("android").apply(false)
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    id("org.jetbrains.compose").apply(false)
}


allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
//        maven {
//            name = "GithubPackages"
//            url = uri("https://maven.pkg.github.com/Qawaz/compose-datetime")
//            credentials {
//                username = (System.getenv("GPR_USER"))!!.toString()
//                password = (System.getenv("GPR_API_KEY"))!!.toString()
//            }
//        }
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
    tasks.withType<Test> {
        testLogging {
            showStandardStreams = true
        }
    }
}