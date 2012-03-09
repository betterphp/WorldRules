package uk.co.jacekk.bukkit.worldrules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.bukkit.configuration.file.YamlConfiguration;

public class WorldRulesConfig {
	
	private File configFile;
	private YamlConfiguration config;
	private LinkedHashMap<String, Object> configDefaults;
	
	public WorldRulesConfig(File configFile, WorldRules plugin){
		this.configFile = configFile;
		this.config = new YamlConfiguration();
		this.configDefaults = new LinkedHashMap<String, Object>();
		
		this.configDefaults.put("settings.rules-on-enter", true);
		
		this.configDefaults.put("global-rules", Arrays.asList("Real world laws/rules apply", "Where possible leave the landscape looking natural.", "All trees must be cut down fully (no floating leaves)."));
		
		this.configDefaults.put("world-rules", new ArrayList<String>());
		
		if (configFile.exists()){
			try{
				this.config.load(configFile);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		
		boolean updateNeeded = false;
		
		for (String key : this.configDefaults.keySet()){
			if (this.config.contains(key) == false){
				this.config.set(key, this.configDefaults.get(key));
				
				updateNeeded = true;
			}
		}
		
		if (updateNeeded){
			try{
				this.config.save(configFile);
				plugin.log.info("The config.yml file has been updated.");
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void reload(){
		if (configFile.exists()){
			try{
				this.config.load(configFile);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void addWorld(String worldName){
		this.config.set("world-rules." + worldName, new ArrayList<String>());
		
		try{
			this.config.save(this.configFile);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public boolean contains(String key){
		return this.config.contains(key);
	}
	
	public boolean getBoolean(String key){
		if (this.configDefaults.containsKey(key) == false){
			return false;
		}
		
		return this.config.getBoolean(key, (Boolean) this.configDefaults.get(key));
	}
	
	public ArrayList<String> getStringList(String key){
		if (this.contains(key) == false){
			return new ArrayList<String>();
		}
		
		return (ArrayList<String>) this.config.getStringList(key);
	}
	
}

	