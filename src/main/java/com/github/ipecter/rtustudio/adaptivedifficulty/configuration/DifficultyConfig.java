package com.github.ipecter.rtustudio.adaptivedifficulty.configuration;

import com.github.ipecter.rtustudio.adaptivedifficulty.data.Difficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.util.LinkedMap;
import kr.rtuserver.framework.bukkit.api.configuration.ConfigurationPart;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@SuppressWarnings({"unused", "InnerClassMayBeStatic", "FieldMayBeFinal"})
public class DifficultyConfig extends ConfigurationPart {

    @Getter
    private String defaultDifficulty = "easy";

    private Map<String, Difficulty> difficulties = LinkedMap.of(
            "peaceful", new Difficulty(
                    "<aqua>Peaceful</aqua>",
                    new Difficulty.Player(
                            new Difficulty.Player.Hardcore(true, true),
                            false, false, 1.0
                    ),
                    new Difficulty.Damage(
                            new Difficulty.Damage.Multiplier(
                                    1.0, 0.1, 0.1, 0.1, 0.1, 0.1
                            ), false
                    ),
                    new Difficulty.Monster(true, true)
            ),
            "easy", new Difficulty(
                    "<green>Easy</green>",
                    new Difficulty.Player(
                            new Difficulty.Player.Hardcore(true, true),
                            true, true, 1.0
                    ),
                    new Difficulty.Damage(
                            new Difficulty.Damage.Multiplier(
                                    1.0, 0.25, 0.25, 0.25, 0.25, 0.25
                            ), true
                    ),
                    new Difficulty.Monster(false, true)
            ),
            "normal", new Difficulty(
                    "<yellow>Normal</yellow>",
                    new Difficulty.Player(
                            new Difficulty.Player.Hardcore(true, true),
                            true, true, 1.0
                    ),
                    new Difficulty.Damage(
                            new Difficulty.Damage.Multiplier(
                                    1.0, 0.5, 0.5, 0.5, 0.5, 0.5
                            ), true
                    ),
                    new Difficulty.Monster(false, true)
            ),
            "hard", new Difficulty(
                    "<red>Hard</red>",
                    new Difficulty.Player(
                            new Difficulty.Player.Hardcore(true, true),
                            true, true, 1.0
                    ),
                    new Difficulty.Damage(
                            new Difficulty.Damage.Multiplier(
                                    1.0, 1.0, 1.0, 1.0, 1.0, 1.0
                            ), true
                    ),
                    new Difficulty.Monster(false, true)
            ),
            "hardcore", new Difficulty(
                    "<dark_red>Hardcore</dark_red>",
                    new Difficulty.Player(
                            new Difficulty.Player.Hardcore(false, false),
                            true, true, 1.0
                    ),
                    new Difficulty.Damage(
                            new Difficulty.Damage.Multiplier(
                                    1.0, 1.0, 1.0, 1.0, 1.0, 1.0
                            ), true
                    ),
                    new Difficulty.Monster(false, true)
            )
    );

    @NotNull
    public List<String> getNames() {
        return new ArrayList<>(difficulties.keySet());
    }

    public boolean exists(String id) {
        return difficulties.containsKey(id);
    }

    @Nullable
    public Difficulty get(String id) {
        return difficulties.get(id);
    }

}
