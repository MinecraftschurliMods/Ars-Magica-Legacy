import com.github.minecraftschurlimods.helperplugin.moddependencies.ModDependency

plugins {
    idea
    id ("com.github.minecraftschurlimods.helperplugin")
}

helper.license.url = helper.gitHub.url.zip(helper.license.file) { url, file -> "$url/blob/version/1.20.x/$file" }

val include: Configuration by configurations.creating {
    isTransitive = false
    dependencies.configureEach {
        if (this is ExternalDependency) {
            val strictVersion = this.versionConstraint.strictVersion
            project.jarJar.ranged(this, strictVersion)
        }
    }
}
sourceSets.forEach { sourceSet ->
    configurations.getByName(sourceSet.implementationConfigurationName).extendsFrom(include)
}
configurations.jarJar.get().extendsFrom(include)

helper.withApiSourceSet()
helper.withDataGenSourceSet()
helper.withTestSourceSet()

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "Minecraftschurli Maven"
        url = uri("https://minecraftschurli.ddns.net/repository/maven-public/")
        content {
            includeGroup("com.github.minecraftschurlimods")
        }
    }
    maven {
        name = "mcjty maven"
        url = uri("https://maven.k-4u.nl")
        content {
            includeGroup("mcjty.theoneprobe")
        }
    }
    maven {
        name = "theillusivec4 Maven"
        url = uri("https://maven.theillusivec4.top/")
        content {
            includeGroup("top.theillusivec4.curios")
        }
    }
    maven {
        name = "blamejared Maven"
        url = uri("https://maven.blamejared.com")
        content {
            includeGroup("com.blamejared.controlling")
            includeGroup("vazkii.patchouli")
            includeGroup("mezz.jei")
        }
    }
    maven {
        name = "GeckoLib Maven"
        url = uri("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        content {
            includeGroup("software.bernie.geckolib")
        }
    }
    maven {
        name = "ModMaven"
        url = uri("https://modmaven.k-4u.nl")
    }
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
}

val NamedDomainObjectProvider<ModDependency>.version: Provider<String> get() = flatMap { it.version }

val jei = helper.dependencies.jei()
val jade = helper.dependencies.jade()
val theoneprobe = helper.dependencies.theoneprobe()
val curios = helper.dependencies.curios()
val configured = helper.dependencies.configured()
val catalogue = helper.dependencies.catalogue()
val potionbundles = helper.dependencies.optional("potionbundles") {
    curseforgeId = "potion-bundles"
    modrinthId = "potion-bundles"
}
val geckolib = helper.dependencies.required("geckolib")
val patchouli = helper.dependencies.required("patchouli")
val embeddium = helper.dependencies.optional("embeddium")

dependencies {
    implementation(helper.neoforge())

    // jei for integration
    compileOnly(jei.version.map { "mezz.jei:jei-1.20.4-common-api:${it}" })
    runtimeOnly(jei.version.map { "mezz.jei:jei-1.20.4-forge:${it}" })

    // curios for additional inventory slots
    compileOnly(curios.version.map { "top.theillusivec4.curios:curios-neoforge:${it}:api" })
    runtimeOnly(curios.version.map { "top.theillusivec4.curios:curios-neoforge:${it}" })

    // patchouli for the guide book (arcane compendium)
    compileOnly(patchouli.version.map { "vazkii.patchouli:Patchouli:${it}:api" })
    val patchouliRuntimeDep = patchouli.version.map { "vazkii.patchouli:Patchouli:${it}" }
    runtimeOnly(patchouliRuntimeDep)
    testRuntimeOnly(patchouliRuntimeDep)
    "dataRuntimeOnly"(patchouliRuntimeDep)

    // geckolib for animations
    val geckolibDep = helper.minecraftVersion.zip(geckolib.version) { mc, version -> "software.bernie.geckolib:geckolib-neoforge-${mc}:${version}" }
    compileOnly(geckolibDep)
    runtimeOnly(geckolibDep)
    testRuntimeOnly(geckolibDep)
    "dataRuntimeOnly"(geckolibDep)

    // theoneprobe for integration
    compileOnly(theoneprobe.version.map { "mcjty.theoneprobe:theoneprobe:${it}:api" }) { (this as ModuleDependency).isTransitive = false }
    runtimeOnly(theoneprobe.version.map { "mcjty.theoneprobe:theoneprobe:${it}" }) { (this as ModuleDependency).isTransitive = false }

    // jade for integration
    val jadeDep = jade.version.map { "maven.modrinth:jade:${it}-neoforge" }
    compileOnly(jadeDep)
    runtimeOnly(jadeDep)

    if (System.getenv("GITHUB_ACTIONS") == null || System.getenv("GITHUB_ACTIONS").isEmpty()) {
        runtimeOnly(potionbundles.version.map { "com.github.minecraftschurlimods:potionbundles:${it}" })
        //runtimeOnly(controlling.version.map { "com.blamejared.controlling:Controlling-neoforge-1.20.4:${it}" })
        //runtimeOnly(embeddium.version.map { "maven.modrinth:embeddium:${it}" })
    }

    // add internal libraries
    include("com.github.minecraftschurlimods:codeclib") {
        version {
            strictly("[1.20.4-1.0-SNAPSHOT,)")
            prefer("1.20.4-1.0-SNAPSHOT")
        }
    }
    include("com.github.minecraftschurlimods:betterkeybindlib") {
        version {
            strictly("[1.20.4-1.1-SNAPSHOT,)")
            prefer("1.20.4-1.1-SNAPSHOT")
        }
    }
    include("com.github.minecraftschurlimods:betterhudlib") {
        version {
            strictly("[1.20.4-1.0-SNAPSHOT,)")
            prefer("1.20.4-1.0-SNAPSHOT")
        }
    }
    "apiCompileOnly"("com.github.minecraftschurlimods:easydatagenlib:1.20.4-1.1.2-SNAPSHOT:api")
    "dataImplementation"("com.github.minecraftschurlimods:easydatagenlib:1.20.4-1.1.2-SNAPSHOT")

    compileOnly("org.jetbrains:annotations:23.0.0")
    "apiCompileOnly"("org.jetbrains:annotations:23.0.0")
    testCompileOnly("org.jetbrains:annotations:23.0.0")
    "dataCompileOnly"("org.jetbrains:annotations:23.0.0")
}

helper.withCommonRuns()
helper.withGameTestRuns()
helper.withDataGenRuns {
    programArguments.add("--mixin.config")
    programArguments.add(helper.projectId.map { "${it}_data.mixins.json" })
}
helper.modproperties.put(
    "catalogueItemIcon", helper.projectId.map { "$it:occulus" }
)

minecraft.accessTransformers.file("src/main/resources/META-INF/accesstransformer.cfg")

helper.publication.pom {
    organization {
        name = "Minecraftschurli Mods"
        url = "https://github.com/MinecraftschurliMods"
    }
    developers {
        developer {
            id = "minecraftschurli"
            name = "Minecraftschurli"
            email = "minecraftschurli@gmail.com"
            url = "https://github.com/Minecraftschurli"
            organization = "Minecraftschurli Mods"
            organizationUrl = "https://github.com/MinecraftschurliMods"
            timezone = "Europe/Vienna"
        }
        developer {
            id = "ichhabehunger54"
            name = "IchHabeHunger54"
            url = "https://github.com/IchHabeHunger54"
            organization = "Minecraftschurli Mods"
            organizationUrl = "https://github.com/MinecraftschurliMods"
            timezone = "Europe/Vienna"
        }
    }
}
