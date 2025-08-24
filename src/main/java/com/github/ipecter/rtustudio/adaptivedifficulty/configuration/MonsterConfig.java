package com.github.ipecter.rtustudio.adaptivedifficulty.configuration;

import kr.rtuserver.framework.bukkit.api.configuration.ConfigurationPart;
import lombok.Getter;

import java.util.List;

public class MonsterConfig extends ConfigurationPart {

    @Getter
    private final List<String> mobs = List.of(
            "BLAZE",
            "BREEZE",
            "CAVE_SPIDER",
            "CREEPER",
            "CREAKING",
            "DROWNED",
            "ELDER_GUARDIAN",
            "ENDER_DRAGON",
            "ENDERMAN",
            "ENDERMITE",
            "EVOKER",
            "GHAST",
            "GUARDIAN",
            "HOGLIN",
            "HUSK",
            "ILLUSIONER",
            "MAGMA_CUBE",
            "PHANTOM",
            "PIGLIN",
            "PIGLIN_BRUTE",
            "PILLAGER",
            "SHULKER",
            "SILVERFISH",
            "SKELETON",
            "SLIME",
            "SPIDER",
            "STRAY",
            "VEX",
            "VINDICATOR",
            "WARDEN",
            "WITCH",
            "WITHER",
            "WITHER_SKELETON",
            "ZOGLIN",
            "ZOMBIE",
            "ZOMBIE_VILLAGER"
    );

}
