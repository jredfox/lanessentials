package com.jredfox.lanessential.commands;

import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.server.MinecraftServer;

public class CommandSmite extends CommandBase {

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(args.length == 0 && !(sender instanceof Entity))
			throw new WrongUsageException("/smite [entity]",new Object[0]);
		
		Entity entity = args.length == 0 ? (Entity) sender : getEntity(server, sender, args[0]);
		boolean fire = false;
		if(args.length > 0)
		{
			String tst = args[args.length-1];
			if(JavaUtil.isStringBoolean(tst))
				fire = Boolean.parseBoolean(tst);
		}
		entity.world.addWeatherEffect(new EntityLightningBolt(entity.world, entity.posX, entity.posY, entity.posZ, !fire));
	}

	@Override
	public String getName() {
		return "smite";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/smite [entity]";
	}
	
    /**
     * get selectors working
     */
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }

}
