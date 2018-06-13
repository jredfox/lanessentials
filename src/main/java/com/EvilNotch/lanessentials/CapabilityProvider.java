package com.EvilNotch.lanessentials;

import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityHandler;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityReg;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapabilityProvider;
import com.EvilNotch.lanessentials.capabilities.CapAbility;
import com.EvilNotch.lanessentials.capabilities.CapCape;
import com.EvilNotch.lanessentials.capabilities.CapHome;
import com.EvilNotch.lanessentials.capabilities.CapNick;
import com.EvilNotch.lanessentials.capabilities.CapSkin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class CapabilityProvider implements ICapabilityProvider
{
	@Override
	public void register(EntityPlayer p,CapabilityContainer c) 
	{
		CapabilityReg.registerCapability(p, new ResourceLocation(Reference.MODID + ":" + "home"), new CapHome(),c);
		CapabilityReg.registerCapability(p, new ResourceLocation(Reference.MODID + ":" + "ability"), new CapAbility(),c);
		CapabilityReg.registerCapability(p, new ResourceLocation(Reference.MODID + ":" + "skin"), new CapSkin(),c);
		CapabilityReg.registerCapability(p, new ResourceLocation(Reference.MODID + ":" + "nick"), new CapNick(),c);
		CapabilityReg.registerCapability(p, new ResourceLocation(Reference.MODID + ":" + "cape"), new CapCape(),c);
		System.out.println("registerng Capabilities:" + (CapabilityReg.getCapabilityConatainer(p) == null) );
	}
}
