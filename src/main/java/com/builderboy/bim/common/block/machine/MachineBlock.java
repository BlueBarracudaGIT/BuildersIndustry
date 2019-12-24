package com.builderboy.bim.common.block.machine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MachineBlock extends Block {

    public MachineBlock(float hardness, float resistance) {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(hardness, resistance).harvestTool(ToolType.PICKAXE));
    }

}