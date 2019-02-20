package com.evilnotch.lanessentials.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.evilnotch.lanessentials.Reference;
import com.evilnotch.lanessentials.api.SkinData;
import com.evilnotch.lanessentials.api.SkinUpdater;
import com.evilnotch.lanessentials.capabilities.CapCape;
import com.evilnotch.lib.minecraft.capability.registry.CapRegHandler;
import com.evilnotch.lib.minecraft.util.EntityUtil;
import com.evilnotch.lib.minecraft.util.EnumChatFormatting;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;

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
         				PlayerUtil.sendClipBoard((EntityPlayer)sender, EnumChatFormatting.YELLOW,EnumChatFormatting.BLUE + EnumChatFormatting.UNDERLINE, "skin url: " + args[1] + ":", url);
     					return;
     				}
     				else if(args[0].equals("getCapability"))
     				{
     					if(player.mcServer.getPlayerList().getPlayerByUsername(args[1]) == null)
     						throw new WrongUsageException("player isn't currently logged in:" + args[1]);
     					
     					CapCape cap = (CapCape) CapRegHandler.getCapability(args[1],new ResourceLocation(Reference.MODID + ":" + "cape") );
     					if(cap == null)
     					{
     						throw new WrongUsageException("capability of player's skin returned null report this to lan-essentials as an issue");
     					}
     					else if(cap.url.equals(""))
     					{
     						PlayerUtil.printChat(player, EnumChatFormatting.RED, "", "cape is blank for user:" + args[1]);
     						return;
     					}
         				if(JavaUtil.isURL(cap.url))
         					PlayerUtil.sendClipBoard((EntityPlayer)sender, EnumChatFormatting.YELLOW,EnumChatFormatting.BLUE + EnumChatFormatting.UNDERLINE, "skin url: " + args[1] + ":", cap.url);
         				else
         					PlayerUtil.sendClipBoard((EntityPlayer)sender, EnumChatFormatting.AQUA,EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.UNDERLINE, "skin url: " + args[1] + ":", cap.url);
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
     			PlayerUtil.printChat((EntityPlayerMP) sender, EnumChatFormatting.RED, "", "Ussage:" + e.getMessage());
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
