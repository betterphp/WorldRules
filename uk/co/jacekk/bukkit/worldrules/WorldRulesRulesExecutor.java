package uk.co.jacekk.bukkit.worldrules;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldRulesRulesExecutor implements CommandExecutor {
	
	private WorldRules plugin;
	
	public WorldRulesRulesExecutor(WorldRules plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if (sender.hasPermission("worldrules.rules.show") == false){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command !");
			return true;
		}
		
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			ArrayList<String> rules = plugin.getRulesForWorld(player.getWorld());
			
			if (rules.size() == 0){
				player.sendMessage(ChatColor.RED + "There are no rules defined for this world.");
				return true;
			}
			
			plugin.sendRules(sender, player.getWorld(), rules);
		}else{
			for (World world : plugin.getServer().getWorlds()){
				plugin.sendRules(sender, world, plugin.getRulesForWorld(world));
			}
		}
		
		return true;
	}
	
}
