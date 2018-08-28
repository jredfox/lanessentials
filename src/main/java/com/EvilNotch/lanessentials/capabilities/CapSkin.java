package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.capabilites.registry.CapContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.IPCapability;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapSkin implements IPCapability{
	
	public String skin = "";
	public boolean isAlex = false;
	
	public CapSkin(){}
	
	public CapSkin(String username){
		this.skin = username;
	}

	@Override
	public void readFromNBT(EntityPlayerMP p,NBTTagCompound nbt, CapContainer c) 
	{
		this.skin = nbt.getString("skin");
		if(this.skin.equals(""))
			this.skin = p.getName();
		this.isAlex = nbt.getBoolean("isAlex");
	}

	@Override
	public void writeToNBT(EntityPlayerMP p,NBTTagCompound nbt, CapContainer c) {
		nbt.setString("skin", this.skin);
		nbt.setBoolean("isAlex", this.isAlex);
	}

}