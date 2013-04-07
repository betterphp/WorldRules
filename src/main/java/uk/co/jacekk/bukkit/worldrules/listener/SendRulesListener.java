package uk.co.jacekk.bukkit.worldrules.listener;

import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.worldrules.WorldRules;

public class SendRulesListener extends BaseListener<WorldRules> {
	
	public SendRulesListener(WorldRules plugin){
		super(plugin);
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
		
		if (!rules.isEmpty()){
			plugin.sendRules(player, world, rules);
		}
	}
	
}
