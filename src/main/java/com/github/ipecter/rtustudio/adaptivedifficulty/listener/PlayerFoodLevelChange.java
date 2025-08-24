package com.github.ipecter.rtustudio.adaptivedifficulty.listener;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.data.Difficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

@SuppressWarnings("unused")
public class PlayerFoodLevelChange extends RSListener<AdaptiveDifficulty> {

    private final DifficultyConfig config;
    private final StatusManager manager;

    public PlayerFoodLevelChange(AdaptiveDifficulty plugin) {
        super(plugin);
        this.config = plugin.getDifficultyConfig();
        this.manager = plugin.getStatusManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (player.getFoodLevel() >= e.getFoodLevel()) {
                Difficulty difficulty = config.get(manager.get(player.getUniqueId()));
                if (difficulty == null) return;
                if (!difficulty.getPlayer().isLoseHunger()) e.setCancelled(true);
            }
        }
    }

}
