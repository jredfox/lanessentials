package com.evilnotch.lanessentials.caps;

import com.evilnotch.lanessentials.api.LEFields;
import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.registry.ICapabilityRegistry;

import net.minecraft.entity.player.EntityPlayerMP;

public class CapabilityProvider implements ICapabilityRegistry<EntityPlayerMP>
{
	@Override
	public void register(EntityPlayerMP p, CapContainer c) 
	{
		c.registerCapability(LEFields.HOME, new CapHome());
		c.registerCapability(LEFields.ABILITY, new CapAbility());
		c.registerCapability(LEFields.NICK, new CapNick());
	}

	@Override
	public Class getObjectClass() 
	{
		return EntityPlayerMP.class;
	}
}
