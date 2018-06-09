package com.EvilNotch.lanessentials.capabilities;

import java.util.ArrayList;
import java.util.Iterator;

import com.EvilNotch.lanessentials.CfgLanEssentials;
import com.EvilNotch.lib.minecraft.content.ConfigLang;
import com.EvilNotch.lib.minecraft.content.pcapabilites.CapabilityContainer;
import com.EvilNotch.lib.minecraft.content.pcapabilites.ICapability;
import com.EvilNotch.lib.util.ICopy;
import com.EvilNotch.lib.util.JavaUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

public class CapHome implements ICapability {

	public ArrayList<CapHomePoint> capPoints = new ArrayList<CapHomePoint>();
	public int maxCount = CfgLanEssentials.maxHomeCount;
	
	public CapHome(){
		
	}
	
	public CapHome(ArrayList copyArrays) 
	{
		this.capPoints = copyArrays;
	}

	@Override
	public ICopy copy() 
	{
		return new CapHome(JavaUtil.copyArrays(this.capPoints));
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt,EntityPlayer p,CapabilityContainer c) 
	{
		this.capPoints = new ArrayList<CapHomePoint>();
		NBTTagList points = (NBTTagList)nbt.getTagList("Homes", 10);
		//if doesn't have key use configed one
		if(nbt.hasKey("HomeCount"))
			this.maxCount = nbt.getInteger("HomeCount");
		
		for(int i=0;i<points.tagCount();i++)
		{
			NBTTagCompound tag = points.getCompoundTagAt(i);
			CapHomePoint chp = new CapHomePoint(new Pos(tag.getInteger("x"), tag.getDouble("y"), tag.getInteger("z")),tag.getInteger("dim"),tag.getString("name"));
			capPoints.add(chp);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt,EntityPlayer p,CapabilityContainer c)
	{
		NBTTagList tagList = new NBTTagList();
		for(int i=0;i<capPoints.size();i++)
		{
			CapHomePoint chp = capPoints.get(i);
			NBTTagCompound compound = new NBTTagCompound();
			compound.setInteger("x",chp.pos.getX());
			compound.setDouble("y",chp.pos.getActualY());
			compound.setInteger("z",chp.pos.getZ());
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
}
