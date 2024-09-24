plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.keepnotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.keepnotes"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material:1.6.5")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // System UI Controller - Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.21.2-beta")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    // Testing
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.7")

    // Room Components
    val room_version = "2.6.0"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    ksp("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-analytics")
//    implementation("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.10.3")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-database")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt:coil-gif:2.6.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("com.mohamedrejeb.richeditor:richeditor-compose:1.0.0-rc04")
    implementation("androidx.compose.material:material-icons-extended-android:1.6.5")
    //Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")


    // Contains the core Credential Manager functionalities including password
    // and passkey support.
    implementation("androidx.credentials:credentials:1.3.0-alpha03")
    // Provides support from Google Play services for Credential Manager,
    // which lets you use the APIs on older devices.
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0-alpha03")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")

    // Google Auth
    implementation("com.google.android.gms:play-services-auth:21.1.1")

    // JWT Decoder
    implementation("com.auth0.android:jwtdecode:2.0.2")

    // Gson needed for Proguard rules for JWT Decoder library (For now).
    // Because JWT Decoder doesn't add those Proguard rules by default.
    implementation("com.google.code.gson:gson:2.10.1")


    // OkHttp3
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")


    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

// Coroutine support for Retrofit (if using coroutines)
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0") // or adapter-coroutines if you use coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

// OkHttp for logging (optional but recommended for debugging)
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")


}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
