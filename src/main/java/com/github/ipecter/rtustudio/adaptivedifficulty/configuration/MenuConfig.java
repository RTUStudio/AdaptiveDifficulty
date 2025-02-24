package com.github.ipecter.rtustudio.adaptivedifficulty.configuration;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class MenuConfig extends RSConfiguration<AdaptiveDifficulty> {

    private final Map<Integer, Icon> icons = new HashMap<>();
    @Getter
    private int line = 3;

    public MenuConfig(AdaptiveDifficulty plugin) {
        super(plugin, "Menu.yml", null);
        setup(this);
    }

    public Icon getIcon(Integer slot) {
        return icons.get(slot);
    }

    private void init() {
        icons.clear();
        line = getInt("line", line, """
                Inventory line (1-6)
                인벤토리 줄 (1-6)""");
        for (String key : getConfigurationSection("icon").getKeys(false)) {
            try {
                String difficulty = getString("icon." + key + ".difficulty", "");
                if (difficulty.isEmpty()) continue;
                String description = getString("icon." + key + ".description", "");
                String item = getString("icon." + key + ".item", "");
                if (item.isEmpty()) continue;
                Icon icon = new Icon(difficulty, description, item);
                icons.put(Integer.parseInt(key), icon);
            } catch (NumberFormatException e) {
                getPlugin().console("<red>Inventory slot must be a number</red>");
                getPlugin().console("<red>인벤토리 슬롯은 숫자여야 합니다</red>");
            }
        }
    }

    public record Icon(String difficulty, String description, String item) {
    }

}
