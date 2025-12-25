package kr.rtustudio.adaptivedifficulty.data;

import kr.rtustudio.configurate.objectmapping.ConfigSerializable;
import kr.rtustudio.configurate.objectmapping.meta.Required;

@ConfigSerializable
@SuppressWarnings({"unused"})
public record Difficulty(@Required String displayName, Difficulty.Player player, Difficulty.Damage damage,
                         Difficulty.Monster monster) {

    public Difficulty {
        if (player == null) player = Player.DEFAULT;
        if (damage == null) damage = Damage.DEFAULT;
        if (monster == null) monster = Monster.DEFAULT;
    }

    @ConfigSerializable
    public record Player(@Required Player.Hardcore hardcore, boolean loseHunger, boolean harmfulEffect,
                         double experience) {

        public static final Player DEFAULT = new Player(Hardcore.DEFAULT, true, true, 1.0);

        public Player {
            if (experience < 0) experience = 0;
        }

        @ConfigSerializable
        public record Hardcore(boolean keepExperience, boolean keepInventory) {

            public static final Hardcore DEFAULT = new Hardcore(false, false);

        }
    }

    @ConfigSerializable
    public record Damage(Damage.Multiplier multiplier, boolean explosion) {

        public static final Damage DEFAULT = new Damage(Multiplier.DEFAULT, true);

        public Damage {
            if (multiplier == null) multiplier = Multiplier.DEFAULT;
        }

        @ConfigSerializable
        public record Multiplier(double pvp, double pve, double fall, double fire, double suffocation,
                                 double drowning) {

            public static final Multiplier DEFAULT = new Multiplier(1.0, 1.0, 1.0, 1.0, 1.0, 1.0);

            public Multiplier {
                if (pvp < 0) pvp = 1.0;
                if (pve < 0) pve = 1.0;
                if (fall < 0) fall = 1.0;
                if (fire < 0) fire = 1.0;
                if (suffocation < 0) suffocation = 1.0;
                if (drowning < 0) drowning = 1.0;
            }

        }
    }

    @ConfigSerializable
    public record Monster(Boolean ignorePlayer, Boolean attackPlayer) {

        public static final Monster DEFAULT = new Monster(false, true);

    }
}
