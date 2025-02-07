package com.evilnotch.lanessentials.commands.vanilla;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandPardonIp;
import net.minecraft.server.MinecraftServer;

public class CMDPardonIp extends CommandPardonIp
{
    /**
     * Get a list of aliases for this command. <b>Never return null!</b>
     */
    public List<String> getAliases()
    {
        return Arrays.<String>asList("pardon-ip");
    }
	
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "unban-ip";
    }
    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
    }
}