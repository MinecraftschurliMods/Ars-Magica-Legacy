package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.entity.WintersGrasp;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class WintersGraspItem extends Item {
    public WintersGraspItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            WintersGrasp entity = Objects.requireNonNull(AMEntities.WINTERS_GRASP.get().create(level, EntitySpawnReason.MOB_SUMMONED));
            Vec3 lookAngle = player.getLookAngle();
            Vec3 vec = player.position().add(0, player.getBbHeight() / 2, 0).add(lookAngle);
            entity.teleportTo(vec.x, vec.y, vec.z);
            entity.setDeltaMovement(lookAngle);
            entity.setXRot(player.getXRot());
            entity.setYRot(player.getYRot());
            entity.setOwner(player);
            entity.setStack(player.getItemInHand(hand));
            level.addFreshEntity(entity);
        }
        player.setItemInHand(hand, ItemStack.EMPTY);
        return InteractionResult.CONSUME;
    }
}
