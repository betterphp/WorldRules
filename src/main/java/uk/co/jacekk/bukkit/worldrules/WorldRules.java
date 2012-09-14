package uk.co.jacekk.bukkit.worldrules;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.World;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.jacekk.bukkit.worldrules.commands.WorldRulesExecutor;
import uk.co.jacekk.bukkit.worldrules.commands.RulesExecutor;
import uk.co.jacekk.bukkit.worldrules.listeners.SendRulesListener;
import uk.co.jacekk.bukkit.worldrules.listeners.NewWorldListener;
import uk.co.jacekk.bukkit.worldrules.util.PluginLogger;

public class WorldRules extends JavaPlugin {
	
	public WorldRulesConfig config;
	public PluginLogger log;
	
	public void onEnable(){
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		
		(new File(pluginFolder)).mkdirs();
		
		this.config = new WorldRulesConfig(new File(pluginFolder + File.separator + "config.yml"), this);
		this.log = new PluginLogger(this);
		
		PluginManager manager = this.getServer().getPluginManager();
		
		manager.registerEvents(new NewWorldListener(this), this);
		
		if (this.config.getBoolean("settings.rules-on-enter")){
			manager.registerEvents(new SendRulesListener(this), this);
		}
		
		this.getCommand("rules").setExecutor(new RulesExecutor(this));
		this.getCommand("worldrules").setExecutor(new WorldRulesExecutor(this));
		
		this.log.info("Enabled");
	}
	
	public void onDisable(){
		this.log.info("Disabled.");
	}
	
	public ArrayList<String> getRulesForWorld(World world){
		String worldName = world.getName();
		ArrayList<String> appliedRules = new ArrayList<String>();
		
		ArrayList<String> globalRules = this.config.getStringList("global-rules");
		ArrayList<String> worldRules = this.config.getStringList("world-rules." + worldName);
		
		appliedRules.addAll(globalRules);
		appliedRules.addAll(worldRules);
		
		return appliedRules;
	}
	
	public void sendRules(CommandSender sender, World world, ArrayList<String> rules){
		sender.sendMessage(ChatColor.BLACK + " ");
		sender.sendMessage(ChatColor.DARK_GREEN + "Rules for '" + world.getName() + "':");
		
		for (int i = 0; i < rules.size(); ++i){
			sender.sendMessage(ChatColor.GREEN + "    " + (i + 1) + ". " + rules.get(i));
		}
		
		sender.sendMessage(ChatColor.BLACK + " ");
	}
	
	public void sendRules(CommandSender sender, World world){
		this.sendRules(sender, world, this.getRulesForWorld(world));
	}
	
	public void sendRules(Player player){
		World world = player.getWorld();
		
		this.sendRules(player, world, this.getRulesForWorld(world));
	}
	
}
