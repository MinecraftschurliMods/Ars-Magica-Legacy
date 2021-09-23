package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillBuilder;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillProvider;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMOcculusTabs;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSkillPoints;
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
        var test = createSkill("test", AMOcculusTabs.OFFENSE.get(), new ResourceLocation("textures/item/golden_apple.png"))
                .setPosition(275, 75)
                .addCost(AMSkillPoints.BLUE.get())
                .build(consumer);
        createSkill("test2", AMOcculusTabs.OFFENSE.get(), new ResourceLocation("textures/item/nether_star.png"))
                .setPosition(275, 120)
                .addCost(AMSkillPoints.BLUE.get())
                .addParent(test)
                .build(consumer);
    }
}
