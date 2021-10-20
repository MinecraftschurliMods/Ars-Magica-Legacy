package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.spell.component.Damage;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.shape.Self;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.SPELL_PARTS;

public interface AMSpellParts {
    RegistryObject<Self> SELF = SPELL_PARTS.register("self", Self::new);

    RegistryObject<Damage> FIRE_DAMAGE = SPELL_PARTS.register("fire_damage", () -> new Damage(livingEntity -> DamageSource.IN_FIRE, 6));

    @Internal
    static void register() {}
}
