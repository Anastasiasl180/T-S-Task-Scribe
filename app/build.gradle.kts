plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.compose.compiler)
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.aopr.taskscribe"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aopr.taskscribe"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {

    //haze for mainBottomBar
    implementation(libs.haze.jetpack.compose)

    implementation(libs.kotlinx.serialization.json)
    //modules
    implementation(project(":home:home-presentation"))
    implementation(project(":home:home-data"))
    implementation(project(":home:home-domain"))
    implementation(project(":shared:shared-data"))
    implementation(project(":shared:shared-ui"))
    implementation(project(":shared:shared-domain"))
    implementation(project(":notes:notes-data"))
    implementation(project(":notes:notes-presentation"))
    implementation(project(":notes:notes-domain"))
    implementation(project(":tasks:tasks-presentation"))
    implementation(project(":tasks:tasks-data"))
    implementation(project(":tasks:tasks-domain"))
    implementation(project(":bookmarks:bookmarks-domain"))
    implementation(project(":bookmarks:bookmarks-data"))
    implementation(project(":bookmarks:bookmarks-presentation"))

    //koin
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp)
    implementation(libs.koin.androidx.compose)

    //navigation
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}