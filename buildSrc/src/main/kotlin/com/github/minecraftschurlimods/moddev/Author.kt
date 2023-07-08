package com.github.minecraftschurlimods.moddev

import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.setProperty

class Author(val name: String, objects: ObjectFactory) {
    val url = objects.property<String>()
    val displayName = objects.property<String>()
    val email = objects.property<String>()
    val organization = objects.property<String>()
    val organizationUrl = objects.property<String>()
    val timezone = objects.property<String>()
    val roles = objects.setProperty<String>()
    val properties = objects.mapProperty<String, String>()
}
