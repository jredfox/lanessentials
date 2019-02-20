package com.evilnotch.lanessentials.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.json.simple.JSONObject;

import com.evilnotch.lanessentials.Reference;
import com.evilnotch.lanessentials.api.SkinData;
import com.evilnotch.lanessentials.api.SkinUpdater;
import com.evilnotch.lanessentials.capabilities.CapSkin;
import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.registry.CapRegHandler;
import com.evilnotch.lib.minecraft.util.EnumChatFormatting;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;
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
		if(!(sender instanceof EntityPlayerMP) || args.length == 0)
			throw new WrongUsageException("/skin [name/url,isSteve]",new Object[0]);
        Thread t = new Thread(
        new Runnable() 
        { 
     	   public void run() 
     	   { 
     		   try
     		   {
     	        if(args[0].equals("getURL"))
     			{
     				if(args.length != 2)
     					throw new WrongUsageException("/skin getURL [playername]");
     				SkinData data = SkinUpdater.getSkinData(args[1]);
     				String url = SkinUpdater.getSkinURL(data, args[1]);
     				if(JavaUtil.isURL(url))
     					PlayerUtil.sendClipBoard((EntityPlayer)sender, EnumChatFormatting.YELLOW,EnumChatFormatting.BLUE + EnumChatFormatting.UNDERLINE, "skin url: " + args[1] + ":", url);
     				else
     					PlayerUtil.sendClipBoard((EntityPlayer)sender, EnumChatFormatting.AQUA,EnumChatFormatting.DARK_PURPLE + EnumChatFormatting.UNDERLINE, "skin url: " + args[1] + ":", url);
     				return;
     			}
     			else if(args[0].equals("getCapability"))
     			{
     				if(args.length != 2)
     					throw new WrongUsageException("/skin getCapability [playername]");
     				EntityPlayerMP player = (EntityPlayerMP)sender;
     				if(player.mcServer.getPlayerList().getPlayerByUsername(args[1]) == null)
     					throw new WrongUsageException("player isn't currently logged in:" + args[1]);
     				
     				CapSkin cap = (CapSkin) CapRegHandler.getCapability(args[1],new ResourceLocation(Reference.MODID + ":" + "skin") );
     				if(cap == null)
     					throw new WrongUsageException("capability of player's skin returned null report this to lan-essentials as an issue");
     				if(JavaUtil.isURL(cap.skin))
     					PlayerUtil.sendURL((EntityPlayer) sender, "skinCapability:", cap.skin);
     				else
     					PlayerUtil.sendClipBoard((EntityPlayer) sender, EnumChatFormatting.AQUA,EnumChatFormatting.DARK_PURPLE, "skinCapability:", cap.skin);
     				return;
     			}
     			else if(JavaUtil.isURL(args[0]) && args.length != 2)
     				throw new WrongUsageException("/skin [url, isSteve]",new Object[0]);
     			long time = System.currentTimeMillis();
     			EntityPlayerMP player = (EntityPlayerMP)sender;
     			CapContainer container = CapRegHandler.getCapContainer(player);
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
     		  catch(Exception e)
     		  {
     			 PlayerUtil.printChat((EntityPlayerMP) sender, EnumChatFormatting.RED, "", "Ussage:" + e.getMessage());
     		  }
     	   }
        });
        t.start();
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
	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
		List<String> list = args.length != 1 && args.length != 2 ? new ArrayList() : JavaUtil.asArray(server.getOnlinePlayerNames());
		list.add(0,"getURL");
		list.add(1,"getCapability");
		return CommandHome.orderList(list, args);
    }
}
