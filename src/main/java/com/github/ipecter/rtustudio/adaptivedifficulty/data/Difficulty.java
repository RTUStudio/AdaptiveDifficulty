package com.github.ipecter.rtustudio.adaptivedifficulty.data;

import kr.rtuserver.framework.bukkit.api.configuration.ConfigurationPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@SuppressWarnings({"unused", "InnerClassMayBeStatic", "FieldMayBeFinal"})
public class Difficulty extends ConfigurationPart {

    private String displayName = "Example DisplayName";
    private Player player;
    private Damage damage;
    private Monster monster;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class Player extends ConfigurationPart {

        private Hardcore hardcore;
        private boolean loseHunger = true;
        private boolean harmfulEffect = true;
        private double experience = 1.0;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor(staticName = "of")
        public static class Hardcore extends ConfigurationPart {

            private boolean keepExperience = false;
            private boolean keepInventory = false;

        }

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class Damage extends ConfigurationPart {

        private Multiplier multiplier;
        private boolean explosion = true;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor(staticName = "of")
        public static class Multiplier extends ConfigurationPart {

            private double pvp = 1.0;
            private double pve = 1.0;
            private double fall = 1.0;
            private double fire = 1.0;
            private double suffocation = 1.0;
            private double drowning = 1.0;

        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class Monster extends ConfigurationPart {

        private boolean ignorePlayer = false;
        private boolean attackPlayer = true;

    }

}
