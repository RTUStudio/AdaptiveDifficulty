package kr.rtustudio.adaptivedifficulty.data;

import kr.rtustudio.configurate.objectmapping.ConfigSerializable;
import kr.rtustudio.configurate.objectmapping.meta.Required;

@ConfigSerializable
public record Icon(String difficulty, String description, @Required String item) {
}