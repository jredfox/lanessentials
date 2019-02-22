package com.evilnotch.lanessentials.capabilities;

import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapability;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class CapSkin implements ICapability<EntityPlayerMP>{
	
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