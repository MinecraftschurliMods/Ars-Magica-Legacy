package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinityItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPointItem;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMFluids;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMTalents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings({"SameParameterValue", "unused"})
class AMEnglishLanguageProvider extends AMLanguageProvider {
    AMEnglishLanguageProvider(DataGenerator generator) {
        super(generator, "en_us");
    }

    @Override
    protected void addTranslations() {
        itemGroupTranslation(AMItems.TAB, ArsMagicaLegacy.getModName());
        itemGroupTranslation(SpellItem.PREFAB_SPELLS_TAB, ArsMagicaLegacy.getModName() + " - Prefab Spells");
        blockIdTranslation(AMBlocks.OCCULUS);
        blockIdTranslation(AMBlocks.INSCRIPTION_TABLE);
        itemIdTranslation(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1);
        itemIdTranslation(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2);
        itemIdTranslation(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3);
        blockIdTranslation(AMBlocks.ALTAR_CORE);
        blockIdTranslation(AMBlocks.MAGIC_WALL);
        blockIdTranslation(AMBlocks.OBELISK);
        blockIdTranslation(AMBlocks.CELESTIAL_PRISM);
        blockIdTranslation(AMBlocks.BLACK_AUREM);
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
        blockIdTranslation(AMBlocks.MOONSTONE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
        itemIdTranslation(AMItems.MOONSTONE);
        addBlock(AMBlocks.MOONSTONE_BLOCK, "Block of Moonstone");
        blockIdTranslation(AMBlocks.SUNSTONE_ORE);
        itemIdTranslation(AMItems.SUNSTONE);
        addBlock(AMBlocks.SUNSTONE_BLOCK, "Block of Sunstone");
        blockIdTranslation(AMBlocks.WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.WITCHWOOD);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD);
        blockIdTranslation(AMBlocks.WITCHWOOD_LEAVES);
        blockIdTranslation(AMBlocks.WITCHWOOD_SAPLING);
        blockIdTranslation(AMBlocks.WITCHWOOD_PLANKS);
        blockIdTranslation(AMBlocks.WITCHWOOD_SLAB);
        blockIdTranslation(AMBlocks.WITCHWOOD_STAIRS);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE_GATE);
        blockIdTranslation(AMBlocks.WITCHWOOD_DOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_TRAPDOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_BUTTON);
        blockIdTranslation(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
        blockIdTranslation(AMBlocks.WITCHWOOD_SIGN);
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
        blockIdTranslation(AMBlocks.IRON_INLAY);
        blockIdTranslation(AMBlocks.REDSTONE_INLAY);
        blockIdTranslation(AMBlocks.GOLD_INLAY);
        fluidIdTranslation(AMFluids.LIQUID_ESSENCE_TYPE, true);
        for (RegistryObject<Affinity> affinity : AMRegistries.AFFINITIES.getEntries()) {
            affinityIdTranslation(affinity);
            affinityItemIdTranslation(AMItems.AFFINITY_ESSENCE, affinity);
            affinityItemIdTranslation(AMItems.AFFINITY_TOME, affinity);
        }
        for (RegistryObject<SkillPoint> skillPoint : AMRegistries.SKILL_POINTS.getEntries()) {
            skillPointIdTranslation(skillPoint);
            skillPointItemIdTranslation(AMItems.INFINITY_ORB, skillPoint);
        }
        itemIdTranslation(AMItems.ETHERIUM_PLACEHOLDER);
        itemIdTranslation(AMItems.SPELL_PARCHMENT);
        itemIdTranslation(AMItems.SPELL_RECIPE);
        itemIdTranslation(AMItems.SPELL);
        itemIdTranslation(AMItems.SPELL_BOOK);
        itemIdTranslation(AMItems.MANA_CAKE);
        itemIdTranslation(AMItems.MANA_MARTINI);
        itemIdTranslation(AMItems.MAGE_HELMET);
        itemIdTranslation(AMItems.MAGE_CHESTPLATE);
        itemIdTranslation(AMItems.MAGE_LEGGINGS);
        itemIdTranslation(AMItems.MAGE_BOOTS);
        itemIdTranslation(AMItems.BATTLEMAGE_HELMET);
        itemIdTranslation(AMItems.BATTLEMAGE_CHESTPLATE);
        itemIdTranslation(AMItems.BATTLEMAGE_LEGGINGS);
        itemIdTranslation(AMItems.BATTLEMAGE_BOOTS);
        itemIdTranslation(AMItems.DRYAD_SPAWN_EGG);
        itemIdTranslation(AMItems.MAGE_SPAWN_EGG);
        itemIdTranslation(AMItems.MANA_CREEPER_SPAWN_EGG);
        itemIdTranslation(AMItems.WATER_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.FIRE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.EARTH_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.AIR_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.ICE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.LIGHTNING_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.NATURE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.LIFE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.ARCANE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.ENDER_GUARDIAN_SPAWN_EGG);
        //        itemIdTranslation(AMItems.NATURE_SCYTHE);
        //        addItem(AMItems.WINTERS_GRASP, "Winter's Grasp");
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
        potionIdTranslation(AMMobEffects.INFUSED_MANA);
        addAttribute(AMAttributes.BURNOUT_REGEN, "Burnout Regeneration");
        addAttribute(AMAttributes.MANA_REGEN, "Mana Regeneration");
        attributeIdTranslation(AMAttributes.MAGIC_VISION);
        attributeIdTranslation(AMAttributes.MAX_BURNOUT);
        attributeIdTranslation(AMAttributes.MAX_MANA);
        entityIdTranslation(AMEntities.PROJECTILE);
        entityIdTranslation(AMEntities.WAVE);
        entityIdTranslation(AMEntities.WALL);
        entityIdTranslation(AMEntities.ZONE);
        entityIdTranslation(AMEntities.BLIZZARD);
        entityIdTranslation(AMEntities.FIRE_RAIN);
        entityIdTranslation(AMEntities.FALLING_STAR);
        entityIdTranslation(AMEntities.WATER_GUARDIAN);
        entityIdTranslation(AMEntities.FIRE_GUARDIAN);
        entityIdTranslation(AMEntities.EARTH_GUARDIAN);
        entityIdTranslation(AMEntities.AIR_GUARDIAN);
        entityIdTranslation(AMEntities.ICE_GUARDIAN);
        entityIdTranslation(AMEntities.LIGHTNING_GUARDIAN);
        entityIdTranslation(AMEntities.NATURE_GUARDIAN);
        entityIdTranslation(AMEntities.LIFE_GUARDIAN);
        entityIdTranslation(AMEntities.ARCANE_GUARDIAN);
        entityIdTranslation(AMEntities.ENDER_GUARDIAN);
        addEntityType(AMEntities.WINTERS_GRASP, "Winter's Grasp");
        entityIdTranslation(AMEntities.NATURE_SCYTHE);
        entityIdTranslation(AMEntities.THROWN_ROCK);
        entityIdTranslation(AMEntities.WHIRLWIND);
        entityIdTranslation(AMEntities.DRYAD);
        entityIdTranslation(AMEntities.MAGE);
        entityIdTranslation(AMEntities.MANA_CREEPER);
        entityIdTranslation(AMEntities.MANA_VORTEX);
        abilityIdTranslation(AMAbilities.FIRE_RESISTANCE, "After using fire spells for some time, you develop some resistance to fire. As you delve deeper, you notice the resistance getting stronger and stronger.$(br2)Affinity: Fire$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.FIRE_PUNCH, "Becoming part fire, enemies you hit now get set on fire.$(br2)Affinity: Fire$(br)Range: 100 %%");
        addAbility(AMAbilities.WATER_DAMAGE_FIRE, "Water Damage (Fire)", "Having fun with fire magic has made you less suitable for water. You will take damage in water, though not enough to kill you on its own.$(br2)Affinity: Fire$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.SWIM_SPEED, "After using water spells for some time, you develop better swimming skills. As you delve deeper, you notice your speed in water getting faster and faster.$(br2)Affinity: Water$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.ENDERMAN_THORNS, "Becoming part water, enderman that hit you now take damage themselves.$(br2)Affinity: Water$(br)Range: 100 %%");
        addAbility(AMAbilities.NETHER_DAMAGE_WATER, "Nether Damage (Water)", "Having fun with water magic has made you less suitable for fire. You will take damage in the nether, though not enough to kill you on its own.$(br2)Affinity: Water$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.RESISTANCE, "After using earth spells for some time, you develop some physical resistance. As you delve deeper, you notice the resistance getting stronger and stronger.$(br2)Affinity: Earth$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.HASTE, "After using earth spells for some time, you develop better block breaking skills. As you delve deeper, you notice that speed getting faster and faster.$(br2)Affinity: Earth$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.FALL_DAMAGE, "Having fun with earth magic has made you heavier. You take more fall damage.$(br2)Affinity: Earth$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.JUMP_BOOST, "After using air spells for some time, you develop better jumping skills. As you delve deeper, you notice your jump strength getting stronger and stronger.$(br2)Affinity: Air$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.FEATHER_FALLING, "After using air spells for some time, you develop better landing skills. As you delve deeper, you notice your fall damage taken getting weaker and weaker.$(br2)Affinity: Air$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.GRAVITY, "Having fun with air magic has made you accidentally bend gravity. You fall a lot faster.$(br2)Affinity: Air$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.FROST_PUNCH, "After using ice spells for some time, you develop a frost punch, slowing your enemies. As you delve deeper, you notice the frost getting stronger and stronger.$(br2)Affinity: Ice$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.FROST_WALKER, "Becoming part ice, water now freezes under your feet.$(br2)Affinity: Ice$(br)Range: 100 %%");
        abilityIdTranslation(AMAbilities.SLOWNESS, "Having fun with ice magic has made you shiver. You move slower.$(br2)Affinity: Ice$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.SPEED, "After using lightning spells for some time, you develop better running skills. As you delve deeper, you notice your speed becoming faster and faster.$(br2)Affinity: Lightning$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.STEP_ASSIST, "Becoming part lightning, you are now able to step up 1-block slopes.$(br2)Affinity: Lightning$(br)Range: 100 %%");
        addAbility(AMAbilities.WATER_DAMAGE_LIGHTNING, "Water Damage (Lightning)", "Having fun with lightning magic has made you less suitable for water. You will take damage in water, though not enough to kill you on its own.$(br2)Affinity: Lightning$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.SATURATION, "After using nature spells for some time, you feel nourished. As you delve deeper, you notice nourishment getting stronger and stronger.$(br2)Affinity: Nature$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.THORNS, "Becoming one with nature, enemies that hit you now take a bit of damage themselves.$(br2)Affinity: Nature$(br)Range: 100 %%");
        addAbility(AMAbilities.NETHER_DAMAGE_NATURE, "Nether Damage (Nature)", "Having fun with nature magic has made you less suitable for fire. You will take damage in the nether, though not enough to kill you on its own.$(br2)Affinity: Nature$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.SMITE, "After using life spells for some time, you feel an urge to slay the undead. As you delve deeper, you notice your damage towards undeads getting stronger and stronger.$(br2)Affinity: Life$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.REGENERATION, "Becoming one with life, you get a permanent regeneration effect.$(br2)Affinity: Life$(br)Range: 100 %%");
        abilityIdTranslation(AMAbilities.NAUSEA, "Having fun with life magic has made you less suitable for killing. You will receive a nausea effect when killing a non-undead enemy.$(br2)Affinity: Life$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.MANA_REDUCTION, "After using arcane spells for some time, your spells' mana requirements goes down. As you delve deeper, you notice the mana cost getting lower and lower.$(br2)Affinity: Arcane$(br)Range: 1 - 100 %%");
        abilityIdTranslation(AMAbilities.CLARITY, "Becoming one with the arcane, you have a chance of receiving the Clarity effect upon casting, which allows you to cast your next spell for free.$(br2)Affinity: Arcane$(br)Range: 100 %%");
        abilityIdTranslation(AMAbilities.MAGIC_DAMAGE, "Having fun with arcane magic has made you vulnerable against the very thing you use. You will receive more damage from magic sources.$(br2)Affinity: Arcane$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.POISON_RESISTANCE, "After using ender spells for quite some time, you develop a resistance against toxins.$(br2)Affinity: Ender$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.NIGHT_VISION, "After using ender spells for quite some time, you gain permanent night vision.$(br2)Affinity: Ender$(br)Range: 50 - 100 %%");
        abilityIdTranslation(AMAbilities.ENDERMAN_PUMPKIN, "Becoming one with the end, enderman will treat you as one of their own and not attack you anymore when staring into their eyes.$(br2)Affinity: Ender$(br)Range: 100 %%");
        abilityIdTranslation(AMAbilities.LIGHT_HEALTH_REDUCTION, "Toying around with ender magic consumes your life in light. When in direct sunlight, your maximum health decreases.$(br2)Affinity: Ender$(br)Range: 50 - 100 %%$(br)There have been rumors among the villagers of true ender mages that managed to nullify this effect...");
        abilityIdTranslation(AMAbilities.WATER_HEALTH_REDUCTION, "Toying around with ender magic consumes your life in water. When in water, your maximum health decreases.$(br2)Affinity: Ender$(br)Range: 50 - 100 %%$(br)There have been rumors among the villagers of true ender mages that managed to nullify this effect...");
        skillTranslation(AMSpellParts.ABSORPTION.getId(), "Absorption", "Like a slightly flimsier shield.", "components", "You gain absorption hearts, like you would when eating a golden apple. This does not stack with golden apples.");
        skillTranslation(AMSpellParts.AGILITY.getId(), "Agility", "Seems like you won't be catching me anytime soon.", "components", "You managed to gain step-up abilities, greater jump height and reduced fall damage.");
        skillTranslation(AMSpellParts.AOE.getId(), "AoE", "All around me!", "shapes", "After charging your spell, you can shape it into a blast that radiates outwards from the spell's origin. An AoE spell will not affect the caster.");
        skillTranslation(AMSpellParts.ASTRAL_DISTORTION.getId(), "Astral Distortion", "Going nowhere.", "components", "This spell entirely prevents teleportation of the target for some time. Also works on endermen and shulkers!");
        skillTranslation(AMSpellParts.ATTRACT.getId(), "Attract", "You go there.", "components", "You create an area of negative pressure around you, pulling everything living in towards you.");
        skillTranslation(AMSpellParts.BANISH_RAIN.getId(), "Banish Rain", "Come back later. Or don't. It would be kind.", "components", "Rain rain, go away. Come again another day!$(br2)Who would have thought that those were the words to the spell?");
        skillTranslation(AMSpellParts.BEAM.getId(), "Beam", "Beam me up, Scotty!", "shapes", "You can fire a concentrated beam of magic at your target. The maximum range of the beam is 64 blocks, which is more than enough for most use cases. Be warned that this requires A LOT of mana.");
        skillTranslation(AMSpellParts.BLINDNESS.getId(), "Blindness", "Just as the name says.", "components", "Having a fireball to throw at dark mages is good. But making it so they also can't see to retaliate is better.");
        skillTranslation(AMSpellParts.BLINK.getId(), "Blink", "Well, I'm out.", "components", "You can teleport a short distance directly forward the way you are facing.$(br2)Blink can take you through solid walls, but will make every effort to ensure you don't get stuck in one.");
        skillTranslation(AMSpellParts.BLIZZARD.getId(), "Blizzard", "Snow. Lots of snow.", "components", "You have learned to summon a fearsome blizzard, which will slow and damage any entities in its radius.$(br2)Blizzard has a built-in $(l:shapes/aoe)AoE$() to it.$(br2)Though blizzard will harm all entities in its radius, it will never harm its caster.");
        skillTranslation(AMSpellParts.BOUNCE.getId(), "Bounce", "We do a little trolling.", "modifiers", "Causes $(l:shapes/projectile)spell projectiles$() to bounce off surfaces.");
        skillTranslation(AMSpellParts.CHAIN.getId(), "Chain", "Looks like you brought friends. Well, I don't mind, you're all gonna die.", "shapes", "You can modify your Beam spell to jump from target to target, hitting up to five enemies, at the cost of range - it is now limited to 16 blocks instead of 64. The jump range between targets is 4 blocks, but can be extended using $(l:modifiers/range)Range$() modifiers.$(br2)The spell will never harm its caster. When the spell is used on a block, the chaining behavior does not occur, and it will act like a regular beam.");
        skillTranslation(AMSpellParts.CHANNEL.getId(), "Channel", "You might want to concentrate.", "shapes", "Through intense concentration, you can maintain a flow of magic on yourself.$(br2)This is useful for things like $(l:components/heal)Heal$(), $(l:components/attract)Attract$() and $(l:components/repel)Repel$().");
        skillTranslation(AMSpellParts.CHARM.getId(), "Charm", "One plus one is three!", "components", "You can cause breedable creatures to breed.");
        skillTranslation(AMSpellParts.CONTINGENCY_DAMAGE.getId(), "Contingency: Damage", "Hurting me? That would be bad.", "shapes", "You have managed to create a spell that triggers only when you get hurt.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpellParts.CONTINGENCY_DEATH.getId(), "Contingency: Death", "You're coming with me.", "shapes", "Enemies may get you, but in those few seconds it takes, you will have all the time you need to ensure they come along for the ride.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpellParts.CONTINGENCY_FALL.getId(), "Contingency: Fall", "The higher you climb, the harder you fall.", "shapes", "You have figured out a spell shape that triggers when falling the instant before you hit the ground.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpellParts.CONTINGENCY_FIRE.getId(), "Contingency: Fire", "You shall (not) burn!", "shapes", "You've decided you really don't like being on fire anymore. To that end, you made a spell that will light up when the flames do.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpellParts.CONTINGENCY_HEALTH.getId(), "Contingency: Health", "I'm not going down. Not right now.", "shapes", "No matter your power, a knife between the shoulder blades will seriously cramp your style. You have found a way to make your enemies regret trying that, though. Or need two knives. This contingency triggers when your health is less than or equal to 25%% of maximum.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpellParts.CREATE_WATER.getId(), "Create Water", "Please help me, I'm under the water.", "components", "You can coalesce moisture from the air around into one location, creating water where there was none. It can also be used to fill a cauldron.$(br2)This will not work in the nether.");
        skillTranslation(AMSpellParts.DAMAGE.getId(), "Damage", "Now it hurts.", "modifiers", "Amplifies the damage dealt by spells, or the healing done by damage spells to the undead.");
        skillTranslation(AMSpellParts.DAYLIGHT.getId(), "Daylight", "Does that mean I can control time?", "components", "You have gained the ability to control time.$(br2)This spell will cause the time to move to dawn.");
        skillTranslation(AMSpellParts.DIG.getId(), "Dig", "Diggy Diggy Hole!", "components", "The ground shatters with a snap of your fingers. Harder blocks take more mana to break.$(br2)Dig starts out equivalent to an Iron pickaxe, but can be upgraded with the use of the $(l:modifiers/mining_power)Mining Power$() modifier.");
        skillTranslation(AMSpellParts.DISARM.getId(), "Disarm", "Woops, you dropped something?", "components", "Now that you have learned to summon tools to your hand, it was a small step to be able to make others drop what they are holding.");
        skillTranslation(AMSpellParts.DISMEMBERING.getId(), "[WIP] Dismembering", "Wasn't me. I swear he had no head when I came in.", "modifiers", "You like souvenirs so much that you have discovered how to make your damaging spells leave some pieces intact.$(br2)Each modifier adds a 5%% chance to drop a head when defeating an enemy.");
        skillTranslation(AMSpellParts.DISPEL.getId(), "Dispel", "Witches have no say here.", "components", "Creating a localized field of deficit, you can remove up to six levels of potion effects on your target.");
        skillTranslation(AMSpellParts.DIVINE_INTERVENTION.getId(), "Divine Intervention", "Dimension-hopping! Yay!", "components", "You have mastered teleportation magic, to the level at which you can transcend interdimensional barriers, and can enter the overworld from anywhere, except the nether.");
        skillTranslation(AMSpellParts.DROUGHT.getId(), "Drought", "Heat. Lots of heat.", "components", "You have taken your knowledge of creating water and have reversed the process.$(br2)This spell will draw water out of whatever it hits, removing water blocks, turning dirt-ish blocks to sand, withering plants it hits, and cracking stone to cobblestone.");
        skillTranslation(AMSpellParts.DROWNING_DAMAGE.getId(), "Drowning Damage", "How can you drown? There isn't any water.", "components", "You can create water directly inside the target's lungs, causing them to take drowning damage.");
        skillTranslation(AMSpellParts.DURATION.getId(), "Duration", "Time manipulation tricks.", "modifiers", "Enhances the duration of all effect spells, and increases the lifetime of $(l:shapes/projectile)projectiles$(), $(l:shapes/wall)walls$(), $(l:shapes/wave)waves$() and $(l:shapes/zone)zones.");
        skillTranslation(AMSpellParts.EFFECT_POWER.getId(), "Effect Power", "Harder, better, faster and my mana pool is empty.", "modifiers", "You can put more power into your effects. Each modifier added increases the level of the effect applied by one.");
        skillTranslation(AMSpellParts.ENDER_INTERVENTION.getId(), "Ender Intervention", "But in the End, it doesn't even matter!", "components", "You have mastered teleportation magic, to the level at which you can transcend interdimensional barriers, and can enter the end from anywhere, except the nether.");
        skillTranslation(AMSpellParts.ENTANGLE.getId(), "Entangle", "Stop right there.", "components", "At your command, vines can burst from the ground and ensnare your target, holding them completely immobile.");
        skillTranslation(AMSpellParts.EXPLOSION.getId(), "Explosion", "Creeper? Aww Man!", "components", "You can cause an explosion, destroying and dropping blocks around its center.");
        skillTranslation(AMSpellParts.FALLING_STAR.getId(), "Falling Star", "Shiny! Wait, is it falling towards me?", "components", "You can call down a star from the skies and cause it to strike all entities within the blast radius. It will harm friendly targets but not the caster, and pierce through walls.$(br2)There is a short delay between casting the spell and the impact. This spell will not work underground (the star will fall onto the surface).");
        skillTranslation(AMSpellParts.FIRE_DAMAGE.getId(), "Fire Damage", "You shall burn!", "components", "With a word, you can release your will, and fire will erupt from out in front of you, searing everything in its path. Fire damage is hard hitting, but many nether mobs are immune to its effects.");
        skillTranslation(AMSpellParts.FIRE_RAIN.getId(), "Fire Rain", "Through the fire and the flames!", "components", "You have learned to summon a terrible firestorm, which will do large amounts of damage to all entities in its radius. Firestorm has a built-in $(l:shapes/aoe)AoE$() to it.$(br2)Firestorm does not ignite the ground and will never harm its caster.");
        skillTranslation(AMSpellParts.FLIGHT.getId(), "Flight", "Does this count as cheating?", "components", "With a word, you can rise into the air.");
        skillTranslation(AMSpellParts.FLING.getId(), "Fling", "Ready for an air fight?", "components", "This spell makes wind whirl around under your target, and suddenly all at once blow them straight up, sending them skyward.");
        skillTranslation(AMSpellParts.FORGE.getId(), "Forge", "Portable furnace.", "components", "You have gained fine control over fire and can use it to magically smelt blocks where they stand, without charring them to ash.");
        skillTranslation(AMSpellParts.FROST.getId(), "Frost", "Freeze!", "components", "You breath deeply and open your eyes. Water will become ice. Enemies move at a crawl. Perfect.");
        skillTranslation(AMSpellParts.FROST_DAMAGE.getId(), "Frost Damage", "Let it snow!", "components", "Many underestimate the power that frost can wield. The creeping chill can bypass many armors.");
        skillTranslation(AMSpellParts.FURY.getId(), "Fury", "Berserker rage!", "components", "You can send yourself into an absolute rage, dealing increased damage, moving extremely fast, passively regenerating, and mining at inhuman speeds.$(br2)When the effect ends, you are left exhausted for a few moments and must recover.");
        skillTranslation(AMSpellParts.GRAVITY.getId(), "Gravity", "Created by Isaac Newton.", "modifiers", "$(l:shapes/zone)Zones$() and $(l:shapes/projectile)projectiles$() will be affected by gravity.");
        skillTranslation(AMSpellParts.GRAVITY_WELL.getId(), "Gravity Well", "Not like a chicken. The opposite.", "components", "You have learned to create a localized gravity well under your target, greatly increasing the speed at which they fall.");
        skillTranslation(AMSpellParts.GROW.getId(), "Grow", "I won't sit all day.", "components", "Pouring energy into plants, equal to months of talking to them, will cause them to grow more rapidly.");
        skillTranslation(AMSpellParts.HARVEST.getId(), "Harvest", "Add a hammer and start a revolution.", "components", "You can cause all plants, leaves, and flowers to be harvested.");
        skillTranslation(AMSpellParts.HASTE.getId(), "Haste", "Mining away!", "components", "Wrapping the target's hands in arcane energy, you can greatly increase mining speed.");
        skillTranslation(AMSpellParts.HEAL.getId(), "Heal", "Instant healing.", "components", "By greatly increasing the amount of power put into regenerative effects, you can knit almost any injury back together. The effect is taxing, however.");
        skillTranslation(AMSpellParts.HEALING.getId(), "Healing", "Efficiency over number.", "modifiers", "Amplifies the healing done by spells, or the damage dealt by healing spells to the undead.");
        skillTranslation(AMSpellParts.HEALTH_BOOST.getId(), "Health Boost", "1 UP!", "components", "Your target receives a temporary boost in health, allowing them to live longer. The extra health is not automatically healed, it must be regenerated using conventional methods first.");
        skillTranslation(AMSpellParts.IGNITION.getId(), "Ignition", "Burn harder!", "components", "You see fire as a damaging, destructive force, and that can be true.$(br2)But how often do you hear fire and imagine lighting a campfire or candle?");
        skillTranslation(AMSpellParts.INVISIBILITY.getId(), "Invisibility", "Wanna play Hide & Seek?", "components", "You can bend light around yourself to become effectively invisible.");
        skillTranslation(AMSpellParts.JUMP_BOOST.getId(), "Jump Boost", "Not a frog? Who cares?", "components", "Gathering wind around you, you can propel yourself into the air.");
        skillTranslation(AMSpellParts.KNOCKBACK.getId(), "Knockback", "Punch from a distance!", "components", "As a mage, you most likely don't want to be in melee range. This component allows that situation to be corrected.");
        skillTranslation(AMSpellParts.LEVITATION.getId(), "Levitation", "Use the force.", "components", "Through practicing air magic, you can now hold yourself suspended in midair.$(br2)With small wind currents, you can move slowly about while floating.");
        skillTranslation(AMSpellParts.LIFE_DRAIN.getId(), "Life Drain", "I'm taking all of it. Including you.", "components", "By creating a sinister link with the target's life force, you can siphon it off into your own, bolstering your own health.");
        skillTranslation(AMSpellParts.LIFE_TAP.getId(), "Life Tap", "I'm borrowing this.", "components", "If you are desparate and mana is scarce, you can fuel your spells using your own life force.");
        skillTranslation(AMSpellParts.LIGHT.getId(), "Light", "The end of a tunnel.", "components", "You can light up an area with magic.$(br2)If you apply this component onto a living being, it will light up the darkness by itself.");
        skillTranslation(AMSpellParts.LIGHTNING_DAMAGE.getId(), "Lightning Damage", "Zap!", "components", "Lightning does an exceptional amount of damage, but carries a hefty mana cost.");
        skillTranslation(AMSpellParts.LUNAR.getId(), "Lunar", "I'm gonna be a werewolf!", "modifiers", "Powers up your spell during the night. The closer to midnight it is, the more $(l:modifiers/damage)damage$() and $(l:modifiers/healing)healing$() the spell does. $(l:modifiers/duration)Duration$() and $(l:modifiers/range)range$() are increased where applicable based on the phase of the moon (more power approaching full moon).$(br2)Lunar is more powerful than $(l:modifiers/solar)Solar$() due to nights not lasting as long as day.");
        skillTranslation(AMSpellParts.MAGIC_DAMAGE.getId(), "Magic Damage", "Hit from the void!", "components", "Magical damage differs from physical damage in that it bypasses many kinds of armors and attacks the target's aura directly - which can be just as devastating, if not more.");
        skillTranslation(AMSpellParts.MANA_BLAST.getId(), "Mana Blast", "I love mana, especially when it blows up in someone's face.", "components", "Your entire mana is used up to damage the target. The more mana you had, the more damage it does!");
        skillTranslation(AMSpellParts.MANA_DRAIN.getId(), "Mana Drain", "So much pools at my disposal!", "components", "You can create a parasitic bond with the target's aura, draining their mana and boosting your own.");
        skillTranslation(AMSpellParts.MINING_POWER.getId(), "Mining Power", "Who needs diamonds?", "modifiers", "You have learned to put more power into your digging spells. This causes them to be able to dig more dense blocks that would require a better tool.$(br2)Each modifier bumps the spell up by one tool level.$(br2)The base $(l:components/dig)dig$() component operates at iron mining level.");
        skillTranslation(AMSpellParts.MOONRISE.getId(), "Moonrise", "Full moon.", "components", "You have gained the ability to control time.$(br2)This spell will cause the time to move to dusk.");
        skillTranslation(AMSpellParts.NIGHT_VISION.getId(), "Night Vision", "Oh? There was a tunnel?", "components", "Your knowledge of light has allowed you to devise a spell that will let you amplify light levels, effectively letting you see in the dark.");
        skillTranslation(AMSpellParts.PHYSICAL_DAMAGE.getId(), "Physical Damage", "Magical swords. Why not?", "components", "Often, you will begin your training with simple physical force. Force is a physical damage type, and does not pierce armor.");
        skillTranslation(AMSpellParts.PIERCING.getId(), "Piercing", "Armor, here I come!", "modifiers", "Allows $(l:shapes/projectile)projectiles$() to pierce through entities and blocks.");
        skillTranslation(AMSpellParts.PLACE_BLOCK.getId(), "Place Block", "Don't mind me, I'm just sending an anvil.", "components", "You can use this spell part to place blocks! In order to place a block, you need to set the spell to place it (shift-use on the block), and you need to have at least one of said block in your inventory.");
        skillTranslation(AMSpellParts.PLANT.getId(), "Plant", "Why bother using hand when magic can do the same?", "components", "You can cause seeds from your inventory to be planted in the ground, if conditions are right for it to grow.$(br2)Best coupled with $(l:shapes/aoe)AoE$() for rapid planting.");
        skillTranslation(AMSpellParts.PLOW.getId(), "Plow", "Hoes are useless. Everyone knows that.", "components", "You can cause the earth to churn at your command, creating deep furrows ideal for planting.");
        skillTranslation(AMSpellParts.PROJECTILE.getId(), "Projectile", "Snowball!", "shapes", "You are able to focus your will into a concentrated ball, which is then propelled forwards away from you.$(br2)The projectile will last for five seconds of flight, or until it strikes something.$(br2)It will by default pass through water and non-collidable blocks unless you modify it with $(l:modifiers/target_non_solid)Target Non Solid$().");
        skillTranslation(AMSpellParts.PROSPERITY.getId(), "Prosperity", "Bling!", "modifiers", "Fortune strikes! You can make your digging spells more likely to drop additional ores, and your damaging spells more likely to cause enemies to drop better loot.$(br2)Each modifier added is equivalent to one level of fortune/looting on the spell.");
        skillTranslation(AMSpellParts.RANDOM_TELEPORT.getId(), "Random Teleport", "I wanna go there! No, the other there!", "components", "You can randomly teleport your target a short distance away.");
        skillTranslation(AMSpellParts.RANGE.getId(), "Range", "Think you're far enough? No, you're not.", "modifiers", "Increases the range/size of many spells.");
        skillTranslation(AMSpellParts.RECALL.getId(), "Recall", "I don't recall leaving my house.", "components", "You can tune your teleportation magic to home in on a mark you have left by shift-using the spell, transporting the target back to that location.");
        skillTranslation(AMSpellParts.REFLECT.getId(), "Reflect", "Bounces back to you.", "components", "You create a magic shield that $(l:shapes/projectile)spell projectiles$(), $(l:shapes/wall)walls$(), $(l:shapes/wave)waves$() and $(l:shapes/zone)zones$() will bounce off.");
        skillTranslation(AMSpellParts.REGENERATION.getId(), "Regeneration", "A little bit of health.", "components", "I wrapped my arm in a healing light, and watched as every injury, down to the last bruise, slowly vanished before my eyes.");
        skillTranslation(AMSpellParts.REPEL.getId(), "Repel", "Go away from me!", "components", "You can create a singularity in space, which, as long as you maintain it, will radiate waves of force, pushing anything moving away from the target position.");
        skillTranslation(AMSpellParts.RIFT.getId(), "Rift", "One day I'll walk through it, for now, it'll just store items.", "components", "You can tear open a rift in space, granting access to a small inventory to store items in. More $(l:modifiers/effect_power)effect power$() modifiers give greater storage access.$(br2)You can, if your friends are foolish enough, also open their personal rift instead.");
        skillTranslation(AMSpellParts.RUNE.getId(), "Rune", "Placeable magic.", "shapes", "You can create a magically infused rune on the ground that, when someone steps on them, can apply powerful buffs - or trigger deadly traps.");
        skillTranslation(AMSpellParts.RUNE_PROCS.getId(), "Rune Procs", "I want more!", "modifiers", "Increases the number of times a $(l:shapes/rune)rune$() can apply its effect before being destroyed.");
        skillTranslation(AMSpellParts.SELF.getId(), "Self", "It's all about me.", "shapes", "One of the simplest forms of magic application is applying the magic to yourself. The distance is low, and the target is willing. You only hope you don't accidentally light yourself on fire.");
        skillTranslation(AMSpellParts.SHIELD.getId(), "Shield", "Don't worry about the weight, it's magic.", "components", "You can summon arcane energy to shield yourself, acting effectively as bonus armor.");
        skillTranslation(AMSpellParts.SHRINK.getId(), "Shrink", "Looks like I'm smaller now!", "components", "You can make yourself tiny! When this effect is active, you are physically smaller, so you can fit through 1x1 gaps.$(br2)Due to your light weight, you fall slowly enough that landing doesn't hurt either. However, all damage you do is halved.");
        skillTranslation(AMSpellParts.SILENCE.getId(), "Silence", "No talking! (Or casting in this case!)", "components", "You can silence another entity, preventing all spell casting for a duration.");
        skillTranslation(AMSpellParts.SILK_TOUCH.getId(), "Silk Touch", "Feels soft.", "modifiers", "With great power comes broken valuables.$(br2)You've learned to be more careful when casting your digging spells and break things less often.$(br2)Each modifier is equivalent to one level of Silk Touch on the spell.");
        skillTranslation(AMSpellParts.SLOWNESS.getId(), "Slowness", "No more running!", "components", "By applying the equivalent of a magical ball and chain, you can greatly slow the movements of your target.");
        skillTranslation(AMSpellParts.SLOW_FALLING.getId(), "Slow Falling", "Like a chicken!", "components", "Become light as a feather, and fall without fear.");
        skillTranslation(AMSpellParts.SOLAR.getId(), "Solar", "Sun power!", "modifiers", "Powers up your spell during the day. The closer to noon it is, the more $(l:modifiers/damage)damage$() and $(l:modifiers/healing)healing$() the spell does. $(l:modifiers/duration)Duration$() and $(l:modifiers/range)range$() are increased where applicable based on the phase of the moon (more power approaching new moon).");
        skillTranslation(AMSpellParts.STORM.getId(), "Storm", "It's raining men! Hallelujah!", "components", "The cloud darken, and rain begins to fall. The wind howls, and a flash of lightning strikes across the sky, leaving bright flashes in your vision.$(br2)This component changes the weather to a thunderstorm.");
        skillTranslation(AMSpellParts.SUMMON.getId(), "[WIP] Summon", "Rise, creation!", "components", "You have learned to harvest the souls of creatures you defeat in combat. These souls can be used in creating a spell to summon that creature to protect you.$(br2)To summon a creature, first craft a $(l:items/crystal_phylactery)Crystal Phylactery$(). During spell creation, you can throw in any filled phylactery when prompted by the lectern. This step is what determines what your spell will summon.");
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
        skillTranslation(AMSpellParts.WAVE.getId(), "Wave", "You might not want to surf on this one.", "shapes", "You can project a wave of magic in front of you that rolls forward, applying its effect to everything in its path.");
        skillTranslation(AMSpellParts.WIZARDS_AUTUMN.getId(), "Wizard's Autumn", "Leaves must leave.", "components", "You have learned to focus your digging magic into a small radius that directly affects leaves.$(br2)This component has a built-in $(l:shapes/aoe)AoE$() that can be modified with $(l:modifiers/range)Range$() modifiers.");
        skillTranslation(AMSpellParts.ZONE.getId(), "Zone", "No one can beat me in my sanctuary!", "shapes", "You have learned to focus your will into an area effect that will persist for a time.");
        skillTranslation(AMTalents.AFFINITY_GAINS_BOOST, "Affinity Gains Boost", "Let's skip to the part where I have superpowers.", "talents", "You gain a 5%% boost in affinity gains.");
        skillTranslation(AMTalents.AUGMENTED_CASTING, "Augmented Casting", "Upgrades, people, upgrades.", "talents", "All your spells gain a little boost. A little more damage, a little more duration, a little more speed, a little bit of everything.");
        /*skillTranslation(AMTalents.EXTRA_SUMMONS, "Extra Summons", "Why should I do the fighting?", "talents", "When $(l:components/summon)summoning$() creatures, you can have just a little bit more of them.");
        skillTranslation(AMTalents.MAGE_BAND_1, "Mage Band I", "Starting a cult.", "talents", "You have built enough trust with light mages for them to follow you if requested.");
        skillTranslation(AMTalents.MAGE_BAND_2, "Mage Band II", "Group effort!", "talents", "Building even more trust, light mages will now automatically link their mana pools with yours if you are close.");*/
        skillTranslation(AMTalents.MANA_REGEN_BOOST_1, "Mana Regeneration I", "And I would gain 500 mana...", "talents", "Your mana regeneration is boosted by 5%%.");
        skillTranslation(AMTalents.MANA_REGEN_BOOST_2, "Mana Regeneration II", "...and I would gain 500 more...", "talents", "Your mana regeneration is boosted by 10%%. This replaces the boost of $(l:talents/mana_regen_1)Mana Regen I$().");
        skillTranslation(AMTalents.MANA_REGEN_BOOST_3, "Mana Regeneration III", "...just to get back every single mana point I have consumed before.", "talents", "Your mana regeneration is boosted by 15%%. This replaces the boosts of $(l:talents/mana_regen_1)Mana Regen I$() and $(l:talents/mana_regen_2)Mana Regen II$().");
        skillTranslation(AMTalents.SHIELD_OVERLOAD, "Shield Overload", "No more wasting excess mana.", "talents", "When your mana bar is full, excess mana regenerated turns into a shield that protects you from 5%% of all incoming damage.");
        skillTranslation(AMTalents.SPELL_MOTION, "Spell Motion", "I like to move it, move it.", "talents", "Manipulating the winds around you, you have found a way to move at normal speed while using spells.");
        configTranslation("require_compendium_crafting", "Whether the player needs to craft the compendium before being able to use magic. If disabled, the player can use magic from the beginning.");
        configTranslation("burnout_ratio", "The default mana to burnout ratio, used in calculating spell costs.");
        configTranslation("crafting_altar_check_time", "The time in ticks between multiblock validation checks for the crafting altar.");
        configTranslation("max_etherium_storage", "The maximum amount of etherium that can be stored in an obelisk / celestial prism / black aurem.");
        configTranslation("affinity_tome_shift", "The affinity shift that should be applied by affinity tomes.");
        configTranslation("enable_inscription_table_in_world_upgrading", "Whether inscription table upgrading is allowed in-world. If disabled, the upgrades must be applied through crafting.");
        configTranslation("mana.base", "The base value for mana calculation. Mana is calculated as base + multiplier * (level - 1).");
        configTranslation("mana.multiplier", "The multiplier for mana calculation. Mana is calculated as base + multiplier * (level - 1).");
        configTranslation("mana.regen_multiplier", "The multiplier for mana regeneration. Mana regen is calculated as (base + multiplier * (level - 1)) * regen_multiplier.");
        configTranslation("burnout.base", "The base value for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).");
        configTranslation("burnout.multiplier", "The multiplier for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).");
        configTranslation("burnout.regen_multiplier", "The multiplier for burnout regeneration. Burnout regen is calculated as (base + multiplier * (level - 1)) * regen_multiplier.");
        configTranslation("leveling.base", "The base value for leveling calculation. XP cost is calculated as multiplier * base ^ level.");
        configTranslation("leveling.multiplier", "The multiplier for leveling calculation. XP cost is calculated as multiplier * base ^ level.");
        configTranslation("leveling.extra_blue_skill_points", "The extra blue skill points a player gets on level 1.");
        configTranslation("spell_parts.damage", "Damage of damage-based components, in half hearts.");
        configTranslation("spell_parts.duration", "Duration of effect-based components, in ticks.");
        configTranslation("entities.dryad.bonemeal_timer", "Every X ticks, dryads have a chance to apply bonemeal to the ground around them.");
        configTranslation("entities.dryad.bonemeal_chance", "The chance of bonemeal being applied.");
        configTranslation("entities.dryad.bonemeal_radius", "The radius of bonemeal application.");
        configTranslation("entities.dryad.kill_cooldown", "If enough dryads are killed during this amount of time (in seconds), the Nature Guardian will spawn. Set this to 0 to disable this way of summoning the Nature Guardian (if you have an alternate way to spawn it).");
        configTranslation("entities.dryad.kills_to_nature_guardian_spawn", "If this amount of dryads is killed within the required timeframe, the Nature Guardian will spawn.");
        occulusTabTranslation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "offense"), "Offense");
        occulusTabTranslation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "defense"), "Defense");
        occulusTabTranslation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "utility"), "Utility");
        occulusTabTranslation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity"), "Affinity");
        occulusTabTranslation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "talent"), "Talents");
        damageSourceTranslation("falling_star", "%1$s was obliterated by a falling star");
        damageSourceTranslation("nature_scythe", "%1$s was ripped apart by %2$s's scythe");
        damageSourceTranslation("shockwave", "%1$s was obliterated by a shockwave");
        damageSourceTranslation("thrown_rock", "%1$s was crushed under a rock by %2$s");
        damageSourceTranslation("wind", "%1$s was torn apart by the wind");
        subtitleTranslation(AMSounds.ARCANE_GUARDIAN_AMBIENT, "Arcane Guardian hisses");
        subtitleTranslation(AMSounds.ARCANE_GUARDIAN_ATTACK, "Arcane Guardian attacks");
        subtitleTranslation(AMSounds.ARCANE_GUARDIAN_DEATH, "Arcane Guardian dies");
        subtitleTranslation(AMSounds.ARCANE_GUARDIAN_HURT, "Arcane Guardian hurts");
        subtitleTranslation(AMSounds.EARTH_GUARDIAN_AMBIENT, "Earth Guardian rumbles");
        subtitleTranslation(AMSounds.EARTH_GUARDIAN_ATTACK, "Earth Guardian attacks");
        subtitleTranslation(AMSounds.EARTH_GUARDIAN_DEATH, "Earth Guardian dies");
        subtitleTranslation(AMSounds.EARTH_GUARDIAN_HURT, "Earth Guardian hurts");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_AMBIENT, "Ender Guardian hisses");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_ATTACK, "Ender Guardian attacks");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_DEATH, "Ender Guardian dies");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_HURT, "Ender Guardian hurts");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_AMBIENT, "Fire Guardian cackles");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_ATTACK, "Fire Guardian attacks");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_DEATH, "Fire Guardian dies");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_HURT, "Fire Guardian hurts");
        subtitleTranslation(AMSounds.ICE_GUARDIAN_AMBIENT, "Ice Guardian cracks");
        subtitleTranslation(AMSounds.ICE_GUARDIAN_DEATH, "Ice Guardian dies");
        subtitleTranslation(AMSounds.LIFE_GUARDIAN_AMBIENT, "Life Guardian hums");
        subtitleTranslation(AMSounds.LIFE_GUARDIAN_ATTACK, "Life Guardian attacks");
        subtitleTranslation(AMSounds.LIFE_GUARDIAN_DEATH, "Life Guardian dies");
        subtitleTranslation(AMSounds.LIFE_GUARDIAN_HURT, "Life Guardian hurts");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_AMBIENT, "Lightning Guardian zaps");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_ATTACK, "Lightning Guardian attacks");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_DEATH, "Lightning Guardian dies");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_HURT, "Lightning Guardian hurts");
        subtitleTranslation(AMSounds.NATURE_GUARDIAN_AMBIENT, "Nature Guardian hisses");
        subtitleTranslation(AMSounds.NATURE_GUARDIAN_ATTACK, "Nature Guardian attacks");
        subtitleTranslation(AMSounds.NATURE_GUARDIAN_DEATH, "Nature Guardian dies");
        subtitleTranslation(AMSounds.NATURE_GUARDIAN_HURT, "Nature Guardian hurts");
        subtitleTranslation(AMSounds.WATER_GUARDIAN_AMBIENT, "Water Guardian bubbles");
        subtitleTranslation(AMSounds.WATER_GUARDIAN_DEATH, "Water Guardian dies");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_FLAP, "Ender Guardian flaps");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_ROAR, "Ender Guardian roars");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_FLAMETHROWER, "Fire Guardian burns");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_NOVA, "Fire Guardian shoots");
        subtitleTranslation(AMSounds.ICE_GUARDIAN_LAUNCH_ARM, "Ice Guardian launches arm");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD, "Lightning Guardian summons lightning");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_STATIC, "Lightning Guardian thunders");
        subtitleTranslation(AMSounds.CAST_AIR, "Air spell is cast");
        subtitleTranslation(AMSounds.CAST_ARCANE, "Arcane spell is cast");
        subtitleTranslation(AMSounds.CAST_EARTH, "Earth spell is cast");
        subtitleTranslation(AMSounds.CAST_ENDER, "Ender spell is cast");
        subtitleTranslation(AMSounds.CAST_FIRE, "Fire spell is cast");
        subtitleTranslation(AMSounds.CAST_ICE, "Ice spell is cast");
        subtitleTranslation(AMSounds.CAST_LIFE, "Life spell is cast");
        subtitleTranslation(AMSounds.CAST_LIGHTNING, "Lightning spell is cast");
        subtitleTranslation(AMSounds.CAST_NATURE, "Nature spell is cast");
        subtitleTranslation(AMSounds.CAST_NONE, "Spell is cast");
        subtitleTranslation(AMSounds.CAST_WATER, "Water spell is cast");
        subtitleTranslation(AMSounds.LOOP_AIR, "Air spell is looped");
        subtitleTranslation(AMSounds.LOOP_ARCANE, "Arcane spell is looped");
        subtitleTranslation(AMSounds.LOOP_EARTH, "Earth spell is looped");
        subtitleTranslation(AMSounds.LOOP_ENDER, "Ender spell is looped");
        subtitleTranslation(AMSounds.LOOP_FIRE, "Fire spell is looped");
        subtitleTranslation(AMSounds.LOOP_ICE, "Ice spell is looped");
        subtitleTranslation(AMSounds.LOOP_LIFE, "Life spell is looped");
        subtitleTranslation(AMSounds.LOOP_LIGHTNING, "Lightning spell is looped");
        subtitleTranslation(AMSounds.LOOP_NATURE, "Nature spell is looped");
        subtitleTranslation(AMSounds.LOOP_WATER, "Water spell is looped");
        subtitleTranslation(AMSounds.CONTINGENCY, "Contingency sparkles");
        subtitleTranslation(AMSounds.MANA_SHIELD, "Mana shield is raised");
        subtitleTranslation(AMSounds.RUNE, "Rune activates");
        subtitleTranslation(AMSounds.STARSTRIKE, "Star falls down");
        subtitleTranslation(AMSounds.CRAFTING_ALTAR_ADD_INGREDIENT, "Crafting altar blings");
        subtitleTranslation(AMSounds.CRAFTING_ALTAR_FINISH, "Crafting altar jingles");
        subtitleTranslation(AMSounds.INSCRIPTION_TABLE_TAKE_BOOK, "Book is taken");
        subtitleTranslation(AMSounds.GET_KNOWLEDGE_POINT, "Magic jingle");
        subtitleTranslation(AMSounds.MAGIC_LEVEL_UP, "Magic jingle");
        statTranslation(AMStats.INTERACT_WITH_INSCRIPTION_TABLE.get(), "Interactions with Inscription Table");
        statTranslation(AMStats.INTERACT_WITH_OBELISK.get(), "Interactions with Obelisk");
        statTranslation(AMStats.INTERACT_WITH_OCCULUS.get(), "Interactions with Occulus");
        statTranslation(AMStats.RITUALS_TRIGGERED.get(), "Rituals triggered");
        statTranslation(AMStats.SPELL_CAST.get(), "Spells cast");
        arcaneCompendiumTranslation("affinities.fire.page0.text", "The fire affinity is associated with lava, explosions and the Nether. Fire components are usually offensive ones, like $(l:components/fire_damage)Fire Damage$(), $(l:components/ignition)Ignition$() or $(l:components/explosion)Explosion$().");
        arcaneCompendiumTranslation("affinities.water.page0.text", "The water affinity is associated with swimming, drowning and potions. Its components therefore often use effects, such as $(l:components/water_breathing)Water Breathing$(), $(l:components/swift_swim)Swift Swim$() or $(l:components/watery_grave)Watery Grave$().");
        arcaneCompendiumTranslation("affinities.earth.page0.text", "The earth affinity is associated with mining, protection and physical attacks. Earth components usually have some kind of physical interaction, like $(l:components/physical_damage)Physical Damage$(), $(l:components/dig)Dig$() or $(l:components/shield)Shield$().");
        arcaneCompendiumTranslation("affinities.air.page0.text", "The air affinity is associated with jumping, flying and falling. Many of them use effects, such as $(l:components/jump_boost)Jump Boost$(), $(l:components/levitation)Levitation$() or $(l:components/slow_falling)Slow Falling$().");
        arcaneCompendiumTranslation("affinities.ice.page0.text", "The ice affinity is associated with snow, frost and slowness. Popular examples include $(l:components/frost_damage)Frost Damage$(), $(l:components/frost)Frost$() and $(l:components/slowness)Slowness$().");
        arcaneCompendiumTranslation("affinities.lightning.page0.text", "The lightning affinity is associated with speed, power and weather. Notable examples are $(l:components/lightning_damage)Lightning Damage$(), $(l:components/haste)Haste$() and $(l:components/storm)Storm$().");
        arcaneCompendiumTranslation("affinities.nature.page0.text", "The nature affinity is associated with attraction, growth and harvest. As such, the most common components are $(l:components/attract)Attract$(), $(l:components/grow)Grow$() and $(l:components/harvest)Harvest$().");
        arcaneCompendiumTranslation("affinities.life.page0.text", "The life affinity is associated with healing, resurrection and anti-undead measures. They are usually defensive, like $(l:components/heal)Heal$(), $(l:components/regeneration)Regeneration$() and $(l:components/summon)Summon$().");
        arcaneCompendiumTranslation("affinities.arcane.page0.text", "The arcane affinity is associated with mana, enchantment and trickery. Arcane components are indirectly offensive for the most part, seen for example with $(l:components/invisibility)Invisibility$(), $(l:components/disarm)Disarm$() and $(l:components/mana_drain)Mana Drain$().");
        arcaneCompendiumTranslation("affinities.ender.page0.text", "The ender affinity is associated with teleportation, darkness and the night. Ender components are the most powerful, but also the most expensive, with examples such as $(l:components/blindness)Blindness$(), $(l:components/astral_distortion)Astral Distortion$() and $(l:components/transplace)Transplace$().");
        arcaneCompendiumTranslation("components.summon.page1.text", "Summoned creatures drop no loot, and no xp, but can be interacted with normally, such as breeding, milking cows, or riding horses. Horses are the exception to the item drop rule and will drop saddles and armor given to them.$(br2)Tameable creatures such as wolves and cats are automatically tamed to their owner upon summoning.");
        arcaneCompendiumTranslation("shapes.chain.page1.text", "When jumping, the spell will try to prefer monsters of the same type. So for example, if you have a group of 4 zombies and 2 skeletons, and you target a zombie, you will always hit the 4 zombies and one of the skeletons.");
        add(TranslationConstants.ALTAR_CORE_LOW_POWER, "Altar has not enough power!");
        add(TranslationConstants.CRYSTAL_WRENCH_TOO_FAR, "You cannot perform this action over such distance!");
        add(TranslationConstants.SPELL_BURNOUT, "Burnout: %d");
        add(TranslationConstants.SPELL_INVALID, "[Invalid Spell]");
        add(TranslationConstants.SPELL_INVALID_DESCRIPTION, "Something is wrong with this spell, please check the log for warnings or errors!");
        add(TranslationConstants.SPELL_MANA_COST, "Mana cost: %d");
        add(TranslationConstants.SPELL_REAGENTS, "Reagents:");
        add(TranslationConstants.SPELL_RECIPE_AFFINITIES, "Affinity Breakdown");
        add(TranslationConstants.SPELL_RECIPE_INGREDIENTS, "Ingredients");
        add(TranslationConstants.SPELL_RECIPE_INVALID, "[Invalid Spell Recipe]");
        add(TranslationConstants.SPELL_RECIPE_INVALID_DESCRIPTION, "Something is wrong with this spell recipe, please check the log for warnings or errors!");
        add(TranslationConstants.SPELL_RECIPE_REAGENTS, "Reagents");
        add(TranslationConstants.SPELL_RECIPE_SHAPE_GROUP, "Shape Group %1$s");
        add(TranslationConstants.SPELL_RECIPE_SPELL_GRAMMAR, "Spell Grammar");
        add(TranslationConstants.SPELL_RECIPE_TITLE, "Spell Recipe");
        add(TranslationConstants.SPELL_RECIPE_UNKNOWN, "Unknown Item");
        add(TranslationConstants.SPELL_UNKNOWN, "Unknown Item");
        add(TranslationConstants.SPELL_UNNAMED, "Unnamed Spell");
        add(TranslationConstants.INSCRIPTION_TABLE_DEFAULT_NAME, "Spell");
        add(TranslationConstants.INSCRIPTION_TABLE_NAME, "Name");
        add(TranslationConstants.INSCRIPTION_TABLE_SEARCH, "Search");
        add(TranslationConstants.INSCRIPTION_TABLE_TITLE, "Inscription Table");
        add(TranslationConstants.INSCRIPTION_TABLE_CREATE_SPELL, "Create Spell");
        add(TranslationConstants.OBELISK_TITLE, "Obelisk");
        add(TranslationConstants.OCCULUS_MISSING_REQUIREMENTS, "You lack the skill points or parent skills to learn this skill!");
        add(TranslationConstants.RANGE_LOWER, "Min: %s");
        add(TranslationConstants.RANGE_UPPER, "Max: %s");
        add(TranslationConstants.RIFT_TITLE, "Rift Storage");
        add(TranslationConstants.SPELL_CUSTOMIZATION_TITLE, "Spell Customization");
        add(TranslationConstants.SPELL_PART_MODIFIES, "Modifies");
        add(TranslationConstants.SPELL_PART_MODIFIED_BY, "Modified By");
        add(TranslationConstants.SKILL_CATEGORY, "Spell Parts");
        add(TranslationConstants.SKILL_INGREDIENTS, "Ingredients:");
        add(TranslationConstants.SKILL_AFFINITY_BREAKDOWN, "Affinity Breakdown:");
        add(TranslationConstants.SKILL_MODIFIED_BY, "Modified By:");
        add(TranslationConstants.ALTAR_POWER, "Power: %s");
        add(TranslationConstants.ETHERIUM_AMOUNT, "Etherium: %d / %d");
        add(TranslationConstants.HOLD_SHIFT_FOR_DETAILS, "Hold Shift for details");
        add(TranslationConstants.NO_TELEPORT, "You are too distorted to teleport!");
        add(TranslationConstants.NO_TELEPORT_NETHER, "The nether's force forbids you to simply teleport out of it!");
        add(TranslationConstants.PREVENT_BLOCK, "Mythical forces prevent you from using this block!");
        add(TranslationConstants.PREVENT_ITEM, "Mythical forces prevent you from using this item!");
        add(TranslationConstants.SPELL_CAST + "burned_out", "Burned out!");
        add(TranslationConstants.SPELL_CAST + "cancelled", "Spell cast failed!");
        add(TranslationConstants.SPELL_CAST + "effect_failed", "Spell cast failed!");
        add(TranslationConstants.SPELL_CAST + "missing_reagents", "Missing reagents!");
        add(TranslationConstants.SPELL_CAST + "no_permission", "No permission!");
        add(TranslationConstants.SPELL_CAST + "not_enough_mana", "Not enough mana!");
        add(TranslationConstants.SPELL_CAST + "silenced", "Silence!");
        add(TranslationConstants.TIER, "Tier: %s");
        add(CommandTranslations.AFFINITY_ADD_MULTIPLE, "Added %s affinity depth for %s players to %f");
        add(CommandTranslations.AFFINITY_ADD_SINGLE, "Added %s affinity depth for player %s to %f");
        add(CommandTranslations.AFFINITY_GET, "Affinity depth of %s for player %s is %f");
        add(CommandTranslations.AFFINITY_RESET_MULTIPLE, "Reset all affinity depths for %s players");
        add(CommandTranslations.AFFINITY_RESET_SINGLE, "Reset all affinity depths for player %s");
        add(CommandTranslations.AFFINITY_SET_MULTIPLE, "Set affinity depth of %s for %s players to %f");
        add(CommandTranslations.AFFINITY_SET_SINGLE, "Set affinity depth of %s for player %s to %f");
        add(CommandTranslations.AFFINITY_UNKNOWN, "Could not find affinity %s");
        add(CommandTranslations.MAGIC_XP_ADD_LEVELS_MULTIPLE, "Gave %s magic xp levels to %s players");
        add(CommandTranslations.MAGIC_XP_ADD_LEVELS_SINGLE, "Gave %s magic xp levels to %s");
        add(CommandTranslations.MAGIC_XP_ADD_POINTS_MULTIPLE, "Gave %s magic xp to %s players");
        add(CommandTranslations.MAGIC_XP_ADD_POINTS_SINGLE, "Gave %s magic xp to %s");
        add(CommandTranslations.MAGIC_XP_GET_LEVELS, "%s has %s magic xp levels");
        add(CommandTranslations.MAGIC_XP_GET_POINTS, "%s has %s magic xp points");
        add(CommandTranslations.MAGIC_XP_SET_LEVELS_MULTIPLE, "Set %s magic xp levels on %s players");
        add(CommandTranslations.MAGIC_XP_SET_LEVELS_SINGLE, "Set %s magic xp levels on %s");
        add(CommandTranslations.MAGIC_XP_SET_POINTS_MULTIPLE, "Set %s magic xp on %s players");
        add(CommandTranslations.MAGIC_XP_SET_POINTS_SINGLE, "Set %s magic xp on %s");
        add(CommandTranslations.SKILL_ALREADY_KNOWN, "Skill %s has already been learned");
        add(CommandTranslations.SKILL_FORGET_ALL_MULTIPLE, "Took all skill knowledge from %s players");
        add(CommandTranslations.SKILL_FORGET_ALL_SINGLE, "Took all skill knowledge from player %s");
        add(CommandTranslations.SKILL_FORGET_MULTIPLE, "Took knowledge of skill %s from %s players");
        add(CommandTranslations.SKILL_FORGET_SINGLE, "Took knowledge of skill %s from player %s");
        add(CommandTranslations.SKILL_LEARN_ALL_MULTIPLE, "Gave all skill knowledge to %s players");
        add(CommandTranslations.SKILL_LEARN_ALL_SINGLE, "Gave all skill knowledge to player %s");
        add(CommandTranslations.SKILL_LEARN_MULTIPLE, "Gave knowledge of skill %s to %s players");
        add(CommandTranslations.SKILL_LEARN_SINGLE, "Gave knowledge of skill %s to player %s");
        add(CommandTranslations.SKILL_NOT_YET_KNOWN, "Skill %s must be learned first");
        add(CommandTranslations.SKILL_POINT_ADD_MULTIPLE, "Added %s skill points of type %s to %s players");
        add(CommandTranslations.SKILL_POINT_ADD_SINGLE, "Added %s skill points of type %s to player %s");
        add(CommandTranslations.SKILL_POINT_CONSUME_MULTIPLE, "Consumed %s skill points of type %s from %s players");
        add(CommandTranslations.SKILL_POINT_CONSUME_SINGLE, "Consumed %s skill points of type %s from player %s");
        add(CommandTranslations.SKILL_POINT_GET, "Player %s has %d skill points of type %s");
        add(CommandTranslations.SKILL_POINT_RESET_MULTIPLE, "Reset all skill points for %s players");
        add(CommandTranslations.SKILL_POINT_RESET_SINGLE, "Reset all skill points for player %s");
        add(CommandTranslations.SKILL_POINT_SET_MULTIPLE, "%s players now have %d skill points of type %s");
        add(CommandTranslations.SKILL_POINT_SET_SINGLE, "Player %s now has %d skill points of type %s");
        add(CommandTranslations.SKILL_POINT_UNKNOWN, "Could not find skill point type %s");
        add(CommandTranslations.SKILL_UNKNOWN, "Could not find skill %s");
        add(EtheriumType.DARK.getTranslationKey(), "Dark Etherium");
        add(EtheriumType.LIGHT.getTranslationKey(), "Light Etherium");
        add(EtheriumType.NEUTRAL.getTranslationKey(), "Neutral Etherium");
        add(AMItems.ETHERIUM_PLACEHOLDER.get().getDescriptionId() + "." + EtheriumType.DARK.getId().getPath(), "Dark Etherium");
        add(AMItems.ETHERIUM_PLACEHOLDER.get().getDescriptionId() + "." + EtheriumType.LIGHT.getId().getPath(), "Light Etherium");
        add(AMItems.ETHERIUM_PLACEHOLDER.get().getDescriptionId() + "." + EtheriumType.NEUTRAL.getId().getPath(), "Neutral Etherium");
        add("config.jade.plugin_arsmagicalegacy.etherium", "Etherium");
        add("key.category." + ArsMagicaAPI.MOD_ID, ArsMagicaLegacy.getModName());
        add("key." + ArsMagicaAPI.MOD_ID + ".configure_spell", "Configure Spell");
        add("key." + ArsMagicaAPI.MOD_ID + ".next_shape_group", "Next Shape Group");
        add("key." + ArsMagicaAPI.MOD_ID + ".prev_shape_group", "Previous Shape Group");
        add("potion.potency.5", "VI");
        add("potion.potency.6", "VII");
        add("potion.potency.7", "VIII");
        add("potion.potency.8", "IX");
        add("potion.potency.9", "X");
        add("hud_manager", "HUD Manager");
        add("hud_manager.open", "Open HUD Manager");
    }

    /**
     * Adds an ability translation that matches the ability id.
     *
     * @param ability The ability to generate the translation for.
     */
    private void abilityIdTranslation(ResourceLocation ability, String description) {
        addAbility(ability, idToTranslation(ability.getPath()), description);
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
     * Adds a block translation that matches the block id.
     *
     * @param fluid The block to generate the translation for.
     */
    private void fluidIdTranslation(RegistryObject<? extends FluidType> fluid, boolean hasBucket) {
        ResourceLocation id = fluid.getId();
        add("fluid_type." + id.getNamespace() + "." + id.getPath(), idToTranslation(id.getPath()));
        add("block." + id.getNamespace() + "." + id.getPath(), idToTranslation(id.getPath()));
        if (hasBucket) {
            add("item." + id.getNamespace() + "." + id.getPath() + "_bucket", idToTranslation(id.getPath()) + " Bucket");
        }
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
    private void skillPointIdTranslation(RegistryObject<? extends SkillPoint> skillPoint) {
        addSkillPoint(skillPoint, idToTranslation(skillPoint.getId().getPath()));
    }

    /**
     * Adds an affinity translation that matches the affinity id.
     *
     * @param affinity The affinity to generate the translation for.
     */
    private void affinityIdTranslation(RegistryObject<? extends Affinity> affinity) {
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
        arcaneCompendiumTranslation(compendiumType + "." + skill.getPath() + ".page0.text", compendiumText);
    }

    /**
     * Adds an ability translation.
     *
     * @param ability     The ability to add the translation for.
     * @param name        The translation for the ability name.
     * @param description The translation for the ability description.
     */
    private void addAbility(ResourceLocation ability, String name, String description) {
        add(Util.makeDescriptionId(Ability.ABILITY, ability) + ".name", name);
        add(Util.makeDescriptionId(Ability.ABILITY, ability) + ".description", description);
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
    private void addAffinity(Supplier<? extends Affinity> affinity, String translation) {
        add(affinity.get(), translation);
    }

    /**
     * Adds an affinity item translation.
     *
     * @param affinityItem The affinity item to add the translation for.
     * @param affinity     The affinity to generate the translation from.
     */
    private void affinityItemIdTranslation(RegistryObject<? extends IAffinityItem> affinityItem, RegistryObject<? extends Affinity> affinity) {
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
    private void affinityItemTranslation(RegistryObject<? extends IAffinityItem> affinityItem, RegistryObject<? extends Affinity> affinity, String translation) {
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
    private void addSkillPoint(Supplier<? extends SkillPoint> skillPoint, String translation) {
        add(skillPoint.get(), translation);
    }

    /**
     * Adds an skill point item translation.
     *
     * @param skillPointItem The skill point item to add the translation for.
     * @param skillPoint     The skill point to generate the translation from.
     */
    private void skillPointItemIdTranslation(RegistryObject<? extends ISkillPointItem> skillPointItem, RegistryObject<? extends SkillPoint> skillPoint) {
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
    private void skillPointItemTranslation(RegistryObject<? extends ISkillPointItem> skillPointItem, RegistryObject<? extends SkillPoint> skillPoint, String translation) {
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
     * Adds an occulus tab translation.
     *
     * @param tab         The occulus tab to add the translation for.
     * @param translation The translation to use.
     */
    private void occulusTabTranslation(ResourceLocation tab, String translation) {
        add("occulus_tab." + tab.getNamespace() + "." + tab.getPath(), translation);
    }

    /**
     * Adds a damage source translation.
     *
     * @param damageSource The damage source to add the translation for.
     * @param translation  The translation to use.
     */
    private void damageSourceTranslation(String damageSource, String translation) {
        add("death.attack." + damageSource, translation);
    }

    /**
     * Adds a subtitle translation.
     *
     * @param soundId     The sound to add the translation for.
     * @param translation The translation to use.
     */
    private void subtitleTranslation(Supplier<SoundEvent> soundId, String translation) {
        add(Util.makeDescriptionId("subtitle", ForgeRegistries.SOUND_EVENTS.getKey(soundId.get())), translation);
    }

    /**
     * Adds a subtitle translation.
     *
     * @param soundId     The sound to add the translation for.
     * @param translation The translation to use.
     */
    private void subtitleTranslation(ResourceLocation soundId, String translation) {
        add(Util.makeDescriptionId("subtitle", soundId), translation);
    }

    /**
     * Adds a stat translation.
     *
     * @param stat        The stat to add the translation for.
     * @param translation The translation to use.
     */
    private void statTranslation(ResourceLocation stat, String translation) {
        add(Util.makeDescriptionId("stat", stat), translation);
    }

    /**
     * Adds an arcane compendium entry translation.
     *
     * @param compendiumEntry The compendium entry to add the translation for.
     * @param translation     The translation to use.
     */
    private void arcaneCompendiumTranslation(String compendiumEntry, String translation) {
        add("item." + ArsMagicaAPI.MOD_ID + ".arcane_compendium." + compendiumEntry, translation);
    }

    private void add(ITranslatable translatable, String translation) {
        add(translatable.getTranslationKey(), translation);
    }

    private void add(ITranslatable.WithDescription translatable, String nameTranslation, String descriptionTranslation) {
        add(translatable.getNameTranslationKey(), nameTranslation);
        add(translatable.getDescriptionTranslationKey(), descriptionTranslation);
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
