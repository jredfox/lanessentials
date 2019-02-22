package com.evilnotch.lanessentials.client;

import com.evilnotch.lib.api.mcp.MCPMappings;
import com.evilnotch.lib.api.mcp.MCPSidedString;

import net.minecraft.server.integrated.IntegratedServer;

public class LanFeildsClient {
	
	public static String allowCheats = null;
	public static String gameMode = null;
	
	public static String isPublic = null;
	public static String lanServerPing = null;
	public static String address = null;
	public static String commandsAllowedForAll = null;
	
	public static void cacheMCP()
	{
		allowCheats = new MCPSidedString("allowCheats", "field_146600_i").toString();
		gameMode = new MCPSidedString("gameMode", "field_146599_h").toString();
		
		isPublic = new MCPSidedString("isPublic", "field_71346_p").toString();
		lanServerPing = new MCPSidedString("lanServerPing", "field_71345_q").toString();
		address = new MCPSidedString("address", "field_77527_e").toString();
		commandsAllowedForAll = new MCPSidedString("commandsAllowedForAll", "field_72407_n").toString();
	}
}
