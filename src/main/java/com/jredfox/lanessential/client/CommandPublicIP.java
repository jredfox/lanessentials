package com.jredfox.lanessential.client;

import com.evilnotch.lib.minecraft.util.EnumChatFormatting;
import com.evilnotch.lib.minecraft.util.PlayerUtil;
import com.evilnotch.lib.util.JavaUtil;

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
			PlayerUtil.sendClipBoard((EntityPlayer)sender, "Your Public Ip Adress is:", ip);
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
