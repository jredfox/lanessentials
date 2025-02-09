package com.jredfox.lanessentials.commands;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.evilnotch.lib.minecraft.util.PlayerUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandKick extends CommandBase {
	
	 /**
     * Gets the name of the command
     */
	@Override
    public String getName()
    {
        return "kick";
    }
    /**
    * Gets the usage string for the command.
    */
    @Override
    public String getUsage(ICommandSender sender)
    {
       return "/kick [player]";
    }
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(args.length == 0)
			throw new WrongUsageException(getUsage(sender),new Object[0]);
		
		Entity e = getEntity(server, sender, args[0]);	
		String msg = "booted";
		if(args.length >= 2)
		{
			msg = "";
			for(int i = 1;i<args.length;i++)
				msg += args[i] + " ";
			msg = msg.trim();
		}
		if(e instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP)e;
			PlayerUtil.disconnectPlayer(player,new TextComponentString(msg));
		}
	}
	
    /**
     * Get a list of options for when the user presses the TAB key
     */
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length != 1 ? Collections.emptyList() : getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
    }

}
