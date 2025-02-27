package com.github.ipecter.rtustudio.adaptivedifficulty.configuration;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import kr.rtuserver.yaml.configuration.ConfigurationSection;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DifficultyConfig extends RSConfiguration<AdaptiveDifficulty> {

    private final Map<String, Difficulty> map = new HashMap<>();
    @Getter
    private String defaultDifficulty = "easy";

    public DifficultyConfig(AdaptiveDifficulty plugin) {
        super(plugin, "Difficulty.yml", null);
        setup(this);
    }

    @NotNull
    public List<String> getNames() {
        return new ArrayList<>(map.keySet());
    }

    public boolean exists(String id) {
        return map.containsKey(id);
    }

    @Nullable
    public Difficulty get(String id) {
        return map.get(id);
    }

    private void init() {
        map.clear();
        defaultDifficulty = getString("default", defaultDifficulty, """
                기본 난이도
                Default difficulty""");
        for (String name : getConfig().getKeys(false)) {
            if (name.equals("default")) continue;
            ConfigurationSection section = getConfigurationSection(name);
            // Difficulty
            String displayName = section.getString("displayName", name);
            // Player
            boolean loseHunger = section.getBoolean("player.loseHunger", true);
            boolean harmfulEffect = section.getBoolean("player.harmfulEffect", true);
            double experience = section.getDouble("player.experience", 1.0);
            // Player/Hardcore
            boolean hardcoreKeepExperience = section.getBoolean("player.hardcore.keepExperience", true);
            boolean hardcoreKeepInventory = section.getBoolean("player.hardcore.keepInventory", true);
            // Multiplier
            // Multiplier/Damage
            double pvp = section.getDouble("damage.multiplier.pvp", 1.0);
            double pve = section.getDouble("damage.multiplier.pve", 1.0);
            double fall = section.getDouble("damage.multiplier.fall", 1.0);
            double fire = section.getDouble("damage.multiplier.fire", 1.0);
            double suffocation = section.getDouble("damage.multiplier.suffocation", 1.0);
            double drowning = section.getDouble("damage.multiplier.drowning", 1.0);
            boolean explosion = section.getBoolean("damage.multiplier.explosion", true);
            // Monster
            boolean ignorePlayer = section.getBoolean("monster.ignorePlayer", false);
            boolean attackPlayer = section.getBoolean("monster.attackPlayer", true);

            Difficulty.Player.Hardcore hardcore = new Difficulty.Player.Hardcore(hardcoreKeepExperience, hardcoreKeepInventory);
            Difficulty.Player player = new Difficulty.Player(hardcore, loseHunger, harmfulEffect, experience);

            Difficulty.Damage.Multiplier multiplier = new Difficulty.Damage.Multiplier(pvp, pve, fall, fire, suffocation, drowning);
            Difficulty.Damage damage = new Difficulty.Damage(multiplier, explosion);

            Difficulty.Monster monster = new Difficulty.Monster(ignorePlayer, attackPlayer);

            Difficulty difficulty = new Difficulty(name, displayName, player, damage, monster);

            map.put(name, difficulty);
        }
    }

    public record Difficulty(String name, String displayName, Player player, Damage damage, Monster monster) {
        public record Player(Hardcore hardcore, boolean loseHunger, boolean harmfulEffect, double experience) {
            public record Hardcore(boolean keepExperience, boolean keepInventory) {
            }
        }

        public record Damage(Multiplier multiplier, boolean explosion) {
            public record Multiplier(double pvp, double pve, double fall, double fire, double suffocation,
                                     double drowning) {
            }
        }

        public record Monster(boolean ignorePlayer, boolean attackPlayer) {
        }
    }
}
