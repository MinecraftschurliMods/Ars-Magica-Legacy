[![Ars Magica: Legacy](src/main/resources/logo.png)](https://mc-mods.cf/ars-magica-legacy)
==================

<!--suppress HtmlDeprecatedAttribute -->
<div align="center">

[![Build Status](https://img.shields.io/github/actions/workflow/status/MinecraftschurliMods/Ars-Magica-Legacy/build.yml?branch=version%2F1.19.x&logo=github)](https://github.com/MinecraftschurliMods/Ars-Magica-Legacy/actions/workflows/build.yml) 
[![GitHub release](https://img.shields.io/github/v/release/MinecraftschurliMods/Ars-Magica-Legacy?display_name=tag&sort=semver)](https://github.com/MinecraftschurliMods/Ars-Magica-Legacy/releases/latest) 
[![Maven](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fminecraftschurli.ddns.net%2Frepository%2Fmaven-public%2Fcom%2Fgithub%2Fminecraftschurlimods%2Farsmagicalegacy%2Fmaven-metadata.xml&versionPrefix=1.19)](https://minecraftschurli.ddns.net/repository/#/maven-public/com/github/minecraftschurli/arsmagicalegacy) 
[![GitHub issues](https://img.shields.io/github/issues-raw/MinecraftschurliMods/Ars-Magica-Legacy/bug?label=open%20bugs)](https://github.com/MinecraftschurliMods/Ars-Magica-Legacy/issues?q=is%3Aopen+is%3Aissue+label%3Abug) 
[![Discord](https://img.shields.io/discord/358283695104458752?color=%235865F2&label=Discord&logo=discord&logoColor=%235865F2)](https://discord.gg/GcFqXwX)

</div>

## Download
You can download the latest version of the mod from [CurseForge](https://mc-mods.cf/ars-magica-legacy) or [Modrinth](https://modrinth.com/mod/ars-magica-legacy).

| Minecraft Version | Latest Mod Version | Minimum Forge Version | Supported |                                                                    Download                                                                    |
|:-----------------:|:------------------:|:---------------------:|:---------:|:----------------------------------------------------------------------------------------------------------------------------------------------:|
|      1.18.1       |       0.1.5        |        39.0.66        |     ❌     |                                        [CurseForge](https://mc-mods.cf/ars-magica-legacy/files/3656337)                                        |
|      1.18.2       |       1.2.3        |        40.1.35        |     ✅     | [CurseForge](https://mc-mods.cf/ars-magica-legacy/files/4343592) / [Modrinth](https://modrinth.com/mod/ars-magica-legacy/version/1.18.2-1.2.3) |
|      1.19.2       |       1.2.3        |        43.1.13        |     ✅     | [CurseForge](https://mc-mods.cf/ars-magica-legacy/files/4341463) / [Modrinth](https://modrinth.com/mod/ars-magica-legacy/version/1.19.2-1.2.3) |


### Dependencies
- [Forge](https://files.minecraftforge.net/) (required)
- [GeckoLib](https://mc-mods.cf/geckolib) (required)
- [Patchouli](https://mc-mods.cf/patchouli) (required)
- [Curios](https://mc-mods.cf/curios) (optional)
- [JEI](https://mc-mods.cf/jei) (optional)
- [The One Probe](https://mc-mods.cf/the-one-probe) (optional)

## Developing addons
You can develop addons for this mod using the [Ars Magica API](https://minecraftschurli.ddns.net/repository/javadoc/maven-public/com/github/minecraftschurlimods/arsmagicalegacy/1.19.2-1.2.2).
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
