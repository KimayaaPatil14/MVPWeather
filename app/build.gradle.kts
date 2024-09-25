plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mvpweather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mvpweather"
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
}

dependencies {

    implementation ("android.appcompt:appcompt:1.3.0")
    implementation ("com.squareup.picasso:2.71828")
    implementation ("com.android.volley:volley:1.1.1")
    implementation ("com.google.android.gms:play-services-location:17.0.0")

    implementation ("com.google.android.material:material:1.4.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation(libs.recyclerview)
    testImplementation ("junit:junit:4.+")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}