package com.evilnotch.lanessentials.api;

import com.evilnotch.lib.api.mcp.MCPMappings;
import com.evilnotch.lib.api.mcp.MCPSidedString;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.server.management.PlayerList;

public class LanFeilds {
	
	public static String flySpeed = null;
	public static String walkSpeed = null;
	public static String nickAction = null;
	public static String gameType = null;
	public static String displayname = null;
	
	//dedicated server side only
	public static String propertyManager = null;
	public static String rconQueryThread = null;
	public static String rconThread = null;
	
	
	public static void cacheMCP()
	{
		flySpeed =  new MCPSidedString("flySpeed", "field_75096_f").toString();
		walkSpeed = new MCPSidedString("walkSpeed", "field_75097_g").toString();
		nickAction = new MCPSidedString("action", "field_179770_a").toString();
		gameType = new MCPSidedString("gameType", "field_72410_m").toString();
		displayname = "displayname";
	}
	
	public static void cacheDedicatedMCP()
	{
		propertyManager = new MCPSidedString("settings", "field_71340_o").toString();
		rconQueryThread = new MCPSidedString("rconQueryThread", "field_71342_m").toString();
		rconThread = new MCPSidedString("rconThread", "field_71339_n").toString();
	}

}
