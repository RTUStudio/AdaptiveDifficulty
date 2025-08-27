package com.github.ipecter.rtustudio.adaptivedifficulty.manager;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtuserver.framework.bukkit.api.platform.JSON;
import kr.rtuserver.framework.bukkit.api.storage.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatusManager {

    private final AdaptiveDifficulty plugin;
    private final DifficultyConfig difficultyConfig;
    private final Map<UUID, String> map = new HashMap<>();

    public StatusManager(AdaptiveDifficulty plugin) {
        this.plugin = plugin;
        this.difficultyConfig = plugin.getConfiguration(DifficultyConfig.class);
    }

    public String get(UUID uuid) {
        return map.getOrDefault(uuid, difficultyConfig.getDefaultDifficulty());
    }

    public void addPlayer(UUID uuid) {
        String defaultDifficulty = difficultyConfig.getDefaultDifficulty();
        Storage storage = plugin.getStorage();
        storage.get("Difficulty", JSON.of("uuid", uuid.toString())).thenAccept(result -> {
            if (result == null || result.isEmpty()) {
                storage.add("Difficulty", JSON.of("uuid", uuid.toString()).append("value", defaultDifficulty).get());
                map.put(uuid, defaultDifficulty);
            } else map.put(uuid, result.getFirst().get("value").getAsString());
        });
    }

    public void removePlayer(UUID uuid) {
        map.remove(uuid);
    }

    public void set(UUID uuid, String difficulty) {
        Storage storage = plugin.getStorage();
        storage.set("Difficulty", JSON.of("uuid", uuid.toString()), JSON.of("value", difficulty));
        map.put(uuid, difficulty);
    }

}
