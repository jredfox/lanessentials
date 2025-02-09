package com.jredfox.lanessentials.commands.network;

import java.net.UnknownHostException;

import com.evilnotch.lib.minecraft.util.EnumChatFormatting;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;
import com.jredfox.lanessentials.LanEssentials;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

public class CommandIP extends CommandBase implements IClientCommand{
	
    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

	@Override
	public String getName() {
		return "ipv4";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/ipv4";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayer))
			return;
		try 
		{
			String ip = JavaUtil.getIpv4();
			int port = LanEssentials.proxy.getServerPort(server);
			if(port > 0)
				ip += ":" + port;
			PlayerUtil.sendClipBoard((EntityPlayer)sender, EnumChatFormatting.BLUE + "IPv4:", EnumChatFormatting.YELLOW + EnumChatFormatting.UNDERLINE + ip);
			PlayerUtil.copyClipBoard((EntityPlayer)sender, ip);
		}
		catch (UnknownHostException e) 
		{
			PlayerUtil.printChat((EntityPlayer)sender, EnumChatFormatting.RED, "", "Unable to fetch ipv4 adress");
		}
	}

	@Override
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
		return true;
	}
	

}
