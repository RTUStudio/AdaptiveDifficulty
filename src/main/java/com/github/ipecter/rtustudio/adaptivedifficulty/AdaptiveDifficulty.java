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
    private DifficultyConfig difficultyConfig;
    @Getter
    private MenuConfig menuConfig;
    @Getter
    private MonsterConfig monsterConfig;
    @Getter
    private StatusManager statusManager;

    private PlaceholderAPI placeholder;

    @Override
    public void enable() {
        instance = this;
        getConfigurations().initStorage("Difficulty");

        difficultyConfig = new DifficultyConfig(this);
        menuConfig = new MenuConfig(this);
        monsterConfig = new MonsterConfig(this);

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

        if (getFramework().isEnabledDependency("PlaceholderAPI")) {
            placeholder = new PlaceholderAPI(this);
            placeholder.register();
        }
    }

    @Override
    public void disable() {
        if (getFramework().isEnabledDependency("PlaceholderAPI")) {
            placeholder.unregister();
        }
    }
}
