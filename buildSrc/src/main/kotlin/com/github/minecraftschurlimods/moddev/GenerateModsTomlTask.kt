package com.github.minecraftschurlimods.moddev

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.setProperty
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault
open class GenerateModsTomlTask : DefaultTask() {
    init {
        outputs.upToDateWhen { false }
    }

    @get:OutputFile val outputFile: RegularFileProperty = project.objects.fileProperty().convention(project.layout.buildDirectory.file("mods.toml"))
    @get:Internal val dependencies: SetProperty<ModDependency> = project.objects.setProperty<ModDependency>()
    @get:Internal val authors: SetProperty<Author> = project.objects.setProperty<Author>()
    @get:Internal val modId: Property<String> = project.objects.property<String>()
    @get:Internal val curseforgeProjectId: Property<Int> = project.objects.property<Int>()
    @get:Internal val modrinthProjectId: Property<String> = project.objects.property<String>()
    @get:Internal val credits: Property<String> = project.objects.property<String>()
    @get:Internal val description: Property<String> = project.objects.property<String>()
    @get:Internal val displayName: Property<String> = project.objects.property<String>().convention(project.displayName)
    @get:Internal val displayURL: Property<String> = project.objects.property<String>()
    @get:Internal val logoFile: RegularFileProperty = project.objects.fileProperty()
    @get:Internal val modLoader: Property<String> = project.objects.property<String>().convention("javafml")
    @get:Internal val loaderVersion: Property<String> = project.objects.property<String>()
    @get:Internal val license: Property<String> = project.objects.property<String>()
    @get:Internal val issueTrackerURL: Property<String> = project.objects.property<String>()
    @get:Internal val forgeVersionRange: Property<String> = project.objects.property<String>()
    @get:Internal val minecraftVersionRange: Property<String> = project.objects.property<String>()
    @get:Internal val properties: MapProperty<String, String> = project.objects.mapProperty<String, String>()

    @TaskAction
    fun generate() {
        val modDependencies = dependencies.get().filter { it.type.get().hasModsToml() }
        val modLoaderLine = "modLoader = \"${modLoader.get()}\"\n"
        val loaderVersionLine = "loaderVersion = \"${loaderVersion.get()}\"\n"
        val licenseLine = "license = \"${license.get().replace("\"", "\\\"")}\"\n"
        val issueTrackerURLLine = if (issueTrackerURL.isPresent) "issueTrackerURL = \"${issueTrackerURL.get()}\"\n" else ""
        val headBlock = modLoaderLine + loaderVersionLine + licenseLine + issueTrackerURLLine + "\n"
        val modId = modId.get()
        val displayName = displayName.get()
        val displayURLLine = if (displayURL.isPresent) "displayURL = \"${displayURL.get()}\"\n    " else ""
        val logoFileLine = if (logoFile.isPresent) "logoFile = \"${logoFile.get().asFile.name}\"\n    " else ""
        val creditsLine = if (credits.isPresent) "credits = \"${credits.get()}\"\n    " else ""
        val authorsLine = if (authors.isPresent) "authors = \"${authors.get().joinToString(", ") { it.displayName.get() }}\"\n    " else ""
        val descriptionBlock = "description = '''\n${description.get()}\n'''\n    "
        val rootMcPublishBlock = if (curseforgeProjectId.isPresent || modrinthProjectId.isPresent) {
            "[mc-publish]\n        " + listOfNotNull(
                curseforgeProjectId.orNull?.run { "curseforge = $this" },
                modrinthProjectId.orNull?.run { "modrinth = \"$this\"" }
            ).joinToString("\n        ")
        } else ""
        val modBlock = """
            [[mods]]
                modId = "$modId"
                version = "${project.version}"
                displayName = "$displayName"
        """.trimIndent() + "\n    " + displayURLLine + logoFileLine + creditsLine + authorsLine + descriptionBlock + rootMcPublishBlock + "\n\n"
        val dependencyHeader = "[[dependencies.$modId]]\n    "
        val dependenciesBlock = modDependencies.joinToString("\n\n") {
            val type = it.type.get()
            val modIdLine = "modId = \"${it.name}\"\n    "
            val mandatoryLine = "mandatory = ${it.required.get()}\n    "
            val versionRangeLine = "versionRange = \"${it.version.orNull?.range?:"*"}\"\n    "
            val embeddedLine = if (type.isEmbedded()) "embedded = true\n    " else ""
            val orderingLine = if (it.ordering.isPresent && it.ordering.get() != ModDependency.Ordering.NONE) "ordering = \"${it.ordering.get().name}\"\n    " else ""
            val sideLine = if (it.required.get()) "side = \"BOTH\"\n    " else ""
            val modrinth = it.modrinth.orNull
            val curseforge = it.curseforge.orNull
            val mcPublishBlock = if (modrinth != null || curseforge != null) {
                "[dependencies.$modId.mc-publish]\n        " + listOfNotNull(
                    modrinth?.run { "modrinth = \"$this\"" },
                    curseforge?.run { "curseforge = \"$this\"" }
                ).joinToString("\n        ")
            } else ""
            dependencyHeader + modIdLine + mandatoryLine + versionRangeLine + embeddedLine + orderingLine + sideLine + mcPublishBlock
        } + "\n\n"
        val additionalDependencies = """    
            [[dependencies.$modId]]
                modId = "forge"
                mandatory = true
                versionRange = "${ forgeVersionRange.get() }"
                ordering = "NONE"
                side = "BOTH"
            
            [[dependencies.$modId]]
                modId = "minecraft"
                mandatory = true
                versionRange = "${ minecraftVersionRange.get() }"
                ordering = "NONE"
                side = "BOTH"
        """.trimIndent()
        val propertiesBlock = if (properties.isPresent) {
            "\n\n[modproperties.$modId]\n    " + properties.get().entries.joinToString("\n    ") { (key, value) ->
                "$key = \"${value.replace("\"", "\\\"")}\""
            }
        } else ""
        val outputText = headBlock + modBlock + dependenciesBlock + additionalDependencies + propertiesBlock + "\n"
        outputFile.get().asFile.writeText(outputText.replace("\n    \n", "\n"))
    }
}
