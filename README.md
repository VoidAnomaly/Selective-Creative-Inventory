# Selective Creative Inventory

Selective Creative Inventory gives chosen survival or adventure players access to Minecraft's normal creative inventory without changing their game mode. Hearts, hunger, damage, breaking speed, flight, item consumption, durability, ammo, and XP costs keep their normal game-mode behavior.

The mod must be installed on both the client and server. It targets:

- Minecraft 1.21.1
- NeoForge 21.1.197+


## Commands

Commands require permission level 2 (the normal game-master/operator level).

```text
/sci enable [player]
/sci disable [player]
```

If `player` is omitted, the command targets the player running it. Console and command-block use must specify a player.

After enabling access, open the inventory normally (`E` by default). Disabling access while the creative inventory is open returns the player to the normal inventory.

## Intended behavior

Enabled players can use the creative tabs, spawn or delete inventory items, clone full stacks, use pick-block, and use saved creative hotbars. Modded creative tabs work through the vanilla NeoForge creative screen.

Other creative behavior is deliberately excluded: no flight, invulnerability, instant breaking, extended interaction range, free enchanting or anvil use, infinite projectiles, infinite durability, or infinite item use. Spectator mode temporarily suppresses inventory access; the setting becomes active again after returning to survival or adventure.

For an initial in-game check, enable the command for a survival player, verify the creative inventory and item spawning, then verify damage, hunger, block-breaking speed, item consumption, and lack of flight before testing alongside a larger modpack.
