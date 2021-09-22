package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillBuilder;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillProvider;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMOcculusTabs;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class AMSkillProvider extends SkillProvider {

    protected AMSkillProvider(DataGenerator generator) {
        super(generator, ArsMagicaAPI.MOD_ID);
    }

    @Override
    public String getName() {
        return "AMSkillProvider";
    }

    @Override
    protected void createSkills(Consumer<SkillBuilder> consumer) {
        createSkill("test", AMOcculusTabs.OFFENSE.get(), new ResourceLocation("textures/item/golden_apple.png"))
                .setPosition(50, 50)
                .build(consumer);
    }
}
