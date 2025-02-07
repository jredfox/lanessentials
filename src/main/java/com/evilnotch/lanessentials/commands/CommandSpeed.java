package com.evilnotch.lanessentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandSpeed extends CommandBase
{

	@Override
	public String getName() {
		return "speed";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/speed [walk/fly] [float]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		
	}
}
