plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.example.home_data"
    compileSdk = 35

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
    implementation(project(":notes:notes-data"))
    implementation(project(":tasks:tasks-data"))
    implementation(project(":bookmarks:bookmarks-data"))
    implementation(project(":notes:notes-domain"))
    implementation(project(":tasks:tasks-domain"))
    implementation(project(":bookmarks:bookmarks-domain"))
    implementation(project(":home:home-domain"))
    implementation(project(":shared:shared-domain"))
    implementation(project(":shared:shared-data"))
    implementation(project(":shared:shared-ui"))
    implementation(project(":firebase:firebase-domain"))

    //koin
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.info.bar.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}