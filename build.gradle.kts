import com.github.minecraftschurlimods.helperplugin.api
import com.github.minecraftschurlimods.helperplugin.localGradleProperty
import com.github.minecraftschurlimods.helperplugin.moddependencies.ModDependency
import com.github.minecraftschurlimods.helperplugin.sourceSets
import com.github.minecraftschurlimods.helperplugin.version

plugins {
    idea
    id ("com.github.minecraftschurlimods.helperplugin")
}

helper.license.url = helper.gitHub.url.zip(helper.license.file) { url, file -> "$url/blob/version/1.20.x/$file" }

helper.withApiSourceSet()
helper.withDataGenSourceSet()
helper.withTestSourceSet()

val include: Configuration by configurations.creating {
    isTransitive = false
    dependencies.configureEach {
        if (this is ExternalDependency) {
            val strictVersion = this.versionConstraint.strictVersion
            project.jarJar.ranged(this, strictVersion)
        }
    }
}
sourceSets.configureEach {
    configurations.getByName(implementationConfigurationName).extendsFrom(include)
}
configurations.jarJar.get().extendsFrom(include)

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
val geckolib = helper.dependencies.required("geckolib") {
    ordering = ModDependency.Ordering.BEFORE
    side = ModDependency.Side.BOTH
}
val patchouli = helper.dependencies.required("patchouli") {
    ordering = ModDependency.Ordering.BEFORE
    side = ModDependency.Side.BOTH
}
val embeddium = helper.dependencies.optional("embeddium")

dependencies {
    implementation(helper.neoforge())

    // jei for integration
    val jeiApiDep = helper.minecraftVersion.zip(jei.version) { mc, version -> "mezz.jei:jei-${mc}-common-api:${version}" }
    val jeiDep = helper.minecraftVersion.zip(jei.version) { mc, version -> "mezz.jei:jei-${mc}-neoforge:${version}" }
    compileOnly(jeiApiDep)

    // curios for additional inventory slots
    val curiosApiDep = curios.version.map { "top.theillusivec4.curios:curios-neoforge:${it}:api" }
    val curiosDep = curios.version.map { "top.theillusivec4.curios:curios-neoforge:${it}" }
    compileOnly(curiosApiDep)
    "dataCompileOnly"(curiosApiDep)
    "dataRuntimeOnly"(curiosDep)

    // patchouli for the guide book (arcane compendium)
    val patchouliApiDep = patchouli.version.map { "vazkii.patchouli:Patchouli:${it}:api" }
    val patchouliDep = patchouli.version.map { "vazkii.patchouli:Patchouli:${it}" }
    compileOnly(patchouliApiDep)
    runtimeOnly(patchouliDep)
    testRuntimeOnly(patchouliDep)
    "dataRuntimeOnly"(patchouliDep)

    // geckolib for animations
    val geckolibDep = helper.minecraftVersion.zip(geckolib.version) { mc, version -> "software.bernie.geckolib:geckolib-neoforge-${mc}:${version}" }
    compileOnly(geckolibDep)
    runtimeOnly(geckolibDep)
    testRuntimeOnly(geckolibDep)
    "dataRuntimeOnly"(geckolibDep)

    // theoneprobe for integration
    val theoneprobeApiDep = theoneprobe.version.map { "mcjty.theoneprobe:theoneprobe:${it}:api" }
    val theoneprobeDep = theoneprobe.version.map { "mcjty.theoneprobe:theoneprobe:${it}" }
    compileOnly(theoneprobeApiDep) { (this as ModuleDependency).isTransitive = false }

    // jade for integration
    val jadeDep = jade.version.map { "maven.modrinth:jade:${it}-neoforge" }
    compileOnly(jadeDep)

    if (!helper.runningInCI.getOrElse(false)) {
        val potionbundlesDep = potionbundles.version.map { "com.github.minecraftschurlimods:potionbundles:${it}" }
        runtimeOnly(potionbundlesDep)
        runtimeOnly(theoneprobeDep) { (this as ModuleDependency).isTransitive = false }
        runtimeOnly(jeiDep)
        runtimeOnly(jadeDep)
        runtimeOnly(curiosDep)
    }

    // add internal libraries
    val codecLibVersion = project.localGradleProperty("dependency.codeclib.version")
    val codecLibDep = codecLibVersion.map { "com.github.minecraftschurlimods:codeclib:$it" }
    include(codecLibDep)
    val betterKeybindLibVersion = project.localGradleProperty("dependency.betterkeybindlib.version")
    val betterKeybindLibDep = betterKeybindLibVersion.map { "com.github.minecraftschurlimods:betterkeybindlib:$it" }
    include(betterKeybindLibDep)
    val betterHudLibVersion = project.localGradleProperty("dependency.betterhudlib.version")
    val betterHudLibDep = betterHudLibVersion.map { "com.github.minecraftschurlimods:betterhudlib:$it" }
    include(betterHudLibDep)

    val easyDatagenLibVersion = project.localGradleProperty("dependency.easydatagenlib.version")
    val easyDatagenLibApiDep = easyDatagenLibVersion.map { "com.github.minecraftschurlimods:easydatagenlib:${it}:api" }
    val easyDatagenLibDep = easyDatagenLibVersion.map { "com.github.minecraftschurlimods:easydatagenlib:${it}" }
    "apiCompileOnly"(easyDatagenLibApiDep)
    "dataImplementation"(easyDatagenLibDep)

    val jetbrainsAnnotations = "org.jetbrains:annotations:23.0.0"
    compileOnly(jetbrainsAnnotations)
    "apiCompileOnly"(jetbrainsAnnotations)
    testCompileOnly(jetbrainsAnnotations)
    "dataCompileOnly"(jetbrainsAnnotations)
}

helper.withCommonRuns()
helper.withGameTestRuns()
helper.withDataGenRuns {
    programArguments.add("--mixin.config")
    programArguments.add(helper.projectId.map { "${it}_data.mixins.json" })
}

helper.modproperties.put("catalogueItemIcon", helper.projectId.map { "$it:occulus" })

helper.mixinConfigs.add(helper.projectId.map { "${it}.mixins.json" })

minecraft.accessTransformers.file("src/main/resources/META-INF/accesstransformer.cfg")

tasks.javadoc {
    classpath = sourceSets.api.get().compileClasspath
    source = sourceSets.api.get().allJava
}

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
