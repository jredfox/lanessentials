package com.jredfox.lanessential.cap;

import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapabilityTick;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.GameType;

public class CapAbility implements ICapabilityTick<EntityPlayerMP> {
	
	public boolean godMode;//lowcase g god gamemode aka you take zero damage
	public boolean fly;
    public float speed = speedDefault;
    public float speedFly = speedFlyDefault;
    public boolean isFlying;//out of sync with player used as a bugfix
    public boolean sendIsFlying;
    private GameType lastGM = null;
    
    private static final float speedDefault = 0.1F;
    private static final float speedFlyDefault = 0.05F;
    
    public void sync(EntityPlayerMP p) 
    {
    	PlayerCapabilities c = p.capabilities;
    	c.disableDamage = this.godMode || this.isCreative(p);
    	c.allowFlying = this.fly || this.isCreative(p);
    	if(!c.allowFlying)
    		c.isFlying = false;
    	else
    		c.isFlying = this.isFlying;
    	c.walkSpeed = this.speed <= 0 ? speedDefault : this.speed;
    	c.flySpeed = this.speedFly <= 0 ? speedFlyDefault : this.speedFly;
		this.sendIsFlying = false;
    	p.sendPlayerAbilities();
    }
    
	@Override
	public void tick(EntityPlayerMP p, CapContainer c)
	{
		GameType mode = p.interactionManager.getGameType();
		if(mode != this.lastGM || mode == null) 
		{
			this.lastGM = mode;
			this.sync(p);
		}
		this.isFlying = p.capabilities.isFlying;
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
		this.speed = nbt.hasKey("speed") ? nbt.getFloat("speed") : speedDefault;
		this.speedFly = nbt.hasKey("speedFly") ? nbt.getFloat("speedFly") : speedFlyDefault;
		this.isFlying = nbt.getBoolean("isFlying");
		this.sendIsFlying = true;
	}

}
