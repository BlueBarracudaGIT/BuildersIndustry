package com.builderboy.bim.core;

import com.builderboy.bim.BuildersIndustry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class ModBlocks {

    public static Block TEST_BLOCK;

    //Materials\\
    public static Block COPPER_ORE;
    public static Block TIN_ORE;
    //public static Block ALUMINIUM_ORE;

    public static Block COPPER_BLOCK;
    public static Block TIN_BLOCK;
    public static Block TIN_IRON_BLOCK;
    public static Block STEEL_BLOCK;
    public static Block BRONZE_BLOCK;
    public static Block NETHERIUM_BLOCK;

    //Machine Parts\\
    public static Block SIMPLE_CASING;
    public static Block BASIC_CASING;
    public static Block CASING;
    public static Block ADVANCED_CASING;
    public static Block ENHANCED_CASING;
    public static Block ENDER_CASING;

    //Machines\\
    //Transport
    public static Block CONVEYOR;
    public static Block SPLITTER;
    public static Block COMBINER;
    public static Block LIFT;
    public static Block IMPORTER;
    public static Block EXPORTER;

    //Mechanical
    public static Block GEARBOX;
    public static Block MECHANICAL_GRINDER;
    public static Block MECHANICAL_CRAFTER;
    public static Block MECHANICAL_PRESS;

    //Combustion

    //Electrical

    public static void registerAll(RegistryEvent.Register<Block> event) {
        if (!event.getName().equals(ForgeRegistries.BLOCKS.getRegistryName())) return;

        TEST_BLOCK = register("test_block", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));

        //Materials\\
        COPPER_ORE = register("copper_ore", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        TIN_ORE = register("tin_ore", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        //ALUMINIUM_ORE = register("aluminium_ore", new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));

        COPPER_BLOCK = register("copper_block", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        TIN_BLOCK = register("tin_block", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        TIN_IRON_BLOCK = register("tin_iron_block", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        STEEL_BLOCK = register("steel_block", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        BRONZE_BLOCK = register("bronze_block", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        NETHERIUM_BLOCK = register("netherium_block", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));

        //Machine Parts\\
        SIMPLE_CASING = register("simple_casing", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        BASIC_CASING = register("basic_casing", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        CASING = register("casing", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        ADVANCED_CASING = register("advanced_casing", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        ENHANCED_CASING = register("enhanced_casing", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));
        ENDER_CASING = register("ender_casing", new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.PICKAXE)));

        //Machines\\
        //Transport

        //Mechanical
        //GEARBOX = register("gearbox", new GearboxBlock());

        //Combustion

        //Electrical

    }

    public static <B extends Block> B register(String name, B block) { return register(name, block, BuildersIndustry.BUILDERS_INDUSTRY); }

    public static <B extends Block> B register(String name, B block, ItemGroup group) {
        BlockItem item = new BlockItem(block, new Item.Properties().group(group));
        return register(name, block, item);
    }

    public static <B extends Block> B register(String name, B block, @Nullable BlockItem item) {
        block.setRegistryName(BuildersIndustry.getLocation(name));
        ForgeRegistries.BLOCKS.register(block);
        if (item != null) { ModItems.BLOCKS_TO_REGISTER.put(name, item); }
        return block;
    }
}