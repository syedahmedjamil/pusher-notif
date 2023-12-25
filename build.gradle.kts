// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.google.firebase.appdistribution") version "4.0.1" apply false
    java
}
apply(plugin = "android-reporting")


// for unit tests report aggregation
val testReport = tasks.register("testReport", TestReport::class) {
    outputs.upToDateWhen { false }
    destinationDir = file("$buildDir/reports/unitTests")
    subprojects {
        reportOn(project.tasks.withType<Test>().map { it.binaryResultsDirectory })
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(testReport)
    ignoreFailures = true
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<Test>().configureEach {
    maxParallelForks = 3
}

