package com.mcprivate.selectivecreativeinventory.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mcprivate.selectivecreativeinventory.SelectiveCreativeInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MultiPlayerGameMode.class)
abstract class MultiPlayerGameModeMixin {
    @WrapOperation(
            method = {"handleCreativeModeItemAdd", "handleCreativeModeItemDrop"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/GameType;isCreative()Z"
            )
    )
    private boolean selectiveCreativeInventory$sendCreativeSlotPacket(
            GameType gameType,
            Operation<Boolean> original
    ) {
        return original.call(gameType)
                || SelectiveCreativeInventory.hasCreativeInventory(Minecraft.getInstance().player);
    }
}
