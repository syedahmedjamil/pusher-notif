// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
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

