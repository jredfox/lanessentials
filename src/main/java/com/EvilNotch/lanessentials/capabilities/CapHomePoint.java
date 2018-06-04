package com.EvilNotch.lanessentials.capabilities;

import net.minecraft.util.math.BlockPos;

public class CapHomePoint 
{
	public Pos pos;
	public int dimId;
	public String name;
	public CapHomePoint(Pos pos,int i,String s)
	{
		this.pos = pos;
		this.dimId = i;
		this.name = s;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof CapHomePoint))
			return false;
		CapHomePoint chp = (CapHomePoint)obj;
		return this.name.equals(chp.name);
	}
	@Override
	public String toString(){return this.name;}
}
