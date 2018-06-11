package com.EvilNotch.lanessentials.commands;

import java.io.File;
import java.util.Scanner;

import com.EvilNotch.lanessentials.SkinUpdater;
import com.EvilNotch.lanessentials.dl.DLFile;
import com.EvilNotch.lib.util.JavaUtil;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
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
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		EntityPlayerMP player = (EntityPlayerMP)sender;
		updateSkin(args[0],player,true);
	}
	
	public static void updateSkin(String username,EntityPlayerMP player,boolean packets)
	{
		PropertyMap pm = player.getGameProfile().getProperties();
		String uuid = DLFile.getUUID(username);
		String[] props = DLFile.getProperties(uuid);
		pm.removeAll("textures");
		pm.put("textures", new Property("textures", props[0],props[1]));
		if(packets)
		{
			SkinUpdater.updateSkin(player);
		}
	}
}
