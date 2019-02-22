package com.evilnotch.lanessentials.commands.vanilla;

import com.mojang.authlib.GameProfile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.server.CommandPardonPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

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