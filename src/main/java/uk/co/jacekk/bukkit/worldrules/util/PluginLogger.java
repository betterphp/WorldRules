package uk.co.jacekk.bukkit.worldrules.util;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class PluginLogger {
	
	private Plugin plugin;
	private Logger logger;
	
	public PluginLogger(Plugin plugin){
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
