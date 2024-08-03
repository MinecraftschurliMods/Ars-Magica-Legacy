pluginManagement {
    plugins {
        id("net.neoforged.gradle.userdev") version "7.0.153"
        id("com.github.minecraftschurlimods.helperplugin") version "1.14"
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven { url = uri("https://minecraftschurli.ddns.net/repository/maven-public") }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "Ars-Magica-Legacy"

includeBuild("../codeclib"){
    dependencySubstitution {
        substitute(module("com.github.minecraftschurlimods:codeclib")).using(project(":"))
    }
}
includeBuild("../betterkeybindlib"){
    dependencySubstitution {
        substitute(module("com.github.minecraftschurlimods:betterkeybindlib")).using(project(":"))
    }
}
includeBuild("../betterhudlib") {
    dependencySubstitution {
        substitute(module("com.github.minecraftschurlimods:betterhudlib")).using(project(":"))
    }
}
