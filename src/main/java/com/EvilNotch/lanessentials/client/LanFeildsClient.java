package com.EvilNotch.lanessentials.client;

import com.EvilNotch.lib.Api.MCPMappings;

import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.server.integrated.IntegratedServer;

public class LanFeildsClient {
	
	public static String allowCheats = null;
	public static String gameMode = null;
	
	public static String isPublic = null;
	public static String lanServerPing = null;
	public static String address = null;
	
	public static void cacheMCP(){
		allowCheats = MCPMappings.getField(GuiShareToLan.class, "allowCheats");
		gameMode = MCPMappings.getField(GuiShareToLan.class, "gameMode");
		
		isPublic = MCPMappings.getField(IntegratedServer.class, "isPublic");
		lanServerPing = MCPMappings.getField(IntegratedServer.class, "lanServerPing");
		address = MCPMappings.getField(ThreadLanServerPing.class, "address");
	}
}
