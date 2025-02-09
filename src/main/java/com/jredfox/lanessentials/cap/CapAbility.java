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
    public boolean flySurvival;
    public float speed;
    public float speedFly;
    
    public GameType gamemode = GameType.NOT_SET;
    public boolean wasgodMode;
    public boolean wasFly;
    
    private static final float speedDefault = 0.1F;
    private static final float speedFlyDefault = 0.05F;
    
    public void sync(EntityPlayerMP p) 
    {
    	this.gamemode = p.interactionManager.getGameType();
    	PlayerCapabilities c = p.capabilities;
    	c.disableDamage = this.godMode || (this.wasgodMode ? this.isCreative(p) : c.disableDamage);
    	c.allowFlying = this.fly || (this.wasFly ? this.isCreative(p) : c.allowFlying);
    	if(!c.allowFlying)
    		c.isFlying = false;
    	else if(this.isSurvival(this.gamemode))
    		c.isFlying = this.flySurvival;
    	c.walkSpeed = this.speed <= 0 ? speedDefault : this.speed;
    	c.flySpeed = this.speedFly <= 0 ? speedFlyDefault : this.speedFly;
    	p.sendPlayerAbilities();
    	
		this.wasgodMode = this.godMode;
		this.wasFly = this.fly;
    }
    
	@Override
	public void tick(EntityPlayerMP p, CapContainer c)
	{
        GameType mode = p.interactionManager.getGameType();
        if(mode != this.gamemode)
        {
        	this.sync(p);
        	return;//sync has already been done on Game Mode Change don't send twice in the same tick
        }
		
		if(this.isSurvival(this.gamemode))
			this.flySurvival = p.capabilities.isFlying;
		this.wasgodMode = this.godMode;
		this.wasFly = this.fly;
	}
    
	private boolean isSurvival(GameType gm) 
	{
		return gm.isSurvivalOrAdventure();
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
		nbt.setBoolean("flySurvival", this.flySurvival);
		nbt.setFloat("speed", this.speed);
		nbt.setFloat("speedFly", this.speedFly);
	}

	@Override
	public void readFromNBT(EntityPlayerMP p, NBTTagCompound nbt, CapContainer c) 
	{
		this.godMode = nbt.getBoolean("godMode");
		this.fly = nbt.getBoolean("fly");
		this.flySurvival = nbt.getBoolean("flySurvival");
		this.speed = nbt.getFloat("speed");
		this.speedFly = nbt.getFloat("speedFly");
	}

}
