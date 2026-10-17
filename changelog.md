# Changes

- Water Orbs, Fire Antennae, Earth Armor and Ender Boots can now no longer have trims applied to them
- Falling Star damage and particle amounts have been tripled (existing instances may need to adjust damage in the config)
- Rune Bags are now crafted using one string, one leather and one rune, adjusting to the newer bundle recipe introduced in Minecraft 1.21.2
- Spells are no longer considered a "tool" by FTB Ultimine and no longer offer the option for veinmining
- Improved syncing logic of Life Wards, now only syncing on demand rather than always

# Fixes

- Fixed Arcane Guardian summon ritual not working properly
- Fixed Arcane Spell Books not being considered "spell books" in some contexts
- Fixed Life Ward texture animation stopping one frame too early
- Fixed a crash when being hit by an exploding creeper while the Thorns ability is active
- Fixed a crash when an empty `ItemStack` is thrown into the crafting altar
- Fixed a crash that could occur when checking magic during world loading or unloading
- Fixed Integrated Dynamics compat recipes
