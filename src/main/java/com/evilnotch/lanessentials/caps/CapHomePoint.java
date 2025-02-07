package com.evilnotch.lanessentials.caps;

public class CapHomePoint 
{
	public Pos pos;
	public int dimId;
	public String name;
	public float yaw = 0.0F;
	public float pitch = 0.0F;
	public CapHomePoint(Pos pos,int i,String s,float y,float p)
	{
		this.pos = pos;
		this.dimId = i;
		this.name = s;
		this.yaw = y;
		this.pitch = p;
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
