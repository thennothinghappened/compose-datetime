plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.wakaztahir.android"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packagingOptions.excludes.addAll(
        listOf(
            "META-INF/LICENSE",
            "META-INF/AL2.0",
            "META-INF/**",
        )
    )
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":demo:common"))
    implementation(compose.ui)
    implementation(compose.material)
    implementation(compose.foundation)
    implementation(compose.materialIconsExtended)
    implementation(compose.animation)

    implementation("com.google.android.material:material:1.7.0-alpha01")
    implementation("androidx.core:core-ktx:1.7.0")

    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.navigation:navigation-compose:2.5.0-beta01")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}