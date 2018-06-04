package com.EvilNotch.lanessentials;

import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapabilityProvider;
import com.EvilNotch.lanessentials.capabilities.CapHome;
import com.EvilNotch.lanessentials.capabilities.CapName;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class CapabilityProvider implements ICapabilityProvider
{
	@Override
	public void register(EntityPlayer p) 
	{
		CapabilityReg.registerCapability(p, new ResourceLocation(Reference.MODID+":home"), new CapHome());
		CapabilityReg.registerCapability(p, new ResourceLocation(Reference.MODID+":Name"), new CapName());
		System.out.println("registerng Capabilities:" + (CapabilityReg.getCapabilityConatainer(p) == null) );	
	}
}
