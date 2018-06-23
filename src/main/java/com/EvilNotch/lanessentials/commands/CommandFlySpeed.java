package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.capabilities.CapSpeed;
import com.EvilNotch.lib.Api.MCPMappings;
import com.EvilNotch.lib.Api.ReflectionUtil;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.util.Line.LineBase;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

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
		CapabilityContainer container = CapabilityReg.getCapabilityConatainer((EntityPlayer)sender);
		CapSpeed cap = (CapSpeed) container.getCapability(new ResourceLocation(Reference.MODID + ":" + "speed"));
		if(args.length == 1)
		{
			EntityPlayerMP epmp = (EntityPlayerMP)sender;
			String strfloat = args[0];
			if(!LineBase.isStringNum(strfloat))
				throw new WrongUsageException(getUsage(sender),new Object[0]);
			
			float flyspeed = Float.parseFloat(strfloat);
			PlayerCapabilities pc = epmp.capabilities;
			ReflectionUtil.setObject(pc, flyspeed, PlayerCapabilities.class, MCPMappings.getField(PlayerCapabilities.class, "flySpeed"));
			cap.fly = flyspeed;
			cap.hasFlySpeed = true;
			epmp.sendPlayerAbilities();
		}	
		if(args.length == 0)
		{
			float defaultFly = 0.05F;
			EntityPlayerMP epmp = (EntityPlayerMP)sender;
			PlayerCapabilities pc = epmp.capabilities;
			ReflectionUtil.setObject(pc, defaultFly, PlayerCapabilities.class, MCPMappings.getField(PlayerCapabilities.class, "flySpeed"));
			cap.fly = 0;
			cap.hasFlySpeed = false;
			epmp.sendPlayerAbilities();
		}
	}
}
