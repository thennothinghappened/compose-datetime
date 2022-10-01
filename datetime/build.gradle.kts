import java.util.Properties
import java.io.FileInputStream

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    id("maven-publish")
    id("shot")
    id("org.jetbrains.dokka")
}

group = "com.wakaztahir"
version = "1.0.5"

kotlin {
    android {
        publishLibraryVariants("release")
    }
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("com.wakaztahir.accompanist:pager:0.26.5-beta")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {

            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
//                api(compose.preview)
            }
        }
        val desktopTest by getting
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "com.karumi.shot.ShotTestRunner"
        testApplicationId = "com.wakaztahir.composematerialdialogs.test"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    dependencies {
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    }
}

shot {
    tolerance = 1.0 // Tolerance needed for CI
}

val githubProperties = Properties()
try {
    githubProperties.load(FileInputStream(rootProject.file("github.properties")))
} catch (ex: Exception) {
    ex.printStackTrace()
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "GithubPackages"
                url = uri("https://maven.pkg.github.com/Qawaz/compose-datetime")
                try {
                    credentials {
                        username = (githubProperties["gpr.usr"] ?: System.getenv("GPR_USER")).toString()
                        password = (githubProperties["gpr.key"] ?: System.getenv("GPR_API_KEY")).toString()
                    }
                }catch(ex : Exception){
                    ex.printStackTrace()
                }
            }
        }
    }
}