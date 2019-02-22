package com.evilnotch.lanessentials;

import com.evilnotch.lanessentials.capabilities.CapAbility;
import com.evilnotch.lanessentials.capabilities.CapCape;
import com.evilnotch.lanessentials.capabilities.CapHome;
import com.evilnotch.lanessentials.capabilities.CapNick;
import com.evilnotch.lanessentials.capabilities.CapSkin;
import com.evilnotch.lanessentials.capabilities.CapSpeed;
import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.registry.ICapabilityRegistry;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class CapabilityProvider implements ICapabilityRegistry<EntityPlayerMP>
{
	@Override
	public void register(EntityPlayerMP p, CapContainer c) 
	{
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "home"), new CapHome());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "ability"), new CapAbility());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "skin"), new CapSkin());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "nick"), new CapNick());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "cape"), new CapCape());
		c.registerCapability(new ResourceLocation(Reference.MODID + ":" + "speed"), new CapSpeed());
	}

	@Override
	public Class getObjectClass() 
	{
		return EntityPlayerMP.class;
	}
}
