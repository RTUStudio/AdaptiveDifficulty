package com.github.ipecter.rtustudio.adaptivedifficulty.listeners;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.util.List;

public class PlayerPotionEffect extends RSListener<AdaptiveDifficulty> {

    private final DifficultyConfig config;
    private final StatusManager manager;
    // PotionEffectType to String for version independent
    private final List<String> harmfulEffects = List.of(
            "BLINDNESS",
            "CONFUSION",
            "DARKNESS",
            "HUNGER",
            "POISON",
            "SLOW",
            "SLOW_DIGGING",
            "WEAKNESS"
    );

    public PlayerPotionEffect(AdaptiveDifficulty plugin) {
        super(plugin);
        this.config = plugin.getDifficultyConfig();
        this.manager = plugin.getStatusManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPotionEffect(EntityPotionEffectEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (e.getAction() == EntityPotionEffectEvent.Action.ADDED) {
                DifficultyConfig.Difficulty difficulty = config.get(manager.get(player.getUniqueId()));
                if (difficulty == null) return;
                if (!difficulty.player().harmfulEffect()) {
                    for (String effect : harmfulEffects) {
                        if (effect.equalsIgnoreCase(e.getModifiedType().getName())) {
                            e.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

}
