package kr.rtustudio.adaptivedifficulty.configuration;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import kr.rtustudio.configurate.model.ConfigurationPart;
import kr.rtustudio.framework.bukkit.api.platform.MinecraftVersion;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterConfig extends ConfigurationPart {

    @Getter
    private final List<EntityType> mobs = make(new ObjectArrayList<>(), list -> {
        for (EntityType type : EntityType.values()) {
            Class<? extends Entity> clazz = type.getEntityClass();
            if (clazz == null) continue;
            if (Monster.class.isAssignableFrom(clazz)) list.add(type);
        }
    });
}
