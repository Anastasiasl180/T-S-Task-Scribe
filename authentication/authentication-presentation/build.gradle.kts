plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.compose.compiler)
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.aopr.authentication_presentation"
    compileSdk = 35

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

    dependencies {

        //modules

        implementation(project(":firebase:firebase-domain"))
        implementation(project(":firebase:firebase-data"))
        implementation(project(":home:home-presentation"))
        implementation(project(":bookmarks:bookmarks-domain"))
        implementation(project(":notes:notes-domain"))
        implementation(project(":tasks:tasks-domain"))
        implementation(project(":shared:shared-ui"))
        implementation(project(":authentication:authentication-domain"))
        implementation(project(":shared:shared-domain"))

        implementation(platform(libs.firebase.bom))
        implementation(libs.com.google.firebase.firebase.analytics)
        implementation(libs.coil)
        implementation(libs.coil3.coil.compose)
        //koin
        implementation(libs.koin.android)
        implementation(libs.koin.annotations)
        ksp(libs.koin.ksp)
        implementation(libs.koin.androidx.compose)

        //navigation
        implementation(libs.androidx.navigation.compose)

        implementation(libs.kotlinx.serialization.json)


        implementation(libs.material3) // Update to the latest version


        implementation(libs.kotlinx.serialization.json)

        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }
}