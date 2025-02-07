package com.evilnotch.lanessentials.api;

import com.evilnotch.lanessentials.LanEssentials;
import com.evilnotch.lib.api.mcp.MCPSidedString;

import net.minecraft.util.ResourceLocation;

public class LanFields {
	
	public static String flySpeed = new MCPSidedString("flySpeed", "field_75096_f").toString();
	public static String walkSpeed = new MCPSidedString("walkSpeed", "field_75097_g").toString();
	public static String nickAction = new MCPSidedString("action", "field_179770_a").toString();
	public static String displayname = "displayname";
	
	public static final ResourceLocation HOME = new ResourceLocation(LanEssentials.MODID, "home");
	public static final ResourceLocation ABILITY = new ResourceLocation(LanEssentials.MODID, "ability");
	public static final ResourceLocation NICK = new ResourceLocation(LanEssentials.MODID, "nick");
	public static final ResourceLocation SPEED = new ResourceLocation(LanEssentials.MODID, "speed");

}
