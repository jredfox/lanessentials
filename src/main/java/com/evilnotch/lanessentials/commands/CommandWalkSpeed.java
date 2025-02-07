package com.evilnotch.lanessentials.commands;

import com.evilnotch.lanessentials.LanEssentials;
import com.evilnotch.lanessentials.api.LanFields;
import com.evilnotch.lanessentials.caps.CapSpeed;
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

public class CommandWalkSpeed extends CommandBase
{

	@Override
	public String getName() {
		return "walkspeed";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/walkspeed [float]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		CapContainer container = CapabilityRegistry.getCapContainer((EntityPlayer)sender);
		CapSpeed cap = (CapSpeed) container.getCapability(new ResourceLocation(LanEssentials.MODID + ":" + "speed"));
		if(args.length == 1)
		{
			EntityPlayerMP epmp = (EntityPlayerMP)sender;
			String strfloat = args[0];
			if(!JavaUtil.isStringNum(strfloat))
				throw new WrongUsageException(getUsage(sender),new Object[0]);
			float walkspeed = Float.parseFloat(strfloat);
			PlayerCapabilities pc = epmp.capabilities;
			ReflectionUtil.setObject(pc, walkspeed, PlayerCapabilities.class, LanFields.walkSpeed);
			cap.walk = walkspeed;
			cap.hasWalkSpeed = true;
			epmp.sendPlayerAbilities();
		}
		if(args.length == 0)
		{
			float defaultWalk = 0.1F;
			EntityPlayerMP epmp = (EntityPlayerMP)sender;
			PlayerCapabilities pc = epmp.capabilities;
			ReflectionUtil.setObject(pc, defaultWalk, PlayerCapabilities.class, LanFields.walkSpeed);
			cap.walk = 0;
			cap.hasWalkSpeed = false;
			epmp.sendPlayerAbilities();
		}	
	}
}
