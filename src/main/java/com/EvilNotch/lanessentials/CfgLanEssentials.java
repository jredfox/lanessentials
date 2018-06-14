package com.EvilNotch.lanessentials;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class CfgLanEssentials {
	
	public static int maxHomeCount = 3;
	public static boolean overrideCape = false;
	
	public static void loadConfig(File dir)
	{
		Configuration cfg = new Configuration(new File(dir,Reference.MODID + ".cfg"));
		cfg.load();
		maxHomeCount = cfg.get("general", "maxHomeCount", maxHomeCount).getInt();
		overrideCape = cfg.get("general", "keepURLCapeAlways", overrideCape).getBoolean();
		cfg.save();
	}

}
