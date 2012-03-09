package uk.co.jacekk.bukkit.worldrules;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;

public class WorldRulesLogger {
	
	private WorldRules plugin;
	private Logger logger;
	
	public WorldRulesLogger(WorldRules plugin){
		this.plugin = plugin;
		this.logger = Logger.getLogger("Minecraft");
	}
	
	private String buildString(String msg){
		PluginDescriptionFile pdFile = plugin.getDescription();
		
		return pdFile.getName() + " " + pdFile.getVersion() + ": " + msg;
	}
	
	public void info(String msg){
		this.logger.info(this.buildString(msg));
	}
	
	public void warn(String msg){
		this.logger.warning(this.buildString(msg));
	}
	
	public void fatal(String msg){
		this.logger.severe(this.buildString(msg));
	}
	
}
