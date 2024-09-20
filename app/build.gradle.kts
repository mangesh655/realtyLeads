plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.reltyleads"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.reltyleads"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.room.common)
    api(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.androidx.paging.compose)
    implementation(libs.coil.compose)
    implementation(libs.androidx.room.ktx)
    implementation(libs.bundles.datastore)
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.palette.ktx)
    implementation(libs.bundles.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.bundles.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}