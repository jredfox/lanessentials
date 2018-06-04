package com.EvilNotch.lanessentials.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandFly extends CommandBase
{

	@Override
	public String getName() {
		return "fly";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/fly";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP epmp = (EntityPlayerMP)sender;
		epmp.capabilities.allowFlying = !epmp.capabilities.allowFlying;
		
		if(!epmp.capabilities.allowFlying)
			epmp.capabilities.isFlying = false;
		epmp.sendPlayerAbilities();
	}

}
