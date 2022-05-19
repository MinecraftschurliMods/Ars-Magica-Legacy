# Version 1.18.2-1.1.0

## Update

- Update to Forge 40.1.0

## Additions

### Abilities

- Added abilities!
- Abilities are slight buffs or debuffs to the player that activate once the player has delved deep enough into an affinity
- Every affinity has a normal buff starting at either 1% and scaling up or starting at 25% depthö, one debuff starting at 50% depth, and a special buff at 100% depth
- Ender is the exception to this, it has two buffs and two debuffs starting at 50%, with a special one at 100%. The debuffs vanish at 100% as well.

### Rituals

- Rituals are now heavily customizable through datapacks
- Lightning Guardian spawning now correctly works

### Dryads

- Added dryads!
- Dryads are passive humanoid mobs that spawn in large groups (15-25 dryads) in forests
- They can be lured with saplings and passively bonemeal nearby plants
- If 20 dryads are slain within a minute, the Nature Guardian spawns
- All values can be tweaked through the config

### Spell Book

- Added the spell book
- It can be used to store multiple spells in one slot
- Spells can be managed by shift-opening the book, the selected spell can be switched via shift-scrolling

## Fixes

- Fixed a copy-paste error with wall and wave shapes that could crash the game
- Prefab spells now show up correctly in JEI
- The levelup advancement trigger now correctly fires
- Commands received an internal cleanup, they should now work correctly
- Various fixes and cleanups

## Compatibility

- Added compatibility with JEI versions 10 and up

# Previous Versions

## Additions

### Arcane Compendium

- Entry point to the mod
- Acts as the guide book to the mod
- Must be crafted (not present in the inventory, crafted) to initiate leveling

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
- Cap and structure materials can be altered via datapacks, default structure materials include vanilla's and AM:L's
- Put a book into the lectern to start the crafting process
- Throw the necessary items in
- Pickup the spell after throwing in the Spell Parchment
- The required power level for creating a spell is determined by that spell's ingredient count

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
- Beam (WIP)
- Chain (WIP)
- Channel
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
- Magic Shield
- Reflect
- Scramble Synapses
- Shield
- Shrink (WIP)
- Silence
- Swift Swim
- Temporal Anchor
- True Sight
- Watery Grave
- Attract
- Banish Rain
- Blink
- Blizzard (WIP)
- Charm
- Create Water
- Daylight
- Dig
- Disarm
- Dispel
- Divine Intervention
- Drought
- Ender Intervention
- Falling Star (WIP)
- Fire Rain (WIP)
- Fling
- Forge
- Grow
- Harvest
- Heal
- Ignition
- Knockback
- Life Drain
- Life Tip
- Light
- Mana Blast (WIP)
- Mana Drain
- Mana Shield (WIP)
- Melt Armor
- Moonrise
- Place Block
- Plant
- Plow
- Random Teleport
- Recall
- Repel
- Rift
- Storm
- Summon (WIP)
- Telekinesis (WIP)
- Transplace
- Wizard's Autumn

#### Modifiers

- Bounce
- Damage
- Dismembering (WIP)
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
- Green skill points are granted every second level up, starting at level 10
- Red skill points are granted every third level up, starting at level 20
- Players start out with 2 extra blue skill points (configurable)
- Can manually be increased via Infinity Orb items or via commands

### Spell Casting

- When first casting a spell, you will be asked to name your spell and give it an icon (finish with ESC)
- Spell Casting consumes mana and gives you burnout, magic XP and affinity bonuses

### Mana, Burnout & Leveling

- Spell casting consumes mana, the more complex the spell, the more mana is consumed
- Spell casting also gives you burnout, the more burnout you have, the higher the mana cost
- Spell casting also gives magic XP, the more complex the spell, the more magic XP you get
- Mana regenerates over time, while burnout degenerates over time
- Max Mana and Max Burnout increase with magic level ups
- A level requires 2.4 * (1.2 ^ next level) magic XP (level 1 -> level 2 requires ~30 casts of a Projectile-Dig spell)

### Affinites

- Added 10 affinites: water, fire, earth, air, ice, lightning, life, nature, arcane, ender
- Added an Affinity Essence item and an Affinity Tome item for each of these affinities
- Added temporary crafting recipes for the Affinity Essences
- Affinities are planned to give both positive and negative traits in the future, keep that in mind when playing

### Bosses

- Added bosses! One boss was added for each affinity
- Each boss can be summoned through a ritual described in the Arcane Compendium
- Bosses drop affinity essences on death
- Currently, only the water guardian is fully implemented

### HUDs

- The player sees their current magic xp level, magic xp progress, mana and burnout in a bar to the right of their hotbar
- Only visible if magic is already known
- The position can be altered through the config

### Witchwood

- Witchwood trees now rarely generate in dark forests (and other biomes marked as SPOOKY)
- They generate as a bit larger dark oak trees
- Witchwood is now used instead of "any wood" in spell recipes
- Witchwood planks are an altar structure material

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

### Mana Cake

- Added the Mana Cake: a food item that gives a mana regen effect when eaten

### Mana Martini

- Added the Mana Martini: a drink that gives a burnout reduction effect when drank

### Mage Armor

- Added mage armor
- Has low durability, but repairs itself using mana
- Added battlemage armor, which is an improved but more expensive variant of the normal mage armor
