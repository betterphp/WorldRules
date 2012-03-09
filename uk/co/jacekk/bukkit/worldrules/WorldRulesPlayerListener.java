package uk.co.jacekk.bukkit.worldrules;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldRulesPlayerListener implements Listener {
	
	private WorldRules plugin;
	
	public WorldRulesPlayerListener(WorldRules instance){
		this.plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event){
		plugin.sendRules(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event){
		Player player = event.getPlayer();
		World world = player.getWorld();
		
		ArrayList<String> rules = plugin.getRulesForWorld(world);
		
		if (rules.size() > 0){
			plugin.sendRules(player, world, rules);
		}
	}
	
}
