package mpermissions.main;

import java.util.ArrayList;

import mpermissions.cmds.ListGroups;
import mpermissions.cmds.PermissionsCommand;
import mpermissions.events.PlayerJoin;
import mpermissions.events.PlayerQuit;
import mpermissions.utils.ConfigManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	ArrayList<String> permissions;
	
	
	public void onEnable() {
		
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
		getCommand("Permissions").setExecutor(new PermissionsCommand());
		getCommand("ListGroups").setExecutor(new ListGroups());
		
		if (!ConfigManager.getConfig().contains("Settings.defaultRank")) {
			
			ConfigManager.getConfig().set("Settings.defaultRank", "Member");
			
		}
		
		if (!ConfigManager.getPermissionsFile().getKeys().contains("groups")) {
			
			permissions = new ArrayList<String>();
			
			permissions.add("mgeneral.list");
			permissions.add("mgeneral.ping");
			
			ConfigManager.getPermissionsFile().set("groups." + ConfigManager.getConfig().get("Settings.defaultRank") + ".permissions", permissions);
			ConfigManager.getPermissionsFile().set("groups." + ConfigManager.getConfig().get("Settings.defaultRank") + ".options.prefix", "&7");
		}
		
	}
	
	public static Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin("mPermissions");
	}
	

}
