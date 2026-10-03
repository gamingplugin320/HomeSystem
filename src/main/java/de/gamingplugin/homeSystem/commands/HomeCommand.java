package de.gamingplugin.homeSystem.commands;

import de.gamingplugin.homeSystem.HomeSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeCommand implements CommandExecutor {

    private String PREFIX = HomeSystem.PREFIX;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if(!(sender instanceof Player player)){
            sender.sendMessage(PREFIX + "Du kannst das leider nur als Spieler!");
            return true;
        }

        if(args.length == 0){
            player.sendMessage(PREFIX + "Bitte verwende §6/home §8(name§8)!");
            return true;
        }



        return false;
    }
}
