package com.github.minecraftschurlimods.moddev

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName

val Project.moddev
    get() = extensions.getByName<ModDevExtension>(ModDevPlugin.MODDEV_EXTENSION_NAME)


