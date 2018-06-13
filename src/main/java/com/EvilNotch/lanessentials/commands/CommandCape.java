package com.EvilNotch.lanessentials.commands;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.capabilities.CapCape;
import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.minecraft.SkinUpdater;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.properties.Property;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class CommandCape extends CommandBase{

	@Override
	public String getName() {
		return "cape";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "cape [url]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		EntityPlayer player = (EntityPlayer)sender;
		String url = args.length == 1 ? args[0] : "";
		ArrayList<Property> props = JavaUtil.toArray(player.getGameProfile().getProperties().get("textures"));
		for(Property p : props)
		{
			String base64 = p.getValue();
			JSONObject obj = toJson(base64);
			JSONObject obj2 = (JSONObject) obj.get("textures");
			JSONObject json = (JSONObject)obj2.get("CAPE");
			if(json == null)
			{
				json = new JSONObject();
				obj2.put("CAPE", json);
			}
			if(url.equals(""))
				obj2.remove("CAPE");//for when args == 0
			
			obj.put("signatureRequired", false);
			json.put("url",url);
			String str = obj.toJSONString();
			byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(str.getBytes());
			ReflectionUtil.setObject(p, new String(bytes,StandardCharsets.UTF_8), Property.class, MCPMappings.getField(Property.class, "value"));
			System.out.println("Field:" + MCPMappings.getField(Property.class, "value"));
		}
		CapCape cape = (CapCape) CapabilityReg.getCapabilityConatainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "cape"));
		cape.url = url;
		SkinUpdater.updateSkinPackets((EntityPlayerMP) player);
	}

	public static JSONObject toJson(String base64) {
		byte[] out = org.apache.commons.codec.binary.Base64.decodeBase64(base64.getBytes());
		String str = new String(out,StandardCharsets.UTF_8);
		JSONParser parser = new JSONParser();
		try {
			return (JSONObject)parser.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
