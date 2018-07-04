package com.EvilNotch.lanessentials.commands.network;

import java.util.List;

import com.EvilNotch.lanessentials.MainMod;
import com.EvilNotch.lanessentials.api.LanUtil;
import com.EvilNotch.lib.minecraft.EntityUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;

public class CommandOpenToNet extends CommandBase{
	
	@Override
	public String getName() {
		return "openToInternet";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/openToInternet [port]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		int port = args.length == 1 ? Integer.parseInt(args[0]) : LanUtil.getMCPortSafley(server);
		MainMod.proxy.closeLan(server);
		String s = LanUtil.shareToLAN(port,(WorldServer) sender.getEntityWorld());
		if(s != null)
		{
			LanUtil.schedulePortForwarding(port, "TCP");
			if(sender instanceof EntityPlayerMP)
				EntityUtil.printChat((EntityPlayer) sender, "", "", "Hosted Lan on Port:" + port);
			else
				server.sendMessage(new TextComponentString("Hosted Lan on Port:" + port));
		}
		else
		{ 
			if(sender instanceof EntityPlayerMP)
				EntityUtil.printChat((EntityPlayer) sender, "", "", "Unable to Bind Port To Lan");
			else
				server.sendMessage(new TextComponentString("Unable to Bind Port To Lan:" + port));
		}
	}

}
