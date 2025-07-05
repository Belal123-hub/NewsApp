plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.news"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.news"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.example.news.HiltTestRunner"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
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
        debug {
            buildConfigField("String", "API_KEY", "\"4c6cb1f21b9b4ea8a22c53520bdf4275\"")
            buildConfigField("String", "BASE_URL", "\"https://newsapi.org/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packagingOptions {
        resources {
            excludes.add("META-INF/*")
        }
    }
}


dependencies {
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation (libs.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.runtime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.serialization.json)
    // SplashScreen
    implementation(libs.androidx.core.splashscreen)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // Paging3
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    // Compose
    implementation(libs.androidx.foundation)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)
    //Navigation
    implementation (libs.androidx.navigation.compose)
    //coil
    implementation(libs.androidx.core)
    implementation(libs.coil.compose)
    implementation(libs.coil.base)

    // Unit testing dependencies
    testImplementation("io.mockk:mockk:1.13.5")

    // Other test dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.5.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("com.google.truth:truth:1.1.3")
    androidTestImplementation("com.google.truth:truth:1.1.3")

    testImplementation("androidx.arch.core:core-testing:2.1.0")

    testImplementation("org.slf4j:slf4j-simple:1.7.36")
    testImplementation ("io.ktor:ktor-client-mock:2.3.7" ) // e.g., 2.3.3
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation ("androidx.paging:paging-testing:3.2.1")
    testImplementation ("androidx.compose.ui:ui-test-junit4:1.6.0")



    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")         // JUnit Android wrapper
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // UI testing
    androidTestImplementation("androidx.test:core:1.5.0")               // AndroidX Test core
    androidTestImplementation("androidx.test:rules:1.5.0")              // JUnit rules
    androidTestImplementation("androidx.test:runner:1.5.2")             // Instrumentation runner
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4") // Coroutine testing (optional)
    androidTestImplementation ("io.mockk:mockk-android:1.13.5")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    kspAndroidTest("com.google.dagger:hilt-compiler:2.51.1")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.7")
    testImplementation ("org.slf4j:slf4j-nop:2.0.7")


    debugImplementation("androidx.fragment:fragment-testing:1.6.2")    // For FragmentScenario etc.
    testImplementation ("app.cash.turbine:turbine:0.12.1")


}