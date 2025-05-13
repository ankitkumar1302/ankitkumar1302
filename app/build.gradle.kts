plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.ankit.appui"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ankit.appui"
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
        compose = true
    }
}

dependencies {

    // Jetpack Compose BOM
    val composeBomVersion = "2024.04.00" // Use the latest stable version
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))

    // Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics") // For Graphics, Brush, etc.
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Compose Material 3
    implementation("androidx.compose.material3:material3")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7") // Use the latest stable version

    // Material Icons
    implementation("androidx.compose.material:material-icons-core")
    implementation(libs.androidx.material.icons.extended) // For more icons like ArrowDropUp

    // Accompanist - System UI Controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    // Blur effects
    implementation("com.github.skydoves:landscapist-glide:2.2.12")
    implementation("com.github.skydoves:landscapist-blur:2.2.12")

    // Animations
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.animation:animation-graphics")
    implementation("androidx.compose.animation:animation-core")
    
    // Shared element transitions
    implementation("com.google.accompanist:accompanist-navigation-material:0.32.0")
    
    // Better ripple effects
    implementation("com.google.accompanist:accompanist-flowlayout:0.32.0")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}