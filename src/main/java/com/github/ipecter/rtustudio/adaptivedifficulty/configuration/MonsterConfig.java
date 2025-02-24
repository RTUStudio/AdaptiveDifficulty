package com.github.ipecter.rtustudio.adaptivedifficulty.configuration;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MonsterConfig extends RSConfiguration<AdaptiveDifficulty> {

    @Getter
    private final List<String> mobs = new ArrayList<>();

    public MonsterConfig(AdaptiveDifficulty plugin) {
        super(plugin, "Monster.yml", null);
        setup(this);
    }

    private void init() {
        mobs.clear();
        mobs.addAll(getStringList("monsters", List.of()));
    }
}
