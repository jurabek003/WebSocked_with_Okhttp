plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "uz.turgunboyevjurabek.websockedwithscarlet"
    compileSdk = 34

    defaultConfig {
        applicationId = "uz.turgunboyevjurabek.websockedwithscarlet"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
//
//// Scarlet
//    implementation("com.tinder.scarlet:scarlet:0.1.12")
//    implementation("com.tinder.scarlet:websocket-okhttp:0.1.12")
//    implementation("com.tinder.scarlet:stream-adapter-rxjava2:0.1.12")
//
////RX
//    implementation("io.reactivex.rxjava2:rxjava:2.2.21")
//    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
//    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
//
//// Gson
//    implementation("com.google.code.gson:gson:2.10.1")

    /**
     * Websocket client for Android and Kotlin with OkHttp 4.x
     */
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")

    /**
     * Permission for Android
     */
    implementation("com.google.accompanist:accompanist-permissions:0.31.0-alpha")

}