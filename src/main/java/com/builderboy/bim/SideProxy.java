package com.builderboy.bim;

import com.builderboy.bim.core.ModBlocks;
import com.builderboy.bim.core.ModItems;
import com.builderboy.bim.core.ModTileEntities;
import com.builderboy.bim.world.ModWorldFeatures;
import com.mrcrayfish.filters.Filters;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class SideProxy {

    public SideProxy() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModBlocks::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModItems::registerAll);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModTileEntities::registerAll);

        MinecraftForge.EVENT_BUS.addListener(SideProxy::serverStarting);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        ModWorldFeatures.addFeatures();
    }

    private static void serverStarting(FMLServerStartingEvent event) {}

    public static class Client extends SideProxy {

        Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Client::clientSetup);
        }

        private static void clientSetup(FMLClientSetupEvent event) {

            if (ModList.get().getModContainerById("filters").isPresent()) {
                System.out.println("Initializing Filters");

                ItemGroup group = BuildersIndustry.BUILDERS_INDUSTRY;
                Filters.get().register(group, BuildersIndustry.getLocation("machine_parts"), new ItemStack(ModItems.CIRCUIT_BOARD));
                //Filters.get().register(group, BuildersIndustry.getLocation("machines"), new ItemStack(ModBlocks.CASING.asItem()));
                Filters.get().register(group, BuildersIndustry.getLocation("materials"), new ItemStack(ModItems.ROCK_CHUNK));
                Filters.get().register(group, BuildersIndustry.getLocation("tools"), new ItemStack(ModItems.HAMMER));
            }
            //Filters
            /*
            if (ModList.get().getModContainerById("filters").isPresent()) {



                //Filters.get().register(group, new ResourceLocation("machines"), new ItemStack(ModBlocks.CASING.asItem()));
                //Filters.get().register(group, new ResourceLocation("materials"), new ItemStack(ModItems.ROCK_CHUNK));
                //Filters.get().register(group, new ResourceLocation("tools"), new ItemStack(ModItems.HAMMER));
            }
             */
        }
    }

    public static class Server extends SideProxy {

        Server() { FMLJavaModLoadingContext.get().getModEventBus().addListener(Server::serverSetup); }

        private static void serverSetup(FMLDedicatedServerSetupEvent event) {}
    }
}