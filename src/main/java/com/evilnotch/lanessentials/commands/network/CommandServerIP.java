package com.evilnotch.lanessentials.commands.network;

import com.evilnotch.lib.minecraft.util.EnumChatFormatting;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
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
			int port = server.getServerPort();
			//TODO: sync actual server ip with client then copy clipboard
			PlayerUtil.sendClipBoard((EntityPlayerMP)sender, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.YELLOW, "", "ServerIP:", ip + ":" + port, true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
