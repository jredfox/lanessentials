package com.EvilNotch.lanessentials;

import com.EvilNotch.lanessentials.capabilities.CapAbility;
import com.EvilNotch.lanessentials.capabilities.CapCape;
import com.EvilNotch.lanessentials.capabilities.CapHome;
import com.EvilNotch.lanessentials.capabilities.CapNick;
import com.EvilNotch.lanessentials.capabilities.CapSkin;
import com.EvilNotch.lanessentials.capabilities.CapSpeed;
import com.EvilNotch.lib.minecraft.content.capabilites.registry.CapContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.IPCapabilityReg;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class CapabilityProvider implements IPCapabilityReg
{
	@Override
	public void register(EntityPlayerMP p,CapContainer c) 
	{
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "home"), new CapHome());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "ability"), new CapAbility());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "skin"), new CapSkin());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "nick"), new CapNick());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "cape"), new CapCape());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "speed"), new CapSpeed());
	}
}
