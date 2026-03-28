package kr.rtustudio.adaptivedifficulty.handler;

import kr.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtustudio.framework.bukkit.api.listener.RSListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("unused")
public class PlayerJoinQuit extends RSListener<AdaptiveDifficulty> {

    private final StatusManager manager;

    public PlayerJoinQuit(AdaptiveDifficulty plugin) {
        super(plugin);
        this.manager = plugin.getStatusManager();
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        manager.addPlayer(e.getPlayer().getUniqueId());
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        manager.removePlayer(e.getPlayer().getUniqueId());
    }

}
