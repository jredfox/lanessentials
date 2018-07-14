package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lib.minecraft.EntityUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CommandSpawn extends CommandBase
{

	@Override
	public String getName() {
		return "spawn";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/" + getName();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		int dimension = 0;
		
		if(!(sender instanceof EntityPlayer) && args.length != 1)
		{
			System.out.println("sender must be a player"); 
		}
		EntityPlayerMP ep = null;
		if(args.length == 1)
		{
			ep = server.getPlayerList().getPlayerByUsername(args[0]);
			if(ep == null)
				throw new WrongUsageException("player isn't currently logged in:" + args[0]);
		}
		if(args.length == 0)
			ep = (EntityPlayerMP)sender;
		EntityUtil.teleportSpawn(ep,server,dimension);	
	}

	/**
     * get selectors working
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }

}
