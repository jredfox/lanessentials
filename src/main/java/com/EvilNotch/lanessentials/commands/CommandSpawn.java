package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lib.minecraft.EntityUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
		return "/" + getName();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		int dimension = 0;
		BlockPos bp = server.getWorld(dimension).getSpawnPoint();
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
		
		EntityUtil.telePortEntity(ep, server, bp.getX() + 0.5, bp.getY(), bp.getZ() + 0.5, ep.rotationYaw, ep.rotationPitch, dimension);
		boolean flag = false;
		while (!ep.world.getCollisionBoxes(ep, ep.getEntityBoundingBox()).isEmpty() && ep.posY < 256.0D)
	    {
	    	ep.setPosition(ep.posX, ep.posY + 1.0D, ep.posZ);
	    	flag = true;
	    }
		if(flag)
			ep.connection.setPlayerLocation(ep.posX, ep.posY, ep.posZ, ep.rotationYaw, ep.rotationPitch);
	}
	
    /**
     * get selectors working
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }

}
