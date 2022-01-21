package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

class AMEnglishLanguageProvider extends AMLanguageProvider {
    AMEnglishLanguageProvider(DataGenerator generator) {
        super(generator, "en_us");
    }

    @Override
    protected void addTranslations() {
        itemGroupTranslation(AMItems.TAB, ArsMagicaLegacy.getModName());
        itemGroupTranslation(PrefabSpellManager.ITEM_CATEGORY,ArsMagicaLegacy.getModName() + " - Prefab Spells");
        blockIdTranslation(AMBlocks.OCCULUS);
        blockIdTranslation(AMBlocks.INSCRIPTION_TABLE);
        blockIdTranslation(AMBlocks.ALTAR_CORE);
        blockIdTranslation(AMBlocks.MAGIC_WALL);
        blockIdTranslation(AMBlocks.OBELISK);
        wipBlockIdTranslation(AMBlocks.CELESTIAL_PRISM);
        wipBlockIdTranslation(AMBlocks.BLACK_AUREM);
        addBlock(AMBlocks.WIZARDS_CHALK, "Wizard's Chalk");
        itemIdTranslation(AMItems.MAGITECH_GOGGLES);
        itemIdTranslation(AMItems.CRYSTAL_WRENCH);
        blockIdTranslation(AMBlocks.CHIMERITE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
        itemIdTranslation(AMItems.CHIMERITE);
        addBlock(AMBlocks.CHIMERITE_BLOCK, "Block of Chimerite");
        blockIdTranslation(AMBlocks.TOPAZ_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_TOPAZ_ORE);
        itemIdTranslation(AMItems.TOPAZ);
        addBlock(AMBlocks.TOPAZ_BLOCK, "Block of Topaz");
        blockIdTranslation(AMBlocks.VINTEUM_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_VINTEUM_ORE);
        itemIdTranslation(AMItems.VINTEUM_DUST);
        addBlock(AMBlocks.VINTEUM_BLOCK, "Block of Vinteum");
        wipBlockIdTranslation(AMBlocks.MOONSTONE_ORE);
        wipBlockIdTranslation(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
        wipItemIdTranslation(AMItems.MOONSTONE);
        addBlock(AMBlocks.MOONSTONE_BLOCK, "[WIP] Block of Moonstone");
        wipBlockIdTranslation(AMBlocks.SUNSTONE_ORE);
        wipItemIdTranslation(AMItems.SUNSTONE);
        addBlock(AMBlocks.SUNSTONE_BLOCK, "[WIP] Block of Sunstone");
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_LOG);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD);
        wipBlockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD_LOG);
        wipBlockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_LEAVES);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_SAPLING);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_PLANKS);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_SLAB);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_STAIRS);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_FENCE);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_FENCE_GATE);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_DOOR);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_TRAPDOOR);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_BUTTON);
        wipBlockIdTranslation(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
        itemIdTranslation(AMItems.BLANK_RUNE);
        for (DyeColor color : DyeColor.values()) {
            itemIdTranslation(AMItems.COLORED_RUNE.registryObject(color));
        }
        itemIdTranslation(AMItems.RUNE_BAG);
        itemIdTranslation(AMItems.ARCANE_COMPOUND);
        itemIdTranslation(AMItems.ARCANE_ASH);
        itemIdTranslation(AMItems.PURIFIED_VINTEUM_DUST);
        blockIdTranslation(AMBlocks.AUM);
        blockIdTranslation(AMBlocks.CERUBLOSSOM);
        blockIdTranslation(AMBlocks.DESERT_NOVA);
        blockIdTranslation(AMBlocks.TARMA_ROOT);
        blockIdTranslation(AMBlocks.WAKEBLOOM);
        blockIdTranslation(AMBlocks.VINTEUM_TORCH);
        itemIdTranslation(AMItems.SPELL_PARCHMENT);
        itemIdTranslation(AMItems.SPELL);
        itemIdTranslation(AMItems.MANA_CAKE);
        itemIdTranslation(AMItems.MANA_MARTINI);
        for (RegistryObject<IAffinity> affinity : AMRegistries.AFFINITIES.getEntries()) {
            affinityIdTranslation(affinity);
            affinityItemIdTranslation(AMItems.AFFINITY_ESSENCE, affinity);
            affinityItemIdTranslation(AMItems.AFFINITY_TOME, affinity);
        }
        for (RegistryObject<ISkillPoint> skillPoint : AMRegistries.SKILL_POINTS.getEntries()) {
            skillPointIdTranslation(skillPoint);
            skillPointItemIdTranslation(AMItems.INFINITY_ORB, skillPoint);
        }
        effectIdTranslation(AMMobEffects.AGILITY);
        effectIdTranslation(AMMobEffects.ASTRAL_DISTORTION);
        effectIdTranslation(AMMobEffects.BURNOUT_REDUCTION);
        effectIdTranslation(AMMobEffects.CLARITY);
        effectIdTranslation(AMMobEffects.ENTANGLE);
        effectIdTranslation(AMMobEffects.FLIGHT);
        effectIdTranslation(AMMobEffects.FROST);
        effectIdTranslation(AMMobEffects.FURY);
        effectIdTranslation(AMMobEffects.GRAVITY_WELL);
        effectIdTranslation(AMMobEffects.ILLUMINATION);
        effectIdTranslation(AMMobEffects.INSTANT_MANA);
        effectIdTranslation(AMMobEffects.MAGIC_SHIELD);
        effectIdTranslation(AMMobEffects.MANA_BOOST);
        effectIdTranslation(AMMobEffects.MANA_REGEN);
        effectIdTranslation(AMMobEffects.REFLECT);
        effectIdTranslation(AMMobEffects.SCRAMBLE_SYNAPSES);
        effectIdTranslation(AMMobEffects.SHIELD);
        effectIdTranslation(AMMobEffects.SHRINK);
        effectIdTranslation(AMMobEffects.SILENCE);
        effectIdTranslation(AMMobEffects.SWIFT_SWIM);
        effectIdTranslation(AMMobEffects.TEMPORAL_ANCHOR);
        effectIdTranslation(AMMobEffects.TRUE_SIGHT);
        effectIdTranslation(AMMobEffects.WATERY_GRAVE);
        potionIdTranslation(AMMobEffects.LESSER_MANA);
        potionIdTranslation(AMMobEffects.STANDARD_MANA);
        potionIdTranslation(AMMobEffects.GREATER_MANA);
        potionIdTranslation(AMMobEffects.EPIC_MANA);
        potionIdTranslation(AMMobEffects.LEGENDARY_MANA);
        addAttribute(AMAttributes.BURNOUT_REGEN, "Burnout Regeneration");
        addAttribute(AMAttributes.MANA_REGEN, "Mana Regeneration");
        attributeIdTranslation(AMAttributes.MAGIC_VISION);
        attributeIdTranslation(AMAttributes.MAX_BURNOUT);
        attributeIdTranslation(AMAttributes.MAX_MANA);
        entityIdTranslation(AMEntities.PROJECTILE);
        entityIdTranslation(AMEntities.WAVE);
        entityIdTranslation(AMEntities.WALL);
        entityIdTranslation(AMEntities.ZONE);
        entityIdTranslation(AMEntities.WATER_GUARDIAN);
        entityIdTranslation(AMEntities.FIRE_GUARDIAN);
        entityIdTranslation(AMEntities.EARTH_GUARDIAN);
        entityIdTranslation(AMEntities.AIR_GUARDIAN);
        entityIdTranslation(AMEntities.WINTER_GUARDIAN);
        entityIdTranslation(AMEntities.LIGHTNING_GUARDIAN);
        entityIdTranslation(AMEntities.NATURE_GUARDIAN);
        entityIdTranslation(AMEntities.LIFE_GUARDIAN);
        entityIdTranslation(AMEntities.ARCANE_GUARDIAN);
        entityIdTranslation(AMEntities.ENDER_GUARDIAN);
        entityIdTranslation(AMEntities.DRYAD);
        entityIdTranslation(AMEntities.MAGE);
        entityIdTranslation(AMEntities.MANA_CREEPER);
        advancementTranslation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "root"), ArsMagicaLegacy.getModName(), "A renewed look into Minecraft with a splash of magic...");
        skillTranslation(AMSpellParts.ABSORPTION.getId(), "Absorption", "Like a slightly flimsier shield.", "components", "You gain absorption hearts, like you would when eating a golden apple. This does not stack with golden apples.");
        skillTranslation(AMSpellParts.AGILITY.getId(), "Agility", "Seems like you won't be catching me anytime soon.", "components", "You managed to gain step-up abilities, greater jump height and reduced fall damage.");
        skillTranslation(AMSpellParts.AOE.getId(), "AoE", "Zone control.", "shapes", "After charging your spell, you can shape it into a blast that radiates outwards from the spell's origin. An AoE spell will not affect the caster.");
        skillTranslation(AMSpellParts.ASTRAL_DISTORTION.getId(), "Astral Distortion", "Going nowhere...", "components", "This spell entirely prevents teleportation of the target for some time. Also works on endermen and shulkers!");
        skillTranslation(AMSpellParts.ATTRACT.getId(), "Attract", "Come closer... or not, just stay where you are.", "components", "You create an area of negative pressure around you, pulling everything living in towards you.");
        skillTranslation(AMSpellParts.BANISH_RAIN.getId(), "Banish Rain", "Come back later. Or don't. It would be kind.", "components", "Rain rain, go away. Come again another day!$(br2)Who would have thought that those were the words to the spell?");
        skillTranslation(AMSpellParts.BEAM.getId(), "[WIP] Beam", "Beam me up, Scotty!", "shapes", "You can switch from a channeled spell to a beam. The amount of mana, however, is another story.$(br2)A beam is a much more controlled version of a channeled spell.");
        skillTranslation(AMSpellParts.BLINDNESS.getId(), "Blindness", "Just as the name says.", "components", "Having a fireball to throw at dark mages is good. But making it so they also can't see to retaliate is better.");
        skillTranslation(AMSpellParts.BLINK.getId(), "Blink", "Well, I'm out.", "components", "You can teleport a short distance directly forward the way you are facing.$(br2)Blink can take you through solid walls, but will make every effort to ensure you don't get stuck in one.");
        skillTranslation(AMSpellParts.BLIZZARD.getId(), "[WIP] Blizzard", "Snow. Lots of snow.", "components", "You have learned to summon a fearsome blizzard, which will slow and damage any entities in its radius.$(br2)Blizzard has a built-in $(l:shapes/aoe)AoE$() to it.$(br2)Though blizzard will harm all entities in its radius, it will never harm its caster.");
        skillTranslation(AMSpellParts.BOUNCE.getId(), "Bounce", "Hey! Come back!", "modifiers", "Causes $(l:shapes/projectile)spell projectiles$() to bounce off surfaces.");
        skillTranslation(AMSpellParts.CHAIN.getId(), "[WIP] Chain", "Looks like you brought friends. Well, I don't mind, you're all gonna die.", "shapes", "You can shape your magic into a blast that jumps from one target to the next. Chain contains an 8-block built in touch-like component, though you can, if you like, add $(l:shapes/projectile)Projectile$() to start the chain somewhere else.$(br2)A chain spell cannot affect the caster. Chain jump range can be extended by using the $(l:modifiers/range)Range$() modifier.");
        skillTranslation(AMSpellParts.CHANNEL.getId(), "Channel", "You might want to concentrate.", "shapes", "Through intense concentration, you can maintain a flow of magic on yourself.$(br2)This is useful for things like $(l:components/heal)Heal$(), $(l:components/attract)Attract$() and $(l:components/repel)Repel$().");
        skillTranslation(AMSpellParts.CHARM.getId(), "Charm", "Friends! Lots of friends! And they blew up...", "components", "You can cause breedable creatures to breed.");
        skillTranslation(AMSpellParts.CREATE_WATER.getId(), "Create Water", "Yep, totally intended turning my snow into water...", "components", "You can coalesce moisture from the air around into one location, creating water where there was none. It can also be used to fill a cauldron.$(br2)This will not work in the nether.");
        skillTranslation(AMSpellParts.DAMAGE.getId(), "Damage", "Now it hurts...", "modifiers", "Amplifies the damage dealt by spells, or the healing done by damage spells to the undead.");
        skillTranslation(AMSpellParts.DAYLIGHT.getId(), "Daylight", "Does that mean I can control time?", "components", "You have gained the ability to control time.$(br2)This spell will cause the time to move to dawn.");
        skillTranslation(AMSpellParts.DIG.getId(), "Dig", "Diggy Diggy Hole!", "components", "The ground shatters with a snap of your fingers. Harder blocks take more mana to break.$(br2)Dig starts out equivalent to an Iron pickaxe, but can be upgraded with the use of the $(l:modifiers/mining_power)Mining Power$() modifier.");
        skillTranslation(AMSpellParts.DISARM.getId(), "Disarm", "Woops, you dropped something?", "components", "Now that you have learned to summon tools to your hand, it was a small step to be able to make others drop what they are holding.");
        skillTranslation(AMSpellParts.DISMEMBERING.getId(), "[WIP] Dismembering", "Wasn't me. I swear he had no head when I came in.", "modifiers", "You like souvenirs so much that you have discovered how to make your damaging spells leave some pieces intact.$(br2)Each modifier adds a 5% chance to drop a head when defeating an enemy.");
        skillTranslation(AMSpellParts.DISPEL.getId(), "Dispel", "Cleaning up the mess.", "components", "Creating a localized field of deficit, you can remove up to six levels of potion effects on your target.");
        skillTranslation(AMSpellParts.DIVINE_INTERVENTION.getId(), "Divine Intervention", "Help from across the cosmos.", "components", "You have mastered teleportation magic, to the level at which you can transcend interdimensional barriers, and can enter the overworld from anywhere, except the nether.");
        skillTranslation(AMSpellParts.DROUGHT.getId(), "Drought", "Sand. Who doesn't know that?", "components", "You have taken your knowledge of creating water and have reversed the process.$(br2)This spell will draw water out of whatever it hits, removing water blocks, turning dirt-ish blocks to sand, withering plants it hits, and cracking stone to cobblestone.");
        skillTranslation(AMSpellParts.DROWNING_DAMAGE.getId(), "Drowning Damage", "How can you drown? There isn't any water.", "components", "You can create water directly inside the target's lungs, causing them to take drowning damage.");
        skillTranslation(AMSpellParts.DURATION.getId(), "Duration", "Time manipulation tricks.", "modifiers", "Enhances the duration of all effect spells, and increases the lifetime of $(l:shapes/projectile)projectiles$(), $(l:shapes/wall)walls$(), $(l:shapes/wave)waves$() and $(l:shapes/zone)zones.");
        skillTranslation(AMSpellParts.EFFECT_POWER.getId(), "Effect Power", "Harder, better, faster and... my mana pool is empty.", "modifiers", "You can put more power into your effects. Each modifier added increases the level of the effect applied by one.");
        skillTranslation(AMSpellParts.ENDER_INTERVENTION.getId(), "Ender Intervention", "Well, I didn't know what hell looked like...", "components", "You have mastered teleportation magic, to the level at which you can transcend interdimensional barriers, and can enter the end from anywhere, except the nether.");
        skillTranslation(AMSpellParts.ENTANGLE.getId(), "Entangle", "Stop right there.", "components", "At your command, vines can burst from the ground and ensnare your target, holding them completely immobile.");
        skillTranslation(AMSpellParts.FALLING_STAR.getId(), "[WIP] Falling Star", "Shiny! Wait, is it falling towards me?", "components", "You can call down a star from the skies and cause it to strike all entities within the blast radius. It will harm friendly targets but not the caster.$(br2)There is a short delay between casting the spell and the impact. This spell will not work underground (the star will fall onto the surface).");
        skillTranslation(AMSpellParts.FIRE_DAMAGE.getId(), "Fire Damage", "You shall burn!", "components", "With a word, you can release your will, and fire will erupt from out in front of you, searing everything in its path. Fire damage is hard hitting, but many nether mobs are immune to its effects.");
        skillTranslation(AMSpellParts.FIRE_RAIN.getId(), "[WIP] Fire Rain", "Well, at least I'm not going to catch a cold...", "components", "You have learned to summon a terrible firestorm, which will do large amounts of damage to all entities in its radius. Firestorm has a built-in $(l:shapes/aoe)AoE$() to it.$(br2)Firestorm does not ignite the ground and will never harm its caster.");
        skillTranslation(AMSpellParts.FLIGHT.getId(), "Flight", "I'd rather use a plane.", "components", "With a word, you can rise into the air.");
        skillTranslation(AMSpellParts.FLING.getId(), "Fling", "Ready for an air fight?", "components", "This spell makes wind whirl around under your target, and suddenly all at once blow them straight up, sending them skyward.");
        skillTranslation(AMSpellParts.FORGE.getId(), "Forge", "Portable furnace!", "components", "You have gained fine control over fire and can use it to magically smelt blocks where they stand, without charring them to ash.");
        skillTranslation(AMSpellParts.FROST.getId(), "Frost", "Freeze!", "components", "You breath deeply and open your eyes. Water will become ice. Enemies move at a crawl. Perfect.");
        skillTranslation(AMSpellParts.FROST_DAMAGE.getId(), "Frost Damage", "Cold...", "components", "Many underestimate the power that frost can wield. The creeping chill can bypass many armors.");
        skillTranslation(AMSpellParts.FURY.getId(), "Fury", "Berserker rage!", "components", "You can send yourself into an absolute rage, dealing increased damage, moving extremely fast, passively regenerating, and mining at inhuman speeds.$(br2)When the effect ends, you are left exhausted for a few moments and must recover.");
        skillTranslation(AMSpellParts.GRAVITY.getId(), "Gravity", "Falling... okay...", "modifiers", "$(l:shapes/zone)Zones$() and $(l:shapes/projectile)projectiles$() will be affected by gravity.");
        skillTranslation(AMSpellParts.GRAVITY_WELL.getId(), "Gravity Well", "Not like a chicken. The opposite.", "components", "You have learned to create a localized gravity well under your target, greatly increasing the speed at which they fall.");
        skillTranslation(AMSpellParts.GROW.getId(), "Grow", "I won't sit all day.", "components", "Pouring energy into plants, equal to months of talking to them, will cause them to grow more rapidly.");
        skillTranslation(AMSpellParts.HARVEST.getId(), "Harvest", "Let's just say you're all grown.", "components", "You can cause all plants, leaves, and flowers to be harvested.");
        skillTranslation(AMSpellParts.HASTE.getId(), "Haste", "I'm out of here...", "components", "Wrapping the target's hands in arcane energy, you can greatly increase mining speed.");
        skillTranslation(AMSpellParts.HEAL.getId(), "Heal", "Instant healing.", "components", "By greatly increasing the amount of power put into regenerative effects, you can knit almost any injury back together. The effect is taxing, however.");
        skillTranslation(AMSpellParts.HEALING.getId(), "Healing", "Efficiency over number.", "modifiers", "Amplifies the healing done by spells, or the damage dealt by healing spells to the undead.");
        skillTranslation(AMSpellParts.IGNITION.getId(), "Ignition", "Burn harder!", "components", "You see fire as a damaging, destructive force, and that can be true.$(br2)But how often do you hear fire and imagine lighting a campfire or candle?");
        skillTranslation(AMSpellParts.INVISIBILITY.getId(), "Invisibility", "Wanna play Hide & Seek?", "components", "You can bend light around yourself to become effectively invisible.");
        skillTranslation(AMSpellParts.JUMP_BOOST.getId(), "Jump Boost", "Not a frog? Who cares?", "components", "Gathering wind around you, you can propel yourself into the air.");
        skillTranslation(AMSpellParts.KNOCKBACK.getId(), "Knockback", "Punch from a distance!", "components", "As a mage, you most likely don't want to be in melee range. This component allows that situation to be corrected.");
        skillTranslation(AMSpellParts.LEVITATION.getId(), "Levitation", "Use the force.", "components", "Through practicing air magic, you can now hold yourself suspended in midair.$(br2)With small wind currents, you can move slowly about while floating.");
        skillTranslation(AMSpellParts.LIFE_DRAIN.getId(), "Life Drain", "Ahem... I'm taking all of it. Including you.", "components", "By creating a sinister link with the target's life force, you can siphon it off into your own, bolstering your own health.");
        skillTranslation(AMSpellParts.LIFE_TAP.getId(), "Life Tap", "I'm burrowing this.", "components", "If you are desparate and mana is scarce, you can fuel your spells using your own life force.");
        skillTranslation(AMSpellParts.LIGHT.getId(), "Light", "The end of a tunnel.", "components", "You can light up an area with magic.$(br2)If you apply this component onto a living being, it will light up the darkness by itself.");
        skillTranslation(AMSpellParts.LIGHTNING_DAMAGE.getId(), "Lightning Damage", "Zap!", "components", "Lightning does an exceptional amount of damage, but carries a hefty mana cost.");
        skillTranslation(AMSpellParts.LUNAR.getId(), "Lunar", "I'm gonna be a werewolf!", "modifiers", "Powers up your spell during the night. The closer to midnight it is, the more $(l:modifiers/damage)damage$() and $(l:modifiers/healing)healing$() the spell does. $(l:modifiers/duration)Duration$() and $(l:modifiers/range)range$() are increased where applicable based on the phase of the moon (more power approaching full moon).$(br2)Lunar is more powerful than $(l:modifiers/solar)Solar$() due to nights not lasting as long as day.");
        skillTranslation(AMSpellParts.MAGIC_DAMAGE.getId(), "Magic Damage", "Hit from the void!", "components", "Magical damage differs from physical damage in that it bypasses many kinds of armors and attacks the target's aura directly - which can be just as devastating, if not more.");
        skillTranslation(AMSpellParts.MANA_BLAST.getId(), "[WIP] Mana Blast", "I love mana, especially when it blows up in someone's face.", "components", "Your entire mana is used up to damage the target. The more mana you had, the more damage it does!");
        skillTranslation(AMSpellParts.MANA_DRAIN.getId(), "Mana Drain", "So much pools at my disposal!", "components", "You can create a parasitic bond with the target's aura, draining their mana and boosting your own.");
        skillTranslation(AMSpellParts.MANA_SHIELD.getId(), "[WIP] Mana Shield", "Well, now I know what boredom looks like.", "components", "Your target becomes shielded by your available mana, causing damage to drain 250 mana per half heart absorbed, and preventing HP damage entirely.");
        skillTranslation(AMSpellParts.MINING_POWER.getId(), "Mining Power", "Who needs diamond?", "modifiers", "You have learned to put more power into your digging spells. This causes them to be able to dig more dense blocks that would require a better tool.$(br2)Each modifier bumps the spell up by one tool level.$(br2)The base $(l:components/dig)dig$() component operates at iron mining level.");
        skillTranslation(AMSpellParts.MOONRISE.getId(), "Moonrise", "Full moon.", "components", "You have gained the ability to control time.$(br2)This spell will cause the time to move to dusk.");
        skillTranslation(AMSpellParts.NIGHT_VISION.getId(), "Night Vision", "Oh? There was a tunnel?", "components", "Your knowledge of light has allowed you to devise a spell that will let you amplify light levels, effectively letting you see in the dark.");
        skillTranslation(AMSpellParts.PHYSICAL_DAMAGE.getId(), "Physical Damage", "Ranged sword... why not?", "components", "Often, you will begin your training with simple physical force. Force is a physical damage type, and does not pierce armor.");
        skillTranslation(AMSpellParts.PIERCING.getId(), "Piercing", "AoE, here I come!", "modifiers", "Allows $(l:shapes/projectile)projectiles$() to pierce through entities and blocks.");
        skillTranslation(AMSpellParts.PLACE_BLOCK.getId(), "Place Block", "Don't mind me, I'm just sending an anvil.", "components", "You can use this spell part to place blocks! In order to place a block, you need to set the spell to place it (shift-use on the block), and you need to have at least one of said block in your inventory.");
        skillTranslation(AMSpellParts.PLANT.getId(), "Plant", "Why bother using hand when magic can do the same?", "components", "You can cause seeds from your inventory to be planted in the ground, if conditions are right for it to grow.$(br2)Best coupled with $(l:shapes/aoe)AoE$() for rapid planting.");
        skillTranslation(AMSpellParts.PLOW.getId(), "Plow", "Hoes are useless. Everyone knows that.", "components", "You can cause the earth to churn at your command, creating deep furrows ideal for planting.");
        skillTranslation(AMSpellParts.PROJECTILE.getId(), "Projectile", "Snowball!", "shapes", "You are able to focus your will into a concentrated ball, which is then propelled forwards away from you.$(br2)The projectile will last for five seconds of flight, or until it strikes something.$(br2)It will by default pass through water and non-collidable blocks unless you modify it with $(l:modifiers/target_non_solid)Target Non Solid$().");
        skillTranslation(AMSpellParts.PROSPERITY.getId(), "Prosperity", "Bling!", "modifiers", "Fortune strikes! You can make your digging spells more likely to drop additional ores, and your damaging spells more likely to cause enemies to drop better loot.$(br2)Each modifier added is equivalent to one level of fortune/looting on the spell.");
        skillTranslation(AMSpellParts.RANDOM_TELEPORT.getId(), "Random Teleport", "I wanna go... there! Woops, wrong spot.", "components", "You can randomly teleport your target a short distance away.");
        skillTranslation(AMSpellParts.RANGE.getId(), "Range", "Think you're far enough? No, you're not.", "modifiers", "Increases the range/size of many spells.");
        skillTranslation(AMSpellParts.RECALL.getId(), "Recall", "Can someone remind me who marked the trash?", "components", "You can tune your teleportation magic to home in on a mark you have left by shift-using the spell, transporting the target back to that location.");
        skillTranslation(AMSpellParts.REFLECT.getId(), "Reflect", "Bounces back to you.", "components", "You create a magic shield that $(l:shapes/projectile)spell projectiles$(), $(l:shapes/wall)walls$(), $(l:shapes/wave)waves$() and $(l:shapes/zone)zones$() will bounce off.");
        skillTranslation(AMSpellParts.REGENERATION.getId(), "Regeneration", "A little bit of health.", "components", "I wrapped my arm in a healing light, and watched as every injury, down to the last bruise, slowly vanished before my eyes.");
        skillTranslation(AMSpellParts.REPEL.getId(), "Repel", "Go away from me!", "components", "You can create a singularity in space, which, as long as you maintain it, will radiate waves of force, pushing anything moving away from the target position.");
        skillTranslation(AMSpellParts.RIFT.getId(), "Rift", "One day I'll walk through it, for now, it'll just store items.", "components", "You can tear open a rift in space, granting access to a small inventory to store items in. More $(l:modifiers/effect_power)effect power$() modifiers give greater storage access.$(br2)You can, if your friends are foolish enough, also open their personal rift instead.");
        skillTranslation(AMSpellParts.RUNE.getId(), "Rune", "Placeable magic.", "shapes", "You can create a magically infused rune on the ground that, when someone steps on them, can apply powerful buffs - or trigger deadly traps.");
        skillTranslation(AMSpellParts.RUNE_PROCS.getId(), "Rune Procs", "I want more!", "modifiers", "Increases the number of times a $(l:shapes/rune)rune$() can apply its effect before being destroyed.");
        skillTranslation(AMSpellParts.SELF.getId(), "Self", "It's all about me.", "shapes", "One of the simplest forms of magic application is applying the magic to yourself. The distance is low, and the target is willing. You only hope you don't accidentally light yourself on fire.");
        skillTranslation(AMSpellParts.SHIELD.getId(), "Shield", "Don't worry about the weight, it's magic.", "components", "You can summon arcane energy to shield yourself, acting effectively as bonus armor.");
        skillTranslation(AMSpellParts.SHRINK.getId(), "[WIP] Shrink", "Looks like I'm smaller now...", "components", "You can make yourself tiny! When this effect is active, you are physically smaller, so you can fit through 1x1 gaps.$(br2)Due to your light weight, you fall slowly enough that landing doesn't hurt either. However, all damage you do is halved.");
        skillTranslation(AMSpellParts.SILENCE.getId(), "Silence", "No talking! (Or casting in this case...)", "components", "You can silence another entity, preventing all spell casting for a duration.");
        skillTranslation(AMSpellParts.SILK_TOUCH.getId(), "Silk Touch", "Feels soft...", "modifiers", "With great power comes broken valuables.$(br2)You've learned to be more careful when casting your digging spells and break things less often.$(br2)Each modifier is equivalent to one level of Silk Touch on the spell.");
        skillTranslation(AMSpellParts.SLOWNESS.getId(), "Slowness", "No more running!", "components", "By applying the equivalent of a magical ball and chain, you can greatly slow the movements of your target.");
        skillTranslation(AMSpellParts.SLOW_FALLING.getId(), "Slow Falling", "Like a chicken...", "components", "Become light as a feather, and fall without fear.");
        skillTranslation(AMSpellParts.SOLAR.getId(), "Solar", "Sun power!", "modifiers", "Powers up your spell during the day. The closer to noon it is, the more $(l:modifiers/damage)damage$() and $(l:modifiers/healing)healing$() the spell does. $(l:modifiers/duration)Duration$() and $(l:modifiers/range)range$() are increased where applicable based on the phase of the moon (more power approaching new moon).");
        skillTranslation(AMSpellParts.STORM.getId(), "Storm", "It's raining...", "components", "The cloud darken, and rain begins to fall. The wind howls, and a flash of lightning strikes across the sky, leaving bright flashes in your vision.$(br2)This component changes the weather to a thunderstorm.");
        skillTranslation(AMSpellParts.SUMMON.getId(), "[WIP] Summon", "Rise, creation! Oh, you look like the others...", "components", "You have learned to harvest the souls of creatures you defeat in combat. These souls can be used in creating a spell to summon that creature to protect you.$(br2)To summon a creature, first craft a $(l:items/crystal_phylactery)Crystal Phylactery$(). During spell creation, you can throw in any filled phylactery when prompted by the lectern. This step is what determines what your spell will summon.");
        skillTranslation(AMSpellParts.SWIFT_SWIM.getId(), "Swift Swim", "No more swimming for hours.", "components", "By manipulating water currents, you can propel yourself along much more quickly underwater.");
        skillTranslation(AMSpellParts.TARGET_NON_SOLID.getId(), "Target Non Solid", "And I decided that I would fight the water.", "modifiers", "Allows the spell to target non-solid blocks (grass, water, lava) rather than passing through it.");
        skillTranslation(AMSpellParts.TELEKINESIS.getId(), "[WIP] Telekinesis", "Up, down, up, down, I think you get the idea.", "components", "You can use your magic to amplify your brainwaves, giving them physical form. These waves can be used to pull entities towards a point in front of you.$(br2)Rolling the mouse wheel while using the spell can change the distance of the point closer or farther away from you.");
        skillTranslation(AMSpellParts.TEMPORAL_ANCHOR.getId(), "Temporal Anchor", "Let's look at the time. Oh dear! It went backward!", "components", "You can anchor yourself in time.$(br2)When the timer runs out, you are transported back to the place you cast this spell, with your health, mana, burnout, and hunger returning to what they were when the spell was first cast.");
        skillTranslation(AMSpellParts.TOUCH.getId(), "Touch", "Someone in there?", "shapes", "Simply wrap your hand in magic, and reach out.$(br2)Touch is a very short range shape, it does not follow the block highlighting. You need to be almost bumping into your target for touch to apply its effect.");
        skillTranslation(AMSpellParts.TRANSPLACE.getId(), "Transplace", "From point A to point B.", "components", "Your knowledge of teleportation magic has grown. You can now switch places with your target.");
        skillTranslation(AMSpellParts.TRUE_SIGHT.getId(), "True Sight", "Reveal what is hidden.", "components", "Your magical sight allows you to see things as they really are. Who knows what beauties and horrors you will discover?");
        skillTranslation(AMSpellParts.VELOCITY.getId(), "Velocity", "Faster! FASTER!", "modifiers", "Enhances speed altering effects of spells, most notably the speed of $(l:shapes/projectile)projectiles$().");
        skillTranslation(AMSpellParts.WALL.getId(), "Wall", "You shall not pass.", "shapes", "You can manifest a wall in front of you.$(br2)Walls function similarly to $(l:shapes/zone)zones$(), but with a different form.");
        skillTranslation(AMSpellParts.WATERY_GRAVE.getId(), "Watery Grave", "Bottom of the ocean.", "components", "You can make water come alive, wrapping tendrils around the target and dragging it down into the black, crushing depths.");
        skillTranslation(AMSpellParts.WATER_BREATHING.getId(), "Water Breathing", "Creating air directly inside my lungs? Cool!", "components", "You can use your magic to pull oxygen from the water, allowing you to get enough to not drown.");
        skillTranslation(AMSpellParts.WAVE.getId(), "Wave", "You might not want to surf on this one...", "shapes", "You can project a wave of magic in front of you that rolls forward, applying its effect to everything in its path.");
        skillTranslation(AMSpellParts.WIZARDS_AUTUMN.getId(), "Wizard's Autumn", "Leaves fall in autumn. But it's kind of a slow process...", "components", "You have learned to focus your digging magic into a small radius that directly affects leaves.$(br2)This component has a built-in $(l:shapes/aoe)AoE$() that can be modified with $(l:modifiers/range)Range$() modifiers.");
        skillTranslation(AMSpellParts.ZONE.getId(), "Zone", "No one can beat me in my sanctuary!", "shapes", "You have learned to focus your will into an area effect that will persist for a time.");
        configTranslation("max_mana", "The default maximum mana for the player.");
        configTranslation("max_burnout", "The default maximum burnout for the player.");
        configTranslation("burnout_ratio", "The mana to burnout ratio.");
        configTranslation("crafting_altar_check_time", "The time in ticks between multiblock validation checks for the crafting altar.");
        configTranslation("max_shape_groups", "The maximum number of shape groups allowed for new spells.");
        configTranslation("extra_starting_blue_points", "The extra skill points a player gets on level 1.");
        configTranslation("effect_duration", "Effect duration of effect-based components, in ticks.");
        configTranslation("damage", "Damage of damage-based components, in half hearts.");
        add(TranslationConstants.ALTAR_CORE_LOW_POWER, "Altar has not enough power!");
        add(TranslationConstants.OCCULUS_MISSING_REQUIREMENTS, "You lack the skill points or parent skills to learn this skill!");
        add(TranslationConstants.CRYSTAL_WRENCH_TOO_FAR, "You cannot perform this action over such distance!");
        add(TranslationConstants.SPELL_BURNOUT, "Burnout: %d");
        add(TranslationConstants.SPELL_INVALID, "[Invalid Spell]");
        add(TranslationConstants.SPELL_INVALID_DESCRIPTION, "Something is wrong with this spell, please check the log for warnings or errors!");
        add(TranslationConstants.SPELL_MANA_COST, "Mana cost: %d");
        add(TranslationConstants.SPELL_REAGENTS, "Reagents:");
        add(TranslationConstants.SPELL_RECIPE_TITLE, "Spell Recipe:");
        add(TranslationConstants.SPELL_UNKNOWN, "Unknown Item");
        add(TranslationConstants.SPELL_UNKNOWN_DESCRIPTION, "Mythical forces prevent you from using this item!");
        add(TranslationConstants.SPELL_UNNAMED, "Unnamed Spell");
        add(TranslationConstants.COMMAND_SKILL_ALREADY_KNOWN, "Skill %s has already been learned");
        add(TranslationConstants.COMMAND_SKILL_EMPTY, "");
        add(TranslationConstants.COMMAND_SKILL_FORGET_ALL_SUCCESS, "Forgot all skills");
        add(TranslationConstants.COMMAND_SKILL_FORGET_SUCCESS, "Forgot skill %s");
        add(TranslationConstants.COMMAND_SKILL_LEARN_ALL_SUCCESS, "Learned all skills");
        add(TranslationConstants.COMMAND_SKILL_LEARN_SUCCESS, "Learned skill %s");
        add(TranslationConstants.COMMAND_SKILL_NOT_YET_KNOWN, "Skill %s must be learned first");
        add(TranslationConstants.COMMAND_SKILL_UNKNOWN, "Could not find skill %s");
        add(TranslationConstants.INSCRIPTION_TABLE_DEFAULT_NAME, "Spell");
        add(TranslationConstants.INSCRIPTION_TABLE_NAME, "Name");
        add(TranslationConstants.INSCRIPTION_TABLE_SEARCH, "Search");
        add(TranslationConstants.INSCRIPTION_TABLE_TITLE, "Inscription Table");
        add(TranslationConstants.OBELISK_TITLE, "Obelisk");
        add(TranslationConstants.RIFT_TITLE, "Rift Storage");
        add(TranslationConstants.SPELL_CUSTOMIZATION_TITLE, "Spell Customization");
        add(TranslationConstants.HOLD_SHIFT_FOR_DETAILS, "Hold Shift for details");
        add(TranslationConstants.NO_TELEPORT, "You are too distorted to teleport!");
        add(TranslationConstants.NO_TELEPORT_NETHER, "The nether's force forbids you to simply teleport out of it!");
        add(TranslationConstants.PREVENT, "Mythical forces prevent you from using this block!");
        add(TranslationConstants.SPELL_CAST + "burned_out", "Burned out!");
        add(TranslationConstants.SPELL_CAST + "cancelled", "Spell cast failed!");
        add(TranslationConstants.SPELL_CAST + "effect_failed", "Spell cast failed!");
        add(TranslationConstants.SPELL_CAST + "missing_reagents", "Missing reagents!");
        add(TranslationConstants.SPELL_CAST + "no_permission", "No permission!");
        add(TranslationConstants.SPELL_CAST + "not_enough_mana", "Not enough mana!");
        add(TranslationConstants.SPELL_CAST + "silenced", "Silence!");
        add(TranslationConstants.MULTIBLOCK_TIER, "Tier: %s");
        add("item." + ArsMagicaAPI.MOD_ID + ".arcane_compendium.components.summon.page1.text", "Summoned creatures drop no loot, and no xp, but can be interacted with normally, such as breeding, milking cows, or riding horses. Horses are the exception to the item drop rule and will drop saddles and armor given to them.$(br2)Tameable creatures such as wolves and cats are automatically tamed to their owner upon summoning.");
    }

    /**
     * Adds a block translation that matches [WIP] + the block id.
     *
     * @param block The block to generate the translation for.
     */
    private void wipBlockIdTranslation(RegistryObject<? extends Block> block) {
        addBlock(block, "[WIP] " + idToTranslation(block.getId().getPath()));
    }

    /**
     * Adds an item translation that matches [WIP] + the item id.
     *
     * @param item The item to generate the translation for.
     */
    private void wipItemIdTranslation(RegistryObject<? extends Item> item) {
        addItem(item, "[WIP] " + idToTranslation(item.getId().getPath()));
    }

    /**
     * Adds a block translation that matches the block id.
     *
     * @param block The block to generate the translation for.
     */
    private void blockIdTranslation(RegistryObject<? extends Block> block) {
        addBlock(block, idToTranslation(block.getId().getPath()));
    }

    /**
     * Adds an item translation that matches the item id.
     *
     * @param item The item to generate the translation for.
     */
    private void itemIdTranslation(RegistryObject<? extends Item> item) {
        addItem(item, idToTranslation(item.getId().getPath()));
    }

    /**
     * Adds an effect translation that matches the effect id.
     *
     * @param effect The effect to generate the translation for.
     */
    private void effectIdTranslation(RegistryObject<? extends MobEffect> effect) {
        addEffect(effect, idToTranslation(effect.getId().getPath()));
    }

    /**
     * Adds a potion translation that matches the potion id.
     * Also covers splash potion, lingering potion and tipped arrow translations.
     *
     * @param potion The potion to generate the translation for.
     */
    private void potionIdTranslation(RegistryObject<? extends Potion> potion) {
        add("item.minecraft.potion.effect." + potion.getId().getPath(), "Potion of " + idToTranslation(potion.getId().getPath()));
        add("item.minecraft.splash_potion.effect." + potion.getId().getPath(), "Splash Potion of " + idToTranslation(potion.getId().getPath()));
        add("item.minecraft.lingering_potion.effect." + potion.getId().getPath(), "Lingering Potion of " + idToTranslation(potion.getId().getPath()));
        add("item.minecraft.tipped_arrow.effect." + potion.getId().getPath(), "Arrow of " + idToTranslation(potion.getId().getPath()));
    }

    /**
     * Adds an attribute translation that matches the attribute id.
     *
     * @param attribute The attribute to generate the translation for.
     */
    private void attributeIdTranslation(RegistryObject<? extends Attribute> attribute) {
        add(attribute.get().getDescriptionId(), idToTranslation(attribute.getId().getPath()));
    }

    /**
     * Adds an entity translation that matches the entity id.
     *
     * @param entity The entity to generate the translation for.
     */
    private void entityIdTranslation(RegistryObject<? extends EntityType<?>> entity) {
        addEntityType(entity, idToTranslation(entity.getId().getPath()));
    }

    /**
     * Adds an skillPoint translation that matches the skillPoint id.
     *
     * @param skillPoint The skillPoint to generate the translation for.
     */
    private void skillPointIdTranslation(RegistryObject<? extends ISkillPoint> skillPoint) {
        addSkillPoint(skillPoint, idToTranslation(skillPoint.getId().getPath()));
    }

    /**
     * Adds an affinity translation that matches the affinity id.
     *
     * @param affinity The affinity to generate the translation for.
     */
    private void affinityIdTranslation(RegistryObject<? extends IAffinity> affinity) {
        addAffinity(affinity, idToTranslation(affinity.getId().getPath()));
    }

    /**
     * Adds a config translation.
     *
     * @param name        The config entry to add the translation for.
     * @param translation The translation to use.
     */
    private void configTranslation(String name, String translation) {
        add(TranslationConstants.CONFIG + name, translation);
    }

    /**
     * Adds an item group translation that matches the item group id.
     *
     * @param translation The creative tab to generate the translation for.
     */
    private void itemGroupTranslation(CreativeModeTab tab, String translation) {
        add("itemGroup." + tab.getRecipeFolderName(), translation);
    }

    /**
     * Adds an advancement translation.
     *
     * @param advancement The advancement id.
     * @param title       The advancement title.
     * @param description The advancement description.
     */
    private void advancementTranslation(ResourceLocation advancement, String title, String description) {
        add(Util.makeDescriptionId("advancement", advancement) + ".title", title);
        add(Util.makeDescriptionId("advancement", advancement) + ".description", description);
    }

    /**
     * Adds a skill translation, including its compendium counterpart.
     *
     * @param skill          The skill id.
     * @param name           The skill name.
     * @param description    The skill description.
     * @param compendiumType The compendium category ("shapes", "components" or "modifiers") this skill is in
     * @param compendiumText The description in the compendium.
     */
    private void skillTranslation(ResourceLocation skill, String name, String description, String compendiumType, String compendiumText) {
        add(Util.makeDescriptionId("skill", skill) + ".name", name);
        add(Util.makeDescriptionId("skill", skill) + ".description", description);
        add("item." + ArsMagicaAPI.MOD_ID + ".arcane_compendium." + compendiumType + "." + skill.getPath(), name);
        add("item." + ArsMagicaAPI.MOD_ID + ".arcane_compendium." + compendiumType + "." + skill.getPath() + ".page0.text", compendiumText);
    }

    /**
     * Adds an attribute translation.
     *
     * @param attribute   The attribute to add the translation for.
     * @param translation The translation for the attribute.
     */
    private void addAttribute(Supplier<? extends Attribute> attribute, String translation) {
        add(attribute.get().getDescriptionId(), translation);
    }

    /**
     * Adds an affinity translation.
     *
     * @param affinity    The affinity to add the translation for.
     * @param translation The translation for the affinity.
     */
    private void addAffinity(Supplier<? extends IAffinity> affinity, String translation) {
        addAffinity(affinity.get(), translation);
    }

    /**
     * Adds an affinity translation.
     *
     * @param affinity    The affinity to add the translation for.
     * @param translation The translation for the affinity.
     */
    private void addAffinity(IAffinity affinity, String translation) {
        add(affinity.getTranslationKey(), translation);
    }

    /**
     * Adds an affinity item translation.
     *
     * @param affinityItem The affinity item to add the translation for.
     * @param affinity     The affinity to generate the translation from.
     */
    private void affinityItemIdTranslation(RegistryObject<? extends IAffinityItem> affinityItem, RegistryObject<? extends IAffinity> affinity) {
        affinityItemIdTranslation(affinityItem.getId(), affinity.getId());
    }

    /**
     * Adds an affinity item translation.
     *
     * @param affinityItemId The affinity item to add the translation for.
     * @param affinityId     The affinity to generate the translation from.
     */
    private void affinityItemIdTranslation(ResourceLocation affinityItemId, ResourceLocation affinityId) {
        String translation = idToTranslation(affinityId.getPath()) + " " + idToTranslation(affinityItemId.getPath());
        affinityItemTranslation(affinityItemId, affinityId, translation);
    }

    /**
     * Adds an affinity item translation.
     *
     * @param affinityItem The affinity item to add the translation for.
     * @param affinity     The affinity to generate the translation from.
     * @param translation  The custom translation to use.
     */
    private void affinityItemTranslation(RegistryObject<? extends IAffinityItem> affinityItem, RegistryObject<? extends IAffinity> affinity, String translation) {
        affinityItemTranslation(affinityItem.getId(), affinity.getId(), translation);
    }

    /**
     * Adds an affinity item translation.
     *
     * @param affinityItemId The affinity item to add the translation for.
     * @param affinityId     The affinity to generate the translation from.
     * @param translation    The custom translation to use.
     */
    private void affinityItemTranslation(ResourceLocation affinityItemId, ResourceLocation affinityId, String translation) {
        add(Util.makeDescriptionId(Util.makeDescriptionId("item", affinityItemId), affinityId), translation);
    }

    /**
     * Adds a skill point translation.
     *
     * @param skillPoint  The skill point to add the translation for.
     * @param translation The translation for the skill point.
     */
    private void addSkillPoint(Supplier<? extends ISkillPoint> skillPoint, String translation) {
        addSkillPoint(skillPoint.get(), translation);
    }

    /**
     * Adds a skill point translation.
     *
     * @param skillPoint  The skill point to add the translation for.
     * @param translation The translation for the skill point.
     */
    private void addSkillPoint(ISkillPoint skillPoint, String translation) {
        add(skillPoint.getTranslationKey(), translation);
    }

    /**
     * Adds an skill point item translation.
     *
     * @param skillPointItem The skill point item to add the translation for.
     * @param skillPoint     The skill point to generate the translation from.
     */
    private void skillPointItemIdTranslation(RegistryObject<? extends ISkillPointItem> skillPointItem, RegistryObject<? extends ISkillPoint> skillPoint) {
        skillPointItemIdTranslation(skillPointItem.getId(), skillPoint.getId());
    }

    /**
     * Adds an skill point item translation.
     *
     * @param skillPointItemId The skill point item to add the translation for.
     * @param skillPointId     The skill point to generate the translation from.
     */
    private void skillPointItemIdTranslation(ResourceLocation skillPointItemId, ResourceLocation skillPointId) {
        String translation = idToTranslation(skillPointId.getPath()) + " " + idToTranslation(skillPointItemId.getPath());
        skillPointItemTranslation(skillPointItemId, skillPointId, translation);
    }

    /**
     * Adds an skill point item translation.
     *
     * @param skillPointItem The skill point item to add the translation for.
     * @param skillPoint     The skill point to generate the translation from.
     * @param translation    The custom translation to use.
     */
    private void skillPointItemTranslation(RegistryObject<? extends ISkillPointItem> skillPointItem, RegistryObject<? extends ISkillPoint> skillPoint, String translation) {
        skillPointItemTranslation(skillPointItem.getId(), skillPoint.getId(), translation);
    }

    /**
     * Adds an skill point item translation.
     *
     * @param skillPointItemId The skill point item to add the translation for.
     * @param skillPointId     The skill point to generate the translation from.
     * @param translation      The custom translation to use.
     */
    private void skillPointItemTranslation(ResourceLocation skillPointItemId, ResourceLocation skillPointId, String translation) {
        add(Util.makeDescriptionId(Util.makeDescriptionId("item", skillPointItemId), skillPointId), translation);
    }

    /**
     * @param id A string of format "word_word_word".
     * @return A string of format "Word Word Word".
     */
    private static String idToTranslation(String id) {
        StringBuilder result = new StringBuilder();
        for (String string : id.split("_")) {
            result.append(string.substring(0, 1).toUpperCase()).append(string.substring(1)).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }
}
