package com.github.ipecter.rtustudio.adaptivedifficulty.data;

import kr.rtuserver.framework.bukkit.api.configuration.ConfigurationPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings({"unused", "InnerClassMayBeStatic", "FieldMayBeFinal"})
public class Icon extends ConfigurationPart {

    private String difficulty;
    private String description;
    private String item;

}