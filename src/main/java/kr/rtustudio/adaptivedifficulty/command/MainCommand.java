package kr.rtustudio.adaptivedifficulty.command;

import kr.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtustudio.adaptivedifficulty.configuration.MenuConfig;
import kr.rtustudio.adaptivedifficulty.configuration.MonsterConfig;
import kr.rtustudio.adaptivedifficulty.data.Difficulty;
import kr.rtustudio.adaptivedifficulty.inventory.DifficultyMenu;
import kr.rtustudio.adaptivedifficulty.manager.StatusManager;
import kr.rtustudio.framework.bukkit.api.command.CommandArgs;
import kr.rtustudio.framework.bukkit.api.command.RSCommand;
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
    protected Result execute(CommandArgs data) {
        Player player = player();
        if (player == null) return Result.ONLY_PLAYER;

        if (data.length(1)) {
            String difficultyStr = data.get(0);
            Difficulty difficulty = difficultyConfig.get(difficultyStr);
            if (difficulty != null) {
                manager.set(player.getUniqueId(), difficultyStr);
                String change = message.get(player, "menu.change")
                        .replace("{name}", difficulty.displayName());
                notifier.announce(player, change);
            } else {
                notifier.announce(player, message.get(player, "not-found.difficulty"));
            }
        } else {
            DifficultyMenu menu = new DifficultyMenu(plugin, player);
            player.openInventory(menu.getInventory());
        }
        return Result.SUCCESS;
    }

    @Override
    protected List<String> tabComplete(CommandArgs data) {
        if (data.length(1)) return difficultyConfig.getNames();
        return List.of();
    }

    @Override
    protected void reload(CommandArgs data) {
        plugin.reloadConfiguration(DifficultyConfig.class);
        plugin.reloadConfiguration(MenuConfig.class);
        plugin.reloadConfiguration(MonsterConfig.class);
    }
}
