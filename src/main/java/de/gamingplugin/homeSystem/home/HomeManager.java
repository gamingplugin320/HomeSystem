package de.gamingplugin.homeSystem.home;

import de.gamingplugin.homeSystem.HomeSystem;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class HomeManager {


    public boolean createHome(Player player, String home_name) {

        final String uuid = player.getUniqueId().toString();

        String world_name = player.getWorld().getName();

        final double x_position = player.getLocation().getX();
        final double y_position = player.getLocation().getY();
        final double z_position = player.getLocation().getZ();

        final float yaw_position = player.getLocation().getYaw();
        final float pitch_position = player.getLocation().getPitch();

        try {

            HomeSystem.getInstance().getMySQLManager().executeUpdate("INSERT INTO homes" +
                            " (uuid," +
                            " home_name," +
                            " world_name," +
                            " x_position," +
                            " y_position," +
                            " z_position," +
                            " yaw_position," +
                            " pitch_position)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                    , uuid, home_name, world_name, x_position, y_position, z_position, yaw_position, pitch_position);

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }

    }


}
