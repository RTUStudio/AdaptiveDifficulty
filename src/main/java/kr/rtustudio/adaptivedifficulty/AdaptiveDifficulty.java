package kr.rtustudio.adaptivedifficulty;

import kr.rtustudio.adaptivedifficulty.command.MainCommand;
import kr.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtustudio.adaptivedifficulty.configuration.MenuConfig;
import kr.rtustudio.adaptivedifficulty.configuration.MonsterConfig;
import kr.rtustudio.adaptivedifficulty.configuration.serializer.EntityTypeSerializer;
import kr.rtustudio.adaptivedifficulty.dependency.PlaceholderAPI;
import kr.rtustudio.adaptivedifficulty.listener.*;
import kr.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtustudio.framework.bukkit.api.RSPlugin;
import lombok.Getter;
import org.bukkit.entity.EntityType;

public class AdaptiveDifficulty extends RSPlugin {

    @Getter
    private static AdaptiveDifficulty instance;
    @Getter
    private StatusManager statusManager;

    @Override
    public void load() {
        instance = this;
    }

    @Override
    public void enable() {
        initStorage("Difficulty");

        registerConfiguration(DifficultyConfig.class, "Difficulty", builder -> {
            builder.register(EntityType.class, new EntityTypeSerializer());
        });
        registerConfiguration(MenuConfig.class, "Menu");
        registerConfiguration(MonsterConfig.class, "Monster");

        statusManager = new StatusManager(this);

        registerEvent(new EntityDeathByPlayer(this));
        registerEvent(new EntityTargetPlayer(this));
        registerEvent(new PlayerDamage(this));
        registerEvent(new PlayerDamageByEntity(this));
        registerEvent(new PlayerDeath(this));
        registerEvent(new PlayerFoodLevelChange(this));
        registerEvent(new PlayerJoinQuit(this));
        registerEvent(new PlayerPotionEffect(this));

        registerCommand(new MainCommand(this), true);

        registerIntegration(new PlaceholderAPI(this));
    }
}
