# Items

- Ten new items, one for each boss

## Water Orbs

- Dropped by the Water Guardian
- Can be equipped into the Leggings slot, or into the Belt slot if Curios is installed
- Provides infinite air underwater when worn
- Disables water drag when worn
- Increases water swim speed similar to having the Dolphin's Grace effect
- Unbreakable

## Fire Antennae

- Dropped by the Fire Guardian
- Is fire-proof like Netherite equipment
- Can be equipped into the Helmet slot, or into the Head slot if Curios is installed
- Negates all incoming fire damage when worn
- Allows swimming and seeing in lava as if it were water when worn
- Disables lava drag when worn
- Unbreakable

## Earth Armor

- Dropped by the Earth Guardian
- Is fire-proof like Netherite equipment
- Can be enchanted
- Repairs itself using mana, like Mage/Battlemage armor
- Provides 16 armor, 4 armor toughness and 1 extra attack damage when worn; outperforming Netherite armor in all metrics except knockback resistance

## Air Sled

- Dropped by the Air Guardian
- Right-click to place
- Mount for creative-like flight
- Shift while on the ground to dismount
- Shift-right-click to pick up

## Winter's Grasp

- Dropped by the Ice Guardian
- Can be used as a melee weapon similar to a sword, dealing 4 attack damage
- Can be thrown at enemies like the Ice Guardian does, damaging the target for 4 damage and pulling it in
- Unbreakable

## Lightning Charm

- Dropped by the Lightning Guardian
- Attracts items in a 16 block range (configurable)
- Can be placed into a Charm slot from Curios if installed

## Nature Scythe

- Dropped by the Nature Guardian
- Can be used as a melee weapon similar to an axe, dealing 12 attack damage
- Can be thrown at enemies like the Nature Guardian does, damaging the target for 12 damage
- Unbreakable

## Life Ward

- Dropped by the Life Guardian
- Adds a shield of 20 extra health (10 hearts) in the form of a green heart outline
- Generates half a heart per second if the last hit is 5 seconds or longer ago
- Regeneration speed, max amount, and cooldowns can all be configured individually
- Can be placed into a Charm slot from Curios if installed

## Arcane Spell Book

- Dropped by the Arcane Guardian
- Is fire-proof like Netherite equipment
- Acts as an upgrade to the regular Spell Book
- Spells cast from the Arcane Spell Book will cost 20% less mana (adjustable in config)
- Spells cast from the Arcane Spell Book will be boosted in some stats by 40%, similar to the Augmented Casting talent (adjustable in config)

## Ender Boots

- Dropped by the Ender Guardian
- Is fire-proof like Netherite equipment
- Can be enchanted
- Repairs itself using mana, like Mage/Battlemage armor
- Provides 3 armor and 3 armor toughness when worn; equivalent to Netherite armor save for the missing knockback resistance

# Miscellaneous

- Swimming in Liquid Etherium now grants the Mana Regeneration effect
- Slightly debuffed the Augmented Casting talent (50% -> 40% boost to some stats)
- Added a list of altar materials to JEI

# Fixes

- Fixed the orientation of the Ice Guardian's arm when thrown
- Fixed an animation error on many bosses
- Fix a crash related to logging affinities

# API Changes

- `SpellCastContext`: Now has and requires an additional `statMultiplier` property
- `SpellHelper#cast`: Now requires two additional parameters `innateManaMultiplier` and `innateStatMultiplier`
