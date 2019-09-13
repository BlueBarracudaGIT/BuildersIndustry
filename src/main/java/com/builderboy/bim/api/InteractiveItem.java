package com.builderboy.bim.api;

import com.builderboy.bim.BuildersIndustry;
import com.builderboy.bim.util.IInteractibleItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class InteractiveItem extends Item implements IInteractibleItem {
    public InteractiveItem() { super(new Properties().group(BuildersIndustry.BUILDERS_INDUSTRY).maxStackSize(1)); }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack held = player.getHeldItem(hand);

        //Client
        if (!world.isRemote) { return clientRightClick(world, player, held, player.isSneaking()); }

        //Server
        return serverRightClick(world, player, held, player.isSneaking());
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();

        //Client
        if (!world.isRemote) { return clientBlockClick(world, player, context.getPos(), context.getItem(), player.isSneaking()); }

        //Server
        return serverBlockClick(world, player, context.getPos(), context.getItem(), player.isSneaking());
    }
}