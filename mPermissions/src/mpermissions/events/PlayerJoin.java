package mpermissions.events;

import mpermissions.main.Main;
import mpermissions.utils.ConfigManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

public class PlayerJoin implements Listener {
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		PermissionAttachment pa = p.addAttachment(Main.getPlugin());
		
		ConfigManager.getPermissionsFile().addPermissionAttachment(p, pa);
		
		
		if (!ConfigManager.getPermissionsFile().userExists(p.getName())) {
			ConfigManager.getPermissionsFile().createProfile(p.getName());
		}
		
		ConfigManager.getPermissionsFile().refreshPermissions(p);
		
	}
	

}
