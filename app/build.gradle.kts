plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.staticshortcut"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.staticshortcut"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        compose = true  // step 1
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"  // Add this block for the Compose compiler
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Add Jetpack Compose dependencies step 2
    implementation (platform(libs.androidx.compose.bom))  // Use the BOM for version management
    implementation (libs.androidx.ui)  // Core UI library for Compose
    implementation (libs.androidx.material3)
    implementation (libs.androidx.material3.window.size.class1)
    implementation (libs.androidx.material.icons.extended)
    implementation( libs.androidx.ui.tooling.preview)  // Preview support in Android Studio
    debugImplementation (libs.androidx.ui.tooling)
    implementation (libs.androidx.navigation.compose)
}