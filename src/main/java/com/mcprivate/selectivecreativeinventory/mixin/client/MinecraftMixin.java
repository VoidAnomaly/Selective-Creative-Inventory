package com.mcprivate.selectivecreativeinventory.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mcprivate.selectivecreativeinventory.SelectiveCreativeInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Abilities;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
abstract class MinecraftMixin {
    @WrapOperation(
            method = "handleKeybinds",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/player/LocalPlayer;isCreative()Z"
            )
    )
    private boolean selectiveCreativeInventory$allowSavedHotbars(
            LocalPlayer player,
            Operation<Boolean> original
    ) {
        return original.call(player) || SelectiveCreativeInventory.hasCreativeInventory(player);
    }

    @WrapOperation(
            method = "pickBlock",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/player/Abilities;instabuild:Z",
                    opcode = Opcodes.GETFIELD
            )
    )
    private boolean selectiveCreativeInventory$allowCreativePickBlock(
            Abilities abilities,
            Operation<Boolean> original
    ) {
        return original.call(abilities)
                || SelectiveCreativeInventory.hasCreativeInventory(Minecraft.getInstance().player);
    }
}
