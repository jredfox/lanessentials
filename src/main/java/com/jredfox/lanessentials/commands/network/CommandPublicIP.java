package com.jredfox.lanessentials.commands.network;

import com.evilnotch.lib.minecraft.util.EnumChatFormatting;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;
import com.jredfox.lanessentials.LanEssentials;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;

public class CommandPublicIP extends CommandBase implements IClientCommand{
	
    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
	
	@Override
	public String getName() {
		return "publicIp";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/publicIp";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayer))
			return;
		try 
		{
			String ip = JavaUtil.getPublicIp();
			int port = LanEssentials.proxy.getServerPort(server);
			if(port > 0)
				ip += ":" + port;
			PlayerUtil.sendClipBoard((EntityPlayer)sender, EnumChatFormatting.BLUE + "Public IP:", EnumChatFormatting.YELLOW + EnumChatFormatting.UNDERLINE + ip);
			PlayerUtil.copyClipBoard((EntityPlayer)sender, ip);
		}
		catch(Exception e) 
		{
			PlayerUtil.printChat((EntityPlayer)sender, EnumChatFormatting.RED, "", "Unable to fetch public ip adress");
		}
	}

	@Override
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
		return true;
	}

}
