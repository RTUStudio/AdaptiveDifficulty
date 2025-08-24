package com.github.ipecter.rtustudio.adaptivedifficulty.listener;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.data.Difficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

@SuppressWarnings("unused")
public class PlayerDamage extends RSListener<AdaptiveDifficulty> {

    private final DifficultyConfig config;
    private final StatusManager manager;

    public PlayerDamage(AdaptiveDifficulty plugin) {
        super(plugin);
        this.config = plugin.getDifficultyConfig();
        this.manager = plugin.getStatusManager();
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player player) {
            Difficulty difficulty = config.get(manager.get(player.getUniqueId()));
            if (difficulty == null) return;
            switch (e.getCause()) {
                case DROWNING -> e.setDamage(e.getDamage() * difficulty.getDamage().getMultiplier().getDrowning());
                case FIRE, FIRE_TICK -> e.setDamage(e.getDamage() * difficulty.getDamage().getMultiplier().getFire());
                case FALL -> e.setDamage(e.getDamage() * difficulty.getDamage().getMultiplier().getFall());
                case SUFFOCATION ->
                        e.setDamage(e.getDamage() * difficulty.getDamage().getMultiplier().getSuffocation());
            }
        }
    }

}
