package kr.rtustudio.adaptivedifficulty.inventory;

import kr.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtustudio.adaptivedifficulty.configuration.MenuConfig;
import kr.rtustudio.adaptivedifficulty.data.Difficulty;
import kr.rtustudio.adaptivedifficulty.data.Icon;
import kr.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtustudio.framework.bukkit.api.format.ComponentFormatter;
import kr.rtustudio.framework.bukkit.api.inventory.RSInventory;
import kr.rtustudio.framework.bukkit.api.platform.MinecraftVersion;
import kr.rtustudio.framework.bukkit.api.registry.CustomItems;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DifficultyMenu extends RSInventory<AdaptiveDifficulty> {

    private final DifficultyConfig difficultyConfig;
    private final MenuConfig menuConfig;
    private final StatusManager manager;

    @Getter
    private final Player player;

    @Getter
    private final Inventory inventory;

    public DifficultyMenu(AdaptiveDifficulty plugin, Player player) {
        super(plugin);
        this.difficultyConfig = plugin.getConfiguration(DifficultyConfig.class);
        this.menuConfig = plugin.getConfiguration(MenuConfig.class);
        this.manager = plugin.getStatusManager();
        this.player = player;
        Component title = ComponentFormatter.mini(message.get(player, "menu.title"));
        int size = menuConfig.getLine() * 9;
        this.inventory = createInventory(size, title);
        init();
    }

    private void init() {
        for (Icon icon : menuConfig.getIcons()) {
            Difficulty difficulty = difficultyConfig.get(icon.difficulty());
            if (difficulty == null) continue;
            ItemStack item = CustomItems.from(icon.item());
            if (item == null) continue;
            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;
            Component displayName = ComponentFormatter.mini("<!italic><white>" + difficulty.displayName());
            String desc = message.get(player, "menu.difficulty." + icon.difficulty() + ".description");
            if (MinecraftVersion.isPaper()) {
                meta.displayName(displayName);
                if (!desc.isEmpty()) {
                    List<String> list = List.of(desc.split("\n"));
                    meta.lore(list.stream().map(v -> ComponentFormatter.mini("<!italic><white>" + v)).toList());
                }
            } else {
                meta.setDisplayName(ComponentFormatter.legacy(displayName));
                if (!desc.isEmpty()) {
                    List<String> list = List.of(desc.split("\n"));
                    meta.setLore(list.stream().map(v -> ComponentFormatter.mini("<!italic><white>" + v)).map(ComponentFormatter::legacy).toList());
                }
            }
            item.setItemMeta(meta);
            inventory.setItem(icon.slot(), item);
        }
    }

    @Override
    public boolean onClick(Event<InventoryClickEvent> event, Click click) {
        if (inventory.isEmpty()) return false;
        int slot = click.slot();
        MenuConfig.Action action = menuConfig.getAction(slot, click.type());
        if (action != null && action.state() == MenuConfig.Action.State.SELECT) {
            Icon icon = menuConfig.getIcon(slot);
            if (icon != null) {
                String difficultyStr = icon.difficulty();
                Difficulty difficulty = difficultyConfig.get(difficultyStr);
                if (difficulty != null) {
                    manager.set(player.getUniqueId(), difficultyStr);
                    String change = message.get(player, "menu.change")
                            .replace("{name}", difficulty.displayName());
                    notifier.announce(player, change);
                    player.closeInventory();
                }
            }
        }
        return false;
    }
}
