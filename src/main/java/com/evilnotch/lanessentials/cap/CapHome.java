package com.evilnotch.lanessentials.cap;

import java.util.ArrayList;
import java.util.Iterator;

import com.evilnotch.lanessentials.LanEssentialsConfig;
import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapability;
import com.evilnotch.lib.util.JavaUtil;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class CapHome implements ICapability<EntityPlayerMP> {

	public ArrayList<CapHomePoint> capPoints = new ArrayList<CapHomePoint>();
	public int maxCount = LanEssentialsConfig.maxHomeCount;
	
	public CapHome(){
		
	}
	
	public CapHome(ArrayList copyArrays) 
	{
		this.capPoints = copyArrays;
	}

	@Override
	public void readFromNBT(EntityPlayerMP p,NBTTagCompound nbt,CapContainer c) 
	{
		this.capPoints = new ArrayList<CapHomePoint>();
		NBTTagList points = (NBTTagList)nbt.getTagList("Homes", 10);
		//if doesn't have key use configed one
		if(nbt.hasKey("HomeCount"))
			this.maxCount = nbt.getInteger("HomeCount");
		
		for(int i=0;i<points.tagCount();i++)
		{
			NBTTagCompound tag = points.getCompoundTagAt(i);
			CapHomePoint chp = new CapHomePoint(new Pos(tag.getInteger("x"), tag.getDouble("y"), tag.getInteger("z")),tag.getInteger("dim"),tag.getString("name"),tag.getFloat("yaw"),tag.getFloat("pitch"));
			capPoints.add(chp);
		}
	}

	@Override
	public void writeToNBT(EntityPlayerMP p,NBTTagCompound nbt,CapContainer c)
	{
		NBTTagList tagList = new NBTTagList();
		for(int i=0;i<capPoints.size();i++)
		{
			CapHomePoint chp = capPoints.get(i);
			NBTTagCompound compound = new NBTTagCompound();
			compound.setInteger("x",chp.pos.getX());
			compound.setDouble("y",chp.pos.getActualY());
			compound.setInteger("z",chp.pos.getZ());
			compound.setFloat("yaw", chp.yaw);
			compound.setFloat("pitch", chp.pitch);
			compound.setInteger("dim",chp.dimId);
			compound.setString("name", chp.name);
			tagList.appendTag(compound);
		}
		nbt.setTag("Homes", tagList);
		nbt.setInteger("HomeCount", this.maxCount);
	}
	
	public CapHomePoint getCapPoint(String s)
	{
		for(int i=0;i<this.capPoints.size();i++)
		{
			CapHomePoint chp = this.capPoints.get(i);
			if(s.equals(chp.name))
			{
				return chp;
			}
		}
		return null;
	}

	public void sethome(CapHomePoint chp) 
	{
		if(!this.capPoints.contains(chp))
		{
			this.capPoints.add(chp);
		}
		else
		{
			for(int i=0;i<this.capPoints.size();i++)
			{
				CapHomePoint cap = this.capPoints.get(i);
				if(cap.equals(chp))
				{
					this.capPoints.set(i, chp);
					break;
				}
			}
		}
	}

	public boolean removePoint(String str) 
	{
		Iterator<CapHomePoint> it = this.capPoints.iterator();
		while(it.hasNext())
		{
			CapHomePoint p = it.next();
			if(p.toString().equals(str))
			{
				it.remove();
				return true;
			}
		}
		return false;
	}
	public boolean containsPoint(String str)
	{
		for(CapHomePoint p : this.capPoints)
			if(p.toString().equals(str))
				return true;
		return false;
	}

	public void setMaxHomeCount(int max) 
	{
		if(max < maxCount)
		{
			this.capPoints = JavaUtil.toArray(this.capPoints.subList(0, max));
		}
		maxCount = max;
	}
}
