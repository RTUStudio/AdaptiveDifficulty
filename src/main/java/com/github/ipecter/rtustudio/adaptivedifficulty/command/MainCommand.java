package com.github.ipecter.rtustudio.adaptivedifficulty.command;

import com.github.ipecter.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.MenuConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.configuration.MonsterConfig;
import com.github.ipecter.rtustudio.adaptivedifficulty.data.Difficulty;
import com.github.ipecter.rtustudio.adaptivedifficulty.inventory.DifficultyMenu;
import com.github.ipecter.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.configuration.RSConfiguration;
import kr.rtuserver.framework.bukkit.api.configuration.internal.translation.message.MessageTranslation;
import org.bukkit.entity.Player;

import java.util.List;

public class MainCommand extends RSCommand<AdaptiveDifficulty> {

    private final DifficultyConfig difficultyConfig;
    private final StatusManager manager;

    public MainCommand(AdaptiveDifficulty plugin) {
        super(plugin, "ad");
        this.difficultyConfig = plugin.getConfiguration(DifficultyConfig.class);
        this.manager = plugin.getStatusManager();
    }

    @Override
    public boolean execute(RSCommandData data) {
        Player player = player();
        if (player == null) {
            chat().announce(message().getCommon(sender(), MessageTranslation.ONLY_PLAYER));
            return true;
        }
        if (data.length(1)) {
            String difficultyStr = data.args(0);
            Difficulty difficulty = difficultyConfig.get(difficultyStr);
            if (difficulty != null) {
                manager.set(player.getUniqueId(), difficultyStr);
                String change = message().get(player, "change");
                change = change.replace("[name]", difficulty.displayName());
                chat().announce(change);
            } else chat().announce(message().get(player, "not-found.difficulty"));
        } else {
            DifficultyMenu menu = new DifficultyMenu(getPlugin(), player);
            player.openInventory(menu.getInventory());
        }
        return true;
    }

    @Override
    public List<String> tabComplete(RSCommandData data) {
        return difficultyConfig.getNames();
    }

    @Override
    public void reload(RSCommandData data) {
        getPlugin().reloadConfiguration(DifficultyConfig.class);
        getPlugin().reloadConfiguration(MenuConfig.class);
        getPlugin().reloadConfiguration(MonsterConfig.class);
    }

}
