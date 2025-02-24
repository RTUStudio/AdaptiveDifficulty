package com.github.ipecter.rtustudio.adaptivedifficulty.dependency;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.dependencies.RSPlaceholder;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPI extends RSPlaceholder<AdaptiveDifficulty> {

    private final StatusManager manager;

    public PlaceholderAPI(AdaptiveDifficulty plugin) {
        super(plugin);
        this.manager = plugin.getStatusManager();
    }

    @Override
    public String request(OfflinePlayer offlinePlayer, String[] params) {
        return manager.get(offlinePlayer.getUniqueId());
    }
}
