package com.github.minecraftschurlimods.moddev

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import net.minecraftforge.gradle.userdev.UserDevExtension
import net.minecraftforge.gradle.userdev.jarjar.JarJarProjectExtension
import net.minecraftforge.gradle.userdev.tasks.JarJar
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace
import org.gradle.api.*
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.external.javadoc.CoreJavadocOptions
import org.gradle.external.javadoc.StandardJavadocDocletOptions
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.ide.idea.model.IdeaModel
import java.time.Instant
import java.time.format.DateTimeFormatter

class ModDevExtension(private val project: Project) {
    private val modDependencies: NamedDomainObjectContainer<ModDependency> = project.objects.domainObjectContainer<ModDependency> { ModDependency(it, project) }

    val mixinVersion: Property<String> = project.objects.property<String>()
    val minecraftVersion: Property<String> = project.objects.property<String>()
    val forgeVersion: Property<String> = project.objects.property<String>()
    val loaderVersion: Property<String> = project.objects.property<String>()
    val fullForgeVersion: Property<String> = project.objects.property<String>().convention(minecraftVersion.zip(forgeVersion) { mc, forge -> "${mc}-${forge}" })
    val mappingsChannel: Property<String> = project.objects.property<String>()
    val parchmentVersion: Property<String> = project.objects.property<String>()
    val mappingsVersion: Property<String> = project.objects.property<String>().convention(minecraftVersion.zip(mappingsChannel) { mc, channel -> if (channel == "official") mc else "${parchmentVersion.get()}-$mc" })
    val forgeVersionRange: Property<String> = project.objects.property<String>().convention(forgeVersion.map { "[$it,)" })
    val minecraftVersionRange: Property<String> = project.objects.property<String>().convention(minecraftVersion.map { "[$it,)" })


    val hasSeparateApi: Property<Boolean> = project.objects.property<Boolean>().convention(true)
    val apiSourceSet: Property<SourceSet> = project.objects.property<SourceSet>()
    val hasSeparateDatagen: Property<Boolean> = project.objects.property<Boolean>().convention(true)
    val datagenSourceSet: Property<SourceSet> = project.objects.property<SourceSet>()
    val hasTests: Property<Boolean> = project.objects.property<Boolean>().convention(true)
    val testSourceSet: Property<SourceSet> = project.objects.property<SourceSet>()
    val mainSourceSet: Property<SourceSet> = project.objects.property<SourceSet>().convention(project.the<SourceSetContainer>().named("main"))

    val additionalManifestAttributes: MapProperty<String, String> = project.objects.mapProperty<String, String>()
    val jarExcludePatterns: SetProperty<String> = project.objects.setProperty<String>()

    val runDirectory: DirectoryProperty = project.objects.directoryProperty().convention(project.layout.dir(project.provider { project.file("run") }))
    val datagenOutput: DirectoryProperty = project.objects.directoryProperty().convention(project.layout.dir(project.provider { project.file("src/main/generated") }))
    val licenseFile: RegularFileProperty = project.objects.fileProperty()
    val logoFile: RegularFileProperty = project.objects.fileProperty()
    val accessTransformers: ConfigurableFileCollection = project.objects.fileCollection()
    val dependencies: ModDependenciesScope = ModDependenciesScope(modDependencies, project)

    val modId: Property<String> = project.objects.property<String>()
    val modName: Property<String> = project.objects.property<String>()
    val modUrl: Property<String> = project.objects.property<String>()
    val modDescription: Property<String> = project.objects.property<String>()
    val curseforgeProjectId: Property<Int> = project.objects.property<Int>()
    val modrinthProjectId: Property<String> = project.objects.property<String>()
    val credits: Property<String> = project.objects.property<String>()
    val authors: NamedDomainObjectContainer<Author> = project.objects.domainObjectContainer<Author> { Author(it, project.objects) }

    val githubOrg: Property<String> = project.objects.property<String>()
    val githubRepo: Property<String> = project.objects.property<String>()
    val organizationName: Property<String> = project.objects.property<String>()
    val organisationUrl: Property<String> = project.objects.property<String>().convention(githubOrg.map { "https://github.com/$it" })
    val githubRepoUrl: Property<String> = project.objects.property<String>().convention(githubOrg.zip(githubRepo) { org, repo -> "https://github.com/$org/$repo" })
    val githubMainBranch: Property<String> = project.objects.property<String>().convention("master")
    val githubMainBranchUrl: Property<String> = project.objects.property<String>().convention(githubRepoUrl.zip(githubMainBranch) { url, branch -> "$url/blob/$branch" })
    val issueTrackerSystem: Property<String> = project.objects.property<String>().convention("github")
    val issueTrackerUrl: Property<String> = project.objects.property<String>().convention(githubRepoUrl.map { "$it/issues" })
    val ciSystem: Property<String> = project.objects.property<String>().convention("github-actions")
    val ciUrl: Property<String> = project.objects.property<String>().convention(githubRepoUrl.map { "$it/actions" })
    val licenseName: Property<String> = project.objects.property<String>().convention("All Rights Reserved")
    val licenseUrl: Property<String> = project.objects.property<String>().convention(githubMainBranchUrl.zip(licenseFile) { url, file -> "$url/${file.asFile.relativeTo(project.projectDir)}" })

    val modsToml = project.tasks.register<GenerateModsTomlTask>("generateModsToml") {
        dependencies.addAll(modDependencies)
        authors.addAll(this@ModDevExtension.authors)
        modId.set(this@ModDevExtension.modId)
        displayName.set(modName)
        credits.set(this@ModDevExtension.credits)
        loaderVersion.set(this@ModDevExtension.loaderVersion)
        license.set(licenseName)
        description.set(modDescription)
        displayURL.set(modUrl)
        curseforgeProjectId.set(this@ModDevExtension.curseforgeProjectId)
        modrinthProjectId.set(this@ModDevExtension.modrinthProjectId)
        forgeVersionRange.set(this@ModDevExtension.forgeVersionRange)
        minecraftVersionRange.set(this@ModDevExtension.minecraftVersionRange)
        issueTrackerURL.set(issueTrackerUrl)
        logoFile.set(this@ModDevExtension.logoFile)
    }

    operator fun invoke(action: ModDevExtension.() -> Unit) {
        project.the<JavaPluginExtension>().apply {
            withJavadocJar()
            withSourcesJar()
        }
        action()
        configure()
    }

    private fun configure() {
        val sourceSetContainer = project.the<SourceSetContainer>()

        println("Using MinecraftForge version ${fullForgeVersion.get()} and mappings from ${mappingsChannel.get()} with version ${mappingsVersion.get()}")
        if (mixinVersion.isPresent) {
            println("Using Mixin version ${mixinVersion.get()}")
        }

        mainSourceSet.get().apply {
            resources {
                srcDir(datagenOutput).exclude(".cache")
            }
        }

        setupDependencies(sourceSetContainer)

        project.the<UserDevExtension>().apply {
            mappings(mappingsChannel, mappingsVersion)
            accessTransformers(this@ModDevExtension.accessTransformers)
            setupRuns()
        }

        setupTasks()
        setupArtifacts()
        setupPublishing()

        project.the<IdeaModel>().module.excludeDirs.add(runDirectory.get().asFile)
    }

    private fun setupPublishing() {
        val publishing = project.the<PublishingExtension>()
        publishing.publications.create<MavenPublication>("${project.the<BasePluginExtension>().archivesName.get()}ToMaven") {
            this.configurePublication()
        }
    }

    private fun MavenPublication.configurePublication() {
        project.the<JarJarProjectExtension>().component(this)

        if (hasSeparateApi.get()) {
            artifact(project.tasks.named("apiJar"))
        }
        artifact(project.tasks.named("deobfJar"))

        groupId = project.group as String
        artifactId = project.the<BasePluginExtension>().archivesName.get()
        version = project.version as String

        from(project.components["java"])

        pom {
            name.set(modName)
            url.set(modUrl)
            packaging = "jar"
            scm {
                connection.set(githubOrg.zip(githubRepo) { org, repo -> "scm:git:git://github.com/$org/$repo.git" })
                developerConnection.set(githubOrg.zip(githubRepo) { org, repo -> "scm:git:git@github.com:$org/$repo.git" })
                url.set(githubRepoUrl)
            }
            issueManagement {
                system.set(issueTrackerSystem)
                url.set(issueTrackerUrl)
            }
            ciManagement {
                system.set(ciSystem)
                url.set(ciUrl)
            }
            organization {
                name.set(organizationName)
                url.set(organisationUrl)
            }
            developers {
                for (author in authors) {
                    developer {
                        id.set(author.name)
                        name.set(author.displayName)
                        email.set(author.email)
                        url.set(author.url)
                        organization.set(author.organization)
                        organizationUrl.set(author.organizationUrl)
                        timezone.set(author.timezone)
                        roles.set(author.roles)
                        properties.set(author.properties)
                    }
                }
            }
            licenses {
                license {
                    name.set(licenseName)
                    url.set(licenseUrl)
                    distribution.set("repo")
                }
            }
        }
    }

    private fun setupArtifacts() {
        project.artifacts {
            add("archives", project.tasks.named("jar"))
            add("archives", project.tasks.named("sourcesJar"))
            add("archives", project.tasks.named("deobfJar"))
            add("archives", project.tasks.named("jarJar"))
            add("archives", project.tasks.named("javadocJar"))
            if (hasSeparateApi.get()) {
                add("archives", project.tasks.named("apiJar"))
            }
        }
    }

    private fun setupTasks() {
        project.the<JarJarProjectExtension>().disableDefaultSources(true)
        project.tasks {
            withType<JavaCompile> {
                options.encoding = "UTF-8"
            }
            named<Copy>("processResources") {
                from(modsToml) {
                    into("META-INF")
                }
                // minify json files
                doLast("minifyJson") {
                    project.fileTree("dir" to outputs.files.asPath, "include" to "**/*.json").forEach {
                        it.writeText(JsonOutput.toJson(JsonSlurper().parse(it)))
                    }
                }
            }
            named<Javadoc>("javadoc") {
                source = (if (hasSeparateApi.get()) apiSourceSet else mainSourceSet).get().allJava
                options {
                    encoding = "UTF-8"
                    if (this !is CoreJavadocOptions) return@options
                    addStringOption("Xdoclint:all,-missing", "-public")
                    if (JavaVersion.current().isJava9Compatible) {
                        (options as CoreJavadocOptions).addBooleanOption("html5", true)
                    }
                    if (this !is StandardJavadocDocletOptions) return@options
                    tags = listOf(
                        "side:a:Side:",
                        "apiNote:a:API Note:",
                        "implSpec:a:Implementation Requirements:",
                        "implNote:a:Implementation Note:"
                    )
                }
            }
            named<Jar>("jar") {
                archiveClassifier.set("slim")
                from(mainSourceSet.map { it.output })
                if (hasSeparateApi.get()) {
                    from(apiSourceSet.map { it.output })
                }
                finalizedBy("reobfJar")
            }
            named<Jar>("sourcesJar") {
                from(mainSourceSet.map { it.allSource })
                if (hasSeparateApi.get()) {
                    from(apiSourceSet.map { it.allSource })
                }
            }
            named<JarJar>("jarJar") {
                archiveClassifier.set("")
                from(mainSourceSet.map { it.output })
                if (hasSeparateApi.get()) {
                    from(apiSourceSet.map { it.output })
                }
                finalizedBy("reobfJarJar")
            }
            if (hasSeparateApi.get()) {
                register<Jar>("apiJar") {
                    archiveClassifier.set("api")
                    from(apiSourceSet.map { it.allSource })
                    from(apiSourceSet.map { it.output })
                    finalizedBy("reobfApiJar")
                }
            }
            register<Jar>("deobfJar") {
                archiveClassifier.set("deobf")
                from(mainSourceSet.map { it.output })
                if (hasSeparateApi.get()) {
                    from(apiSourceSet.map { it.output })
                }
            }
            withType<Jar> {
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                from(licenseFile)
                exclude(jarExcludePatterns.get())
                manifest.attributes(
                    "Maven-Artifact" to "${project.group}:${archiveBaseName.get()}:${archiveVersion.get()}${if (archiveClassifier.orNull.isNullOrBlank()) "" else "-${archiveClassifier.get()}"}",
                    "Built-On-Java" to "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                    "Built-On-Forge" to forgeVersion.get(),
                    "Built-On-Minecraft" to minecraftVersion.get(),
                    "Timestamp" to DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
                    "FMLModType" to "MOD"
                )
                manifest.attributes(additionalManifestAttributes.get())
            }
        }

        val reobf = project.extensions.getByName<NamedDomainObjectContainer<RenameJarInPlace>>("reobf")
        if (hasSeparateApi.get()) {
            reobf.create("apiJar") {
                libraries.setFrom(apiSourceSet.map { it.compileClasspath })
            }
        }
    }

    private fun setupDependencies(sourceSetContainer: SourceSetContainer) {
        val minecraftConfiguration = project.configurations.getByName("minecraft")
        val jarJarConfiguration = project.configurations.getByName("jarJar")
        val implementation = project.configurations.getByName(mainSourceSet.get().implementationConfigurationName)
        val compileOnly = project.configurations.getByName(mainSourceSet.get().compileOnlyConfigurationName)
        val runtimeOnly = project.configurations.getByName(mainSourceSet.get().runtimeOnlyConfigurationName)

        val compileDataLibrary = project.configurations.create("compileDataLibrary")
        val runtimeDataLibrary = project.configurations.create("runtimeDataLibrary")
        val implementationDataLibrary = project.configurations.create("implementationDataLibrary")
        compileDataLibrary.extendsFrom(implementationDataLibrary)
        runtimeDataLibrary.extendsFrom(implementationDataLibrary)

        val compileEmbedded = project.configurations.create("compileEmbedded")
        val runtimeEmbedded = project.configurations.create("runtimeEmbedded")
        val implementationEmbedded = project.configurations.create("implementationEmbedded")
        compileEmbedded.extendsFrom(implementationEmbedded)
        runtimeEmbedded.extendsFrom(implementationEmbedded)
        compileOnly.extendsFrom(compileEmbedded)
        runtimeOnly.extendsFrom(runtimeEmbedded)
        jarJarConfiguration.extendsFrom(runtimeEmbedded)

        val compileApiDependency = project.configurations.create("compileApiDependency")
        val runtimeApiDependency = project.configurations.create("runtimeApiDependency")
        val implementationApiDependency = project.configurations.create("implementationApiDependency")
        compileApiDependency.extendsFrom(implementationApiDependency)
        runtimeApiDependency.extendsFrom(implementationApiDependency)
        compileOnly.extendsFrom(compileApiDependency)
        runtimeOnly.extendsFrom(runtimeApiDependency)

        val compileDependency = project.configurations.create("compileDependency")
        val runtimeDependency = project.configurations.create("runtimeDependency")
        val implementationDependency = project.configurations.create("implementationDependency")
        compileDependency.extendsFrom(implementationDependency)
        runtimeDependency.extendsFrom(implementationDependency)
        compileOnly.extendsFrom(compileDependency)
        runtimeOnly.extendsFrom(runtimeDependency)

        val library = project.configurations.create("library")
        implementation.extendsFrom(library)

        val compileTestLibrary = project.configurations.create("compileTestLibrary")
        val runtimeTestLibrary = project.configurations.create("runtimeTestLibrary")
        val implementationTestLibrary = project.configurations.create("implementationTestLibrary")

        if (hasSeparateApi.get()) {
            apiSourceSet.convention(project.provider { sourceSetContainer.maybeCreate("api") }).finalizeValueOnRead()
            val apiCompileOnly = project.configurations.getByName(apiSourceSet.get().compileOnlyConfigurationName)
            apiCompileOnly.extendsFrom(minecraftConfiguration)
            apiCompileOnly.extendsFrom(compileEmbedded)
            apiCompileOnly.extendsFrom(compileApiDependency)
            apiCompileOnly.extendsFrom(library)
        }
        if (hasSeparateDatagen.get()) {
            datagenSourceSet.convention(project.provider { sourceSetContainer.maybeCreate("data") }).finalizeValueOnRead()
            val dataImplementation = project.configurations.getByName(datagenSourceSet.get().implementationConfigurationName)
            val dataCompileOnly = project.configurations.getByName(datagenSourceSet.get().compileOnlyConfigurationName)
            val dataRuntimeOnly = project.configurations.getByName(datagenSourceSet.get().runtimeOnlyConfigurationName)
            dataImplementation.extendsFrom(minecraftConfiguration)
            dataCompileOnly.extendsFrom(compileDataLibrary)
            dataRuntimeOnly.extendsFrom(runtimeDataLibrary)
            dataImplementation.extendsFrom(library)
        } else {
            compileOnly.extendsFrom(compileDataLibrary)
            runtimeOnly.extendsFrom(runtimeDataLibrary)
        }
        if (hasTests.get()) {
            testSourceSet.convention(project.provider { sourceSetContainer.maybeCreate("test") }).finalizeValueOnRead()
            val testImplementation = project.configurations.getByName(testSourceSet.get().implementationConfigurationName)
            val testCompileOnly = project.configurations.getByName(testSourceSet.get().compileOnlyConfigurationName)
            val testRuntimeOnly = project.configurations.getByName(testSourceSet.get().runtimeOnlyConfigurationName)
            testImplementation.extendsFrom(minecraftConfiguration)
            testCompileOnly.extendsFrom(compileTestLibrary)
            testRuntimeOnly.extendsFrom(runtimeTestLibrary)
            testCompileOnly.extendsFrom(compileDependency)
            testRuntimeOnly.extendsFrom(runtimeDependency)
            testImplementation.extendsFrom(library)
        }

        val getConfigurationFor: (ModDependency.Type, ModDependencyArtifact.Type) -> Configuration = { dependencyType, artifactType ->
            when (dependencyType) {
                ModDependency.Type.INCLUDE, ModDependency.Type.INCLUDED_LIBRARY -> when (artifactType) {
                    ModDependencyArtifact.Type.COMPILE -> compileEmbedded
                    ModDependencyArtifact.Type.RUNTIME -> runtimeEmbedded
                    ModDependencyArtifact.Type.ALL -> implementationEmbedded
                }

                ModDependency.Type.COMPAT -> when (artifactType) {
                    ModDependencyArtifact.Type.COMPILE -> compileOnly
                    ModDependencyArtifact.Type.RUNTIME -> throw IllegalArgumentException("Runtime dependencies are not allowed for compat dependencies")
                    ModDependencyArtifact.Type.ALL -> compileOnly
                }

                ModDependency.Type.RUNTIME_COMPAT, ModDependency.Type.DEPENDENCY -> when (artifactType) {
                    ModDependencyArtifact.Type.RUNTIME -> runtimeOnly
                    ModDependencyArtifact.Type.COMPILE -> compileOnly
                    ModDependencyArtifact.Type.ALL -> implementation
                }

                ModDependency.Type.DATA_LIBRARY -> when (artifactType) {
                    ModDependencyArtifact.Type.RUNTIME -> runtimeDataLibrary
                    ModDependencyArtifact.Type.COMPILE -> compileDataLibrary
                    ModDependencyArtifact.Type.ALL -> implementationDataLibrary
                }

                ModDependency.Type.API_DEPENDENCY -> when (artifactType) {
                    ModDependencyArtifact.Type.RUNTIME -> runtimeApiDependency
                    ModDependencyArtifact.Type.COMPILE -> compileApiDependency
                    ModDependencyArtifact.Type.ALL -> implementationApiDependency
                }

                ModDependency.Type.LIBRARY -> library
                ModDependency.Type.TEST_LIBRARY -> when (artifactType) {
                    ModDependencyArtifact.Type.RUNTIME -> compileTestLibrary
                    ModDependencyArtifact.Type.COMPILE -> runtimeTestLibrary
                    ModDependencyArtifact.Type.ALL -> implementationTestLibrary
                }
            }
        }

        project.dependencies {
            minecraftConfiguration(group = "net.minecraftforge", name = "forge", version = fullForgeVersion.get())
            if (mixinVersion.isPresent) {
                project.configurations.getByName("annotationProcessor")(group = "org.spongepowered", name = "mixin", version = mixinVersion.get(), classifier = "processor")
            }

            for (dependency in modDependencies) {
                val dependencyType = dependency.type.get()
                if (dependencyType == ModDependency.Type.TEST_LIBRARY && !hasTests.get()) {
                    throw IllegalStateException("Cannot add test library dependency when tests are disabled")
                }
                for (artifact in dependency.artifacts) {
                    getConfigurationFor(dependencyType, artifact.type)(artifact.asDependency())
                }
            }

            if (hasSeparateApi.get()) {
                implementation(apiSourceSet.map { it.output })
            }

            if (hasSeparateDatagen.get()) {
                val dataImplementation = project.configurations.getByName(datagenSourceSet.get().implementationConfigurationName)
                if (hasSeparateApi.get()) {
                    dataImplementation(apiSourceSet.map { it.output })
                }
                dataImplementation(mainSourceSet.map { it.output })
            }

            if (hasTests.get()) {
                val testImplementation = project.configurations.getByName(testSourceSet.get().implementationConfigurationName)
                if (hasSeparateApi.get()) {
                    testImplementation(apiSourceSet.map { it.output })
                }
                testImplementation(mainSourceSet.map { it.output })
            }
        }
    }

    private fun UserDevExtension.setupRuns() {
        val rootModuleName = project.name.replace(' ', '_')

        runs.create("client") {
            ideaModule("$rootModuleName.${mainSourceSet.get().name}")

            property("forge.logging.markers", "REGISTRIES")
            property("forge.enabledGameTestNamespaces", modId.get())

            mods.create(modId.get()) {
                if (hasSeparateApi.get()) {
                    source(apiSourceSet.get())
                }
                source(mainSourceSet.get())
                if (hasTests.get()) {
                    source(testSourceSet.get())
                }
            }
        }

        runs.create("server") {
            ideaModule("$rootModuleName.${mainSourceSet.get().name}")

            singleInstance(true)
            property("forge.logging.markers", "REGISTRIES")

            mods.create(modId.get()) {
                if (hasSeparateApi.get()) {
                    source(apiSourceSet.get())
                }
                source(mainSourceSet.get())
            }
        }

        runs.create("data") {
            ideaModule("$rootModuleName.${if (hasSeparateDatagen.get()) datagenSourceSet.get().name else mainSourceSet.get().name}")
            val output = datagenOutput.get().asFile

            singleInstance(true)
            args("--mod", modId.get())
            arg("--all")
            args("--output", output)
            for (file in mainSourceSet.get().resources.srcDirs) {
                args("--existing", file)
            }

            if (hasSeparateDatagen.get()) {
                args("--mixin.config", "${modId.get()}_data.mixins.json")
            }

            mods.create(modId.get()) {
                if (hasSeparateApi.get()) {
                    source(apiSourceSet.get())
                }
                source(mainSourceSet.get())
                if (hasSeparateDatagen.get()) {
                    source(datagenSourceSet.get())
                }
            }
        }

        if (hasTests.get()) {
            runs.create("gameTestServer") {
                ideaModule("$rootModuleName.${testSourceSet.get().name}")

                singleInstance(true)
                jvmArg("-ea") // Enable assertions
                property("forge.logging.markers", "REGISTRIES")
                property("forge.enabledGameTestNamespaces", modId.get())

                mods.create(modId.get()) {
                    if (hasSeparateApi.get()) {
                        source(apiSourceSet.get())
                    }
                    source(mainSourceSet.get())
                    source(testSourceSet.get())
                }
            }
        }

        runs.configureEach {
            workingDirectory(runDirectory.dir(name).get().asFile)
            property("forge.logging.console.level", "debug")
            project.the<JavaPluginExtension>().toolchain.vendor.orNull?.let {
                @Suppress("UnstableApiUsage")
                if (it.matches("JetBrains s.r.o.")) {
                    jvmArg("-XX:+AllowEnhancedClassRedefinition")
                }
            }
        }
    }

    private inline fun <reified T> ObjectFactory.domainObjectContainer(factory: NamedDomainObjectFactory<T>): NamedDomainObjectContainer<T> = this.domainObjectContainer(T::class.java, factory)
}
