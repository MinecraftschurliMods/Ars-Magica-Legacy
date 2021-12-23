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
