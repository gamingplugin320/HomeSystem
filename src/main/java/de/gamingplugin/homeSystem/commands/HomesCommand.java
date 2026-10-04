package de.gamingplugin.homeSystem.commands;

import de.gamingplugin.homeSystem.HomeSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomesCommand implements CommandExecutor {

    String PREFIX = HomeSystem.PREFIX;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + "Du kannst das leider nur als Spieler!");
            return true;
        }

        if (args.length != 0) {
            sender.sendMessage(PREFIX + "Bitte verwende §6/homes§7!");
            return true;
        }

        List<String> homes = HomeSystem.getHomeManager().getHomeNames(player);

        if(homes.isEmpty()){
            player.sendMessage(PREFIX + "Du hast aktuell §ckeine §7Homes!");
        }else{
            player.sendMessage(PREFIX + "Homes: §6" + String.join("§7, §6", homes));
        }

        return true;
    }
}
