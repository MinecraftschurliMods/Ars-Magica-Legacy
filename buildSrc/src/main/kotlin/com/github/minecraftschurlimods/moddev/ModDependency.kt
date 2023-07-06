package com.github.minecraftschurlimods.moddev

import org.gradle.api.DomainObjectSet
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.property

/*
 * create("modid") {
 *   // only one of these for non include dependencies
 *   version(
 *     range = "[1.0.0,)"
 *     strict = "1.0.0"
 *   )
 *   version("1.0.0")
 *
 *   artifacts {
 *     runtime(group = "group", name = "name")
 *     compile(group = "group", name = "name", classifier = "api")
 *   }
 *
 *   artifact(group = "group", name = "name") // equivalent to both compile and runtime
 *
 *   curseforge = "mod-id" // only if different from modid
 *   modrinth = "mod-id" // only if different from modid
 * }
 */
class ModDependency(val name: String, val project: Project) {
    val version: Property<ModDependencyVersion> = project.objects.property<ModDependencyVersion>()
    val type: Property<Type> = project.objects.property<Type>()
    val required: Property<Boolean> = project.objects.property<Boolean>()
    val ordering: Property<Ordering> = project.objects.property<Ordering>().convention(required.map { if (it) Ordering.BEFORE else Ordering.NONE })
    val artifacts: DomainObjectSet<ModDependencyArtifact> = project.objects.domainObjectSet<ModDependencyArtifact>()
    val curseforge: Property<String> = project.objects.property<String>()
    val modrinth: Property<String> = project.objects.property<String>()
    val group: Property<String> = project.objects.property<String>()
    val mavenURL: Property<String> = project.objects.property<String>()

    fun version(range: String, strict: String) = this.version.set(
        ModDependencyVersion(range, strict, null)
    )

    fun version(range: Provider<String>, strict: Provider<String>) = this.version.set(
        range.zip(strict) { r, s -> ModDependencyVersion(r, s, null) }
    )

    fun version(version: String) = this.version.set(
        ModDependencyVersion(null, null, version)
    )

    fun version(version: Provider<String>) = this.version.set(
        version.map { v -> ModDependencyVersion(null, null, v) }
    )

    fun artifacts(action: DomainObjectSet<ModDependencyArtifact>.() -> Unit) = artifacts.action()

    fun artifact(group: String, name: String, classifier: String? = null, obfuscated: Boolean = true, transitive: Boolean = true) = artifacts.add(
        ModDependencyArtifact(this, project.dependencies.create(group = group, name = name, classifier = classifier), obfuscated, transitive, ModDependencyArtifact.Type.ALL)
    )

    fun artifact(name: String, classifier: String? = null, obfuscated: Boolean = true, transitive: Boolean = true) = artifacts.add(
        ModDependencyArtifact(this, project.dependencies.create(group = group.get(), name = name, classifier = classifier), obfuscated, transitive, ModDependencyArtifact.Type.ALL)
    )

    fun DomainObjectSet<ModDependencyArtifact>.compile(group: String, name: String, classifier: String? = null, obfuscated: Boolean = true, transitive: Boolean = true) = add(
        ModDependencyArtifact(this@ModDependency, project.dependencies.create(group = group, name = name, classifier = classifier), obfuscated, transitive, ModDependencyArtifact.Type.COMPILE)
    )

    fun DomainObjectSet<ModDependencyArtifact>.runtime(group: String, name: String, classifier: String? = null, obfuscated: Boolean = true, transitive: Boolean = true) = add(
        ModDependencyArtifact(this@ModDependency, project.dependencies.create(group = group, name = name, classifier = classifier), obfuscated, transitive, ModDependencyArtifact.Type.RUNTIME)
    )

    fun DomainObjectSet<ModDependencyArtifact>.compile(name: String, classifier: String? = null, obfuscated: Boolean = true, transitive: Boolean = true) = add(
        ModDependencyArtifact(this@ModDependency, project.dependencies.create(group = group.get(), name = name, classifier = classifier), obfuscated, transitive, ModDependencyArtifact.Type.COMPILE)
    )

    fun DomainObjectSet<ModDependencyArtifact>.runtime(name: String, classifier: String? = null, obfuscated: Boolean = true, transitive: Boolean = true) = add(
        ModDependencyArtifact(this@ModDependency, project.dependencies.create(group = group.get(), name = name, classifier = classifier), obfuscated, transitive, ModDependencyArtifact.Type.RUNTIME)
    )

    enum class Type {
        INCLUDE,
        INCLUDED_LIBRARY,
        COMPAT,
        RUNTIME_COMPAT,
        DATA_LIBRARY,
        DEPENDENCY,
        API_DEPENDENCY,
        LIBRARY,
        TEST_LIBRARY;

        fun hasModsToml(): Boolean {
            return this == INCLUDE || this == COMPAT || this == RUNTIME_COMPAT || this == DEPENDENCY || this == API_DEPENDENCY
        }

        fun isEmbedded(): Boolean {
            return this == INCLUDE
        }
    }

    enum class Ordering {
        BEFORE,
        AFTER,
        NONE
    }

    private inline fun <reified T> ObjectFactory.domainObjectSet(): DomainObjectSet<T> = this.domainObjectSet(T::class.java)
}
