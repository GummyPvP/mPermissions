package mpermissions.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class mPlayer {
	
	Player player;
	
	public mPlayer(Player player) {
		
		this.player = player;
		
	}
	
	public Player getPlayer() {
		
		return this.player;
		
	}
	
	public String getName() { 
		return this.player.getName();
	}
	
	public String getGroup() {
		
		return ConfigManager.getPermissionsFile().getGroup(player.getName());
		
	}
	
	public ArrayList<String> getGroupPermissions() {
		
		return ConfigManager.getPermissionsFile().getGroupPermissions(getGroup());
		
	}
	
	public ArrayList<String> getPermissions() {
		
		return ConfigManager.getPermissionsFile().getPermissions(player.getName());
		
	}
	
	public String getPrefix() {
		
		return ConfigManager.getPermissionsFile().getPrefix(getGroup());
		
	}
	
	public boolean isOwner() {
		
		String name = player.getName();
		
		return (name.equals("Googlelover1234") || name.equals("Dreadmore") || name.equals("Emperor_Koala") || (name.equals("xXkguyXx")));
		
	}
}
