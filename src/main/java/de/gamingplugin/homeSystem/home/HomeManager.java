package de.gamingplugin.homeSystem.home;

import de.gamingplugin.homeSystem.HomeSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HomeManager {


    public boolean createHome(final Player player, final String home_name) {

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

    public List<String> getHomeNames(final Player player) {

        List<String> homes = new ArrayList<>();

        try {
            List<Map<String, Object>> playerHomes = HomeSystem.getInstance().getMySQLManager().executeQuery(
                    "SELECT home_name FROM homes WHERE uuid = ?", player.getUniqueId().toString());

            for (Map<String, Object> row : playerHomes) {

                String uuid = (String) row.get("uuid");
                String home_name = (String) row.get("home_name");

                homes.add(home_name);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return homes;
    }


    public Location getHome(final Player player, final String name) {

        Location location = null;

        try {
            List<Map<String, Object>> playerHomes = HomeSystem.getInstance().getMySQLManager().executeQuery(
                    "SELECT home_name, world_name, x_position, y_position, z_position," +
                            " yaw_position, pitch_position FROM homes WHERE uuid = ? AND home_name = ?"
                    , player.getUniqueId().toString(), name);

            for (Map<String, Object> row : playerHomes) {

                String worldName = (String) row.get("world_name");
                World world = Bukkit.getWorld(worldName);

                double x_position = (double) row.get("x_position");
                double y_position = (double) row.get("y_position");
                double z_position = (double) row.get("z_position");

                float yaw_position = (float) row.get("yaw_position");
                float ptich_position = (float) row.get("pitch_position");


                location = new Location(world, x_position, y_position, z_position, yaw_position, ptich_position);
                return location;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();

        }
        return location;
    }


    public boolean deleteHome(final Player player, final String name) {

        try {
            HomeSystem.getInstance().getMySQLManager().executeUpdate("DELETE FROM homes WHERE uuid = ? AND home_name = ?",
                    player.getUniqueId().toString(), name);
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }

    }


}
