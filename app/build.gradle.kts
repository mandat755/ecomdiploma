plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")                  // ← ЗАМІНИЛИ kapt на ksp
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

    packagingOptions {
        // Викидаємо файли io.netty.versions.properties
        exclude("META-INF/io.netty.versions.properties")
        // Додаємо виключення для інших проблемних файлів
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/*.kotlin_module")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
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

    // Google Sign-In
    implementation("com.google.android.gms:play-services-auth")

    // Stripe
    implementation("com.stripe:stripe-android:22.8.1")

    // Lifecycle + Activity KTX
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation("androidx.activity:activity-ktx:1.9.3")

    // Retrofit + Gson + OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    // Room Database — ВИПРАВЛЕНО + оновлено до сумісної версії
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")           // ← замість kapt
    implementation("androidx.room:room-ktx:2.6.1")

    // Ktor (перевір версії)
    implementation("io.ktor:ktor-server-core-jvm:3.0.3")  // Перевірте сумісність з Kotlin 2.3.0
    implementation("io.ktor:ktor-server-netty-jvm:3.0.3")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:3.0.3")
    implementation("io.ktor:ktor-serialization-gson-jvm:3.0.3")
}