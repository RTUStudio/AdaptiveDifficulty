package kr.rtustudio.adaptivedifficulty.data;

import kr.rtustudio.configurate.objectmapping.ConfigSerializable;
import kr.rtustudio.configurate.objectmapping.meta.Required;

@ConfigSerializable
public record Icon(@Required String difficulty, @Required String item, int slot) {
}