package at.minecraftschurli.mods.arsmagicalegacy.worldgen;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMWorldgen;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.Collection;
import java.util.List;

public class BlockStatePropertyMatchTest extends RuleTest {
    private static final String SEPARATOR = "=";
    public static final MapCodec<BlockStatePropertyMatchTest> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.STRING.comapFlatMap(
            string -> {
                String[] split = string.split(SEPARATOR);
                if (split.length != 2) return DataResult.error(() -> "Invalid property " + string);
                return DataResult.success(Pair.of(split[0], split[1]));
            },
            pair -> pair.getFirst() + SEPARATOR + pair.getSecond()
        ).listOf().fieldOf("properties").forGetter(e -> e.properties)
    ).apply(inst, BlockStatePropertyMatchTest::new));
    private final List<Pair<String, String>> properties;

    public BlockStatePropertyMatchTest(BlockState state, List<Property<?>> whitelist, boolean isBlacklist) {
        this.properties = state.getProperties()
            .stream()
            .filter(p -> isBlacklist != whitelist.contains(p))
            .map(p -> Pair.of(p.getName(), AMUtil.getPropertyValueName(p, state)))
            .toList();
    }

    public BlockStatePropertyMatchTest(BlockState state, List<Property<?>> blacklist) {
        this(state, blacklist, true);
    }

    public BlockStatePropertyMatchTest(BlockState state) {
        this(state, List.of(), true);
    }

    private BlockStatePropertyMatchTest(List<Pair<String, String>> properties) {
        this.properties = properties;
    }

    @Override
    public boolean test(BlockState state, RandomSource random) {
        Collection<Property<?>> stateProperties = state.getProperties();
        return properties.stream().allMatch(p -> {
            String name = p.getFirst();
            String value = p.getSecond();
            return stateProperties.stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .filter(property -> AMUtil.getPropertyValueName(property, state).equals(value))
                .isPresent();
        });
    }

    @Override
    protected RuleTestType<?> getType() {
        return AMWorldgen.BLOCK_STATE_PROPERTY.get();
    }
}
