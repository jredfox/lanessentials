package com.jredfox.lanessentials.commands.network;

import com.evilnotch.lib.minecraft.util.EnumChatFormatting;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;
import com.jredfox.lanessentials.LanEssentials;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandServerIP extends CommandBase{
	@Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

	@Override
	public String getName() {
		return "getServerIp";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/getServerIp";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		try
		{
			String ip = JavaUtil.getPublicIp();
			int port = LanEssentials.proxy.getServerPort(server);
			String address = port > 0 ? (ip + ":" + port) : ip;
			PlayerUtil.sendClipBoard((EntityPlayer)sender, EnumChatFormatting.DARK_AQUA + "Server IP:", EnumChatFormatting.YELLOW + EnumChatFormatting.UNDERLINE + address);
			PlayerUtil.copyClipBoard((EntityPlayer)sender, address);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
