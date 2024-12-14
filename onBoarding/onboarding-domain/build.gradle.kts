plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.compose.compiler)
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.aopr.onboarding_domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //koin
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.info.bar.compose)
    implementation(libs.koin.androidx.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}