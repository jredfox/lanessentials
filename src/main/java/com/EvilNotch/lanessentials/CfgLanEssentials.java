package com.EvilNotch.lanessentials;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class CfgLanEssentials {
	
	public static int maxHomeCount = 3;
	public static boolean overrideCape = false;
	public static boolean portForwardDedicated = true;
	public static String dedicatedPortProtocal = "TCP";
	public static boolean closePort = true;
	
	public static int maxSkinCache = 200000;
	public static long maxSkinCacheDays = 2;
	public static String[] skinDomains = null;
	
	public static void loadConfig(File dir)
	{
		Configuration cfg = new Configuration(new File(dir,Reference.MODID + ".cfg"));
		cfg.load();
		maxHomeCount = cfg.get("general", "maxHomeCount", maxHomeCount).getInt();
		overrideCape = cfg.get("general", "keepURLCapeAlways", overrideCape).getBoolean();
		portForwardDedicated = cfg.get("network", "portForwardDedicatedServer", portForwardDedicated).getBoolean();
		cfg.addCustomCategoryComment("network", "Protocal can be either \"TCP\", or \"UDP\"");
		dedicatedPortProtocal = cfg.get("network", "portForwardDedicatedProtocal", dedicatedPortProtocal).getString().toUpperCase();
		closePort = cfg.get("network", "closePortOnShutdown", closePort).getBoolean();
		
		//skins
		maxSkinCache = cfg.get("vanilla_fixer", "SkinCacheMaxSize",200000).getInt();
		maxSkinCacheDays = (long)cfg.get("vanilla_fixer", "SkinCacheStaleDays",(int)maxSkinCacheDays).getInt();
		String[] myLists = {
		       ".minecraft.net",
		       ".mojang.com",
		       "crafatar.com",
		       ".cloudfront.net",
		       ".imgur.com"
		};
		skinDomains = cfg.getStringList("WHITELISTED_DOMAINS", "general", myLists, "white listed https:// domains urls for skins/capes string must end in the domain of the url to be valid");

		cfg.save();
	}
}
