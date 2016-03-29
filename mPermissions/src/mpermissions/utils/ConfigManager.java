package mpermissions.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import mpermissions.main.Main;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class ConfigManager {

	private static ConfigManager configuration = new ConfigManager("config"),
			permissions = new ConfigManager("permissions");
	private static HashMap<String, PermissionAttachment> permissionAttachment = new HashMap<String, PermissionAttachment>();

	public static ConfigManager getConfig() {
		return configuration;
	}

	public static ConfigManager getPermissionsFile() {
		return permissions;
	}

	private File file;
	private FileConfiguration config;

	

	
	private ConfigManager(String fileName) {

		if (!Main.getPlugin().getDataFolder().exists()) {

			Main.getPlugin().getDataFolder().mkdir();

		}

		file = new File(Main.getPlugin().getDataFolder(), fileName + ".yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		config = YamlConfiguration.loadConfiguration(file);

	}

	public void set(String path, Object value) {
		config.set(path, value);
		save();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String path) {

		return (T) config.get(path);

	}

	public Set<String> getKeys() {
		return config.getKeys(false);
	}

	public List<String> getStringList(String path) {

		return config.getStringList(path);

	}
	
	
	public boolean contains(String path) {
		return config.contains(path);
	}

	public ConfigurationSection createSection(String path) {
		ConfigurationSection section = config.createSection(path);
		save();
		return section;
	}

	public ConfigurationSection getSection(String path) {

		ConfigurationSection section = config.getConfigurationSection(path);
		return section;

	}

	public void save() {
		try {
			config.save(file);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PermissionAttachment getPermissionAttachment(Player player) {

		if (!permissionAttachment.containsKey(player.getName()))
			return null;

		return permissionAttachment.get(player.getName());

	}

	public void addPermissionAttachment(Player player, PermissionAttachment pa) {

		permissionAttachment.put(player.getName(), pa);

	}

	public void addPermissionToAttachment(Player player, String permission) {

		if (!permissionAttachment.containsKey(player.getName()))
			return;

		permissionAttachment.get(player.getName()).setPermission(permission, true);

	}

	public void removePermissionFromAttachment(Player player, String permission) {

		if (!permissionAttachment.containsKey(player.getName()))
			return;

		permissionAttachment.get(player.getName()).unsetPermission(permission);


	}

	public boolean userExists(String username) {

		return getPermissionsFile().contains("users." + username);

	}

	public boolean groupExists(String group) {

		return getPermissionsFile().contains("groups." + group);

	}

	public void createProfile(String username) {

		ArrayList<String> permissions = new ArrayList<String>();
		permissions.add("mgeneral.message");
		permissions.add("mgeneral.reply");

		getPermissionsFile().set("users." + username + ".group", getConfig().get("Settings.defaultRank"));
		getPermissionsFile().set("users." + username + ".permissions", permissions);
		getPermissionsFile().save();

	}

	public ArrayList<String> getPermissions(String username) {

		ArrayList<String> permissions = new ArrayList<String>();

		for (String userPermissions : getPermissionsFile().getStringList("users." + username + ".permissions")) {

			permissions.add(userPermissions);

		}

		return permissions;
	}

	public ArrayList<String> getGroupPermissions(String group) {

		ArrayList<String> permissions = new ArrayList<String>();

		for (String groupPermissions : getPermissionsFile().getStringList("groups." + group + ".permissions")) {

			permissions.add(groupPermissions);

		}

		return permissions;

	}

	public String getGroup(String username) {

		return getPermissionsFile().get("users." + username + ".group");

	}

	public void setGroup(String username, String group) {

		getPermissionsFile().set("users." + username + ".group", group);

	}

	public String getPrefix(String group) {

		return getPermissionsFile().get("groups." + group + ".options.prefix");

	}

	public void addUserPermission(String username, String permission) {

		ArrayList<String> permissions = getPermissions(username);

		permissions.add(permission);

		getPermissionsFile().set("users." + username + ".permissions", permissions);
		
		getPermissionsFile().save();

	}

	public void removeUserPermission(String username, String permission) {

		ArrayList<String> permissions = getPermissions(username);

		permissions.remove(permission);

		getPermissionsFile().set("users." + username + ".permissions", permissions);

		getPermissionsFile().save();

	}

	public void addGroupPermission(String group, String permission) {

		ArrayList<String> permissions = getGroupPermissions(group);

		permissions.add(permission);

		getPermissionsFile().set("groups." + group + ".permissions", permissions);
		
		getPermissionsFile().save();

	}

	public void removeGroupPermission(String group, String permission) {

		ArrayList<String> permissions = getGroupPermissions(group);

		permissions.remove(permission);

		getPermissionsFile().set("groups." + group + ".permissions", permissions);
		
		getPermissionsFile().save();

	}

	public void refreshPermissions(Player player) {

		PermissionAttachment pa = player.addAttachment(Main.getPlugin());

		for (String userPermissions : getPermissionsFile().getPermissions(player.getName())) {

			pa.setPermission(userPermissions, true);

		}

		for (String groupPermissions : getPermissionsFile()
				.getGroupPermissions(getPermissionsFile().getGroup(player.getName()))) {

			pa.setPermission(groupPermissions, true);
		}

		if (getGroup(player.getName()).equals("Member") == false) {
			
			for (String inheritancePermissions : getInheritancePermissions(getGroup(player.getName()))) {

				if (inheritancePermissions == null)
					continue;

				pa.setPermission(inheritancePermissions, true);
			}

		}

	}

	public List<String> getInheritanceGroups(String group) {
		
		return getStringList("groups." + group + ".inheritance");
		

	}

	public List<String> getInheritancePermissions(String group) {


		
		for (String groupName : getInheritanceGroups(group)) {

			
			return getGroupPermissions(groupName);

		}
		return null;

	}

}