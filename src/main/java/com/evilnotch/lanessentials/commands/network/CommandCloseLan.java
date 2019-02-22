package com.evilnotch.lanessentials.commands.network;

import com.evilnotch.lanessentials.MainMod;
import com.evilnotch.lib.minecraft.util.PlayerUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
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
	
	@Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
		if(!(sender instanceof EntityPlayerMP))
			return false;
		EntityPlayerMP player = (EntityPlayerMP)sender;
		return PlayerUtil.isPlayerOwner(player) || super.checkPermission(server, sender);
	}

}
