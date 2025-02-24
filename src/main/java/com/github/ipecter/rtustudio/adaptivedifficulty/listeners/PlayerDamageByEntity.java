package com.github.ipecter.rtustudio.adaptivedifficulty.listeners;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class PlayerDamageByEntity extends RSListener<AdaptiveDifficulty> {

    private final DifficultyConfig config;
    private final StatusManager manager;
    // EntityType to String for version independent
    private final List<String> explosions = List.of(
            "CREEPER",
            "ENDER_CRYSTAL",
            "PRIMED_TNT",
            "MINECART_TNT"
    );

    public PlayerDamageByEntity(AdaptiveDifficulty plugin) {
        super(plugin);
        this.config = plugin.getDifficultyConfig();
        this.manager = plugin.getStatusManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player player) {
            DifficultyConfig.Difficulty difficulty = config.get(manager.get(player.getUniqueId()));
            if (difficulty == null) return;
            if (!difficulty.damage().explosion()) {
                for (String type : explosions) {
                    if (type.equalsIgnoreCase(e.getDamager().getType().name())) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
            if (e.getDamager() instanceof Projectile projectile) {
                if (projectile.getShooter() instanceof Player) {
                    e.setDamage(e.getDamage() * difficulty.damage().multiplier().pvp());
                } else e.setDamage(e.getDamage() * difficulty.damage().multiplier().pve());
            } else if (e.getDamager() instanceof Player) {
                e.setDamage(e.getDamage() * difficulty.damage().multiplier().pvp());
            } else {
                e.setDamage(e.getDamage() * difficulty.damage().multiplier().pve());
            }
        }
    }

}
