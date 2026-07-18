package com.mcprivate.selectivecreativeinventory.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mcprivate.selectivecreativeinventory.SelectiveCreativeInventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
abstract class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

    @WrapOperation(
            method = "handleSetCreativeModeSlot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayerGameMode;isCreative()Z"
            )
    )
    private boolean selectiveCreativeInventory$allowCreativeSlotPacket(
            ServerPlayerGameMode gameMode,
            Operation<Boolean> original
    ) {
        return original.call(gameMode) || SelectiveCreativeInventory.hasCreativeInventory(this.player);
    }
}
