package com.github.ipecter.rtustudio.adaptivedifficulty.dependency;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.dependency.RSPlaceholder;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPI extends RSPlaceholder<AdaptiveDifficulty> {

    private final DifficultyConfig config;
    private final StatusManager manager;

    public PlaceholderAPI(AdaptiveDifficulty plugin) {
        super(plugin);
        this.config = plugin.getDifficultyConfig();
        this.manager = plugin.getStatusManager();
    }

    @Override
    public String request(OfflinePlayer offlinePlayer, String[] params) {
        switch (params[0]) {
            case "name" -> {
                return manager.get(offlinePlayer.getUniqueId());
            }
            case "display" -> {
                DifficultyConfig.Difficulty difficulty = config.get(manager.get(offlinePlayer.getUniqueId()));
                if (difficulty == null) return "";
                return difficulty.displayName();
            }
        }
        return "ERROR";
    }

}
