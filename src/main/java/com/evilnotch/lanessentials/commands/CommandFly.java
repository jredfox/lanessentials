package com.evilnotch.lanessentials.commands;

import com.evilnotch.lanessentials.api.CapHandler;
import com.evilnotch.lanessentials.caps.CapPlayer;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandFly extends CommandBase
{

	@Override
	public String getName() 
	{
		return "fly";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "/fly";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP p = (EntityPlayerMP)sender;
		CapPlayer cp = CapHandler.getCapAbility(p);
		cp.fly = !cp.fly;
	}

}
