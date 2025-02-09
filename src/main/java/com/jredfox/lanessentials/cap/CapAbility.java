package com.jredfox.lanessentials.cap;

import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapabilityTick;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.GameType;

public class CapAbility implements ICapabilityTick<EntityPlayerMP> {
	
	public boolean godMode;
	public boolean fly;
    public boolean isFlying;
    public float speed;
    public float speedFly;
    
    public boolean wasgodMode;
    public boolean wasFly;
    
    private static final float speedDefault = 0.1F;
    private static final float speedFlyDefault = 0.05F;
    
    public void sync(EntityPlayerMP p) 
    {
    	PlayerCapabilities c = p.capabilities;
    	c.disableDamage = this.godMode || this.wasgodMode ? this.isCreative(p) : c.disableDamage;
    	c.allowFlying = this.fly || this.wasFly ? this.isCreative(p) : c.allowFlying;
    	if(!c.allowFlying)
    		c.isFlying = false;
    	else
    		c.isFlying = this.isFlying;
    	c.walkSpeed = this.speed <= 0 ? speedDefault : this.speed;
    	c.flySpeed = this.speedFly <= 0 ? speedFlyDefault : this.speedFly;
    	p.sendPlayerAbilities();
    }
    
	@Override
	public void tick(EntityPlayerMP p, CapContainer c)
	{
		boolean send = false;
		PlayerCapabilities pc = p.capabilities;
		if(this.godMode && !pc.disableDamage) {
			pc.disableDamage = true;
			send = true;
		}
		else if(this.wasgodMode && !this.godMode) {
			pc.disableDamage = this.isCreative(p);
			send = true;
		}
		if(this.fly && !pc.allowFlying) {
			pc.allowFlying = true;
			pc.isFlying = this.isFlying;//sync isFlying with the last tick
			send = true;
		}
		else if(this.wasFly && !this.fly) {
			pc.allowFlying = this.isCreative(p);
			if(!pc.allowFlying)
				pc.isFlying = false;
			send = true;
		}
		
		this.wasgodMode = this.godMode;
		this.wasFly = this.fly;
		this.isFlying = pc.isFlying;
		
		if(send)
			p.sendPlayerAbilities();
	}
    
	private boolean isCreative(EntityPlayerMP p)
	{
		return p.isCreative() || p.isSpectator();
	}

	@Override
	public void writeToNBT(EntityPlayerMP p, NBTTagCompound nbt, CapContainer c)
	{
		nbt.setBoolean("godMode", this.godMode);
		nbt.setBoolean("fly", this.fly);
		nbt.setBoolean("isFlying", p.capabilities.isFlying);
		nbt.setFloat("speed", this.speed);
		nbt.setFloat("speedFly", this.speedFly);
	}

	@Override
	public void readFromNBT(EntityPlayerMP p, NBTTagCompound nbt, CapContainer c) 
	{
		this.godMode = nbt.getBoolean("godMode");
		this.fly = nbt.getBoolean("fly");
		this.isFlying = nbt.getBoolean("isFlying");
		this.speed = nbt.getFloat("speed");
		this.speedFly = nbt.getFloat("speedFly");
	}

}
