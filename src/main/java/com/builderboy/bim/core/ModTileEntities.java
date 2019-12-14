package com.builderboy.bim.core;

import com.builderboy.bim.BuildersIndustry;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    //public static TileEntityType<GearboxTileEntity> GEARBOX;

    private static <T extends TileEntity> TileEntityType<T> register(String name, TileEntityType.Builder<T> builder) {
        Type<?> type = null;
        try {
            type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, name);
        } catch (IllegalArgumentException illegalStateException) {
            if (SharedConstants.developmentMode) {
                throw illegalStateException;
            }
            BuildersIndustry.LOGGER.warn("No data fixer registered for block entity {}", name);
        }

        TileEntityType<T> tile = builder.build(type);
        tile.setRegistryName(BuildersIndustry.getLocation(name));
        ForgeRegistries.TILE_ENTITIES.register(tile);
        return tile;
    }

    public static void registerAll(RegistryEvent.Register<TileEntityType<?>> event) {
        if (!event.getName().equals(ForgeRegistries.TILE_ENTITIES.getRegistryName())) return;

        //GEARBOX = register("gearbox", TileEntityType.Builder.create(GearboxTileEntity::new, ModBlocks.GEARBOX));
    }
}