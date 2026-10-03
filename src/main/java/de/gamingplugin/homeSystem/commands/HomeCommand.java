package de.gamingplugin.homeSystem.commands;

import de.gamingplugin.homeSystem.HomeSystem;
import de.gamingplugin.homeSystem.home.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

public class HomeCommand implements CommandExecutor {

    private String PREFIX = HomeSystem.PREFIX;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + "Du kannst das leider nur als Spieler!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(PREFIX + "Bitte verwende §6/home §8(§7name§8)!");
            return true;
        }

        String home_name = args[0];

        if (!HomeSystem.getHomeManager().getHomeNames(player).contains(home_name)) {
            player.sendMessage(PREFIX + "Dieser §6Home§7 existiert noch nicht.");
            return true;
        }


        int coutdown = HomeSystem.getInstance().getConfig().getInt("COUNTDOWN.length");

        if (coutdown < 0) {
            player.sendMessage(PREFIX + "Die Cooldown-länge muss großer als 0 sein!");
            System.out.print("Die Cooldown-länge muss großer als 0 sein!");
            return true;
        }

        AtomicInteger seconds = new AtomicInteger(coutdown);

        Bukkit.getScheduler().runTaskTimer(HomeSystem.getInstance(), task -> {


            if(seconds.get() == 0){
                player.teleport(HomeSystem.getHomeManager().getHome(player, home_name));
                task.cancel();
                return;
            }

            player.sendMessage(PREFIX + "Du wirst in §6" + seconds + "§7 teleportiert..");

            seconds.decrementAndGet();
        }, 0, 20L);


        return false;
    }
}
