plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.ecomdiploma"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.ecomdiploma"
        minSdk = 26
        targetSdk = 36
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

    buildFeatures {
        viewBinding = true
    }
}

// Правильний спосіб налаштувати Kotlin JVM target для Android-проєктів
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        // allWarningsAsErrors.set(false)  // розкоментуй, якщо хочеш
    }
}

dependencies {
    // Основні AndroidX бібліотеки з version catalog (libs.versions.toml)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.cardview)
    implementation(libs.play.services.analytics.impl)

    // Тестування
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Jetpack Navigation
    implementation(libs.androidx.navigation.fragmentKtx)
    implementation(libs.androidx.navigation.uiKtx)

    // ────────────────────────────────────────────────
    // Firebase (завжди використовуй BOM + без версій)
    implementation(platform("com.google.firebase:firebase-bom:34.10.0"))

    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-functions")
    //implementation("com.google.firebase:firebase-functions-ktx")
    // якщо дійсно потрібен firebase-core (зазвичай не потрібен з BOM)
    // implementation("com.google.firebase:firebase-core")

    // Google Sign-In (рекомендовано без версії, BOM сумісний)
    implementation("com.google.android.gms:play-services-auth")

    // Stripe
    implementation("com.stripe:stripe-android:22.8.1")

    // Lifecycle + Activity KTX (оновлені версії)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation("androidx.activity:activity-ktx:1.9.3")  // сумісно з твоїм libs.activity

    // Retrofit + Gson + OkHttp (оновлені версії)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}