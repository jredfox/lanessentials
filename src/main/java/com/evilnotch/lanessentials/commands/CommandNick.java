package com.evilnotch.lanessentials.commands;

import com.evilnotch.lanessentials.LanEssentials;
import com.evilnotch.lanessentials.api.CapHandler;
import com.evilnotch.lanessentials.api.CapUtil;
import com.evilnotch.lanessentials.caps.CapNick;
import com.evilnotch.lanessentials.packets.PacketNick;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class CommandNick extends CommandBase{
	
	@Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

	@Override
	public String getName() {
		return "nick";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "nick [displayname]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayerMP)) return;
		EntityPlayerMP player = (EntityPlayerMP) sender;
		String nick = args.length == 0 ? player.getName() : args[0];
		CapHandler.setNick(player, nick, true);
	}
}
