import org.gradle.internal.impldep.bsh.commands.dir

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    jacoco
    java
}
//apply jacoco
apply(plugin = "jacoco")
apply(plugin = "android-reporting")

jacoco {
    toolVersion = "0.8.9"
}

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

