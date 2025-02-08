package com.jredfox.lanessential.commands.cap;

import com.jredfox.lanessential.cap.CapAbility;
import com.jredfox.lanessential.cap.CapHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandGod extends CommandBase
{

	@Override
	public String getName() {
		return "god";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/god";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP epmp = (EntityPlayerMP)sender;
		if(epmp.isCreative() || epmp.isSpectator())
			return;
		
		CapAbility c = CapHandler.getCapAbility(epmp);
		c.godMode = !c.godMode;
		c.sync(epmp);
	}

}
