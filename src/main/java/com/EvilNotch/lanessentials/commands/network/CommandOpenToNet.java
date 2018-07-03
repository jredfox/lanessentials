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
		List<EntityPlayerMP> players = server.getPlayerList().getPlayers();
		for(EntityPlayerMP p : players)
			if(!EntityUtil.isPlayerOwner(p))
				EntityUtil.disconnectPlayer(p, new TextComponentString("Lan World Resetting"));
		MainMod.proxy.closeLan(server);
		int port = args.length == 1 ? Integer.parseInt(args[0]) : LanUtil.getRandomPort();
		String s = LanUtil.shareToLAN(port, GameType.SURVIVAL, false);
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
				server.sendMessage(new TextComponentString("Unable to Bind Port To Lan" + port));
		}
	}

}
