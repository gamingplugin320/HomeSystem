package de.gamingplugin.homeSystem.commands;

import de.gamingplugin.homeSystem.HomeSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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


        if (args[0].length() > 32) {
            player.sendMessage(PREFIX + "Dein Home darf maximal §632§7 Zeichen lang sein.");
            return true;
        }

        String home_name = args[0];
        if (HomeSystem.getHomeManager().getHomeNames(player).contains(home_name)) {
            player.sendMessage(PREFIX + "Dieser §6Home §7exisiert schon!");
            return true;
        }


        int home_count = HomeSystem.getHomeManager().getHomeNames(player).size();


        if (home_count < 1) {

            boolean created = HomeSystem.getHomeManager().createHome(player, home_name);
            if (created) {

                player.sendMessage(PREFIX + "Der Home §6" + home_name + "§7 wurde §aerfolgreich§7 gesetzt!");
            } else
                player.sendMessage(PREFIX + "Der Home §6" + home_name + "§7 konnte leider §cnicht§7 gesetzt werden!");


        } else if (player.hasPermission(HomeSystem.getInstance().getConfig().getString("HOMES.2")) && home_count < 2) {

            boolean created = HomeSystem.getHomeManager().createHome(player, home_name);
            if (created) {

                player.sendMessage(PREFIX + "Der Home §6" + home_name + "§7 wurde §aerfolgreich§7 gesetzt!");
            } else
                player.sendMessage(PREFIX + "Der Home §6" + home_name + "§7 konnte leider §cnicht§7 gesetzt werden!");


        } else if (player.hasPermission(HomeSystem.getInstance().getConfig().getString("HOMES.3")) && home_count < 3) {

            boolean created = HomeSystem.getHomeManager().createHome(player, home_name);
            if (created) {

                player.sendMessage(PREFIX + "Der Home §6" + home_name + "§7 wurde §aerfolgreich§7 gesetzt!");
            } else
                player.sendMessage(PREFIX + "Der Home §6" + home_name + "§7 konnte leider §cnicht§7 gesetzt werden!");

        } else player.sendMessage(PREFIX + "Du hast deine maximale §6Anzahl §7von Homes erreicht.");


        return true;
    }
}
