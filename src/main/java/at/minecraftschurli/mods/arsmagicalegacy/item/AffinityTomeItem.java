package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("DataFlowIssue")
public class AffinityTomeItem extends HolderDataComponentItem<Affinity> {
    public AffinityTomeItem(Properties properties) {
        super(properties, AMDataComponents.AFFINITY.get());
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!stack.has(AMDataComponents.AFFINITY)) return super.use(level, player, usedHand);
        MagicHelper helper = ArsMagicaApi.magicHelper();
        if (!helper.knowsMagic(player)) {
            player.sendOverlayMessage(AMTranslations.PREVENT_ITEM);
            return InteractionResult.FAIL;
        }
        Holder<Affinity> affinity = stack.get(AMDataComponents.AFFINITY);
        double shift = AMServerConfig.AFFINITY_TOME_SHIFT.get();
        double reduction = -AMServerConfig.AFFINITY_TOME_REDUCTION.get();
        helper.addAffinityDepth(player, AMRegistries.affinities(level.registryAccess())
            .listElements()
            .filter(e -> e.getKey() != Affinity.NONE)
            .map(e -> Map.entry(e, e.getKey() == affinity.getKey() ? shift : reduction))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), true, false);
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
    }
}
