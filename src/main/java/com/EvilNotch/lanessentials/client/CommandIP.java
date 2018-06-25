package com.EvilNotch.lanessentials.client;

import java.net.UnknownHostException;

import com.EvilNotch.lib.minecraft.EntityUtil;
import com.EvilNotch.lib.minecraft.EnumChatFormatting;
import com.EvilNotch.lib.util.JavaUtil;

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
			EntityUtil.sendClipBoard(EnumChatFormatting.WHITE,EnumChatFormatting.BLUE + EnumChatFormatting.UNDERLINE,(EntityPlayer)sender, "Your Ipv4 Adress is:", ip);
		} 
		catch (UnknownHostException e) 
		{
			EntityUtil.printChat((EntityPlayer)sender, EnumChatFormatting.RED, "", "Unable to fetch ipv4 adress");
		}
		
	}

	@Override
	public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
		return true;
	}
	

}
