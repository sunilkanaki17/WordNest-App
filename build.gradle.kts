plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    //kotlin("kapt") version "2.1.20"
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.0.4")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        //classpath ("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.40.5")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}