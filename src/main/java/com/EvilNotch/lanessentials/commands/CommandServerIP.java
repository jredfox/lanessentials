package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lanessentials.proxy.ClientProxy;
import com.EvilNotch.lanessentials.proxy.ServerProxy;
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.minecraft.EnumChatFormatting;
import com.EvilNotch.lib.util.JavaUtil;

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
			String port = MainJava.isClient ? ClientProxy.getPort() : ServerProxy.getServerPort(server);
			EntityUtil.sendClipBoard(EnumChatFormatting.DARK_AQUA, EnumChatFormatting.YELLOW + EnumChatFormatting.UNDERLINE, (EntityPlayerMP)sender, "ServerIP:", ip + ":" + port);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
