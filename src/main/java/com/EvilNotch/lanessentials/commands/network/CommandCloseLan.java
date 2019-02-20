package com.evilnotch.lanessentials.commands.network;

import com.evilnotch.lanessentials.MainMod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandCloseLan extends CommandBase{

	@Override
	public String getName() {
		return "closeLan";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/closeLan";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		MainMod.proxy.closeLan(server);
	}

}
