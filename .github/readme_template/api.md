## Developing addons

You can develop addons for this mod using the {% link url:apiLink name:"Ars Magica API" text:"Ars Magica API" %}.
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
