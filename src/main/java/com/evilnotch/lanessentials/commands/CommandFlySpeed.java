package com.evilnotch.lanessentials.commands;

import com.evilnotch.lanessentials.Reference;
import com.evilnotch.lanessentials.api.LanFeilds;
import com.evilnotch.lanessentials.capabilities.CapSpeed;
import com.evilnotch.lib.api.ReflectionUtil;
import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.registry.CapabilityRegistry;
import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class CommandFlySpeed extends CommandBase
{

	@Override
	public String getName() {
		return "flyspeed";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/flyspeed [float]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		CapContainer container = CapabilityRegistry.getCapContainer((EntityPlayer)sender);
		CapSpeed cap = (CapSpeed) container.getCapability(new ResourceLocation(Reference.MODID + ":" + "speed"));
		if(args.length == 1)
		{
			EntityPlayerMP epmp = (EntityPlayerMP)sender;
			String strfloat = args[0];
			if(!JavaUtil.isStringNum(strfloat))
				throw new WrongUsageException(getUsage(sender),new Object[0]);
			
			float flyspeed = Float.parseFloat(strfloat);
			PlayerCapabilities pc = epmp.capabilities;
			ReflectionUtil.setObject(pc, flyspeed, PlayerCapabilities.class, LanFeilds.flySpeed);
			cap.fly = flyspeed;
			cap.hasFlySpeed = true;
			epmp.sendPlayerAbilities();
		}	
		if(args.length == 0)
		{
			float defaultFly = 0.05F;
			EntityPlayerMP epmp = (EntityPlayerMP)sender;
			PlayerCapabilities pc = epmp.capabilities;
			ReflectionUtil.setObject(pc, defaultFly, PlayerCapabilities.class, LanFeilds.flySpeed);
			cap.fly = 0;
			cap.hasFlySpeed = false;
			epmp.sendPlayerAbilities();
		}
	}
}
