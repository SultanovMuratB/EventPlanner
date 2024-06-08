plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("androidx.navigation.safeargs")
    alias(libs.plugins.parcelize)
    alias(libs.plugins.ksp)
    id ("kotlin-kapt")
}

android {
    namespace = "com.sultanov.eventplanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sultanov.eventplanner"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
//
//    implementation(libs.room.core)
//    implementation(libs.room.compiler)

    implementation(libs.picasso)

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")


    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gsonConverter)

    implementation(libs.navigation.fragment)
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
//    implementation(libs.androidx.navigation.safe.args.gradle.plugin)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}