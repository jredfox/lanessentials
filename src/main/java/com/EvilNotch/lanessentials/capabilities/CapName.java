package com.EvilNotch.lanessentials.capabilities;

import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapability;
import com.EvilNotch.lib.util.ICopy;
import com.EvilNotch.lib.util.JavaUtil;

import net.minecraft.nbt.NBTTagCompound;

public class CapName implements ICapability{

	public String name;
	
	public CapName(){
		this.name = null;
	}
	public CapName(String name) {
		this.name = name;
	}

	@Override
	public ICopy copy() {
		return new CapName(this.name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound tag = nbt;
		this.name = nbt.getString("Name");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Name", name);
	}

}
