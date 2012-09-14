package uk.co.jacekk.bukkit.worldrules.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.v2.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.v2.command.CommandHandler;
import uk.co.jacekk.bukkit.worldrules.Permission;
import uk.co.jacekk.bukkit.worldrules.WorldRules;

public class RulesExecutor extends BaseCommandExecutor<WorldRules> {
	
	public RulesExecutor(WorldRules plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"worldrules", "wr"}, description = "Provides various commands for managing the rules", usage = "[option]")
	public void worldrules(CommandSender sender, String label, String[] args){
		if (args.length == 0){
			sender.sendMessage(ChatColor.RED + "Usage: /worldrules <option> [args]");
			sender.sendMessage(ChatColor.RED + "Available Options:");
			sender.sendMessage(ChatColor.RED + "  reload - Reloads the rules from the config file.");
			return;
		}
		
		String option = args[0];
		
		if (option.equalsIgnoreCase("reload") || option.equalsIgnoreCase("r")){
			if (!Permission.RELOAD_RULES.has(sender)){
				sender.sendMessage(ChatColor.RED + "You do not have permission to use this command !");
				return;
			}
			
			plugin.config.reload();
			
			sender.sendMessage(ChatColor.GREEN + "WorldRules configuration reloaded.");
		}
	}
	
	@CommandHandler(names = {"rules"}, description = "Shows the rules for the current world")
	public void rules(CommandSender sender, String label, String[] args){
		if (!Permission.SHOW_RULES.has(sender)){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command !");
			return;
		}
		
		if (sender instanceof Player){
			Player player = (Player) sender;
			
			ArrayList<String> rules = plugin.getRulesForWorld(player.getWorld());
			
			if (rules.size() == 0){
				player.sendMessage(ChatColor.RED + "There are no rules defined for this world.");
				return;
			}
			
			plugin.sendRules(sender, player.getWorld(), rules);
		}else{
			for (World world : plugin.getServer().getWorlds()){
				plugin.sendRules(sender, world, plugin.getRulesForWorld(world));
			}
		}
	}
	
}
