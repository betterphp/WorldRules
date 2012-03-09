package uk.co.jacekk.bukkit.worldrules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class WorldRulesWorldListener implements Listener {
	
	private WorldRules plugin;
	
	public WorldRulesWorldListener(WorldRules instance){
		this.plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onWorldInit(WorldInitEvent event){
		String worldName = event.getWorld().getName();
		
		if (plugin.config.contains("world-rules." + worldName) == false){
			plugin.config.addWorld(worldName);
		}
	}
	
}
