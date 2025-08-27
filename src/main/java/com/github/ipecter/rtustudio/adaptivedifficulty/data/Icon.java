package com.github.ipecter.rtustudio.adaptivedifficulty.data;

import kr.rtuserver.configurate.objectmapping.ConfigSerializable;
import kr.rtuserver.configurate.objectmapping.meta.Required;

@ConfigSerializable
public record Icon(String difficulty, String description, @Required String item) {
}