package com.EvilNotch.lanessentials.contianer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerWB extends ContainerWorkbench{
	
	public World w;
	public BlockPos p;

	public ContainerWB(InventoryPlayer playerInventory, World worldIn, BlockPos posIn) {
		super(playerInventory, worldIn, posIn);
		this.w = worldIn;
		this.p = posIn;
	}
	
    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
       return playerIn.getDistanceSq((double)this.p.getX() + 0.5D, (double)this.p.getY() + 0.5D, (double)this.p.getZ() + 0.5D) <= 64.0D;
    }

}