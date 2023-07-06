plugins {
    `kotlin-dsl`
}

gradlePlugin.plugins.register("com.github.minecraftschurlimods.moddev") {
    id = "com.github.minecraftschurlimods.moddev"
    implementationClass = "com.github.minecraftschurlimods.moddev.ModDevPlugin"
}

repositories {
    maven {
        name = "MinecraftForge"
        url = uri("https://maven.minecraftforge.net/")
    }
}

dependencies {
    implementation("net.minecraftforge.gradle:ForgeGradle:6.0.7")
}
