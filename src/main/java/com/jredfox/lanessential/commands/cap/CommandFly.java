package com.jredfox.lanessential.commands.cap;

import com.jredfox.lanessential.cap.CapAbility;
import com.jredfox.lanessential.cap.CapHandler;

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
		CapAbility cp = CapHandler.getCapAbility((EntityPlayerMP)sender);
		cp.fly = !cp.fly;
		cp.sync((EntityPlayerMP) sender);
	}

}
