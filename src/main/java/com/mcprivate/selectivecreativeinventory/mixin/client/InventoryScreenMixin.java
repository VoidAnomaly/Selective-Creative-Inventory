package com.mcprivate.selectivecreativeinventory.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mcprivate.selectivecreativeinventory.SelectiveCreativeInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InventoryScreen.class)
abstract class InventoryScreenMixin {
    @WrapOperation(
            method = {"init", "containerTick"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasInfiniteItems()Z"
            )
    )
    private boolean selectiveCreativeInventory$openCreativeInventory(
            MultiPlayerGameMode gameMode,
            Operation<Boolean> original
    ) {
        return original.call(gameMode)
                || SelectiveCreativeInventory.hasCreativeInventory(Minecraft.getInstance().player);
    }
}
