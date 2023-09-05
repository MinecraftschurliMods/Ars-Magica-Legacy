[![Ars Magica: Legacy](src/main/resources/logo.png)][CurseForge Project]
==================

<!--suppress HtmlDeprecatedAttribute -->
<div align="center">

[![Build Status](https://img.shields.io/github/actions/workflow/status/MinecraftschurliMods/Ars-Magica-Legacy/build.yml?branch=version%2F1.19.x&logo=github)][Build Workflow]
[![GitHub Releases](https://img.shields.io/github/v/release/MinecraftschurliMods/Ars-Magica-Legacy?sort=semver&display_name=tag&logo=github)][GitHub Releases]
[![GitHub Issues](https://img.shields.io/github/issues-raw/MinecraftschurliMods/Ars-Magica-Legacy/bug?label=open%20bugs)][GitHub Issues]
[![Maven](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fminecraftschurli.ddns.net%2Frepository%2Fmaven-public%2Fcom%2Fgithub%2Fminecraftschurlimods%2Farsmagicalegacy%2Fmaven-metadata.xml&versionPrefix=1.19)][Maven]
</br>
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/350734?logo=curseforge&label=CurseForge%20Downloads&color=orange)][CurseForge Project]
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/hm4S7JIe?logo=modrinth&logoColor=%231bd96a&label=Modrinth%20Downloads&color=%231bd96a)][Modrinth Project]
[![Discord](https://img.shields.io/discord/358283695104458752?logo=discord&label=Discord&color=%235865F2)][Discord]

</div>

## Download

You can download the latest version of the mod from [CurseForge][CurseForge Project] or [Modrinth][Modrinth Project].

| Minecraft Version | Latest Mod Version | Minimum Forge Version | Supported |                                    Download                                     |
|:-----------------:|:------------------:|:---------------------:|:---------:|:-------------------------------------------------------------------------------:|
|      1.18.1       |       0.1.5        |        39.0.66        |     ❌     |                    [CurseForge][Download-1.18.1-CursedForge]                    |
|      1.18.2       |       1.3.0        |        40.1.35        |     ❌     | [CurseForge][Download-1.18.2-CurseForge] / [Modrinth][Download-1.18.2-Modrinth] |
|      1.19.2       |       1.3.1        |        43.1.13        |     ✅     | [CurseForge][Download-1.19.2-CurseForge] / [Modrinth][Download-1.19.2-Modrinth] |

## Dependencies

- [Forge] (required)
- GeckoLib ([CurseForge][GeckoLib-CurseForge] / [Modrinth][GeckoLib-Modrinth]) (required)
- Patchouli ([CurseForge][Patchouli-CurseForge] / [Modrinth][Patchouli-Modrinth]) (required)
- Curios ([CurseForge][Curios-CurseForge] / [Modrinth][Curios-Modrinth]) (optional)
- JEI ([CurseForge][JEI-CurseForge] / [Modrinth][JEI-Modrinth]) (optional)
- The One Probe ([CurseForge][TOP-CurseForge] / [Modrinth][TOP-Modrinth]) (optional)

## Developing addons

You can develop addons for this mod using the [Ars Magica API].
To use the API you need to first add the maven repository to your `build.gradle`:

```groovy
repositories {
    maven {
        name = "MinecraftschurliMods"
        url = "https://minecraftschurli.ddns.net/repository/maven-public/"
    }
}
```

Then you can add the API as a dependency (don't forget to also include the required dependencies at runtime):

```groovy
dependencies {
    compileOnly fg.deobf('com.github.minecraftschurli:arsmagicalegacy:<arsmagicalegacy-version>:api')
    runtimeOnly fg.deobf('com.github.minecraftschurli:arsmagicalegacy:<arsmagicalegacy-version>')
    runtimeOnly fg.deobf('vazkii.patchouli:Patchouli:<patchouli-version>')
    runtimeOnly fg.deobf('software.bernie.geckolib:geckolib-forge-<mc-version>:<geckolib-version>')
}
```

## Development

### Setup

1. Clone the repository.
2. Run `./gradlew genIntellijRuns` / `./gradlew genEclipseRuns` / `./gradlew genVSCodeRuns` depending on your IDE to set up the workspace.
3. Open the project in your IDE.

### Launching the game

#### Client

To launch the client either:

1. Run the `runClient` run configuration generated in setup.
2. Run `./gradlew runClient` in the project directory.

#### Server

To launch the dedicated server either:

1. Run the `runServer` run configuration generated in setup.
2. Run `./gradlew runServer` in the project directory.

### Running the data generators

To run the data generators either:

1. Run the `runData` run configuration generated in setup.
2. Run `./gradlew runData` in the project directory.

### Testing

#### Adding test cases

1. Create a new class in `src/test/java` in the package `com.github.minecraftschurli.arsmagicalegacy.test`.
2. Add the `@PrefixGameTestTemplate(false)` and `@GameTestHolder(ArsMagicaAPI.MOD_ID)` annotations to the class.
3. Add the `@GameTest` annotation to the test method and specify the template name (e.g. `@GameTest(template = "example")`). Use the `empty` template for tests that don't require a specific template.
4. Write the test case. Use the `GameTestHelper` parameter of your test method to fail or succeed. Your testcase is required to call succeed at least once or it will fail with a timeout.

#### Running tests

To run the tests either:

1. Run the `runGameTestServer` run configuration generated in setup.
2. Run `./gradlew runGameTestServer` in the project directory.
3. Launch the client and use the `/test` command.

### Building

Run `./gradlew build` to build the mod. The resulting jars will be located in `build/libs`.

## Legal disclaimer

Ars Magica is a trademark of Atlas Games®, used with permission.

Some textures used in this mod are property of D3miurge, used with permission.


[Build Workflow]: https://github.com/MinecraftschurliMods/Ars-Magica-Legacy/actions/workflows/build.yml
[GitHub Releases]: https://github.com/MinecraftschurliMods/Ars-Magica-Legacy/releases/latest
[GitHub Issues]: https://github.com/MinecraftschurliMods/Ars-Magica-Legacy/issues?q=is%3Aopen+is%3Aissue+label%3Abug
[Maven]: https://minecraftschurli.ddns.net/repository/#/maven-public/com/github/minecraftschurli/arsmagicalegacy
[CurseForge Downloads]: https://www.curseforge.com/minecraft/mc-mods/ars-magica-legacy/files
[Modrinth Downloads]: https://modrinth.com/mod/ars-magica-legacy/versions#all-versions
[Discord]: https://discord.gg/GcFqXwX

[CurseForge Project]: https://www.curseforge.com/minecraft/mc-mods/ars-magica-legacy
[Modrinth Project]: https://modrinth.com/mod/ars-magica-legacy

[Download-1.18.1-CursedForge]: https://www.curseforge.com/minecraft/mc-mods/ars-magica-legacy/files/3656337
[Download-1.18.2-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/ars-magica-legacy/files/4657864
[Download-1.18.2-Modrinth]: https://modrinth.com/mod/ars-magica-legacy/version/1.18.2-1.3.0
[Download-1.19.2-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/ars-magica-legacy/files/4675845
[Download-1.19.2-Modrinth]: https://modrinth.com/mod/ars-magica-legacy/version/1.19.2-1.3.0

[Forge]: https://files.minecraftforge.net/

[GeckoLib-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/geckolib
[GeckoLib-Modrinth]: https://modrinth.com/mod/geckolib

[Patchouli-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/patchouli
[Patchouli-Modrinth]: https://modrinth.com/mod/patchouli

[Curios-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/curios
[Curios-Modrinth]: https://modrinth.com/mod/curios

[JEI-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/jei
[JEI-Modrinth]: https://modrinth.com/mod/jei

[TOP-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/the-one-probe
[TOP-Modrinth]: https://modrinth.com/mod/the-one-probe

[Ars Magica API]: https://minecraftschurli.ddns.net/repository/javadoc/maven-public/com/github/minecraftschurlimods/arsmagicalegacy/1.19.2-1.3.0
