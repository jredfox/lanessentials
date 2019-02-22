package com.evilnotch.lanessentials.commands.vanilla;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBanIp;
import net.minecraft.server.MinecraftServer;

public class CMDBanIp extends CommandBanIp {
	
    /**
     * Check if the given ICommandSender has permission to execute this command
     */
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return sender.canUseCommand(this.getRequiredPermissionLevel(), this.getName());
    }

}
