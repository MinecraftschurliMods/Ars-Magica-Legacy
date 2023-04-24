# Additions

- Implemented the Beam shape.
- Implemented the Chain shape.
- Implemented the Falling Star component.
- Implemented the Health Boost component.
- Implemented the Mana Blast component.
- Added tags for spellcrafting starting and finishing. These contain a Blank Rune and a Spell Parchment, respectively.
  Gameplay behavior remains the same; this is intended primarily for modpack/datapack makers.

# Changes

- Chain is now located in the Offense tree, between Beam and Damage.
- Water Breathing is now a Green part and slightly more expensive.
- Some spell recipes have been changed.
- Updated compendium entries for various spell parts, including the ones that were newly implemented.

# Removals

- The hidden Mana Shield component has been removed in favor of the Health Boost component. Health Boost also reuses the
  assets that previously belonged to Mana Shield. This is because in AM2, Mana Shield appears to have been duplicate
  content; it did the same thing as the Shield component.

# Fixes

- Fixed spell book scrolling in the offhand (#380)
- Fixed mana and burnout attributes not reapplying correctly after death (#382)
- Fixed various smaller issues encountered during development

# API Changes

- Datagen has been reworked. All of our data providers now inherit from a common superclass that contains some (mostly
  IO-related) functionality and defines the base methods. If you work with data generators in any way, this mainly means
  some renaming.
