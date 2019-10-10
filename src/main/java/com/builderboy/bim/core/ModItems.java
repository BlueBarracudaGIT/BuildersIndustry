package com.builderboy.bim.core;

import com.builderboy.bim.BuildersIndustry;
import com.builderboy.bim.common.item.CircuitItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {

    public static Item TEST;

    //Materials
    public static Item ROCK_CHUNK;
    public static Item IRON_CHUNK;
    public static Item GOLD_CHUNK;
    public static Item COPPER_CHUNK;
    public static Item TIN_CHUNK;
    public static Item NETHERIUM_CHUNK;

    public static Item COPPER_INGOT;
    public static Item COPPER_NUGGET;

    public static Item TIN_INGOT;
    public static Item TIN_NUGGET;

    public static Item NETHERIUM_INGOT;
    public static Item NETHERIUM_NUGGET;

    public static Item BRONZE_INGOT;
    public static Item BRONZE_NUGGET;

    public static Item STEEL_INGOT;
    public static Item STEEL_NUGGET;

    public static Item TIN_IRON;
    public static Item TIN_IRON_NUGGET;

    public static Item RUBY;
    public static Item SAPPHIRE;

    public static Item EMBER;

    public static Item PLASTIC_BLEND;
    public static Item PLASTIC;

    public static Item SOLDER;

    public static Item REINFORCED_BRICK;
    public static Item BLASTED_BRICK;

    //Machine Parts
    public static Item IRON_STRIP;
    public static Item IRON_PLATE;
    public static Item IRON_ROD;
    public static Item IRON_GEAR;

    public static Item GOLD_STRIP;
    public static Item GOLD_PLATE;
    public static Item GOLD_ROD;
    public static Item GOLD_GEAR;

    public static Item COPPER_STRIP;
    public static Item COPPER_PLATE;
    public static Item COPPER_ROD;
    public static Item COPPER_GEAR;

    public static Item TIN_STRIP;
    public static Item TIN_PLATE;
    public static Item TIN_ROD;
    public static Item TIN_GEAR;

    public static Item BRONZE_STRIP;
    public static Item BRONZE_PLATE;
    public static Item BRONZE_ROD;
    public static Item BRONZE_GEAR;

    public static Item STEEL_STRIP;
    public static Item STEEL_PLATE;
    public static Item STEEL_ROD;
    public static Item STEEL_GEAR;

    public static Item TIN_IRON_STRIP;
    public static Item TIN_IRON_PLATE;
    public static Item TIN_IRON_ROD;
    public static Item TIN_IRON_GEAR;

    public static Item COMPONENT;
    public static Item HEATER_COMPONENT;
    public static Item COOLER_COMPONENT;
    public static Item ENERGY_COMPONENT;
    public static Item INVENTORY_COMPONENT;

    public static Item CIRCUIT_BOARD;
    public static Item SIMPLE_CIRCUIT;
    public static Item BASIC_CIRCUIT;
    public static Item CIRCUIT;
    public static Item ADVANCED_CIRCUIT;
    public static Item ENHANCED_CIRCUIT;
    public static Item ENDER_CIRCUIT;

    public static Item COMBUSTION_ENGINE;

    //Tools
    public static Item HAMMER;

    static final Map<String, BlockItem> BLOCKS_TO_REGISTER = new LinkedHashMap<>();

    public static void registerAll(RegistryEvent.Register<Item> event) {
        if (!event.getName().equals(ForgeRegistries.ITEMS.getRegistryName())) return;
        BLOCKS_TO_REGISTER.forEach(ModItems::register);

        TEST = register("test", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        //Materials
        ROCK_CHUNK = register("rock_chunk", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(32)));
        IRON_CHUNK = register("iron_chunk", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(32)));
        GOLD_CHUNK = register("gold_chunk", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(32)));
        COPPER_CHUNK = register("copper_chunk", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(32)));
        TIN_CHUNK = register("tin_chunk", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(32)));
        NETHERIUM_CHUNK = register("netherium_chunk", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(32)));

        COPPER_INGOT = register("copper_ingot", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));
        COPPER_NUGGET = register("copper_nugget", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        TIN_INGOT = register("tin_ingot", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));
        TIN_NUGGET = register("tin_nugget", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        NETHERIUM_INGOT = register("netherium_ingot", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));
        NETHERIUM_NUGGET = register("netherium_nugget", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        BRONZE_INGOT = register("bronze_ingot", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));
        BRONZE_NUGGET = register("bronze_nugget", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        STEEL_INGOT = register("steel_ingot", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));
        STEEL_NUGGET = register("steel_nugget", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        TIN_IRON = register("tin_iron", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));
        TIN_IRON_NUGGET = register("tin_iron_nugget", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        RUBY = register("ruby", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));
        SAPPHIRE = register("sapphire", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        EMBER = register("ember", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));

        PLASTIC_BLEND = register("plastic_blend", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));
        PLASTIC = register("plastic", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        SOLDER = register("solder", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        REINFORCED_BRICK = register("reinforced_brick", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));
        BLASTED_BRICK = register("blasted_brick", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY)));

        //Machine Parts
        IRON_STRIP = register("iron_strip", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        IRON_PLATE = register("iron_plate", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        IRON_ROD = register("iron_rod", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        IRON_GEAR = register("iron_gear", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));

        GOLD_STRIP = register("gold_strip", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        GOLD_PLATE = register("gold_plate", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        GOLD_ROD = register("gold_rod", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        GOLD_GEAR = register("gold_gear", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));

        COPPER_STRIP = register("copper_strip", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        COPPER_PLATE = register("copper_plate", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        COPPER_ROD = register("copper_rod", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        COPPER_GEAR = register("copper_gear", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));

        TIN_STRIP = register("tin_strip", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        TIN_PLATE = register("tin_plate", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        TIN_ROD = register("tin_rod", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        TIN_GEAR = register("tin_gear", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));

        BRONZE_STRIP = register("bronze_strip", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        BRONZE_PLATE = register("bronze_plate", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        BRONZE_ROD = register("bronze_rod", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        BRONZE_GEAR = register("bronze_gear", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));

        STEEL_STRIP = register("steel_strip", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        STEEL_PLATE = register("steel_plate", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        STEEL_ROD = register("steel_rod", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        STEEL_GEAR = register("steel_gear", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));

        TIN_IRON_STRIP = register("tin_iron_strip", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        TIN_IRON_PLATE = register("tin_iron_plate", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        TIN_IRON_ROD = register("tin_iron_rod", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));
        TIN_IRON_GEAR = register("tin_iron_gear", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(16)));

        COMPONENT = register("component", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(4)));
        HEATER_COMPONENT = register("heater_component", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(4)));
        COOLER_COMPONENT = register("cooler_component", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(4)));
        ENERGY_COMPONENT = register("energy_component", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(4)));
        INVENTORY_COMPONENT = register("inventory_component", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(4)));

        CIRCUIT_BOARD = register("circuit_board", new CircuitItem());
        SIMPLE_CIRCUIT = register("simple_circuit", new CircuitItem());
        BASIC_CIRCUIT = register("basic_circuit", new CircuitItem());
        CIRCUIT = register("circuit", new CircuitItem());
        ADVANCED_CIRCUIT = register("advanced_circuit", new CircuitItem());
        ENHANCED_CIRCUIT = register("enhanced_circuit", new CircuitItem());
        ENDER_CIRCUIT = register("ender_circuit", new CircuitItem());

        COMBUSTION_ENGINE = register("combustion_engine", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(1)));

        //Tools
        HAMMER = register("hammer", new Item(new Item.Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(1)));
    }

    public static <I extends Item> I register(String name, I item) {
        item.setRegistryName(BuildersIndustry.getLocation(name));
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}