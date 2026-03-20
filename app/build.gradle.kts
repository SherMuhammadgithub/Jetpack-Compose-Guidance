plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.googledesign"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.googledesign"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true       // Removes unused code (shrinks APK)
            isShrinkResources = true     // Removes unused resources
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Sign with debug key so you can install without a signing config
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // This tells Gradle: "Enable Jetpack Compose in this module"
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Compose BOM — this is the "master version list" for all Compose libs
    implementation(platform(libs.androidx.compose.bom))

    // Core Compose libraries
    implementation(libs.androidx.compose.ui)             // Basic UI building blocks
    implementation(libs.androidx.compose.ui.graphics)     // Drawing, colors, shapes
    implementation(libs.androidx.compose.material3)       // Material 3 components (Button, Card, etc.)
    implementation(libs.androidx.compose.material.icons.extended) // Extended icons (Visibility, etc.)
    implementation(libs.androidx.activity.compose)        // Lets Activity use setContent {}
    implementation(libs.androidx.navigation.compose)       // Navigation between screens

    // Tooling — for previewing Compose UI inside Android Studio
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}