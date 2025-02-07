package com.evilnotch.lanessentials.caps;

import com.evilnotch.lib.minecraft.capability.CapContainer;
import com.evilnotch.lib.minecraft.capability.ICapabilityTick;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;

public class CapAbility implements ICapabilityTick<EntityPlayerMP> {
	
	public boolean godMode;//lowcase g god gamemode aka you take zero damage
	private boolean setgodMode;
	public boolean fly;
	private boolean isFlying;
	public boolean speedEnabled;
    public float speed = speedDefault;
    public float speedFly = speedFlyDefault;
    
    private static final float speedDefault = 0.1F;
    private static final float speedFlyDefault = 0.05F;
	
	@Override
	public void tick(EntityPlayerMP p, CapContainer c) {
		PlayerCapabilities pc = p.capabilities;
		boolean send = false;
		if(this.godMode && !pc.disableDamage) {
			pc.disableDamage = true;
			setgodMode = true;
			send = true;
		}
		else if(!this.godMode && setgodMode && pc.disableDamage && !p.isCreative()) {
			pc.disableDamage = false;
			setgodMode = false;
			send = true;
		}
		if(this.fly && !pc.allowFlying) {
			pc.allowFlying = true;
			send = true;
		}
		if(this.speedEnabled) {
			if(this.speed != pc.walkSpeed) {
				pc.walkSpeed = this.speed;
				send = true;
			}
			if(this.speedFly != pc.flySpeed) {
				pc.flySpeed = this.speedFly;
				send = true;
			}
		}
		if(send)
			p.sendPlayerAbilities();
	}

	@Override
	public void writeToNBT(EntityPlayerMP p, NBTTagCompound nbt, CapContainer c) {
		nbt.setBoolean("godMode", this.godMode);
		nbt.setBoolean("fly", this.fly);
		nbt.setBoolean("isFlying", p.capabilities.isFlying);
		nbt.setBoolean("speedEnabled", this.speedEnabled);
		nbt.setFloat("speed", this.speed);
		nbt.setFloat("speedFly", this.speedFly);
	}

	@Override
	public void readFromNBT(EntityPlayerMP p, NBTTagCompound nbt, CapContainer c) {
		this.godMode = nbt.getBoolean("godMode");
		this.fly = nbt.getBoolean("fly");
		this.isFlying = nbt.getBoolean("isFlying");
		this.speedEnabled = nbt.getBoolean("speedEnabled");
		this.speed = nbt.hasKey("speed") ? nbt.getFloat("speed") : speedDefault;
		this.speed = nbt.hasKey("speedFly") ? nbt.getFloat("speed") : speedFlyDefault;
	}
	
	/**
	 * This should only be used during login as it will be out of sync
	 */
	public boolean getIsFlying()
	{
		return this.isFlying;
	}

}
