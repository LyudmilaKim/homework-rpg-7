package com.narxoz.rpg.observer;

import java.util.HashSet;
import java.util.Set;

public class AchievementTracker implements GameObserver {
    private final Set<String> unlocked = new HashSet<>();
    private int attackCount = 0;

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED:
                attackCount++;
                if (attackCount == 5 && unlocked.add("Relentless Attacker")) {
                    System.out.println("[ACHIEVEMENT] Unlocked: Relentless Attacker (5+ attacks landed)");
                }
                break;

            case BOSS_DEFEATED:
                if (unlocked.add("Boss Slayer")) {
                    System.out.println("[ACHIEVEMENT] Unlocked: Boss Slayer");
                }
                break;

            case HERO_DIED:
                if (unlocked.add("Heavy Losses")) {
                    System.out.println("[ACHIEVEMENT] Unlocked: Heavy Losses (a hero died)");
                }
                break;
        }
    }
}