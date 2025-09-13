plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.mahnoosh.ui"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(projects.core.common)

    val composeBom = platform(libs.androidx.compose.bom)
    api(composeBom)
    androidTestImplementation(composeBom)
    api(libs.androidx.ui.test.manifest)
    api(libs.androidx.junit)
    api(libs.androidx.espresso.core)
    api(libs.androidx.ui.test.junit4)
    api(libs.truth)
    api(libs.hilt.test)
    api(libs.navigation.test)
    api(libs.androidx.runner)
    api(libs.androidx.core)
    api(libs.coroutines.test)
    api(libs.androidx.activity.compose)
}