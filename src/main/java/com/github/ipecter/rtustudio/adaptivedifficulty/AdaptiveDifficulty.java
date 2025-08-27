package com.github.ipecter.rtustudio.adaptivedifficulty;

import com.github.ipecter.rtustudio.adaptivedifficulty.command.MainCommand;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.MenuConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.MonsterConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.dependency.PlaceholderAPI;
import com.github.ipecter.rtustudio.adaptivedifficulty.listener.*;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import lombok.Getter;

public class AdaptiveDifficulty extends RSPlugin {

    @Getter
    private static AdaptiveDifficulty instance;
    @Getter
    private StatusManager statusManager;

    @Override
    public void enable() {
        instance = this;
        initStorage("Difficulty");

        registerConfiguration(DifficultyConfig.class, "Difficulty");
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
