package mpermissions.events;

import mpermissions.main.Main;
import mpermissions.utils.ConfigManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		
		PermissionAttachment pa = p.addAttachment(Main.getPlugin());
		
		
		for (String perms : ConfigManager.getPermissionsFile().getGroupPermissions(ConfigManager.getPermissionsFile().getGroup(p.getName()))) {
			
			pa.unsetPermission(perms);
		}
		
		for (String perms : ConfigManager.getPermissionsFile().getPermissions(p.getName())) {
			
			pa.unsetPermission(perms);
			
			
		}
		
//		for (String inheritancePermissions : ConfigManager.getPermissionsFile().getInheritancePermissions(ConfigManager.getPermissionsFile().getGroup(p.getName()))) {
//			
//			if (inheritancePermissions == null) continue;
//			
//			pa.unsetPermission(inheritancePermissions);
//			
//		}
		
		
	}
}
