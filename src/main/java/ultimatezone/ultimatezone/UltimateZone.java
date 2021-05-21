package ultimatezone.ultimatezone;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimateZone extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("uz").setExecutor(new CommandCreate());
        this.getCommand("uz delete zone").setExecutor(new CommandCreate());
        //Bukkit.getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        System.out.println("stoppe Plugin");
    }


    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }
}
