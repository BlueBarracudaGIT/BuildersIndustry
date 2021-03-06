package com.builderboy.bim;

import com.builderboy.bim.core.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Optional;

@Mod("bim")
public class BuildersIndustry {

    public static final String MOD_ID = "bim";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup BUILDERS_INDUSTRY = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.EMBER);
        }
    };

    public BuildersIndustry() {
        DistExecutor.runForDist(
                () -> SideProxy.Client::new,
                () -> SideProxy.Server::new
        );
    }

    @Nonnull
    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MOD_ID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "NONE";
    }


    public static boolean isDevBuild() {
        return !getVersion().equals("NONE");
    }

    public static ResourceLocation getLocation(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}