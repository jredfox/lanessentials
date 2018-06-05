package com.EvilNotch.lanessentials.capabilities;

import net.minecraft.util.math.BlockPos;

public class Pos{
	
	public double posY;
	public double posX;
	public double posZ;
	public int x;
	public int y;
	public int z;
	
	public Pos(double x, double y, double z) 
	{
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.x = (int) x;
		this.y = (int) y;
		this.z = (int) z;
	}
	
	public Pos(BlockPos p, double y) 
	{
		this((double)p.getX(),y,(double)p.getZ() );
	}
	public Pos(int x, int y, int z)
	{
		this((double)x,(double)y,(double)z);
	}

	public double getActualX(){
		return this.posX;
	}
	public double getActualY(){
		return this.posY;
	}
	public double getActualZ(){
		return this.posZ;
	}

	public int getX() {
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public int getZ(){
		return this.z;
	}

}
