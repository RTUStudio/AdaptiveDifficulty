package com.github.ipecter.rtustudio.adaptivedifficulty.listeners;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.MonsterConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTargetPlayer extends RSListener<AdaptiveDifficulty> {

    private final DifficultyConfig difficultyConfig;
    private final MonsterConfig monsterConfig;
    private final StatusManager manager;

    public EntityTargetPlayer(AdaptiveDifficulty plugin) {
        super(plugin);
        this.difficultyConfig = plugin.getDifficultyConfig();
        this.monsterConfig = plugin.getMonsterConfig();
        this.manager = plugin.getStatusManager();
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player player) {
            DifficultyConfig.Difficulty difficulty = difficultyConfig.get(manager.get(player.getUniqueId()));
            if (difficulty == null) return;
            if (difficulty.monster().attackPlayer())
                if (e.getReason() == EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY) return;
            if (difficulty.monster().ignorePlayer()) {
                for (String mob : monsterConfig.getMobs()) {
                    if (mob.equalsIgnoreCase(e.getEntity().getType().name())) {
                        e.setCancelled(true);
                        e.setTarget(null);
                        return;
                    }
                }
            }
        }
    }

}
