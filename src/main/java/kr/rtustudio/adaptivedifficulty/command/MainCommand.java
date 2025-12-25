package kr.rtustudio.adaptivedifficulty.command;

import kr.rtustudio.adaptivedifficulty.AdaptiveDifficulty;
import kr.rtustudio.adaptivedifficulty.configuration.DifficultyConfig;
import kr.rtustudio.adaptivedifficulty.configuration.MenuConfig;
import kr.rtustudio.adaptivedifficulty.configuration.MonsterConfig;
import kr.rtustudio.adaptivedifficulty.data.Difficulty;
import kr.rtustudio.adaptivedifficulty.inventory.DifficultyMenu;
import kr.rtustudio.adaptivedifficulty.manager.StatusManager;
import java.util.List;
import kr.rtustudio.framework.bukkit.api.command.RSCommand;
import kr.rtustudio.framework.bukkit.api.command.RSCommandData;
import org.bukkit.entity.Player;

public class MainCommand extends RSCommand<AdaptiveDifficulty> {

    private final DifficultyConfig difficultyConfig;
    private final StatusManager manager;

    public MainCommand(AdaptiveDifficulty plugin) {
        super(plugin, "ad");
        this.difficultyConfig = plugin.getConfiguration(DifficultyConfig.class);
        this.manager = plugin.getStatusManager();
    }

    @Override
    protected Result execute(RSCommandData data) {
        Player player = player();
        if (player == null) return Result.ONLY_PLAYER;

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
        return Result.SUCCESS;
    }

    @Override
    protected List<String> tabComplete(RSCommandData data) {
        if (data.length(1)) return difficultyConfig.getNames();
        return List.of();
    }

    @Override
    protected void reload(RSCommandData data) {
        getPlugin().reloadConfiguration(DifficultyConfig.class);
        getPlugin().reloadConfiguration(MenuConfig.class);
        getPlugin().reloadConfiguration(MonsterConfig.class);
    }
}
