package uk.co.jacekk.bukkit.worldrules;

import java.util.Arrays;

import uk.co.jacekk.bukkit.baseplugin.v2.config.PluginConfigKey;

public enum Config implements PluginConfigKey {
	
	SHOW_RULES_ON_ENTER(	"settings.rules-on-enter",	true),
	HEADER_COLOUR(			"settings.header-colour",	"DARK_GREEN"),
	RULE_COLOUR(			"settings.rule-colour",		"GREEN"),
	
	GLOBAL_RULES(			"global-rules",				Arrays.asList("Don't be evil.")),
	WORLD_RULES(			"world-rules",				Arrays.asList());
	
	private String key;
	private Object defaultValue;
	
	private Config(String key, Object defaultValue){
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public Object getDefault(){
		return this.defaultValue;
	}
	
}
