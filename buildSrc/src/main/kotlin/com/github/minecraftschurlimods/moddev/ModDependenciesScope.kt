package com.github.minecraftschurlimods.moddev

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.repositories
import java.net.URI

/*
 * include = {
 *   compile ==> main, api
 *   runtime ==> main, jarJar
 *   all     ==> main, api, jarJar
 *   mods.toml <== mandatory & embedded
 * }
 * includedLibrary = {
 *   compile ==> main, api
 *   runtime ==> main, jarJar
 *   all     ==> main, api, jarJar
 * }
 * compat = {
 *   compile ==> compileCompatMod
 *   all     ==> compileCompatMod
 *   mods.toml <== optional
 * }
 * runtimeCompat = {
 *   compile ==> compileDependency
 *   runtime ==> runtimeDependency
 *   all     ==> implementationDependency
 *   mods.toml <== optional
 * }
 * dataLibrary = {
 *   compile ==> compileDataLibrary
 *   runtime ==> runtimeDataLibrary
 *   all     ==> implementationDataLibrary
 * }
 * dependency = {
 *   compile ==> compileDependency
 *   runtime ==> runtimeDependency
 *   all     ==> implementationDependency
 *   mods.toml <==  mandatory
 * }
 * apiDependency = {
 *   compile ==> main, api
 *   runtime ==> main
 *   all     ==> main, api
 *   mods.toml <== mandatory
 * }
 * library = {
 *   all     ==> library
 * }
 * testLibrary = {
 *   compile ==> testCompileOnly
 *   runtime ==> testRuntimeOnly
 *   all     ==> testImplementation
 * }
 */
class ModDependenciesScope(private val dependencies: NamedDomainObjectContainer<ModDependency>, private val project: Project) {
    private val locations: MutableMap<URI, MutableSet<String>> = mutableMapOf()

    operator fun invoke(action: ModDependenciesScope.() -> Unit) {
        action()
        project.repositories {
            mavenLocal()
            mavenCentral()
            for (entry in locations) {
                maven {
                    url = entry.key
                    content {
                        for (group in entry.value) {
                            includeGroup(group)
                        }
                    }
                }
            }
            maven {
                name = "ModMaven (fallback)"
                url = URI.create("https://modmaven.k-4u.nl")
            }
        }
    }

    fun include(id: String, action: ModDependency.() -> Unit): ModDependency {
        val dependency = dependencies.create(id, action)
        dependency.type.set(ModDependency.Type.INCLUDE)
        dependency.required.set(true)
        setupRepository(dependency)
        return dependency
    }

    fun includedLibrary(id: String, action: ModDependency.() -> Unit): ModDependency {
        val dependency = dependencies.create(id, action)
        dependency.type.set(ModDependency.Type.INCLUDED_LIBRARY)
        setupRepository(dependency)
        return dependency
    }

    fun compat(id: String, action: ModDependency.() -> Unit): ModDependency {
        val dependency = dependencies.create(id, action)
        dependency.type.set(ModDependency.Type.COMPAT)
        dependency.required.set(false)
        setupRepository(dependency)
        return dependency
    }

    fun runtimeCompat(id: String, action: ModDependency.() -> Unit): ModDependency {
        val dependency = dependencies.create(id, action)
        dependency.type.set(ModDependency.Type.RUNTIME_COMPAT)
        dependency.required.set(false)
        setupRepository(dependency)
        return dependency
    }

    fun dataLibrary(id: String, action: ModDependency.() -> Unit): ModDependency {
        val dependency = dependencies.create(id, action)
        dependency.type.set(ModDependency.Type.DATA_LIBRARY)
        setupRepository(dependency)
        return dependency
    }

    fun dependency(id: String, action: ModDependency.() -> Unit): ModDependency {
        val dependency = dependencies.create(id, action)
        dependency.type.set(ModDependency.Type.DEPENDENCY)
        dependency.required.set(true)
        setupRepository(dependency)
        return dependency
    }

    fun apiDependency(id: String, action: ModDependency.() -> Unit): ModDependency {
        val dependency = dependencies.create(id, action)
        dependency.type.set(ModDependency.Type.API_DEPENDENCY)
        dependency.required.set(true)
        setupRepository(dependency)
        return dependency
    }

    fun library(name: String, action: ModDependency.() -> Unit): ModDependency {
        val dependency = dependencies.create(name, action)
        dependency.type.set(ModDependency.Type.LIBRARY)
        setupRepository(dependency)
        return dependency
    }

    fun library(group: String, name: String, version: String, classifier: String? = null, obfuscated: Boolean = false, transitive: Boolean = false): ModDependency {
        return library("$group:$name") {
            version(version)
            artifact(group, name, classifier, obfuscated, transitive)
        }
    }

    fun testLibrary(name: String, action: ModDependency.() -> Unit): ModDependency {
        val dependency = dependencies.create(name, action)
        dependency.type.set(ModDependency.Type.TEST_LIBRARY)
        setupRepository(dependency)
        return dependency
    }

    private fun setupRepository(dependency: ModDependency) {
        if (dependency.mavenURL.isPresent) {
            val url = URI.create(dependency.mavenURL.get())
            val groupsForUrl = locations.computeIfAbsent(url) { mutableSetOf() }
            if (dependency.group.isPresent) {
                groupsForUrl.add(dependency.group.get())
            } else {
                dependency.artifacts.forEach { artifact ->
                    groupsForUrl.add(artifact.dependency.group!!)
                }
            }
        }
    }
}
