package kr.rtustudio.adaptivedifficulty.listener;

import kr.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtustudio.adaptivedifficulty.data.Difficulty;
import kr.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtustudio.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;

@SuppressWarnings("unused")
public class EntityDeathByPlayer extends RSListener<AdaptiveDifficulty> {

    private final DifficultyConfig config;
    private final StatusManager manager;

    public EntityDeathByPlayer(AdaptiveDifficulty plugin) {
        super(plugin);
        this.config = plugin.getConfiguration(DifficultyConfig.class);
        this.manager = plugin.getStatusManager();
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent e) {
        Player player = e.getEntity().getKiller();
        if (player != null) {
            Difficulty difficulty = config.get(manager.get(player.getUniqueId()));
            if (difficulty == null) return;
            e.setDroppedExp((int) Math.round(e.getDroppedExp() * difficulty.player().experience()));
        }
    }

}
