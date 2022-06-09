import org.gradle.kotlin.dsl.`kotlin-dsl`

repositories {
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}
buildscript {

    dependencies {

        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    }
}