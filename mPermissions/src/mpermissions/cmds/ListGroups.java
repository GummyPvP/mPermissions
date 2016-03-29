package mpermissions.cmds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mpermissions.utils.ChatManager;
import mpermissions.utils.ConfigManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListGroups implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		
		if (cmd.getName().equalsIgnoreCase("listgroups")) {
			
			if (sender.hasPermission("mpermissions.listgroups")) {
				
				if (args.length != 0) {
					
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &cUsage: /" + commandLabel));
					return true;
					
				}
				
				StringBuilder sb = new StringBuilder();
				
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l==== &cList of Groups &7&l===="));
				
				for (String groupName : ConfigManager.getPermissionsFile().getSection("groups").getKeys(false)) {
					
					sb.append(ConfigManager.getPermissionsFile().getPrefix(groupName) + groupName + ", ");
					
				}
				
				String groupList = sb.toString();
				Pattern pattern = Pattern.compile(", $");
				Matcher matcher = pattern.matcher(groupList);
				groupList = matcher.replaceAll("");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', groupList));
				return true;
			} else ChatManager.getInstance().messageNoPermission(sender);
			
		}
		
		
		return true;
		
	}
	

}
