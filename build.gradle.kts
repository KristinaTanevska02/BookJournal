// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        // Add the Google Services classpath
        classpath("com.google.gms:google-services:4.4.2") // This should be added here
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply true
}
