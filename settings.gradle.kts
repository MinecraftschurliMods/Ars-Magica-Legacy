pluginManagement {
    plugins {
        id("net.neoforged.gradle.userdev") version "7.1.39"
        id("at.minecraftschurli.gradle.modhelper") version "2.0"
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven { url = uri("https://maven.minecraftschurli.at/maven-public") }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "ArsMagicaLegacy"
