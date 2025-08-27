package com.github.ipecter.rtustudio.adaptivedifficulty.configuration;

import com.github.ipecter.rtustudio.adaptivedifficulty.data.Icon;
import com.github.ipecter.rtustudio.adaptivedifficulty.util.LinkedMap;
import kr.rtuserver.configurate.objectmapping.meta.Comment;
import kr.rtuserver.framework.bukkit.api.configuration.ConfigurationPart;
import lombok.Getter;

import java.util.Map;

@SuppressWarnings({"unused", "InnerClassMayBeStatic", "FieldMayBeFinal"})
public class MenuConfig extends ConfigurationPart {

    @Getter
    @Comment("""
            Inventory line (1-6)
            인벤토리 줄 (1-6)
            """)
    private int line = 3;
    private Map<Integer, Icon> icons = Map.of(
            11, new Icon("peaceful", "Vanilla peaceful difficulty", "minecraft:nether_star"),
            12, new Icon("easy", "Vanilla easy difficulty", "minecraft:nether_star"),
            13, new Icon("normal", "Vanilla normal difficulty", "minecraft:nether_star"),
            14, new Icon("hard", "Vanilla hard difficulty", "minecraft:nether_star"),
            15, new Icon(
                    "hardcore",
                    "Vanilla hardcore mode\nIt will remove your experience and inventory when you die",
                    "minecraft:nether_star"
            )
    );

    public Icon getIcon(Integer slot) {
        return icons.get(slot);
    }

}
