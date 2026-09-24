package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageTypes;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class Whirlwind extends AbstractOwnableEntity {
    public static final Identifier PARTICLES = ArsMagicaApi.id("whirlwind");
    private final Map<Player, Integer> cooldowns = new HashMap<>();

    public Whirlwind(EntityType<? extends Whirlwind> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            AMClientUtil.spawnWhirlwindParticles(this);
        } else if (tickCount > 140) {
            remove(RemovalReason.KILLED);
        }
        cooldowns.replaceAll((_, v) -> Math.max(v - 1, 0));
        setPos(position().add(getDeltaMovement()));
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (!(level() instanceof ServerLevel level) || player.isCreative()) return;
        Integer cooldown = cooldowns.get(player);
        if (cooldown == null || cooldown <= 0) {
            if (random.nextInt(100) < 10) {
                Inventory inventory = player.getInventory();
                int slot = inventory.getNonEquipmentItems().size() + random.nextInt(4);
                ItemStack stack = inventory.getItem(slot).copy();
                inventory.setItem(slot, ItemStack.EMPTY);
                if (!inventory.add(stack)) {
                    ItemEntity item = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), stack);
                    item.setDeltaMovement(random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2 - 0.1, random.nextDouble() * 0.2 - 0.1);
                    level.addFreshEntity(item);
                }
            }
            player.hurtServer(level, damageSource(AMDamageTypes.WHIRLWIND), 6);
            player.setDeltaMovement(getDeltaMovement().x() + random.nextFloat() * 0.2f, getDeltaMovement().y() + 0.2 + random.nextFloat() * 0.2, getDeltaMovement().z() + random.nextFloat() * 0.2f);
            player.fallDistance = 0f;
            cooldowns.put(player, 20);
        }
    }
}
