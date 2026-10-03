package de.gamingplugin.homeSystem;

import de.gamingplugin.homeSystem.commands.HomeCommand;
import de.gamingplugin.homeSystem.commands.setHomeCommand;
import de.gamingplugin.homeSystem.home.HomeManager;
import de.gamingplugin.homeSystem.mysql.MySQLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public final class HomeSystem extends JavaPlugin {

    public static final String PREFIX = "§6Home §8| §7",
            NO_PERMS = PREFIX + "Du hast keine Berechtigung!";

    private static HomeSystem instance;
    private static HomeManager homeManager = new HomeManager();


    MySQLManager mySQLManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        defaultConfigQuestions();

        String HOST = getConfig().getString("MySQL.HOST");
        int PORT = getConfig().getInt("MySQL.PORT");
        String DATABASE = getConfig().getString("MySQL.DATABASE");
        String USERNAME = getConfig().getString("MySQL.USERNAME");
        String PASSWORD = getConfig().getString("MySQL.PASSWORD");

        mySQLManager = new MySQLManager("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);


        try {
            mySQLManager.connect();
            mySQLManager.createTable();
            getLogger().info("MySQL-Connection successfully!");
        } catch (SQLException exception) {
            exception.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().info("MySQL-Connection failed!");
        }

        getCommand("sethome").setExecutor(new setHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());



        getLogger().info("Plugin started!");

    }

    @Override
    public void onDisable() {


    }


    private void defaultConfigQuestions() {

        if (!getConfig().contains("MySQL.HOST")) {
            getConfig().set("MySQL.HOST", "Host-Name");
        }
        if (!getConfig().contains("MySQL.PORT")) {
            getConfig().set("MySQL.PORT", 3306);
        }
        if (!getConfig().contains("MySQL.DATABASE")) {
            getConfig().set("MySQL.DATABASE", "Database-Name");
        }
        if (!getConfig().contains("MySQL.USERNAME")) {
            getConfig().set("MySQL.USERNAME", "Username");
        }
        if (!getConfig().contains("MySQL.PASSWORD")) {
            getConfig().set("MySQL.PASSWORD", "Password");
        }
        if (!getConfig().contains("HOMES.1")) {
            getConfig().set("HOMES.1", "homesystem.homes.1");
        }
        if (!getConfig().contains("HOMES.2")) {
            getConfig().set("HOMES.2", "homesystem.homes.2");
        }
        if (!getConfig().contains("HOMES.3")) {
            getConfig().set("HOMES.3", "homesystem.homes.3");
        }
        if (!getConfig().contains("COUNTDOWN.length")) {
            getConfig().set("COUNTDOWN.length", 3);
        }
        saveConfig();
    }


    public MySQLManager getMySQLManager() {
        return mySQLManager;
    }

    public static HomeSystem getInstance() {
        return instance;
    }

    public static HomeManager getHomeManager() {
        return homeManager;
    }
}
