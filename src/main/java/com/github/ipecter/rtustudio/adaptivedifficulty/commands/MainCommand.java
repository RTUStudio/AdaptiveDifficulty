package com.github.ipecter.rtustudio.adaptivedifficulty.commands;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.MenuConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.MonsterConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.inventory.DifficultyMenu;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import org.bukkit.entity.Player;

import java.util.List;

public class MainCommand extends RSCommand<AdaptiveDifficulty> {

    private final DifficultyConfig difficultyConfig;
    private final MenuConfig menuConfig;
    private final MonsterConfig monsterConfig;
    private final StatusManager manager;

    public MainCommand(AdaptiveDifficulty plugin) {
        super(plugin, "ad");
        this.difficultyConfig = plugin.getDifficultyConfig();
        this.menuConfig = plugin.getMenuConfig();
        this.monsterConfig = plugin.getMonsterConfig();
        this.manager = plugin.getStatusManager();
    }

    @Override
    public boolean execute(RSCommandData data) {
        if (getSender() instanceof Player player) {
            if (data.length(1)) {
                DifficultyConfig.Difficulty difficulty = difficultyConfig.get(data.args(0));
                if (difficulty != null) {
                    manager.set(player.getUniqueId(), difficulty.name());
                    String message = getMessage().get(getPlayer(), "change");
                    message = message.replace("[name]", difficulty.displayName());
                    getChat().announce(getAudience(), message);
                } else getChat().announce(getAudience(), getMessage().get(getPlayer(), "notFound.difficulty"));
            } else {
                player.openInventory(new DifficultyMenu(getPlugin(), player).getInventory());
            }
        } else getChat().announce(getAudience(), getCommon().getMessage(getSender(), "onlyPlayer"));
        return true;
    }

    @Override
    public List<String> tabComplete(RSCommandData data) {
        return difficultyConfig.getNames();
    }

    @Override
    public void reload(RSCommandData data) {
        difficultyConfig.reload();
        menuConfig.reload();
        monsterConfig.reload();
    }
}
