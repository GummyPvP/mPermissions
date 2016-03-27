package mpermissions.cmds;

import mpermissions.utils.ChatManager;
import mpermissions.utils.ConfigManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("permissions")) {

			if (sender.hasPermission("mpermissions.modify")) {

				if (args.length < 2) {
					
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l» &cUsage: /" + commandLabel + "[user/group] [username/groupname] (add/remove) (permission)"));
					return true;
					
				}
				
				if (args.length == 2) {
					
					if (args[0].equalsIgnoreCase("user")) {
						
						if (ConfigManager.getPermissionsFile().userExists(args[1])) {
							
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l==== &cShowing permissions profile for " + args[1] + "... &7&l===="));
							
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGroup: " + ConfigManager.getPermissionsFile().getPrefix(ConfigManager.getPermissionsFile().getGroup(args[1])) + ConfigManager.getPermissionsFile().getGroup(args[1])));
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPermissions: " + ConfigManager.getPermissionsFile().getPermissions(args[1]).toString()));
							
						} else {
							
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUser does not exist in DB, usernames are CaSe SeNsItIvE!"));
						}
						
						return true;
						
					}
					
					if (args[0].equalsIgnoreCase("group")) {
						
						if (ConfigManager.getPermissionsFile().groupExists(args[1])) {
							
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l==== &cShowing permissions profile for " + args[1] + "... &7&l===="));
							
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGroup: " + ConfigManager.getPermissionsFile().getPrefix(args[1]) + args[1]));
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aPermissions: " + ConfigManager.getPermissionsFile().getGroupPermissions(args[1]).toString()));							
						} else {
							
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cGroup does not exist in DB, groups are CaSe SeNsItIvE!"));
						}
						
						return true;
						
					}
					
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cUsage: /" + commandLabel + " [user/group]"));
					
				}
				
				if (args.length == 4) {
					
					if (args[0].equalsIgnoreCase("user")) {
						
						if (ConfigManager.getPermissionsFile().userExists(args[1])) {
							
							Player p = Bukkit.getPlayer(args[1]);
							
							switch (args[2].toLowerCase()) {
							case "add":
								
								ConfigManager.getPermissionsFile().addUserPermission(args[1], args[3]);
								ConfigManager.getPermissionsFile().save();
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cSuccessfully added permission &e" + args[3] + " &cto user " + args[1]));
								
								
								if (p != null) {
									
									ConfigManager.getPermissionsFile().refreshPermissions(p);
									
									
								} else return true;
								
								break;
							case "remove":
								
								ConfigManager.getPermissionsFile().removeUserPermission(args[1], args[3]);
								ConfigManager.getPermissionsFile().save();
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cSuccessfully remove permission &e" + args[3] + " &cfrom user " + args[1]));
								
								if (p != null) {
									
									
									ConfigManager.getPermissionsFile().refreshPermissions(p);
									
									
								} else return true;
								
								break;
							}
							
						}
						
						return true;
						
					}
					
					if (args[0].equalsIgnoreCase("group")) {
						
						if (ConfigManager.getPermissionsFile().groupExists(args[1])) {
							
							switch (args[2].toLowerCase()) {
							case "add":
								
								ConfigManager.getPermissionsFile().addGroupPermission(args[1], args[3]);
								ConfigManager.getPermissionsFile().save();
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cSuccessfully added permission &e" + args[3] + " &cto group " + args[1]));
								
								break;
							case "remove":
								
								ConfigManager.getPermissionsFile().removeGroupPermission(args[1], args[3]);
								ConfigManager.getPermissionsFile().save();
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cSuccessfully remove permission &e" + args[3] + " &cfrom group " + args[1]));								
								
								break;
							}
							
						}
						
						return true;
						
					}
					
					
					return true;
				}
				
				if (args.length == 5) {
					
					if (args[0].equalsIgnoreCase("user")) {
						
						if (ConfigManager.getPermissionsFile().userExists(args[1])) {
							
							if (args[2].equalsIgnoreCase("group")) {
								
								if (args[3].equalsIgnoreCase("set")) {
									
									if (ConfigManager.getPermissionsFile().groupExists(args[4])) {
										
										ConfigManager.getPermissionsFile().setGroup(args[1], args[4]);
										
										ConfigManager.getPermissionsFile().save();
										
										sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &aSuccessfully changed user group!"));
										
										Player p = Bukkit.getPlayer(args[1]);
										
										if (p != null) {
											
											ConfigManager.getPermissionsFile().refreshPermissions(p);
											
											
										} else return true;
										
										
									} else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cGroup " + args[4] + " does not exist!"));
									
								} else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cUsage: /" + commandLabel + " [user/group] group set [group]"));
								
							} else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cUsage: /" + commandLabel + " [user/group] group set [group]"));
							
						} else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cUser " + args[1] + " does not exist!"));
						
					} else sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &cUsage: /" + commandLabel + " [user/group] group set [group]"));
					
					return true;
				}

			} else
				ChatManager.getInstance().messageNoPermission(sender);
		}
		return true;
	}
}
