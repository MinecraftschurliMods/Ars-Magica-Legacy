package com.github.minecraftschurlimods.moddev

import net.minecraftforge.gradle.userdev.DependencyManagementExtension
import net.minecraftforge.gradle.userdev.jarjar.JarJarProjectExtension
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.kotlin.dsl.getByName

class ModDependencyArtifact(private val parent: ModDependency, internal val dependency: ModuleDependency, private val obfuscated: Boolean = true, transitive: Boolean = true, val type: Type) {

    init {
        dependency.isTransitive = transitive
    }

    fun asDependency(): Dependency {
        val version = parent.version.get()
        if (parent.type.get().isEmbedded()) {
            val jarJar = parent.project.extensions.getByName<JarJarProjectExtension>("jarJar")
            jarJar.pin(dependency, version.pinned)
            jarJar.ranged(dependency, version.range)
        }
        if (dependency is ExternalModuleDependency) {
            dependency.version {
                if (version.version != null) {
                    require(version.version)
                } else if (version.range != null && version.pinned != null) {
                    strictly(version.range)
                    prefer(version.pinned)
                }
            }
        }
        return if (obfuscated) parent.project.extensions.getByName<DependencyManagementExtension>("fg").deobf(dependency) else dependency
    }

    enum class Type {
        RUNTIME,
        COMPILE,
        ALL
    }
}
