package uk.co.jacekk.bukkit.worldrules;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.World;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfigKey;
import uk.co.jacekk.bukkit.worldrules.command.RulesExecutor;
import uk.co.jacekk.bukkit.worldrules.listener.SendRulesListener;

public class WorldRules extends BasePlugin {
	
	public void onEnable(){
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		if (this.config.getBoolean(Config.SHOW_RULES_ON_ENTER)){
			this.pluginManager.registerEvents(new SendRulesListener(this), this);
		}
		
		this.commandManager.registerCommandExecutor(new RulesExecutor(this));
	}
	
	/**
	 * Fetches a list of rules enabled in a specific world.
	 * 
	 * @param world The world to fetch the rules for.
	 * @return A list of enabled rules (not numbered).
	 */
	public ArrayList<String> getRulesForWorld(final World world){
		ArrayList<String> rules = new ArrayList<String>();
		
		rules.addAll(this.config.getStringList(Config.GLOBAL_RULES));
		rules.addAll(this.config.getStringList(new PluginConfigKey(Config.WORLD_RULES.getKey() + "." + world.getName(), Arrays.asList())));
		
		return rules;
	}
	
	/**
	 * Formats and sends a list of rules to the user.
	 * 
	 * @param sender The CommandSender to receive the rules.
	 * @param world The World that the rules are for.
	 * @param rules The list of rules.
	 */
	public void sendRules(CommandSender sender, World world, ArrayList<String> rules){
		ChatColor headerColour, ruleColour;
		
		try{
			headerColour = ChatColor.valueOf(this.config.getString(Config.HEADER_COLOUR));
			ruleColour = ChatColor.valueOf(this.config.getString(Config.RULE_COLOUR));
		}catch (IllegalArgumentException e){
			this.log.warn("Invalid colours given in config.yml, reverting to defaults.");
			this.log.info("Valid colours are:");
			
			for (ChatColor colour : ChatColor.values()){
				this.log.info("  - " + colour.name());
			}
			
			headerColour = ChatColor.DARK_GREEN;
			ruleColour = ChatColor.GREEN;
		}
		
		sender.sendMessage(ChatColor.BLACK + " ");
		sender.sendMessage(headerColour + "Rules for '" + world.getName() + "':");
		
		for (int i = 0; i < rules.size(); ++i){
			sender.sendMessage(ruleColour + "    " + (i + 1) + ". " + rules.get(i));
		}
		
		sender.sendMessage(ChatColor.BLACK + " ");
	}
	
	/**
	 * Formats and sends a list of rules to the user.
	 * 
	 * @param sender The CommandSender to receive the rules.
	 * @param world The World to send rules for.
	 */
	public void sendRules(CommandSender sender, World world){
		this.sendRules(sender, world, this.getRulesForWorld(world));
	}
	
	/**
	 * Formats and sends a list of rules to the player.
	 * 
	 * @param player The Player to receive the rules.
	 */
	public void sendRules(Player player){
		World world = player.getWorld();
		
		this.sendRules(player, world, this.getRulesForWorld(world));
	}
	
}
