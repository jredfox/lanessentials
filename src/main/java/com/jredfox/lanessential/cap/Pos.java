package com.jredfox.lanessential.cap;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Pos{
	
	protected double posY;
	protected double posX;
	protected double posZ;
	protected int x;
	protected int y;
	protected int z;
	
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

	public Pos(Vec3d vec3d) {
		this.posX = vec3d.x;
		this.posY = vec3d.y;
		this.posZ = vec3d.z;
		
		this.x = (int) vec3d.x;
		this.y = (int) vec3d.y;
		this.z = (int) vec3d.z;
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
	
	public void setX(double x){
		this.x = (int)x;
		this.posX = x;
	}
	
	public void setY(double y){
		this.y = (int)y;
		this.posY = y;
	}
	
	public void setZ(double z){
		this.z = (int)z;
		this.posZ = z;
	}

	public String getString() {
		return new BlockPos(this.x,this.y,this.z).toString();
	}

}
