package kr.rtustudio.adaptivedifficulty.configuration;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import kr.rtustudio.adaptivedifficulty.data.Icon;
import kr.rtustudio.configurate.objectmapping.ConfigSerializable;
import kr.rtustudio.configurate.objectmapping.meta.Comment;
import kr.rtustudio.configurate.objectmapping.meta.PostProcess;
import kr.rtustudio.configurate.model.ConfigurationPart;
import lombok.Getter;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

@Getter
@SuppressWarnings({"unused", "CanBeFinal", "FieldCanBeLocal", "FieldMayBeFinal", "InnerClassMayBeStatic"})
public class MenuConfig extends ConfigurationPart {

    @Comment("""
            Inventory line (1-6)
            인벤토리 줄 (1-6)
            """)
    private int line = 3;

    private List<Icon> icons = List.of(
            new Icon("peaceful", "minecraft:nether_star", 11),
            new Icon("easy", "minecraft:nether_star", 12),
            new Icon("normal", "minecraft:nether_star", 13),
            new Icon("hard", "minecraft:nether_star", 14),
            new Icon("hardcore", "minecraft:nether_star", 15)
    );

    private List<Action> actions = List.of(
            new Action(Action.State.SELECT, 11, List.of(ClickType.LEFT)),
            new Action(Action.State.SELECT, 12, List.of(ClickType.LEFT)),
            new Action(Action.State.SELECT, 13, List.of(ClickType.LEFT)),
            new Action(Action.State.SELECT, 14, List.of(ClickType.LEFT)),
            new Action(Action.State.SELECT, 15, List.of(ClickType.LEFT))
    );

    private transient Set<Integer> disabledSlots = new ObjectOpenHashSet<>();

    @PostProcess
    public void check() {
        disabledSlots.clear();
        Map<Integer, Map<ClickType, Action.State>> slotClickMap = new Object2ObjectOpenHashMap<>();

        for (Action action : actions) {
            int slot = action.slot();
            Map<ClickType, Action.State> clickMap = slotClickMap.computeIfAbsent(slot, k -> new Object2ObjectOpenHashMap<>());

            for (ClickType click : action.clickTypes()) {
                if (clickMap.containsKey(click)) {
                    Logger.getLogger("AdaptiveDifficulty").warning(
                            "[MenuConfig] Duplicate slot/clickType: slot=" + slot + ", click=" + click +
                                    " (states: " + clickMap.get(click) + ", " + action.state() + "). Disabling slot.");
                    disabledSlots.add(slot);
                } else {
                    clickMap.put(click, action.state());
                }
            }
        }
    }

    @Nullable
    public Icon getIcon(int slot) {
        for (Icon icon : icons) {
            if (icon.slot() == slot) return icon;
        }
        return null;
    }

    @Nullable
    public Action getAction(int slot, ClickType clickType) {
        if (disabledSlots.contains(slot)) return null;
        for (Action action : actions) {
            if (action.slot() == slot && (action.clickTypes().isEmpty() || action.clickTypes().contains(clickType))) {
                return action;
            }
        }
        return null;
    }

    @ConfigSerializable
    public record Action(@NotNull State state, int slot, @NotNull List<ClickType> clickTypes) {
        public enum State {
            SELECT;

            public String getKey() {
                return name().toLowerCase().replace('_', '-');
            }
        }
    }
}
