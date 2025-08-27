package com.github.ipecter.rtustudio.adaptivedifficulty.inventory;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.MenuConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.data.Difficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.data.Icon;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.format.ComponentFormatter;
import kr.rtuserver.framework.bukkit.api.inventory.RSInventory;
import kr.rtuserver.framework.bukkit.api.platform.MinecraftVersion;
import kr.rtuserver.framework.bukkit.api.player.PlayerChat;
import kr.rtuserver.framework.bukkit.api.registry.CustomItems;
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
        Component title = ComponentFormatter.mini(message().get(player, "title"));
        int size = menuConfig.getLine() * 9;
        this.inventory = createInventory(size, title);
        init();
    }

    private void init() {
        for (int i = 0; i < menuConfig.getLine() * 9; i++) {
            Icon icon = menuConfig.getIcon(i);
            if (icon == null) continue;
            Difficulty difficulty = difficultyConfig.get(icon.difficulty());
            if (difficulty == null) continue;
            ItemStack item = CustomItems.from(icon.item());
            if (item == null) continue;
            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;
            Component displayName = ComponentFormatter.mini("<!italic><white>" + difficulty.displayName());
            List<String> list = List.of(icon.description().split("\n"));
            if (MinecraftVersion.isPaper()) {
                meta.displayName(displayName);
                if (!icon.description().isEmpty())
                    meta.lore(list.stream().map(v -> ComponentFormatter.mini("<!italic><white>" + v)).toList());
            } else {
                meta.setDisplayName(ComponentFormatter.legacy(displayName));
                if (!icon.description().isEmpty())
                    meta.setLore(list.stream().map(v -> ComponentFormatter.mini("<!italic><white>" + v)).map(ComponentFormatter::legacy).toList());
            }
            item.setItemMeta(meta);
            inventory.setItem(i, item);
        }
    }

    @Override
    public boolean onClick(Event<InventoryClickEvent> event, Click click) {
        if (inventory.isEmpty()) return false;
        int slot = click.slot();
        Icon icon = menuConfig.getIcon(slot);
        if (icon != null) {
            String difficultyStr = icon.difficulty();
            Difficulty difficulty = difficultyConfig.get(difficultyStr);
            if (difficulty != null) {
                manager.set(player.getUniqueId(), difficultyStr);
                String change = message().get(player, "change");
                change = change.replace("[name]", difficulty.displayName());
                PlayerChat.of(getPlugin()).announce(player, change);
                player.closeInventory();
            }
        }
        return false;
    }

}
