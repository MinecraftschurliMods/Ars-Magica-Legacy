import at.minecraftschurli.gradle.modhelper.localGradleProperty
import at.minecraftschurli.gradle.modhelper.moddependencies.ModDependency

plugins {
    idea
    id("net.neoforged.gradle.userdev")
    id("at.minecraftschurli.gradle.modhelper")
}

helper {
    withApiSourceSet()
    withTestSourceSet()
    withJarJar()
    withCommonRuns()
    mixin("arsmagicalegacy.mixins.json")
}

dependencies {
    mods {
        // curios for integration
        val curios = curios()
        // datagen needs curios even in CI, where optional dependencies are not put on the run runtime
        "dataRuntimeOnly"(curios.artifact)
        // geckolib for animations
        val geckolib = geckolib()
        // geckolib publishes interface injections that we need to add to the classpath
        "interfaceInjection"(geckolib.artifact)
        // jade for integration
        jade()
        // jei for integration
        jei()
        // patchouli for the guide book (arcane compendium)
        required("patchouli") {
            ordering = ModDependency.Ordering.BEFORE
            artifact("vazkii.patchouli:patchouli-neoforge:{version}", apiClassifier = "api")
            repository("https://maven.blamejared.com/")
        }
    }

    val easyDatagenLibVersion = project.localGradleProperty("dependency.easydatagenlib.version")
    val easyDatagenLibApiDep = easyDatagenLibVersion.map { "at.minecraftschurli.mods:easydatagenlib:${it}:api" }
    val easyDatagenLibDep = easyDatagenLibVersion.map { "at.minecraftschurli.mods:easydatagenlib:${it}" }
    "apiCompileOnly"(easyDatagenLibApiDep)
    "dataImplementation"(easyDatagenLibDep)
    "accessTransformer"(easyDatagenLibDep)

    val colorthief = project.localGradleProperty("colorthief_version").map { "de.androidpit:color-thief:${it}" }
    compileOnly(colorthief)
    localRuntime(colorthief)
    jarJar(colorthief)

    "modCompileOnly"("org.jetbrains:annotations:26.1.0")
}

tasks.jar {
    exclude("at/minecraftschurli/arsmagicalegacy/api/data")
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
