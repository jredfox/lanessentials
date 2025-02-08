package com.jredfox.lanessentials.commands.cap;

import com.jredfox.lanessentials.cap.CapAbility;
import com.jredfox.lanessentials.cap.CapHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandSpeedFly extends CommandBase
{

	@Override
	public String getName() {
		return "speedFly";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/speedFly [float]";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayerMP))
			return;
		CapAbility c = CapHandler.getCapAbility((EntityPlayerMP) sender);
		c.speedFly = args.length == 0 ? 0 : Float.parseFloat(args[0]);
		c.sync((EntityPlayerMP) sender);
	}
	
}
