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

public class CommandHomeRemove extends CommandBase{

	@Override
	public String getName() {
		return "homeRemove";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "homeRemove [homename]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP) || args.length != 1)
			throw new WrongUsageException("unable to find player",new Object[0]);
		EntityPlayerMP p = (EntityPlayerMP)sender;
		CapHome ch = (CapHome) CapabilityReg.getCapabilityConatainer(p).getCapability(new ResourceLocation(Reference.MODID + ":" + "home"));
		ch.removePoint(args[0]);
	}
}
