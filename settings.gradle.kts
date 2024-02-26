pluginManagement {
    plugins {
        id("net.neoforged.gradle.userdev") version "7.0.80"
        id("com.github.minecraftschurlimods.helperplugin") version "1.7"
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven { url = uri("https://minecraftschurli.ddns.net/repository/maven-public") }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "Ars Magica Legacy"
