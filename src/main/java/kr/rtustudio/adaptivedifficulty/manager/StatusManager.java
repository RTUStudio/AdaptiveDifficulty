package kr.rtustudio.adaptivedifficulty.manager;

import kr.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtustudio.storage.JSON;
import kr.rtustudio.storage.Storage;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;
import java.util.UUID;

public class StatusManager {

    private final AdaptiveDifficulty plugin;
    private final DifficultyConfig difficultyConfig;
    private final Map<UUID, String> map = new Object2ObjectOpenHashMap<>();

    public StatusManager(AdaptiveDifficulty plugin) {
        this.plugin = plugin;
        this.difficultyConfig = plugin.getConfiguration(DifficultyConfig.class);
    }

    public String get(UUID uuid) {
        return map.getOrDefault(uuid, difficultyConfig.getDefaultDifficulty());
    }

    public void addPlayer(UUID uuid) {
        String defaultDifficulty = difficultyConfig.getDefaultDifficulty();
        kr.rtustudio.storage.Storage storage = plugin.getStorage("Difficulty");
        storage.get(kr.rtustudio.storage.JSON.of("uuid", uuid.toString())).thenAccept(result -> {
            if (result == null || result.isEmpty()) {
                storage.add(kr.rtustudio.storage.JSON.of("uuid", uuid.toString()).append("value", defaultDifficulty));
                map.put(uuid, defaultDifficulty);
            } else map.put(uuid, result.getFirst().get("value").getAsString());
        });
    }

    public void removePlayer(UUID uuid) {
        map.remove(uuid);
    }

    public void set(UUID uuid, String difficulty) {
        kr.rtustudio.storage.Storage storage = plugin.getStorage("Difficulty");
        storage.set(kr.rtustudio.storage.JSON.of("uuid", uuid.toString()), kr.rtustudio.storage.JSON.of("value", difficulty));
        map.put(uuid, difficulty);
    }

}
