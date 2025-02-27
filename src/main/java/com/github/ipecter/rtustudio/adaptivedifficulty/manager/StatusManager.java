package com.github.ipecter.rtustudio.adaptivedifficulty.manager;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtuserver.framework.bukkit.api.storage.Storage;
import kr.rtuserver.framework.bukkit.api.utility.platform.JSON;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class StatusManager {

    private final AdaptiveDifficulty plugin;
    private final Map<UUID, String> map = new HashMap<>();

    public String get(UUID uuid) {
        return map.getOrDefault(uuid, plugin.getDifficultyConfig().getDefaultDifficulty());
    }

    public void addPlayer(UUID uuid) {
        String defaultDifficulty = plugin.getDifficultyConfig().getDefaultDifficulty();
        Storage storage = plugin.getStorage();
        storage.get("Difficulty", Pair.of("uuid", uuid.toString())).thenAccept(result -> {
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
        storage.set("Difficulty", Pair.of("uuid", uuid.toString()), Pair.of("value", difficulty));
        map.put(uuid, difficulty);
    }

}
