package com.EvilNotch.lanessentials.commands;

import com.EvilNotch.lanessentials.Reference;
import com.EvilNotch.lanessentials.capabilities.CapAbility;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class CommandFly extends CommandBase
{

	@Override
	public String getName() 
	{
		return "fly";
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "/fly";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(!(sender instanceof EntityPlayerMP))
			return;
		EntityPlayerMP p = (EntityPlayerMP)sender;
		CapAbility cap = (CapAbility) CapabilityReg.getCapabilityConatainer(p).getCapability(new ResourceLocation(Reference.MODID + ":" + "ability"));
		cap.flyEnabled = !cap.flyEnabled;
		p.capabilities.allowFlying =  cap.flyEnabled;
		
		if(!p.capabilities.allowFlying)
			p.capabilities.isFlying = false;
		p.sendPlayerAbilities();
	}

}
