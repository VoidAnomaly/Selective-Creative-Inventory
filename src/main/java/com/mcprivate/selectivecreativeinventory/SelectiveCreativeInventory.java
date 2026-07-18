package com.mcprivate.selectivecreativeinventory;

import com.mojang.serialization.Codec;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@Mod(SelectiveCreativeInventory.MOD_ID)
public final class SelectiveCreativeInventory {
    public static final String MOD_ID = "selective_creative_inventory";

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

    public static final Supplier<AttachmentType<Boolean>> CREATIVE_INVENTORY = ATTACHMENT_TYPES.register(
            "creative_inventory",
            () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL)
                    .copyOnDeath()
                    .sync((holder, recipient) -> holder == recipient, ByteBufCodecs.BOOL)
                    .build()
    );

    public SelectiveCreativeInventory(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
        NeoForge.EVENT_BUS.addListener(SelectiveCreativeInventory::registerCommands);
    }

    public static boolean hasCreativeInventory(Player player) {
        return player != null
                && !player.isSpectator()
                && player.hasData(CREATIVE_INVENTORY)
                && Boolean.TRUE.equals(player.getData(CREATIVE_INVENTORY));
    }

    private static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("sci")
                        .requires(source -> source.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.literal("enable")
                                .executes(context -> setForSelf(context.getSource(), true))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> setForPlayer(
                                                context.getSource(),
                                                EntityArgument.getPlayer(context, "player"),
                                                true
                                        )))
                        )
                        .then(Commands.literal("disable")
                                .executes(context -> setForSelf(context.getSource(), false))
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> setForPlayer(
                                                context.getSource(),
                                                EntityArgument.getPlayer(context, "player"),
                                                false
                                        )))
                        )
        );
    }

    private static int setForSelf(CommandSourceStack source, boolean enabled) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        setEnabled(player, enabled);
        source.sendSuccess(
                () -> Component.translatable(enabled
                        ? "commands.selective_creative_inventory.enable.self"
                        : "commands.selective_creative_inventory.disable.self"),
                true
        );
        return 1;
    }

    private static int setForPlayer(CommandSourceStack source, ServerPlayer player, boolean enabled) {
        setEnabled(player, enabled);
        source.sendSuccess(
                () -> Component.translatable(
                        enabled
                                ? "commands.selective_creative_inventory.enable.player"
                                : "commands.selective_creative_inventory.disable.player",
                        player.getDisplayName()
                ),
                true
        );
        return 1;
    }

    private static void setEnabled(ServerPlayer player, boolean enabled) {
        if (enabled) {
            player.setData(CREATIVE_INVENTORY, true);
        } else {
            player.removeData(CREATIVE_INVENTORY);
        }
    }
}
