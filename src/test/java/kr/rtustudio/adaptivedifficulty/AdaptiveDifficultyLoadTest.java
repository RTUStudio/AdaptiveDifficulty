package kr.rtustudio.adaptivedifficulty;

import kr.astria.testing.BaseRSPluginTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdaptiveDifficultyLoadTest extends BaseRSPluginTest<AdaptiveDifficulty> {

    @Override
    protected AdaptiveDifficulty createPluginMock() {
        return loadPlugin(AdaptiveDifficulty.class);
    }

    @Test
    @DisplayName("서버 초기화(MockBukkit) 시 런타임 예외가 발생하지 않고 로드된다")
    void should_load_without_exceptions() {
        assertNotNull(plugin, "플러그인이 정상적으로 로드되지 않았습니다.");
        assertTrue(plugin.isEnabled(), "플러그인이 비활성화된 상태입니다.");
    }

    @Test
    @DisplayName("명령어(/ad)가 정상적으로 등록되어 있다")
    void should_register_command() {
        verifyCommand("ad");
    }

    @Test
    @DisplayName("기본 명령어 런타임 예외 검증 (MockBukkit E2E)")
    void should_execute_command_without_exception() {
        var player = safeAddPlayer();
        if (player == null) return;
        try {
            player.performCommand("ad");
            server.getScheduler().performTicks(100L); 
        } catch (Exception e) {
            org.junit.jupiter.api.Assertions.fail("명령어 실행 중 예외 발생: " + e.getMessage());
        }
    }
}
