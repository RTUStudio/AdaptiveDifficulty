package kr.rtustudio.adaptivedifficulty.configuration;

import kr.rtustudio.configurate.model.ConfigurationPart;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({
        "unused",
        "CanBeFinal",
        "FieldCanBeLocal",
        "FieldMayBeFinal",
        "InnerClassMayBeStatic"
})
public class MonsterConfig extends ConfigurationPart {

    @Getter
    private List<EntityType> mobs = make(new ArrayList<>(), list -> {
        for (EntityType type : EntityType.values()) {
            Class<? extends Entity> clazz = type.getEntityClass();
            if (clazz == null) continue;
            if (Monster.class.isAssignableFrom(clazz)) list.add(type);
        }
    });
}
