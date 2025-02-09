package com.jredfox.lanessentials;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class LanEssentialsConfig {
	
	public static int maxHomeCount = 3;
	public static boolean overrideHomeCount = true;
	public static boolean ipCommands = true;
	
	public static void loadConfig(File dir)
	{
		Configuration cfg = new Configuration(new File(dir, LanEssentials.MODID + ".cfg"));
		cfg.load();
		maxHomeCount = cfg.get("general", "maxHomeCount", maxHomeCount).getInt();
		overrideHomeCount = cfg.get("general", "overrideHomeCountMin", overrideHomeCount).getBoolean();
		ipCommands = cfg.get("general", "ipCommands", ipCommands).getBoolean();
		cfg.save();
	}
}
