package com.github.ipecter.rtustudio.adaptivedifficulty.listeners;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath extends RSListener<AdaptiveDifficulty> {

    private final DifficultyConfig config;
    private final StatusManager manager;

    public PlayerDeath(AdaptiveDifficulty plugin) {
        super(plugin);
        this.config = plugin.getDifficultyConfig();
        this.manager = plugin.getStatusManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        DifficultyConfig.Difficulty difficulty = config.get(manager.get(player.getUniqueId()));
        if (difficulty == null) return;
        if (!difficulty.player().hardcore().keepExperience()) {
            player.setLevel(0);
            player.setExp(0.0F);
            e.setDroppedExp(0);
            e.setNewExp(0);
            e.setNewLevel(0);
            e.setNewTotalExp(0);
            e.setShouldDropExperience(false);
            e.setKeepLevel(false);
        }
        if (!difficulty.player().hardcore().keepInventory()) {
            player.getInventory().clear();
            e.setKeepInventory(false);
            e.getDrops().clear();
        }
    }

}
