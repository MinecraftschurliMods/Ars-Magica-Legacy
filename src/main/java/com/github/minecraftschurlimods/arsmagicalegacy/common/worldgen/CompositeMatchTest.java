package com.github.minecraftschurlimods.arsmagicalegacy.common.worldgen;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMWorldgen;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.List;

public class CompositeMatchTest extends RuleTest {
    public static final MapCodec<CompositeMatchTest> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        RuleTest.CODEC.listOf().fieldOf("rule_tests").forGetter(e -> e.ruleTests),
        Codec.BOOL.optionalFieldOf("all_match", false).forGetter(e -> e.allMatch)
    ).apply(inst, CompositeMatchTest::new));
    private final List<RuleTest> ruleTests;
    private final boolean allMatch;

    public CompositeMatchTest(List<RuleTest> ruleTests, boolean allMatch) {
        this.ruleTests = ruleTests;
        this.allMatch = allMatch;
    }

    public CompositeMatchTest(List<RuleTest> ruleTests) {
        this(ruleTests, false);
    }

    @Override
    public boolean test(BlockState state, RandomSource random) {
        return allMatch ? ruleTests.stream().allMatch(e -> e.test(state, random)) : ruleTests.stream().anyMatch(e -> e.test(state, random));
    }

    @Override
    protected RuleTestType<?> getType() {
        return AMWorldgen.COMPOSITE.get();
    }
}
