package uk.co.jacekk.bukkit.worldrules.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import uk.co.jacekk.bukkit.worldrules.WorldRules;

public class NewWorldListener implements Listener {
	
	private WorldRules plugin;
	
	public NewWorldListener(WorldRules instance){
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
