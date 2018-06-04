package com.EvilNotch.lanessentials.commands;

import java.io.File;
import java.util.Scanner;

import com.EvilNotch.lanessentials.SkinUpdater;
import com.EvilNotch.lanessentials.dl.Dlfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandSkin  extends CommandBase
{

	@Override
	public String getName() {
		return "skin";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/skin [skinName]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		if(args.length==1)
		{
			if(sender instanceof EntityPlayerMP)
			{
				Dlfile.downloadSkinFile(args[0]);
				sender.sendMessage(new TextComponentString("HI"));
				EntityPlayerMP epmp = (EntityPlayerMP) sender;
				PropertyMap pm = epmp.getGameProfile().getProperties();		
				
				String line = "";
				try {
					File file = new File("config/skinFiles/" + args[0] + ".txt");
					Scanner sc = new Scanner(file);  
			    	line = sc.nextLine();
			    	sc.close();
				} catch (Exception e) {
					return;
				}
				String texture = line.substring(line.indexOf("\"value\":\"")+9,line.indexOf("\",\"signature\":\""));	
				String key = line.substring(line.indexOf("\",\"signature\":\"")+15,line.lastIndexOf('"'));
				if(texture.isEmpty()||key.isEmpty())
				{
					return;
				}
				if(pm.get("textures").isEmpty())
					pm.put("textures", new Property("textures", texture,key));
				else
				{
					pm.removeAll("textures");
					pm.put("textures", new Property("textures", texture,key));
				}
				
				SkinUpdater.updateSkin(epmp);
				
			}
			
		}else
		if(sender instanceof EntityPlayerMP)
		{	
			
			//if player has official skin use it otherwise fall back and set skin to default no skin
			if(Dlfile.downloadSkinFile(sender.getName()))
			{
				try
				{
					String[] s = new String[]{sender.getName()};
					execute(server, sender, s);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}else
			{
				try
				{
					String[] s = new String[]{"steve"};
					execute(server, sender, s);
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	
	}

}
