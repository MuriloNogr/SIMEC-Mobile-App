buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.google.gms:google-services:4.4.2")
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}
plugins {
    id("com.google.gms.google-services") version "4.4.2" apply false

}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
