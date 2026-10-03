package de.gamingplugin.homeSystem.commands;

import de.gamingplugin.homeSystem.HomeSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class setHomeCommand implements CommandExecutor {

    private String PREFIX = HomeSystem.PREFIX;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + "Du kannst das leider nur als Spieler!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(PREFIX + "Bitte verwende §6/sethome §8(§7name§8)§7!");
            return true;
        }

        String home_name = args[0];


        boolean created = HomeSystem.getHomeManager().createHome(player, home_name);

        if (created) {
            player.sendMessage(PREFIX + "Du hast den Home §6" + home_name + "§a erfolgreich §7gesetzt");
        } else
            player.sendMessage(PREFIX + "Der Home §6" + home_name + "§7 konnte leider §cnicht§7 gesetzt werden!");


        return false;
    }
}
