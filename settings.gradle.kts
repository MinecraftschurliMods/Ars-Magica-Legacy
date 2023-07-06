pluginManagement {
    plugins {
        id("org.ec4j.editorconfig") version "0.0.3"
        id("net.minecraftforge.gradle") version "[6.0,6.2)"
        id("org.spongepowered.mixin") version "0.7-SNAPSHOT"
        id("org.parchmentmc.librarian.forgegradle") version "1.2+"
    }
    repositories {
        gradlePluginPortal()
        maven {
            name = "MinecraftForge"
            url = uri("https://maven.minecraftforge.net/")
        }
        maven {
            name = "ParchmentMC"
            url = uri("https://maven.parchmentmc.org")
        }
        maven {
            name = "SpongeGradle"
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
        }
    }
}

rootProject.name = "Ars Magica Legacy"
