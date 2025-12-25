package kr.rtustudio.adaptivedifficulty.listener;

import kr.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtustudio.adaptivedifficulty.configuration.MonsterConfig;
import kr.rtustudio.adaptivedifficulty.data.Difficulty;
import kr.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtustudio.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;

@SuppressWarnings("unused")
public class EntityTargetPlayer extends RSListener<AdaptiveDifficulty> {

    private final DifficultyConfig difficultyConfig;
    private final MonsterConfig monsterConfig;
    private final StatusManager manager;

    public EntityTargetPlayer(AdaptiveDifficulty plugin) {
        super(plugin);
        this.difficultyConfig = plugin.getConfiguration(DifficultyConfig.class);
        this.monsterConfig = plugin.getConfiguration(MonsterConfig.class);
        this.manager = plugin.getStatusManager();
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e) {
        if (e.getTarget() instanceof Player player) {
            Difficulty difficulty = difficultyConfig.get(manager.get(player.getUniqueId()));
            if (difficulty == null) return;
            if (difficulty.monster().attackPlayer())
                if (e.getReason() == EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY) return;
            if (difficulty.monster().ignorePlayer()) {
                if (monsterConfig.getMobs().contains(e.getEntityType())) {
                    e.setCancelled(true);
                    e.setTarget(null);
                }
            }
        }
    }

}
