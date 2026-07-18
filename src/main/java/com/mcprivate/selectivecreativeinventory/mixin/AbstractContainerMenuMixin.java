package com.mcprivate.selectivecreativeinventory.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mcprivate.selectivecreativeinventory.SelectiveCreativeInventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerMenu.class)
abstract class AbstractContainerMenuMixin {
    @WrapOperation(
            method = {"doClick", "isValidQuickcraftType"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;hasInfiniteMaterials()Z"
            )
    )
    private static boolean selectiveCreativeInventory$allowInventoryClone(
            Player player,
            Operation<Boolean> original
    ) {
        return original.call(player) || SelectiveCreativeInventory.hasCreativeInventory(player);
    }
}
