package com.narxoz.rpg.observer;

public class LootDropper implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            String loot = switch (event.getValue()) {
                case 2 -> "Ancient Rune";
                case 3 -> "Cursed Amulet";
                default -> "Mysterious Shard";
            };
            System.out.println("[LOOT] Phase " + event.getValue() + " drop: " + loot);
        }
        else if (event.getType() == GameEventType.BOSS_DEFEATED) {
            System.out.println("[LOOT] Final Boss Drop: Dragon's Heart + 500 Gold");
        }
    }
}