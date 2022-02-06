# Version 1.18.1-0.1.2

## Fixes

- Fixed mana not regenerating when obtaining the compendium (fixes #258)

# Version 1.18.1-0.1.1

## Fixes

- Fixed issue with latest forge

**!! WARNING !! This version now requires forge 39.0.66 or above**

# Version 1.18.1-0.1

## Additions

### Etherium

- Etherium is an invisible magical substance that is required for spellcrafting and will be required for other things in
  the future
- It comes in three types: neutral, light and dark

### Obelisk

- Added the Obelisk
- Opening the obelisk opens a simple GUI with a fuel slot, you can put vinteum dust or vinteum blocks in the slot
- The obelisk will burn fuel to neutral etherium
- The fuels can be altered via datapacks
- Upgradeable via a multiblock, see the compendium entry for more info

### Celestial Prism

- Added the Celestial Prism
- Works similar to the obelisk, except that it generates light instead of neutral etherium and uses daylight instead of
  vinteum
- Currently unobtainable in survival
- Upgradeable via a multiblock, see the compendium entry for more info

### Black Aurem

- Added the Black Aurem
- Works similar to the obelisk, except that it generates light instead of neutral etherium and uses hitpoints of nearby
  living things instead of vinteum
- Currently unobtainable in survival
- Upgradeable via a multiblock, see the compendium entry for more info

### Crystal Wrench

- Added the Crystal Wrench
- Used to link an Obelisk/Celestial Prism/Black Aurem to a Crafting Altar

### Mana Cake

- Added the Mana Cake: a food item that gives a mana regen effect when eaten

### Mana Martini

- Added the Mana Martini: a drink that gives a burnout reduction effect when drank

### Mage Armor

- Added mage armor
- Has low durability, but repairs itself using mana
- Added battlemage armor, which is an improved but more expensive variant of the normal mage armor

### Potions

- Added Lesser Mana Potion
- Brewed with Chimerite
- Added Standard Mana Potion
- Brewed with Wakebloom
- Added Greater Mana Potion
- Brewed with Vinteum Dust
- Added Epic Mana Potion
- Brewed with Arcane Ash
- Added Legendary Mana Potion
- Brewed with Purified Vinteum Dust
- Added Infused Mana Potion
- Brewed with Tarma Root

### Magic XP HUD

- The player's magic xp level and progress is now shown above the mana and burnout bars

### Spell Parts

- Added all missing spell parts to the occulus
- Implemented functionality for:
    - Channel
    - Scramble Synapses
    - True Sight
    - Attract
    - Blink
    - Daylight
    - Divine Intervention
    - Ender Intervention
    - Life Drain
    - Life Tip
    - Mana Drain
    - Melt Armor
    - Moonrise
    - Place Block
    - Recall
    - Reflect
    - Rift
    - Transplace
- Removed Mark component (was merged with Recall)
- Marked all work in progress spell parts as [WIP]
- Reworked all spell part recipes to be more balanced

### Altar Materials

- Added copper, chimerite, topaz, vinteum and netherite blocks as cap materials; netherite is now the strongest (
  material strength of 13)
- Added many more altar structure materials, end stone bricks or purpur blocks are now the strongest (material strength
  of 6)

### Witchwood

- Witchwood trees now rarely generate in dark forests (and other biomes marked as SPOOKY)
- They generate as a bit larger dark oak trees
- Witchwood is now used instead of "any wood" in spell recipes
- Witchwood planks are an altar structure material

### Prefab Spells

- Added a system for prefab spells
- Prefab spells show in a separate creative tab
- This is currently an unused system

### Affinity Ability API

- Added a system for affinity abilities
- This is currently an unused system

## Fixes

- Fixed flower pot rendering and drops
- Fixed dupe bug with Inscription Table
- Fixed crash when dying or travelling back from the end
- Reworked the spell modifier system, solar and lunar modifiers should now work as they should
- Fixed a few other minor bugs

# Version 1.18.1-0.0.5

## Fixes

### Issues

- fix #242 crash on forge 39.0.18

# Version 1.18.1-0.0.4

## Fixes

### Issues

- fix #241 crash with catalogue

### Compat

- make compatible with newer versions of jei

# Version 1.18.1-0.0.3

## Fixes

### Issues

- fix #236 error with syncing altar materials
- fix #238 flowers can't be put into flowerpots

# Version 1.18.1-0.0.2

## Fixes

### Commands

- Remove test commands

### Arcane Compendium

- Affinity and Shape Group pages are now properly displaying text
- All pages now use translation keys

### Occulus

- The Occulus now consumes skill points

### Components

- Physical Damage, Fire Damage, Frost Damage, Lightning Damage, Magic Damage and Drowning Damage now respect the server
  PvP setting

# Version 1.18.1-0.0.1

## Additions

### Arcane Compendium

- Entry point to the mod
- Acts as the Guide Book to the mod
- Worth reading, but still a WIP
- Must be crafted (not present in the inventory, crafted) to be able to use any items of this mod

### Occulus

- Added the Occulus
- Opening the Occulus opens a GUI with three skill tree tabs and one affinity tab
- Skills can be learned by clicking on them, provided the player has the necessary skill points and all parent skills
- Creative players can learn all skills, ignoring requirements

### Inscription Table

- Added the Inscription Table
- Opening the Inscription Table opens a GUI with the following:
    - An area at the top, showing all spell parts currently known
    - A search bar to search in the known spell parts
    - A book slot
    - A name textbox
    - Five shape group squares
    - The spell grammar sections at the very bottom
- To make a spell recipe, drag at least one shape into a shape group and at least one component into the spell grammar
  section. Put a Book & Quill into the book slot, then take it out (shift-remove the Book & Quill to move it out again
  instead)
- Keeps the last spell, allowing you to close and re-open the GUI without problems

### Crafting Altar

- Added the Crafting Altar multiblock
- Added the Altar Core and Magic Wall blocks
- Added cap and structure materials
- Cap and structure materials can be altered via datapacks
- Put a book into the lectern to start the crafting process
- Throw the necessary items in
- Pickup the spell after throwing in the Spell Parchment

### Skills

- Skills are mapped to spell parts based on the name
- Position, parents, skill point cost and occulus tab can be altered via datapacks

### Spell Parts

- Spell Parts are independent from skills, they may exist without skills for them
- Mana cost, reagents (NYI), affinity values and the spell recipe can be altered via datapacks
- The recipe takes either normal ingredients (item/tag with count) or etherium (currently unobtainable, so better don't
  use this)
- Three groups: shapes (for shape groups), components (for spell grammar), modifiers (to alter either)

#### Shapes

- AoE
- Beam (NYI)
- Chain (NYI)
- Channel (NYI)
- Projectile
- Rune
- Self
- Touch
- Wall
- Wave
- Zone

#### Components

- Drowning Damage
- Fire Damage
- Frost Damage
- Lightning Damage
- Magic Damage
- Physical Damage
- Absorption
- Blindness
- Haste
- Invisibility
- Jump Boost
- Levitation
- Nausea
- Night Vision
- Regeneration
- Slowness
- Slow Falling
- Water Breathing
- Agility
- Astral Distortion
- Entangle
- Flight
- Frost
- Fury
- Gravity Well
- Scramble Synapses (NYI)
- Shield
- Shrink (NYI)
- Silence
- Swift Swim
- Temporal Anchor
- True Sight (NYI)
- Watery Grave
- Attract (NYI)
- Banish Rain
- Blink (NYI)
- Blizzard (NYI)
- Charm
- Create Water
- Daylight (NYI)
- Dig
- Disarm
- Dispel
- Divine Intervention (NYI)
- Drought
- Ender Intervention (NYI)
- Falling Star (NYI)
- Fire Rain (NYI)
- Fling
- Forge
- Grow
- Harvest
- Heal
- Ignition
- Knockback
- Life Drain (NYI)
- Life Tip (NYI)
- Light
- Mana Blast (NYI)
- Mana Drain (NYI)
- Mana Shield (NYI)
- Mark (NYI)
- Melt Armor (NYI)
- Moonrise (NYI)
- Place Block (NYI)
- Plant
- Plow
- Random Teleport
- Recall (NYI)
- Reflect (NYI)
- Repel
- Rift (NYI)
- Storm
- Summon (NYI)
- Telekinesis (NYI)
- Transplace (NYI)
- Wizard's Autumn

#### Modifiers

- Bounce
- Damage
- Dismembering (NYI)
- Duration
- Effect Power
- Gravity
- Healing
- Lunar
- Mining Power
- Piercing
- Prosperity
- Range
- Rune Procs
- Silk Touch
- Solar
- Target Non Solid
- Velocity

### Skill Points

- Are used to unlock skills
- Come in three variants (blue, green, red)
- Blue skill points are granted on level up
- Green skill points are granted on level up, starting at level 20
- Red skill points are granted on level up, starting at level 30
- Players start out with 3 blue skill points (configurable)
- Can manually be increased via Infinity Orb items

### Spell Casting

- When first casting a spell, you will be asked to name your spell and give it an icon (finish with ESC)
- Spell Casting consumes mana and gives you burnout, magic XP and affinity bonuses

### Mana, Burnout & Leveling

- Spell casting consumes mana, the more complex the spell, the more mana is consumed
- Spell casting also gives you burnout, the more burnout you have, the higher the mana cost
- Spell casting also gives magic XP, the more complex the spell, the more magic XP you get
- Mana regenerates over time, while burnout degenerates over time
- Max Mana and Max Burnout increase with magic level ups
- A level requires 2.5 * (1.2 ^ next level) magic XP (level 1 -> level 2 requires ~30 casts of a Projectile-Dig spell)

### Affinites

- Added 10 affinites: water, fire, earth, air, ice, lightning, life, nature, arcane, ender
- Added an Affinity Essence item and an Affinity Tome item for each of these affinities
- Added temporary crafting recipes for the Affinity Essences
- Affinities are planned to give both positive and negative traits in the future, keep that in mind when playing

## API

This part is only relevant if you plan to make an addon mod for Ars Magica: Legacy. It allows you to add new spell
parts, skill points and affinites, and to hook into some of the mod's core mechanics.

### Advancements

- Added PlayerLearnedSkillTrigger and PlayerLevelUpTrigger

### Affinities

- Added IAffinity, IAffinityHelper and IAffinityItem

### Altar

- Added AltarCapMaterial and AltarStructureMaterial

### Datagen

- Added AltarStructureMaterialProvider, OcculusTabBuilder, OcculusTabProvider, SkillBuilder, SkillProvider and
  SpellPartProvider

### Etherium

- Added EtheriumType, IEtheriumConsumer and IEtheriumProvider

### Events

- Added AffinityChangingEvent, PlayerLevelUpEvent and SpellCastEvent

### Magic

- Added IBurnoutHelper, IMagicHelper and IManaHelper

### Occulus

- Added ISpellIngredientRenderer, OcculusTabRenderer, IOcculusTab and IOcculusTabManager

### Skills

- Added ISkill, ISkillHelper, ISkillManager, ISkillPoint and ISkillPointItem

### Spells & Spell Parts

- Added ISpell, ISpellComponent, ISpellDataManager, ISpellHelper, ISpellIngredient, ISpellItem, ISpellModifier,
  ISpellPart, ISpellPartData, ISpellShape, ShapeGroup, SpellCastResult and SpellStack
