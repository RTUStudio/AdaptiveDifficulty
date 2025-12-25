package kr.rtustudio.adaptivedifficulty.listener;

import kr.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtustudio.adaptivedifficulty.data.Difficulty;
import kr.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtustudio.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

@SuppressWarnings("unused")
public class PlayerFoodLevelChange extends RSListener<AdaptiveDifficulty> {

    private final DifficultyConfig config;
    private final StatusManager manager;

    public PlayerFoodLevelChange(AdaptiveDifficulty plugin) {
        super(plugin);
        this.config = plugin.getConfiguration(DifficultyConfig.class);
        this.manager = plugin.getStatusManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (player.getFoodLevel() >= e.getFoodLevel()) {
                Difficulty difficulty = config.get(manager.get(player.getUniqueId()));
                if (difficulty == null) return;
                if (!difficulty.player().loseHunger()) e.setCancelled(true);
            }
        }
    }

}
