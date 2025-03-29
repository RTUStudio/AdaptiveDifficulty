package com.github.ipecter.rtustudio.adaptivedifficulty.command;

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
        Player player = player();
        if (player == null) {
            chat.announce(audience(), common.getMessage(sender(), "onlyPlayer"));
            return true;
        }
        if (data.length(1)) {
            DifficultyConfig.Difficulty difficulty = difficultyConfig.get(data.args(0));
            if (difficulty != null) {
                manager.set(player.getUniqueId(), difficulty.name());
                String change = message.get(player, "change");
                change = change.replace("[name]", difficulty.displayName());
                chat.announce(audience(), change);
            } else chat.announce(audience(), message.get(player, "notFound.difficulty"));
        } else {
            player.openInventory(new DifficultyMenu(getPlugin(), player).getInventory());
        }
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
