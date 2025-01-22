// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.hilt) apply false
    kotlin("jvm") version "2.1.0" apply false
}
buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "2.1.0"))
    }
}