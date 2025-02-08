package com.evilnotch.lanessentials.commands.cap;

import com.evilnotch.lanessentials.api.CapHandler;
import com.evilnotch.lanessentials.cap.CapAbility;

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
		return "/speed [float]";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayerMP))
			return;
		CapAbility c = CapHandler.getCapAbility((EntityPlayerMP) sender);
		c.speed = args.length == 0 ? 0 : Float.parseFloat(args[0]);
		c.sync((EntityPlayerMP) sender);
	}
	
}
