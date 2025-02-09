package com.jredfox.lanessentials.commands.vanilla;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBanPlayer;
import net.minecraft.server.MinecraftServer;

public class CMDBanPlayer extends CommandBanPlayer
{
    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
    }
}