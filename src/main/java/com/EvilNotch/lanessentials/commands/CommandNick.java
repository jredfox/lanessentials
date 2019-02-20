package com.evilnotch.lanessentials.commands;

import com.evilnotch.lanessentials.Reference;
import com.evilnotch.lanessentials.api.CapUtil;
import com.evilnotch.lanessentials.capabilities.CapNick;
import com.evilnotch.lib.minecraft.capability.registry.CapRegHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class CommandNick extends CommandBase{

	@Override
	public String getName() {
		return "nick";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "nick [displayname]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		EntityPlayer player = (EntityPlayer) sender;
		CapNick name = (CapNick) CapRegHandler.getCapContainer(player).getCapability(new ResourceLocation(Reference.MODID + ":" + "nick"));
		name.nick = args.length == 1 ? args[0] : player.getName();
		CapUtil.updateNickName((EntityPlayerMP) player);
	}
}
