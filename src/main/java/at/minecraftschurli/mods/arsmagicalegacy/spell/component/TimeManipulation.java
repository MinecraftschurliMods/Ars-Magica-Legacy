package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.clock.ClockTimeMarker;
import net.minecraft.world.clock.WorldClock;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class TimeManipulation extends SpellComponent {
    private final Function<ServerLevel, @Nullable Holder<WorldClock>> clockGetter;
    private final ResourceKey<ClockTimeMarker> timeMarker;
    private final Component failureMessage;

    public TimeManipulation(ResourceKey<ClockTimeMarker> timeMarker, Component failureMessage) {
        this(level -> level.dimensionType().defaultClock().orElse(null), timeMarker, failureMessage);
    }

    public TimeManipulation(Function<ServerLevel, @Nullable Holder<WorldClock>> clockGetter, ResourceKey<ClockTimeMarker> timeMarker, Component failureMessage) {
        this.clockGetter = clockGetter;
        this.timeMarker = timeMarker;
        this.failureMessage = failureMessage;
    }

    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(context.spell());
        Holder<WorldClock> clock = clockGetter.apply(level);
        if (clock == null) return SpellComponentCastResult.failure(context.spell(), failureMessage);
        level.clockManager().moveToTimeMarker(clock, timeMarker);
        return SpellComponentCastResult.success(context.spell());
    }
}
