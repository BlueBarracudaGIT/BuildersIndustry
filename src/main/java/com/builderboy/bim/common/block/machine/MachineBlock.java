package com.builderboy.bim.common.block.machine;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ToolType;

public class MachineBlock extends Block {

    public MachineBlock(float hardness, float resistance) {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(hardness, resistance).harvestTool(ToolType.PICKAXE));
    }

    public static Direction getFacingFromEntity(BlockPos clicked, LivingEntity entity) {
        return Direction.getFacingFromVector((float)(entity.posX - clicked.getX()), (float)(entity.posY - clicked.getY()), (float)(entity.posZ - clicked.getZ()));
    }
}