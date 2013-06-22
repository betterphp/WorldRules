package uk.co.jacekk.bukkit.worldrules.command;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.command.BaseCommandExecutor;
import uk.co.jacekk.bukkit.baseplugin.command.CommandHandler;
import uk.co.jacekk.bukkit.baseplugin.command.CommandTabCompletion;
import uk.co.jacekk.bukkit.baseplugin.command.SubCommandHandler;
import uk.co.jacekk.bukkit.worldrules.Permission;
import uk.co.jacekk.bukkit.worldrules.WorldRules;

public class RulesExecutor extends BaseCommandExecutor<WorldRules> {
	
	public RulesExecutor(WorldRules plugin){
		super(plugin);
	}
	
	@CommandHandler(names = {"worldrules", "wr"}, description = "Provides various commands for managing the rules", usage = "[option]")
	@CommandTabCompletion({"reload"})
	public void worldrules(CommandSender sender, String label, String[] args){
		sender.sendMessage(ChatColor.RED + "Usage: /worldrules <option> [args]");
		sender.sendMessage(ChatColor.RED + "Available Options:");
		
		if (Permission.RELOAD_RULES.has(sender)){
			sender.sendMessage(ChatColor.RED + "  reload - Reloads the rules from the config file.");
		}
	}
	
	@SubCommandHandler(name = "reload", parent = "worldrules")
	public void worldrulesReload(CommandSender sender, String label, String[] args){
		if (!Permission.RELOAD_RULES.has(sender)){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command !");
			return;
		}
		
		plugin.config.reload();
		
		sender.sendMessage(ChatColor.GREEN + "WorldRules configuration reloaded.");
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
			
			if (!rules.isEmpty()){
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
