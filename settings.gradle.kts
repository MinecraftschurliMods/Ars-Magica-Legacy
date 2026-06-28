pluginManagement {
    plugins {
        id("net.neoforged.gradle.userdev") version "7.1.38"
        id("com.github.minecraftschurlimods.helperplugin") version "1.21"
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven { url = uri("https://maven.minecraftschurli.at/maven-public") }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "ArsMagicaLegacy"
