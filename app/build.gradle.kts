plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKapt)
}

android {
    namespace = "com.github.syedahmedjamil.pushernotif"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.syedahmedjamil.pushernotif"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testApplicationId = "com.github.syedahmedjamil.pushernotif.test"
//      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "io.cucumber.android.runner.CucumberAndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            setProperty("archivesBaseName", "PusherNotifRelease")
            resValue("string", "app_name", "Pusher Notif (Release)")
            applicationIdSuffix = ".release"
            versionNameSuffix = "-RELEASE"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            setProperty("archivesBaseName", "PusherNotifDebug")
            resValue("string", "app_name", "Pusher Notif (Debug)")
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            isDebuggable = true
            buildConfigField("boolean", "DEBUG", "true")
            //test coverage
//            enableUnitTestCoverage = true
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
        dataBinding = true
        buildConfig = true
    }

    testOptions {
        animationsDisabled = true
    }

    dependencies {

        //project
        implementation(project(":data"))
        implementation(project(":domain"))
        implementation(project(":usecases"))
        implementation(project(":core"))

        //local
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.activity.ktx)
        implementation(libs.androidx.constraintlayout)
        implementation("androidx.datastore:datastore-preferences:1.0.0")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2") // for asLiveData method

        //test
        testImplementation(project(":shared-test"))
        testImplementation(libs.junit)
        testImplementation(libs.androidx.junit.ktx)
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") //for runTest and dispatchers
        testImplementation("androidx.arch.core:core-testing:2.2.0") // for instant task executor rule

        //androidTest
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(libs.cucumber.android)
        androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    }

}