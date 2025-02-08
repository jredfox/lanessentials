package com.jredfox.lanessential.commands.vanilla;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandPardonPlayer;
import net.minecraft.server.MinecraftServer;

public class CMDPardonPlayer extends CommandPardonPlayer
{
    /**
     * Get a list of aliases for this command. <b>Never return null!</b>
     */
    public List<String> getAliases()
    {
        return Arrays.<String>asList("pardon");
    }
    /**
     * Gets the name of the command
     */
    public String getName()
    {
        return "unban";
    }
    
    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
    }
}