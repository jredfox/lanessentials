package com.EvilNotch.lanessentials.api;

import com.EvilNotch.lib.Api.MCPMappings;

import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.server.integrated.IntegratedServer;

public class LanFeilds {
	
	public static String flySpeed = null;
	public static String walkSpeed = null;
	public static String nickAction = null;
	
	public static void cacheMCP(){
		flySpeed = MCPMappings.getField(PlayerCapabilities.class, "flySpeed");
		walkSpeed = MCPMappings.getField(PlayerCapabilities.class, "walkSpeed");
		nickAction = MCPMappings.getField(SPacketPlayerListItem.class, "action");
	}

}
