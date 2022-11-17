# Dependencies
The new version now uses GeckoLib 3.0.37 (or newer) as a dependency for entity rendering!

# Additions
- All bosses now have proper attacks and animations! The compendium entries have been updated accordingly, and issues with the textures have been fixed.
- Mana Creepers now roam the world! In addition to exploding, they will leave behind a Mana Vortex that will follow you for some time and consume some of your mana when too close.
- Spells now have fancy in-hand animations!

# Changes
- Spell icons have been reorganized internally. If your spell has a missing texture icon, set a new icon by using the Ctrl+M keybind.
- Spell parts received a rebalancing. In most cases, this means spells are cheaper!
- Affinity gains from spell parts have been greatly reduced.
- The Blizzard and Fire Rain components are now functional (still not obtainable though :( - this is part of a future update coming soon(tm))
- The Blink component has been heavily nerfed.
- Mana Cake can now be eaten even with a full hunger bar.
- Players now respawn with half their mana instead of no mana at all.

# Fixes
- Mana regeneration and burnout reduction now properly scale with the player's level.
- Mana regeneration and burnout reduction potion effects now properly work.
- The spell book can now be used in midair (e.g. when under the influence of the Flight effect.)
- The weird client-side lagging of spell projectiles has been resolved.
- Prefab Spells now show up in alphabetical order instead of a random order.
- Keybinds now have proper translations.
- And many other small fixes!

# API Changes
- `IAffinityHelper` now has a new method `getAffinityDepthOrElse()` that will return the given default value if the affinity cannot be retrieved.
- `PrefabSpellBuilder` has been extracted into its own class and is no longer an inner class in `PrefabSpellProvider`.
- Several changes to `PrefabSpellProvider` have been made.
  - The abstract `createPrefabSpells()` method now has a `Consumer<PrefabSpellBuilder>` parameter.
  - `addPrefabSpell()` now returns the builder. Call `build(consumer)` after the builder creation in `createPrefabSpells()` to adapt to these changes.
  - The constructor now takes an optional `LanguageProvider`. If the language provider is given, a translation for the prefab spell will be auto-generated, otherwise the literal name will be used as before.
- The `api/entity` package was removed.
    - `AbstractBoss`, `AbstractBossGoal` and `ExecuteSpellGoal` were moved out of the API.
    - `ISpellCasterEntity` was moved to the `api/spell` package.
- `IPrefabSpell` now extends `Comparable<IPrefabSpell>`.
- `ISpellHelper` has a new method `getPointedEntity()` that returns the entity an entity is looking at, or null if it is not looking at an entity.
