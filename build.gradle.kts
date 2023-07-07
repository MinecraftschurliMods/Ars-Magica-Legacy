import com.github.minecraftschurlimods.moddev.moddev
import java.util.*

plugins {
    idea
    eclipse
    `maven-publish`
    id("net.minecraftforge.gradle")
    id("org.spongepowered.mixin")
    id("org.parchmentmc.librarian.forgegradle")
    id("com.github.minecraftschurlimods.moddev")
}

//=============================================

with(project.rootProject.file("local.properties")) {
    if (exists()) {
        this.inputStream().use {
            val properties = Properties()
            properties.load(it)
            properties.forEach { (key, value) ->
                project.ext.set(key.toString(), value)
            }
        }
    }
}

//=============================================

val modGroup: String by project
val modId: String by project
val modName: String by project
val modVersion: String by project
val modAuthor: String by project
val modCredits: String by project
val modUrl: String by project
val url: String by project
val github: String by project
val vendor: String by project
val javaVersion: String by project
val loaderVersionRange: String by project
val mixinVersion: String by project
val mappingsChannel: String by project
val mappingsVersion: String by project
val mcVersion: String by project
val mcMajorVersion: String by project
val forgeVersion: String by project
val jeiVersion: String by project
val topVersion: String by project
val curiosVersion: String by project
val patchouliVersion: String by project
val geckolibVersion: String by project
val potionbundlesVersion: String by project
val controllingVersion: String by project
val simpleNetLibVersion: String by project
val codecLibVersion: String by project
val betterKeybindLibVersion: String by project
val modrinthProjectId: String by project
val curseProjectId: String by project
val forgeVersionRange: String by project
val mcVersionRange: String by project
val patchouliVersionRange: String by project
val geckolibVersionRange: String by project
val curiosVersionRange: String by project
val jeiVersionRange: String by project

//=============================================

mixin {
    add(sourceSets.main.get(), "${modId}.refmap.json")
    config("${modId}.mixins.json")
}

group = modGroup
version = "${mcVersion}-${modVersion}"
base.archivesName.set(modId)

if (System.getenv("RELEASE_TYPE") != null) {
    status = System.getenv("RELEASE_TYPE").lowercase()
    if (status == "snapshot") status = (status as String).uppercase()
} else {
    status = "SNAPSHOT"
}

if (status != "release") {
    version = "${version}-${status}"
}

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(javaVersion))
    if (System.getenv("GITHUB_ACTIONS") == null || System.getenv("GITHUB_ACTIONS").isEmpty()) {
        vendor.set(JvmVendorSpec.matching("JetBrains s.r.o."))
    } else {
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}

sourceSets {
    create("api")
    main
    create("data")
}

moddev {
    modId.set(this@Build_gradle.modId)
    modName.set(this@Build_gradle.modName)
    modUrl.set(this@Build_gradle.modUrl)
    modDescription.set("Ars Magica: Legacy is a fan continuation of the original Ars Magica 2 mod created by Mithion for Minecraft 1.7.10.")
    modrinthProjectId.set(this@Build_gradle.modrinthProjectId)
    curseforgeProjectId.set(curseProjectId.toInt())
    credits.set(modCredits)
    minecraftVersion.set(mcVersion)
    mixinVersion.set(this@Build_gradle.mixinVersion)
    forgeVersion.set(this@Build_gradle.forgeVersion)
    mappingsChannel.set(this@Build_gradle.mappingsChannel)
    parchmentVersion.set(this@Build_gradle.mappingsVersion)
    loaderVersion.set(loaderVersionRange)
    forgeVersionRange.set(this@Build_gradle.forgeVersionRange)
    minecraftVersionRange.set(mcVersionRange)
    logoFile.set(project.file("src/main/resources/logo.png"))
    accessTransformers.from("src/main/resources/META-INF/accesstransformer.cfg")

    jarExcludePatterns.addAll("**/*.psd", "**/*.bbmodel")

    organizationName.set("Minecraftschurli Mods")
    githubOrg.set("MinecraftschurliMods")
    githubRepo.set("Ars-Magica-Legacy")
    githubMainBranch.set(minecraftVersion.map { "version/$it" })
    licenseFile.set(project.file("LICENSE.md"))
    licenseName.set("\"Don't Be a Jerk\" License")

    authors {
        create("minecraftschurli") {
            displayName.set("Minecraftschurli")
            url.set("https://github.com/Minecraftschurli")
            email.set("minecraftschurli@gmail.com")
            organization.set("Minecraftschurli Mods")
            organizationUrl.set("https://github.com/MinecraftschurliMods")
            timezone.set("Europe/Vienna")
            roles.addAll("OWNER", "DEVELOPER")
        }
        create("ichhabehunger54") {
            displayName.set("IchHabeHunger54")
            url.set("https://github.com/IchHabeHunger54")
            organization.set("Minecraftschurli Mods")
            organizationUrl.set("https://github.com/MinecraftschurliMods")
            timezone.set("Europe/Vienna")
            roles.addAll("DEVELOPER", "ARTIST")
        }
    }

    dependencies {
        runtimeCompat("jei") {
            version(jeiVersionRange, jeiVersion)
            group.set("mezz.jei")
            mavenURL.set("https://maven.blamejared.com")
            artifacts {
                compile("jei-${mcVersion}-common-api")
                compile("jei-${mcVersion}-forge-api")
                runtime("jei-${mcVersion}-forge")
            }
        }
        runtimeCompat("curios") {
            version(curiosVersionRange, curiosVersion)
            group.set("top.theillusivec4.curios")
            mavenURL.set("https://maven.theillusivec4.top/")
            artifacts {
                compile("curios-forge", classifier = "api")
                runtime("curios-forge")
            }
        }
        runtimeCompat("potionbundles") {
            version(potionbundlesVersion)
            group.set("com.github.ichhabehunger54")
            mavenURL.set("https://minecraftschurli.ddns.net/repository/maven-public/")
            modrinth.set("potion-bundles")
            curseforge.set("potion-bundles")
            artifact("potionbundles")
        }
        dependency("patchouli") {
            version(patchouliVersionRange, patchouliVersion)
            group.set("vazkii.patchouli")
            mavenURL.set("https://maven.blamejared.com")
            artifacts {
                compile("Patchouli", classifier = "api")
                runtime("Patchouli")
            }
        }
        dependency("geckolib3") {
            version(geckolibVersionRange, geckolibVersion)
            group.set("software.bernie.geckolib")
            mavenURL.set("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
            modrinth.set("geckolib")
            curseforge.set("geckolib")
            artifact("geckolib-forge-${mcMajorVersion}")
        }
        runtimeCompat("theoneprobe") {
            version(topVersion)
            group.set("mcjty.theoneprobe")
            mavenURL.set("https://maven.k-4u.nl")
            curseforge.set("the-one-probe")
            artifacts {
                compile("theoneprobe", transitive = false, classifier = "api")
                runtime("theoneprobe", transitive = false)
            }
        }
        runtimeCompat("configured") {
            version("*")
        }
        runtimeCompat("catalogue") {
            version("*")
        }
        includedLibrary("simplenetlib") {
            version("[$simpleNetLibVersion,)", simpleNetLibVersion)
            group.set("com.github.minecraftschurli")
            mavenURL.set("https://minecraftschurli.ddns.net/repository/maven-public/")
            artifact("simplenetlib")
        }
        includedLibrary("codeclib") {
            version("[$codecLibVersion,)", codecLibVersion)
            group.set("com.github.minecraftschurli")
            mavenURL.set("https://minecraftschurli.ddns.net/repository/maven-public/")
            artifact("codeclib")
        }
        includedLibrary("betterkeybindlib") {
            version("[$betterKeybindLibVersion,)", betterKeybindLibVersion)
            group.set("com.github.minecraftschurli")
            mavenURL.set("https://minecraftschurli.ddns.net/repository/maven-public/")
            artifact("betterkeybindlib")
        }
        dataLibrary("patchouli_datagen") {
            version("1.0-SNAPSHOT")
            group.set("com.github.minecraftschurli")
            mavenURL.set("https://minecraftschurli.ddns.net/repository/maven-public/")
            artifact("patchouli_datagen-forge-1.18.2")
        }
        library("org.jetbrains", "annotations", "23.0.0")
    }
    modsToml {
        properties.put("catalogueItemIcon", modId.map { "$it:occulus" })
    }
}

tasks.register("setupGithubActions") {
    doLast {
        println("##[set-output name=modid;]${modId}")
        println("##[set-output name=version;]${project.version}")
    }
}

publishing.repositories.maven {
    if (
            (System.getenv("MAVEN_USER") != null) &&
            (System.getenv("MAVEN_PASSWORD") != null) &&
            (System.getenv("MAVEN_URL") != null)
    ) {
        url = uri(System.getenv("MAVEN_URL"))
        credentials {
            username = System.getenv("MAVEN_USER")
            password = System.getenv("MAVEN_PASSWORD")
        }
    } else {
        url = uri("$buildDir/repo")
    }
}

evaluationDependsOnChildren()
