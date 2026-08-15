# Items

- Ten new items, one for each boss

## Water Orbs

- Dropped by the Water Guardian
- Can be equipped into the Leggings slot, or into the Belt slot if Curios is installed
- Provides infinite air underwater

## Fire Antennae

- Dropped by the Fire Guardian
- Is fire-proof like Netherite equipment
- Can be equipped into the Helmet slot, or into the Head slot if Curios is installed
- Negates all incoming fire damage

## Earth Armor

- Dropped by the Earth Guardian
- Is fire-proof like Netherite equipment
- Can be enchanted
- Repairs itself using mana, like Mage/Battlemage armor
- Provides 16 armor, 4 armor toughness and 1 extra attack damage when worn; outperforming Netherite armor in all metrics except knockback resistance

## Air Sled

- Dropped by the Air Guardian

## Winter's Grasp

- Dropped by the Ice Guardian
- Can be used as a melee weapon similar to a sword, dealing 4 attack damage
- Can be thrown at enemies like the Ice Guardian does, damaging and pulling in the target

## Lightning Charm

- Dropped by the Lightning Guardian

## Nature Scythe

- Dropped by the Nature Guardian
- Can be used as a melee weapon similar to an axe, dealing 12 attack damage
- Can be thrown at enemies like the Nature Guardian does, damaging the target

## Life Ward

- Dropped by the Life Guardian

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
- Provides 3 armor and 3 armor toughness; equivalent to Netherite armor save for the missing knockback resistance

# Miscellaneous

- Slightly debuffed the Augmented Casting talent (50% -> 40% boost to some stats)
- Fixed the orientation of the Ice Guardian's arm when thrown

# API Changes

- `SpellCastContext`: Now has and requires an additional `statMultiplier` property
- `SpellHelper#cast`: Now requires two additional parameters `innateManaMultiplier` and `innateStatMultiplier`
