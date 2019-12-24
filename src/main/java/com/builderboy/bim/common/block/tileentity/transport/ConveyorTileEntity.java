package com.builderboy.bim.common.block.tileentity.transport;

import com.builderboy.bim.core.ModTileEntities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class ConveyorTileEntity extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler itemHandler = new ItemStackHandler();

    public ConveyorTileEntity() {
        super(ModTileEntities.CONVEYOR);
    }

    @Override
    public void tick() {

    }

    public boolean hasItems() {
        if (itemHandler.getStackInSlot(0) != ItemStack.EMPTY) { return true; }
        return false;
    }

    public void placeItem(Item item) {}
}
