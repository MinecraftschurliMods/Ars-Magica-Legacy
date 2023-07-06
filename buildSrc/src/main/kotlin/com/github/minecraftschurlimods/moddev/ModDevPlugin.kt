package com.github.minecraftschurlimods.moddev

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.add

class ModDevPlugin : Plugin<Project> {
    companion object {
        const val MODDEV_EXTENSION_NAME = "moddev"
    }

    override fun apply(project: Project) {
        project.extensions.add<ModDevExtension>(MODDEV_EXTENSION_NAME, ModDevExtension(project))
    }
}
