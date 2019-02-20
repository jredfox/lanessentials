package com.evilnotch.lanessentials.commands;

import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandSetHealth extends CommandBase
{

	@Override
	public String getName() {
		return "sethealth";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/sethealth [int]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP) || args.length != 1 || !JavaUtil.isStringNum(args[0]))
			throw new WrongUsageException(getUsage(sender),new Object[0]);
		
		EntityPlayerMP epmp = (EntityPlayerMP)sender;
		int health = Integer.parseInt(args[0]);
		epmp.setHealth(health);	
		sender.sendMessage(new TextComponentString("Set You To " + epmp.getHealth() + " Health"));
	}

}
