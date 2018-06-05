package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.capabilities.CapHome;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class CommandSetHomeCount extends CommandBase{

	@Override
	public String getName() {
		return "sethomeCount";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "sethomeCount [int-count]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			throw new WrongUsageException("unable to find player",new Object[0]);
		EntityPlayerMP epmp = (EntityPlayerMP) sender;
		CapHome ch = (CapHome) CapabilityReg.getCapabilityConatainer(epmp).getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
		ch.maxCount = Integer.parseInt(args[0]);
	}

}
