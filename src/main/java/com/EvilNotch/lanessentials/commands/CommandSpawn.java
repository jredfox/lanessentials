package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lib.minecraft.EntityUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandSpawn extends CommandBase
{

	@Override
	public String getName() {
		return "spawn";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/sapwn";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		int dimension = 0;
		BlockPos bp = server.getWorld(dimension).getSpawnPoint();
		if(!(sender instanceof EntityPlayer)&&args.length!=1)
		{
			System.out.println("sender must be a player"); 
		}
		EntityPlayer ep=null;
		if(args.length==1)
		{
			if(server.getPlayerList().getPlayerByUsername(args[0]) == null)
				throw new WrongUsageException("player isn't currently logged in:" + args[0]);
			else
				ep = server.getPlayerList().getPlayerByUsername(args[0]);	
		}
		if(args.length==0)
			ep = (EntityPlayer)sender;
		EntityUtil.telePortEntity(ep, server, bp.getX() + 0.5, bp.getY(), bp.getZ() + 0.5, ep.rotationYaw, ep.rotationPitch, dimension);
	}

}
