package com.EvilNotch.lanessentials.commands.network;

import com.EvilNotch.lanessentials.api.LanUtil;
import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.minecraft.EnumChatFormatting;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandPortForward extends CommandBase{

	@Override
	public String getName() {
		return "portForward";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/portForward";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(!(sender instanceof EntityPlayerMP))
			throw new WrongUsageException("Sender must be player",new Object[0]);
		EntityPlayerMP p = (EntityPlayerMP)sender;
		if(!LanUtil.ports.isEmpty())
		{
			EntityUtil.printChat(p, EnumChatFormatting.RED, "", "Closing Ports");
			LanUtil.stopPorts();
		}
		String port = LanUtil.getMCPort(server);
		int actualPort = Integer.parseInt(port);
		if(actualPort <= 0)
			throw new WrongUsageException("Lan Must Be open Before Port Forwarding",new Object[0]);
		if(actualPort > 65535)
			throw new WrongUsageException("Invalid Port",new Object[0]);
		EntityUtil.printChat(p, EnumChatFormatting.WHITE, "", "Starting Port Forwarding. This command is still loading even if you don't think so");
		LanUtil.schedulePortForwarding(actualPort, "TCP");
	}

}
