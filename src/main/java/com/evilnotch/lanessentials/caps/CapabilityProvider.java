package com.evilnotch.lanessentials.caps;

import com.evilnotch.lanessentials.api.LanFields;
import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.registry.ICapabilityRegistry;

import net.minecraft.entity.player.EntityPlayerMP;

public class CapabilityProvider implements ICapabilityRegistry<EntityPlayerMP>
{
	@Override
	public void register(EntityPlayerMP p, CapContainer c) 
	{
		c.registerCapability(LanFields.HOME, new CapHome());
		c.registerCapability(LanFields.ABILITY, new CapAbility());
		c.registerCapability(LanFields.NICK, new CapNick());
		c.registerCapability(LanFields.SPEED, new CapSpeed());
	}

	@Override
	public Class getObjectClass() 
	{
		return EntityPlayerMP.class;
	}
}
