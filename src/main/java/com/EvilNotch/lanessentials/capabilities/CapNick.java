package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.capabilites.registry.CapContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.IPCapability;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapNick implements IPCapability{

	public String nick = "";
	
	public CapNick(){}
	
	public CapNick(String username){
		this.nick = username;
	}

	@Override
	public void readFromNBT(EntityPlayerMP p,NBTTagCompound nbt, CapContainer c) 
	{
		this.nick = nbt.getString("nick");
	}

	@Override
	public void writeToNBT(EntityPlayerMP p,NBTTagCompound nbt, CapContainer c) {
		nbt.setString("nick", this.nick);
	}

}
