import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.appdistribution")
    id("androidx.navigation.safeargs.kotlin")
}
val keystorePropertiesFile = rootProject.file("app/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.github.syedahmedjamil.pushernotif"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.syedahmedjamil.pushernotif"
        minSdk = 24
        targetSdk = 33
        versionCode = project.property("versionCode").toString().toInt()
        versionName = "1.2.0"
        testApplicationId = "com.github.syedahmedjamil.pushernotif.test"
        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "io.cucumber.android.runner.CucumberAndroidJUnitRunner"
        //apk file name
        setProperty("archivesBaseName", "${rootProject.name}-${versionName}-${versionCode}")

    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        debug {
            val suffix = "debug"
            resValue("string", "app_name", "Pusher Notif (${suffix})")
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            applicationIdSuffix = ".${suffix}"
            versionNameSuffix = "-${suffix}"
        }
        release {
            val suffix = "release"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "app_name", "Pusher Notif (${suffix})")
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            applicationIdSuffix = ".${suffix}"
            versionNameSuffix = "-${suffix}"
            signingConfig = signingConfigs.getByName("release")

            firebaseAppDistribution {
                serviceCredentialsFile = keystoreProperties["serviceCredentialsFile"] as String
                artifactType = "APK"
                releaseNotesFile = keystoreProperties["releaseNotesFile"] as String
                testersFile = keystoreProperties["testersFile"] as String
            }
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
        implementation("com.google.firebase:firebase-iid:21.1.0")
        implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
        implementation("com.google.firebase:firebase-messaging")
        implementation("com.pusher:push-notifications-android:1.9.2")
        implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
        implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")

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
        androidTestImplementation(libs.androidx.rules)
        androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
        androidTestImplementation("com.squareup.okhttp3:okhttp:3.12.0")
        androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0-alpha03")
    }

}
