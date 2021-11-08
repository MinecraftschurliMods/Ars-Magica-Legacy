package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillBuilder;
import com.github.minecraftschurli.arsmagicalegacy.api.data.SkillProvider;
import net.minecraft.data.DataGenerator;

import java.util.function.Consumer;

class AMSkillProvider extends SkillProvider {
    protected AMSkillProvider(DataGenerator generator) {
        super(generator, ArsMagicaAPI.MOD_ID);
    }

    @Override
    public String getName() {
        return "AMSkillProvider";
    }

    @Override
    protected void createSkills(Consumer<SkillBuilder> consumer) {
        /*
        var test = createSkill("test", new ResourceLocation(ArsMagicaAPI.MOD_ID, "offense"), new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/icon/skill/affinity_gains.png"))
                .setPosition(275, 75)
                .addCost(AMSkillPoints.BLUE.get())
                .build(consumer);
        createSkill("test2", new ResourceLocation(ArsMagicaAPI.MOD_ID, "offense"), new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/icon/skill/augmented_casting.png"))
                .setPosition(275, 120)
                .addCost(AMSkillPoints.BLUE.get())
                .addParent(test)
                .build(consumer);
        */
    }
}
