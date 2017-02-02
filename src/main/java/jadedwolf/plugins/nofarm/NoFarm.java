package jadedwolf.plugins.nofarm;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NoFarm extends JavaPlugin {

    static final Logger log = Logger.getLogger("Minecraft");
    private static Plugin plugin;

    @Override
    public void onDisable() {
        PluginDescriptionFile plugin = getDescription();
        System.out.println(plugin.getName() + " version " + plugin.getVersion()
                + " is now Disabled");
    }

    @Override
    public void onEnable() {
        plugin = this;

        // Load configuration
        File cfgFile = new File(getDataFolder(), "config.yml");
        if (!cfgFile.exists()) {
            saveResource("config.yml", true);
            reloadConfig();
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new NoFarmEntityListener(), this);
        pm.registerEvents(new NoFarmMobListener(), this);

        PluginDescriptionFile plugin = getDescription();
        System.out.println(plugin.getName() + " version " + plugin.getVersion() + " by lagcraft.com is now enabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("nofarm")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args[0].equalsIgnoreCase("reload")) {
                    reloadConfig();
                    player.sendMessage("The config.yml file has been reloaded.");
                }
            } else {
                reloadConfig();
                System.out.println("NoFarm Config Reloaded");
            }
        }
        return false;
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}