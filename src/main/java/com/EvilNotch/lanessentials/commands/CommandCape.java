package com.EvilNotch.lanessentials.commands;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.api.SkinData;
import com.EvilNotch.lanessentials.api.SkinUpdater;
import com.EvilNotch.lanessentials.capabilities.CapCape;
import com.EvilNotch.lanessentials.capabilities.CapSkin;
import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.minecraft.EnumChatFormatting;
import com.EvilNotch.lib.minecraft.TestProps;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

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
        Thread t = new Thread(
        new Runnable() 
        { 
     	   public void run() 
     	   { 
     		   try
     		   {
     			  EntityPlayerMP player = (EntityPlayerMP)sender;
     		 	  //cape getURL [playername]
     			  if(args.length == 2)
     			  {
     				if(args[0].equals("getURL"))
     				{
     					SkinData skin = SkinUpdater.getSkinData(args[1].toLowerCase());
     					String url = SkinUpdater.getCapeURL(skin,args[1]);
     					EntityUtil.sendURL(player,"Cape Link for " + args[1] + ":", url);
     					return;
     				}
     				else if(args[0].equals("getCapability"))
     				{
     					if(player.mcServer.getPlayerList().getPlayerByUsername(args[1]) == null)
     						throw new WrongUsageException("player isn't currently logged in:" + args[1]);
     					
     					CapCape cap = (CapCape) CapabilityReg.getCapability(args[1],new ResourceLocation(Reference.MODID + ":" + "cape") );
     					if(cap == null)
     					{
     						throw new WrongUsageException("capability of player's skin returned null report this to lan-essentials as an issue");
     					}
     					else if(cap.url.equals(""))
     					{
     						EntityUtil.printChat(player, EnumChatFormatting.RED, "", "cape is blank for user:" + args[1]);
     						return;
     					}
     					EntityUtil.sendURL((EntityPlayer) sender, "capeCapability:", cap.url);
     					return;
     				}
     				else
     					throw new WrongUsageException("/cape [getURL/getCapability, playername]",new Object[0]);
     			}
     			String url = args.length == 1 ? args[0] : "";

     			SkinUpdater.setCape(player,url,true);
     		 }
     		 catch(Exception e)
     		 {
     			EntityUtil.printChat((EntityPlayerMP) sender, EnumChatFormatting.RED, "", "Ussage:" + e.getMessage());
     		 }
     	   }
        });
        t.start();
	}
	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
		List<String> list = args.length != 1 && args.length != 2 ? new ArrayList() : JavaUtil.asArray(server.getOnlinePlayerNames());
		list.add(0,"getURL");
		list.add(0,"getCapability");
		return CommandHome.orderList(list, args);
    }

}
