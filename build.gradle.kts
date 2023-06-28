@file:Suppress("PropertyName")

import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import net.minecraftforge.gradle.userdev.DependencyManagementExtension
import net.minecraftforge.gradle.userdev.tasks.JarJar
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

plugins {
    idea
    eclipse
    `maven-publish`
    id("net.minecraftforge.gradle")
    id("org.spongepowered.mixin")
    id("org.parchmentmc.librarian.forgegradle")
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

// region helpers
val mod_group: String by project
val mod_id: String by project
val mod_name: String by project
val mod_version: String by project
val mod_author: String by project
val mod_credits: String by project
val mod_url: String by project
val url: String by project
val github: String by project
val vendor: String by project
val java_version: String by project
val mixin_version: String by project
val mappings_channel: String by project
val mappings_version: String by project
val mc_version: String by project
val mc_major_version: String by project
val forge_version: String by project
val jei_version: String by project
val top_version: String by project
val curios_version: String by project
val patchouli_version: String by project
val geckolib_version: String by project
val potionbundles_version: String by project
val controlling_version: String by project

val issue_tracker_url by extra("https://github.com/${github}/issues")

val SourceSetContainer.api: NamedDomainObjectProvider<SourceSet>
    get() = named("api")

val SourceSetContainer.data: NamedDomainObjectProvider<SourceSet>
    get() = named("data")

val Provider<SourceSet>.output: Provider<SourceSetOutput>
    get() = map { it.output }

val Provider<SourceSet>.allSource: Provider<SourceDirectorySet>
    get() = map { it.allSource }

val Provider<SourceSet>.compileClasspath: Provider<FileCollection>
    get() = map { it.compileClasspath }

fun DependencyManagementExtension.deobf(dependency: Any, configure: Dependency.() -> Unit) = deobf(dependency, closureOf(configure))

open class ModConfigurationsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.configurations {
            val library: Configuration by creating
            val modAPI: Configuration by creating
            val runtimeMod: Configuration by creating
            val include: Configuration by creating {
                isTransitive = false
            }
            val datagenLibrary: Configuration by creating {
                isCanBeConsumed = false
                isCanBeResolved = true
                isTransitive = false
            }
            project.sourceSets.forEach { sourceSet ->
                val extLib: Configuration = create(sourceSet.getTaskName(null, "externalLibrary"))
                val impl: Configuration = getByName(sourceSet.implementationConfigurationName)
                val compile: Configuration = getByName(sourceSet.compileOnlyConfigurationName)
                val runtime: Configuration = getByName(sourceSet.runtimeOnlyConfigurationName)
                val ccp: Configuration = getByName(sourceSet.compileClasspathConfigurationName)
                val rcp: Configuration = getByName(sourceSet.runtimeClasspathConfigurationName)
                impl.extendsFrom(extLib)
                compile.extendsFrom(library)
                compile.extendsFrom(modAPI)
                if (sourceSet.name != "api") {
                    if (sourceSet.name != "main") {
                        rcp.extendsFrom(minecraft.get())
                    }
                    runtime.extendsFrom(runtimeMod)
                }
                if (sourceSet.name != "main") {
                    ccp.extendsFrom(minecraft.get())
                }
                impl.extendsFrom(include)
            }
            jarJar.get().extendsFrom(include)

            val dataImplementation: Configuration = getByName("dataImplementation")
            dataImplementation.extendsFrom(datagenLibrary)
        }
    }
}

val library: Configuration
    get() = configurations.getByName("library")

val modAPI: Configuration
    get() = configurations.getByName("modAPI")

val runtimeMod: Configuration
    get() = configurations.getByName("runtimeMod")

val include: Configuration
    get() = configurations.getByName("include")

val datagenLibrary: Configuration
    get() = configurations.getByName("datagenLibrary")

val dataImplementation: Configuration
    get() = configurations.getByName("dataImplementation")

val TaskContainer.editorconfigCheck: TaskProvider<Task>
    get() = named("editorconfigCheck")

val TaskContainer.apiClasses: TaskProvider<Task>
    get() = named("apiClasses")

val TaskContainer.apiJar: TaskProvider<Jar>
    get() = named<Jar>("apiJar")

val TaskContainer.sourcesJar: TaskProvider<Jar>
    get() = named<Jar>("sourcesJar")

val TaskContainer.javadocJar: TaskProvider<Jar>
    get() = named<Jar>("javadocJar")

val TaskContainer.deobfJar: TaskProvider<Jar>
    get() = named<Jar>("deobfJar")

val TaskContainer.jarJar: TaskProvider<JarJar>
    get() = named<JarJar>("jarJar")

// endregion

// region project

group = mod_group
version = "${mc_version}-${mod_version}"
base.archivesName.set(mod_id)

if (System.getenv("RELEASE_TYPE") != null) {
    status = System.getenv("RELEASE_TYPE").lowercase()
    if (status == "snapshot") status = (status as String).uppercase()
} else {
    status = "SNAPSHOT"
}

if (status != "release") {
    version = "${version}-${status}"
}

// endregion

mixin {
    add(sourceSets.main.get(), "${mod_id}.refmap.json")
    config("${mod_id}.mixins.json")
    dumpTargetOnFailure = true
}

java {
    withSourcesJar()
    withJavadocJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(java_version))
        if (System.getenv("GITHUB_ACTIONS") == null || System.getenv("GITHUB_ACTIONS").isEmpty()) {
            vendor.set(JvmVendorSpec.matching("JetBrains s.r.o."))
        } else {
            vendor.set(JvmVendorSpec.ADOPTIUM)
        }
    }
}

sourceSets {
    create("api")
    main {
        resources {
            srcDir(file("src/main/generated"))
            exclude(".cache")
        }
    }
    create("data")
}

apply<ModConfigurationsPlugin>()

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "Sponge maven"
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
        content {
            includeGroup("org.spongepowered")
        }
    }
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
}

dependencies {
    minecraft(group = "net.minecraftforge", name = "forge", version = "${mc_version}-${forge_version}")
    annotationProcessor(group = "org.spongepowered", name = "mixin", version = mixin_version, classifier = "processor")

    // jei for integration
    modAPI(fg.deobf("mezz.jei:jei-1.20.1-common-api:${jei_version}"))
    runtimeMod(fg.deobf("mezz.jei:jei-1.20.1-forge:${jei_version}"))

    // curios for additional inventory slots
    modAPI(fg.deobf("top.theillusivec4.curios:curios-forge:${curios_version}:api"))
    runtimeMod(fg.deobf("top.theillusivec4.curios:curios-forge:${curios_version}"))

    // patchouli for the guide book (arcane compendium)
    modAPI(fg.deobf("vazkii.patchouli:Patchouli:${patchouli_version}:api"))
    runtimeMod(fg.deobf("vazkii.patchouli:Patchouli:${patchouli_version}"))

    // geckolib for animations
    modAPI(fg.deobf("software.bernie.geckolib:geckolib-forge-${mc_major_version}:${geckolib_version}"))
    runtimeMod(fg.deobf("software.bernie.geckolib:geckolib-forge-${mc_major_version}:${geckolib_version}"))

    // theoneprobe for integration
    modAPI(fg.deobf("mcjty.theoneprobe:theoneprobe:${top_version}:api") { (this as ModuleDependency).setTransitive(false) })
    runtimeMod(fg.deobf("mcjty.theoneprobe:theoneprobe:${top_version}") { (this as ModuleDependency).setTransitive(false) })

    if (System.getenv("GITHUB_ACTIONS") == null || System.getenv("GITHUB_ACTIONS").isEmpty()) {
        if (project.hasProperty("github_packages_user") && project.hasProperty("github_packages_token")) {
            println("Using github packages")
        }
        runtimeMod(fg.deobf("com.github.minecraftschurlimods:potionbundles:${potionbundles_version}"))
        //runtimeMod(fg.deobf("com.blamejared.controlling:Controlling-forge-1.19.2:${project.controlling_version}"))
    }

    // add internal libraries
    include(fg.deobf("com.github.minecraftschurlimods:simplenetlib:1.20.1-1.0-SNAPSHOT") {
        jarJar.ranged(this, "[1.20.1-1.0-SNAPSHOT,)")
    })
    include(fg.deobf("com.github.minecraftschurlimods:codeclib:1.20.1-1.0-SNAPSHOT") {
        jarJar.ranged(this, "[1.20.1-1.0-SNAPSHOT,)")
    })
    include(fg.deobf("com.github.minecraftschurlimods:betterkeybindlib:1.19-1.1-SNAPSHOT") {
        jarJar.ranged(this, "[1.19-1.1-SNAPSHOT,)")
    })
    include(fg.deobf("com.github.minecraftschurlimods:betterhudlib:1.20.1-1.0-SNAPSHOT") {
        jarJar.ranged(this, "[1.20.1-1.0-SNAPSHOT,)")
    })
    modAPI(fg.deobf("com.github.minecraftschurlimods:easydatagenlib:1.19.3-1.1.1-SNAPSHOT"))
    datagenLibrary(fg.deobf("com.github.minecraftschurlimods:easydatagenlib:1.19.3-1.1.1-SNAPSHOT"))
    datagenLibrary(fg.deobf("com.github.minecraftschurlimods:patchouli_datagen-forge-1.19.3:1.0-SNAPSHOT"))

    implementation(sourceSets.api.output)

    dataImplementation(sourceSets.api.output)
    dataImplementation(sourceSets.main.output)

    testImplementation(sourceSets.api.output)
    testImplementation(sourceSets.main.output)

    library("org.jetbrains:annotations:23.0.0")
}

minecraft {
    mappings(mappings_channel, mappings_version)
    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))
    runs {
        create("client") {
            ideaModule("${project.name.replace(' ', '_')}.main")
            property("forge.logging.markers", "REGISTRIES")
            property("forge.enabledGameTestNamespaces", mod_id)
            mods.create(mod_id) {
                source(sourceSets.api.get())
                source(sourceSets.main.get())
                source(sourceSets.test.get())
            }
        }
        create("server") {
            ideaModule("${project.name.replace(' ', '_')}.main")
            singleInstance(true)
            property("forge.logging.markers", "REGISTRIES")
            mods.create(mod_id) {
                source(sourceSets.api.get())
                source(sourceSets.main.get())
            }
        }
        create("data") {
            ideaModule("${project.name.replace(' ', '_')}.data")
            singleInstance(true)
            args("--mod", mod_id, "--all", "--output", file("src/main/generated/"), "--existing", file("src/main/resources/"), "--mixin.config", "${mod_id}_data.mixins.json")
            mods.create(mod_id) {
                source(sourceSets.api.get())
                source(sourceSets.main.get())
                source(sourceSets.data.get())
            }
        }
        create("gameTestServer") {
            ideaModule("${project.name.replace(' ', '_')}.test")
            singleInstance(true)
            jvmArg("-ea") // Enable assertions
            property("forge.logging.markers", "REGISTRIES")
            property("forge.enabledGameTestNamespaces", mod_id)

            mods.create(mod_id) {
                source(sourceSets.api.get())
                source(sourceSets.main.get())
                source(sourceSets.test.get())
            }
        }
    }
    runs.configureEach {
        workingDirectory(file("run/${name}"))
        property("forge.logging.console.level", "debug")
        if (System.getenv("GITHUB_ACTIONS") == null || System.getenv("GITHUB_ACTIONS").isEmpty()) {
            jvmArg("-XX:+AllowEnhancedClassRedefinition")
        }
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    processResources {
        val buildProps: MutableMap<String, *> = project.properties.toMutableMap()
        buildProps.values.removeIf { it !is CharSequence && it !is Number && it !is Boolean }
        inputs.properties(buildProps)

        filesMatching("META-INF/mods.toml") {
            expand(buildProps)
        }
        // minify json files
        doLast {
            fileTree("dir" to outputs.files.asPath, "include" to "**/*.json").forEach {
                it.writeText(JsonOutput.toJson(JsonSlurper().parse(it)))
            }
        }
    }

    javadoc {
        source = sourceSets.api.get().allJava
        options.encoding = "UTF-8"
        (options as StandardJavadocDocletOptions).tags = listOf(
                "side:a:Side:",
                "apiNote:a:API Note:",
                "implSpec:a:Implementation Requirements:",
                "implNote:a:Implementation Note:"
        )
        if (JavaVersion.current().isJava9Compatible) {
            (options as CoreJavadocOptions).addBooleanOption("html5", true)
        }
    }

    jar {
        archiveClassifier.set("slim")
        from(sourceSets.main.output)
        from(sourceSets.api.output)
        finalizedBy("reobfJar")
    }

    sourcesJar {
        from(sourceSets.main.allSource)
        from(sourceSets.api.allSource)
    }

    this.jarJar {
        archiveClassifier.set("")
        from(sourceSets.api.output)
        finalizedBy("reobfJarJar")
    }

    register<Jar>("apiJar") {
        dependsOn("apiClasses")
        archiveClassifier.set("api")
        from(sourceSets.api.allSource)
        from(sourceSets.api.output)
        finalizedBy("reobfJar")
    }

    register<Jar>("deobfJar") {
        dependsOn(classes)
        dependsOn(apiClasses)
        archiveClassifier.set("deobf")
        from(sourceSets.main.output)
        from(sourceSets.api.output)
    }

    withType<Jar>().configureEach {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        var extension = ""
        if (archiveClassifier.isPresent) {
            extension = archiveClassifier.get()
            if (extension != "") {
                extension = "-${extension}"
            }
        }
        from("LICENSE.md")
        manifest {
            attributes(mapOf(
                    "Maven-Artifact"         to "${mod_group}:${base.archivesName.get()}:${project.version}",
                    "Specification-Title"    to base.archivesName.get(),
                    "Specification-Vendor"   to vendor,
                    "Specification-Version"  to "1",
                    "Implementation-Title"   to "${base.archivesName.get()}${extension}",
                    "Implementation-Version" to mod_version,
                    "Implementation-Vendor"  to vendor,
                    "Built-On-Java"          to "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                    "Built-On"               to "${mc_version}-${forge_version}",
                    "Timestamp"              to DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
                    "FMLModType"             to "MOD",
            ))
        }

        exclude("**/*.psd")
        exclude("**/*.bbmodel")
    }

    register("reobf") {
        dependsOn("reobfJar", "reobfApiJar", "reobfJarJar")
    }

    register("setupGithubActions") {
        doLast {
            println("##[set-output name=modid;]${mod_id}")
            println("##[set-output name=version;]${project.version}")
        }
    }

    publish {
        dependsOn(check)
    }
}

reobf {
    create("apiJar") { classpath.from(sourceSets.api.compileClasspath) }
    create("jar") { classpath.from(sourceSets.main.compileClasspath) }
    create("jarJar") { classpath.from(sourceSets.main.compileClasspath) }
}

artifacts {
    archives(tasks.jar)
    archives(tasks.apiJar)
    archives(tasks.sourcesJar)
    archives(tasks.javadocJar)
    archives(tasks.deobfJar)
}

publishing {
    publications.create<MavenPublication>("${base.archivesName.get()}ToMaven") {
        artifact(tasks.apiJar)
        artifact(tasks.deobfJar)
        groupId = project.group as String
        artifactId = base.archivesName.get()
        version = project.version as String
        from(components["java"])
        jarJar.component(this)
        pom {
            name.set(mod_name)
            url.set(mod_url)
            packaging = "jar"
            scm {
                connection.set("scm:git:git://github.com/${github}.git")
                developerConnection.set("scm:git:git@github.com:${github}.git")
                url.set("https://github.com/${github}")
            }
            issueManagement {
                system.set("github")
                url.set("https://github.com/${github}/issues")
            }
            organization {
                name.set("Minecraftschurli Mods")
                url.set("https://github.com/MinecraftschurliMods")
            }
            developers {
                developer {
                    id.set("minecraftschurli")
                    url.set("https://github.com/Minecraftschurli")
                    name.set("Minecraftschurli")
                    email.set("minecraftschurli@gmail.com")
                    organization.set("Minecraftschurli Mods")
                    organizationUrl.set("https://github.com/MinecraftschurliMods")
                    timezone.set("Europe/Vienna")
                }
                developer {
                    id.set("ichhabehunger54")
                    name.set("IchHabeHunger54")
                    url.set("https://github.com/IchHabeHunger54")
                    organization.set("Minecraftschurli Mods")
                    organizationUrl.set("https://github.com/MinecraftschurliMods")
                    timezone.set("Europe/Vienna")
                }
            }
            licenses {
                license {
                    name.set("\"Don't Be a Jerk\" License")
                    url.set("https://github.com/${github}/blob/version/${mc_version}/LICENSE.md")
                    distribution.set("repo")
                }
            }
        }
    }
    repositories {
        maven {
            if ((System.getenv("MAVEN_USER") != null) &&
                (System.getenv("MAVEN_PASSWORD") != null) &&
                (System.getenv("MAVEN_URL") != null)) {
                url = uri(System.getenv("MAVEN_URL"))
                credentials {
                    username = System.getenv("MAVEN_USER")
                    password = System.getenv("MAVEN_PASSWORD")
                }
            } else {
                url = uri("$buildDir/repo")
            }
        }
    }
}

idea.module.excludeDirs.addAll(arrayOf("run", "out", "libs").map(::file))

evaluationDependsOnChildren()
