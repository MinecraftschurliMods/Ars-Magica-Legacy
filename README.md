# Ars Magica: Legacy

<!--suppress HtmlDeprecatedAttribute -->
<div align="center">

[![Build Status](https://img.shields.io/github/actions/workflow/status/MinecraftschurliMods/Ars-Magica-Legacy/build.yml?branch=version%2F1.19.x&logo=github)][Build Workflow]
[![GitHub Releases](https://img.shields.io/github/v/release/MinecraftschurliMods/Ars-Magica-Legacy?sort=semver&display_name=tag&logo=github)][GitHub Releases]
[![GitHub Issues](https://img.shields.io/github/issues-raw/MinecraftschurliMods/Ars-Magica-Legacy/bug?label=open%20bugs)][GitHub Issues]
[![Maven](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Fminecraftschurli.ddns.net%2Frepository%2Fmaven-public%2Fcom%2Fgithub%2Fminecraftschurlimods%2Farsmagicalegacy%2Fmaven-metadata.xml&versionPrefix=1.19)][Maven]
<br>
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/350734?logo=curseforge&label=CurseForge%20Downloads&color=orange)][CurseForge Project]
[![Modrinth Downloads](https://img.shields.io/modrinth/dt/hm4S7JIe?logo=modrinth&logoColor=%231bd96a&label=Modrinth%20Downloads&color=%231bd96a)][Modrinth Project]
[![Discord](https://img.shields.io/discord/358283695104458752?logo=discord&label=Discord&color=%235865F2)][Discord]

</div>

## Download

You can download the latest version of the mod from [CurseForge][CurseForge Downloads] or [Modrinth][Modrinth Downloads].

| Minecraft Version | Modloader | Supported |
|:-----------------:|:---------:|:---------:|
|       26.1        | NeoForge  |    Yes    |
|      1.20.4       | NeoForge  |    No     |
|      1.20.1       |   Forge   |    No     |
|      1.19.2       |   Forge   |    No     |
|      1.18.2       |   Forge   |    No     |
|      1.18.1       |   Forge   |    No     |

## Dependencies

- [Forge] (required on Minecraft 1.18.1-1.20.1) / [NeoForge] (required on Minecraft 1.20.4+)
- GeckoLib ([CurseForge][GeckoLib-CurseForge] / [Modrinth][GeckoLib-Modrinth]) (required)
- Patchouli ([CurseForge][Patchouli-CurseForge] / [Modrinth][Patchouli-Modrinth]) (required)
- Curios ([CurseForge][Curios-CurseForge] / [Modrinth][Curios-Modrinth]) (optional)
- Jade ([CurseForge][Jade-CurseForge] / [Modrinth][Jade-Modrinth]) (optional)
- JEI ([CurseForge][JEI-CurseForge] / [Modrinth][JEI-Modrinth]) (optional)
- The One Probe ([CurseForge][TOP-CurseForge] / [Modrinth][TOP-Modrinth]) (optional)

## Developing Addons

You can develop addons for this mod using the Ars Magica: Legacy API. To use the API you need to first add the maven repository to your `build.gradle`:

```groovy
repositories {
    maven {
        name = "MinecraftschurliMods"
        url = "https://maven.minecraftschurli.at/maven-public"
    }
}
```

Then you can add the API as a dependency (don't forget to also include the required dependencies at runtime):

```groovy
dependencies {
    compileOnly "com.github.minecraftschurli:arsmagicalegacy:${arsmagicalegacy_version}:api"
    runtimeOnly "com.github.minecraftschurli:arsmagicalegacy:${arsmagicalegacy_version}"
    runtimeOnly "vazkii.patchouli:Patchouli:${patchouli_version}"
    runtimeOnly "software.bernie.geckolib:geckolib-neoforge-${mc_version}:${geckolib_version}"
}
```

## Legal Disclaimers & Licensing

Ars Magica is a trademark of Atlas Games®, used with permission.

Different parts of the repository are licensed under different licenses. See [LICENSE.md] for a detailed breakdown of the repository's licensing.

[Build Workflow]: https://github.com/MinecraftschurliMods/Ars-Magica-Legacy/actions/workflows/build.yml
[GitHub Releases]: https://github.com/MinecraftschurliMods/Ars-Magica-Legacy/releases/latest
[GitHub Issues]: https://github.com/MinecraftschurliMods/Ars-Magica-Legacy/issues?q=is%3Aopen+is%3Aissue+label%3Abug
[Maven]: https://minecraftschurli.ddns.net/repository/#/maven-public/com/github/minecraftschurli/arsmagicalegacy
[CurseForge Project]: https://www.curseforge.com/minecraft/mc-mods/ars-magica-legacy
[Modrinth Project]: https://modrinth.com/mod/ars-magica-legacy
[Discord]: https://discord.gg/GcFqXwX
[CurseForge Downloads]: https://www.curseforge.com/minecraft/mc-mods/ars-magica-legacy/files
[Modrinth Downloads]: https://modrinth.com/mod/ars-magica-legacy/versions#all-versions
[LICENSE.md]: LICENSE.md

[Forge]: https://files.minecraftforge.net/
[NeoForge]: https://neoforged.net
[GeckoLib-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/geckolib
[GeckoLib-Modrinth]: https://modrinth.com/mod/geckolib
[Patchouli-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/patchouli
[Patchouli-Modrinth]: https://modrinth.com/mod/patchouli
[Curios-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/curios
[Curios-Modrinth]: https://modrinth.com/mod/curios
[Jade-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/jade
[Jade-Modrinth]: https://modrinth.com/mod/jade
[JEI-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/jei
[JEI-Modrinth]: https://modrinth.com/mod/jei
[TOP-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/the-one-probe
[TOP-Modrinth]: https://modrinth.com/mod/the-one-probe
