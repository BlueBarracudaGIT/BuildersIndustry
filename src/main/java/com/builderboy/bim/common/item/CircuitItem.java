package com.builderboy.bim.common.item;

import com.builderboy.bim.api.InteractiveItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class CircuitItem extends InteractiveItem {

    public CircuitItem() { super(); }

    @Override
    public ActionResult<ItemStack> serverRightClick(World world, PlayerEntity player, ItemStack held, boolean isSneaking) {
        world.playSound(player, player.getPosition(), new SoundEvent(new ResourceLocation("ui.button.click")), SoundCategory.MASTER, 0.5f, ((float) Math.random()));

        return new ActionResult<>(ActionResultType.SUCCESS, held);
    }
}