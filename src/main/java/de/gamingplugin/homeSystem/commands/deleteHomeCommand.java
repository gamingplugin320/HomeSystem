package de.gamingplugin.homeSystem.commands;

import de.gamingplugin.homeSystem.HomeSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class deleteHomeCommand implements CommandExecutor {

    String PREFIX = HomeSystem.PREFIX;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + "Du kannst das leider nur als Spieler!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(PREFIX + "Bitte verwende §6/deletehome §8(§7name§8)§7!");
            return true;
        }

        String home_name = args[0];

        if (!HomeSystem.getHomeManager().getHomeNames(player).contains(home_name)) {
            sender.sendMessage(PREFIX + "Dieser §6Home §7existiert nicht!");
            return true;
        }

        boolean deleted = HomeSystem.getHomeManager().deleteHome(player, home_name);

        if (deleted) {
            player.sendMessage(PREFIX + "Das Home §6" + home_name + "§7 wurde §eerfolreich §7gelöscht!");
            return true;
        } else player.sendMessage(PREFIX + "Das Home §6" + home_name + "§7 konnte §cnicht §7gelöscht werden.");


        return true;
    }
}
