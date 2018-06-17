package com.EvilNotch.lanessentials.commands;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.capabilities.CapSkin;
import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.minecraft.SkinUpdater;
import com.EvilNotch.lib.minecraft.TestProps;
import com.EvilNotch.lib.minecraft.content.SkinData;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class CommandSkin  extends CommandBase
{

	@Override
	public String getName() {
		return "skin";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/skin [skinName/url]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP) || args.length == 0 || JavaUtil.isURL(args[0]) && args.length != 2)
			throw new WrongUsageException("/skin [name/url,isSteve]",new Object[0]);
		long time = System.currentTimeMillis();
		EntityPlayerMP player = (EntityPlayerMP)sender;
		CapabilityContainer container = CapabilityReg.getCapabilityConatainer(player);
		CapSkin skin = (CapSkin) container.getCapability(new ResourceLocation(Reference.MODID + ":" + "skin"));
		String username = args[0];
		if(args.length == 2)
			if(!JavaUtil.isStringBoolean(args[1]))
				throw new WrongUsageException("/skin [url, isSteve]",new Object[0]);
		boolean isAlex = args.length == 2 ? !Boolean.parseBoolean(args[1]) : false;
		SkinUpdater.updateSkin(username,player,true,isAlex);
		skin.skin = username;//if updating the skin thows a wrong usage exception the skin name doesn't get reset
		skin.isAlex = getIsAlex(player);
		JavaUtil.printTime(time, "Done Skin " + skin.isAlex + ":");
	}

	public boolean getIsAlex(EntityPlayerMP player) 
	{
		Collection<Property> props = player.getGameProfile().getProperties().get("textures");
		for(Property p : props)
		{
			JSONObject json = JavaUtil.toJsonFrom64(p.getValue());
			JSONObject textures = (JSONObject) json.get("textures");
			if(textures.containsKey("SKIN"))
			{
				JSONObject skin = (JSONObject) textures.get("SKIN");
				if(skin.containsKey("metadata"))
				{
					JSONObject meta = (JSONObject) skin.get("metadata");
					if(meta.containsKey("model"))
						return true;
				}
			}
		}
		return false;
	}
}
