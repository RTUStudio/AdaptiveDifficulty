package kr.rtustudio.adaptivedifficulty.configuration;

import kr.rtustudio.framework.bukkit.api.configuration.ConfigurationPart;
import kr.rtustudio.framework.bukkit.api.platform.MinecraftVersion;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterConfig extends ConfigurationPart {

    @Getter
    private final List<EntityType> mobs = make(new ArrayList<>(), list -> {
        for (EntityType type : EntityType.values()) {
            Class<? extends Entity> clazz = type.getEntityClass();
            if (clazz == null) continue;
            if (Monster.class.isAssignableFrom(clazz)) list.add(type);
        }
    });
}
