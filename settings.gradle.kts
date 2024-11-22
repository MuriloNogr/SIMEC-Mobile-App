pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // Incluído para resolver plugins Kotlin e Android Gradle
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SIMEC"
include(":app")
