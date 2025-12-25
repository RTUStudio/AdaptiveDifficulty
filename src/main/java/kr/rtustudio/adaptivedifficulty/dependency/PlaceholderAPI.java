package kr.rtustudio.adaptivedifficulty.dependency;

import kr.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtustudio.adaptivedifficulty.data.Difficulty;
import kr.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtustudio.framework.bukkit.api.integration.wrapper.PlaceholderWrapper;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPI extends PlaceholderWrapper<AdaptiveDifficulty> {

    private final DifficultyConfig config;
    private final StatusManager manager;

    public PlaceholderAPI(AdaptiveDifficulty plugin) {
        super(plugin);
        this.config = plugin.getConfiguration(DifficultyConfig.class);
        this.manager = plugin.getStatusManager();
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String[] params) {
        switch (params[0]) {
            case "name" -> {
                return manager.get(offlinePlayer.getUniqueId());
            }
            case "display" -> {
                Difficulty difficulty = config.get(manager.get(offlinePlayer.getUniqueId()));
                if (difficulty == null) return "";
                return difficulty.displayName();
            }
        }
        return "ERROR";
    }
}
