package com.EvilNotch.lanessentials.capabilities;

import net.minecraft.util.math.BlockPos;

public class Pos extends BlockPos{
	
	public double posY;
	public double posX;
	public double posZ;
	
	public Pos(double x, double y, double z) 
	{
		super(x, y, z);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
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

}
