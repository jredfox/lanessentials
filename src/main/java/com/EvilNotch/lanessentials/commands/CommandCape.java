package com.EvilNotch.lanessentials.commands;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.capabilities.CapCape;
import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.minecraft.EnumChatFormatting;
import com.EvilNotch.lib.minecraft.SkinUpdater;
import com.EvilNotch.lib.minecraft.content.SkinData;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.properties.Property;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class CommandCape extends CommandBase{

	@Override
	public String getName() {
		return "cape";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "cape [url/playername]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		EntityPlayer player = (EntityPlayer)sender;
		//cape getURL [playername]
		if(args.length == 2)
		{
			if(!args[0].equals("getURL"))
				throw new WrongUsageException("/cape getURL [playername]",new Object[0]);
			SkinData skin = SkinUpdater.getSkinData(args[1].toLowerCase());
			String url = SkinUpdater.getCapeURL(skin,args[1]);
			EntityUtil.sendURL(player,"Cape Link for " + args[1] + ":", url);
			return;
		}
		String url = args.length == 1 ? args[0] : "";
		if(!url.equals(""))
		{	
			if(!JavaUtil.isURL(url))
			{
				SkinData data = SkinUpdater.getSkinData(url.toLowerCase());//since url is player at this point use that for player name
				url = SkinUpdater.getCapeURL(data,url);
			}
		}
		ArrayList<Property> props = JavaUtil.toArray(player.getGameProfile().getProperties().get("textures"));
		if(props.size() == 0)
			throw new WrongUsageException("player skin and cape not set! set skin before setting a cape",new Object[0]);
		for(Property p : props)
		{
			String base64 = p.getValue();
			JSONObject obj = JavaUtil.toJsonFrom64(base64);
			JSONObject obj2 = (JSONObject) obj.get("textures");
			if(!obj2.containsKey("SKIN"))
				throw new WrongUsageException("player skin not set! set skin before setting a cape",new Object[0]);
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
			ReflectionUtil.setObject(p, new String(bytes,StandardCharsets.UTF_8), Property.class, "value");
		}
		CapCape cape = (CapCape) CapabilityReg.getCapabilityConatainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "cape"));
		cape.url = url;
		SkinUpdater.updateSkinPackets((EntityPlayerMP) player);
	}
	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
		List<String> list = args.length != 1 && args.length != 2 ? new ArrayList() : JavaUtil.asArray(server.getOnlinePlayerNames());
		list.add(0,"getURL");
		return CommandHome.orderList(list, args);
    }

}
