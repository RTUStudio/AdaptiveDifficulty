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
            "peaceful", Difficulty.of(
                    "<aqua>Peaceful</aqua>",
                    Difficulty.Player.of(
                            Difficulty.Player.Hardcore.of(true, true),
                            false, false, 1.0
                    ),
                    Difficulty.Damage.of(
                            Difficulty.Damage.Multiplier.of(
                                    1.0, 0.1, 0.1, 0.1, 0.1, 0.1
                            ), false
                    ),
                    Difficulty.Monster.of(true, true)
            ),
            "easy", Difficulty.of(
                    "<green>Easy</green>",
                    Difficulty.Player.of(
                            Difficulty.Player.Hardcore.of(true, true),
                            true, true, 1.0
                    ),
                    Difficulty.Damage.of(
                            Difficulty.Damage.Multiplier.of(
                                    1.0, 0.25, 0.25, 0.25, 0.25, 0.25
                            ), true
                    ),
                    Difficulty.Monster.of(false, true)
            ),
            "normal", Difficulty.of(
                    "<yellow>Normal</yellow>",
                    Difficulty.Player.of(
                            Difficulty.Player.Hardcore.of(true, true),
                            true, true, 1.0
                    ),
                    Difficulty.Damage.of(
                            Difficulty.Damage.Multiplier.of(
                                    1.0, 0.5, 0.5, 0.5, 0.5, 0.5
                            ), true
                    ),
                    Difficulty.Monster.of(false, true)
            ),
            "hard", Difficulty.of(
                    "<red>Hard</red>",
                    Difficulty.Player.of(
                            Difficulty.Player.Hardcore.of(true, true),
                            true, true, 1.0
                    ),
                    Difficulty.Damage.of(
                            Difficulty.Damage.Multiplier.of(
                                    1.0, 1.0, 1.0, 1.0, 1.0, 1.0
                            ), true
                    ),
                    Difficulty.Monster.of(false, true)
            ),
            "hardcore", Difficulty.of(
                    "<dark_red>Hardcore</dark_red>",
                    Difficulty.Player.of(
                            Difficulty.Player.Hardcore.of(false, false),
                            true, true, 1.0
                    ),
                    Difficulty.Damage.of(
                            Difficulty.Damage.Multiplier.of(
                                    1.0, 1.0, 1.0, 1.0, 1.0, 1.0
                            ), true
                    ),
                    Difficulty.Monster.of(false, true)
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
