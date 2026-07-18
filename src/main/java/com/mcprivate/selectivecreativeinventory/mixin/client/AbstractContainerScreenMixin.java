package com.mcprivate.selectivecreativeinventory.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mcprivate.selectivecreativeinventory.SelectiveCreativeInventory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerScreen.class)
abstract class AbstractContainerScreenMixin {
    @WrapOperation(
            method = "mouseClicked",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;hasInfiniteMaterials()Z"
            ),
            // NeoForge 21.1.197 removes this gate; later 21.1.x builds retain it.
            require = 0
    )
    private boolean selectiveCreativeInventory$allowMiddleClickClone(
            LocalPlayer player,
            Operation<Boolean> original
    ) {
        return original.call(player) || SelectiveCreativeInventory.hasCreativeInventory(player);
    }
}
