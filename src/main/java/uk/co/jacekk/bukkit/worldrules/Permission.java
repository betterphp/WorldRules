package uk.co.jacekk.bukkit.worldrules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import uk.co.jacekk.bukkit.baseplugin.v2.permissions.PluginPermission;

public enum Permission implements PluginPermission {
	
	SHOW_RULES(		"worldrules.rules.show",	PermissionDefault.TRUE,		"Allows the player to see the rules for the world thy are in."),
	RELOAD_RULES(	"worldrules.rules.reload",	PermissionDefault.OP,		"Allows the player to reload the config file.");
	
	private String node;
	private PermissionDefault defaultValue;
	private String description;
	
	private Permission(String node, PermissionDefault defaultValue, String description){
		this.node = node;
		this.defaultValue = defaultValue;
		this.description = description;
	}
	
	public List<Player> getPlayersWith(){
		ArrayList<Player> players = new ArrayList<Player>();
		
		for (Player player : Bukkit.getServer().getOnlinePlayers()){
			if (this.has(player)){
				players.add(player);
			}
		}
		
		return players;
	}
	
	public boolean has(CommandSender sender){
		return sender.hasPermission(this.node);
	}
	
	public String getNode(){
		return this.node;
	}
	
	public PermissionDefault getDefault(){
		return this.defaultValue;
	}
	
	public String getDescription(){
		return this.description;
	}
	
}
