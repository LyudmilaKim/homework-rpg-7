package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;

public class HeroStatusMonitor implements GameObserver {
    private final List<Hero> heroes;

    public HeroStatusMonitor(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP ||
                event.getType() == GameEventType.HERO_DIED) {

            System.out.println("\n[HERO STATUS]");
            for (Hero hero : heroes) {
                String status = hero.isAlive() ? "Alive" : "Dead";
                System.out.printf("  %s: %d/%d HP (%s)%n",
                        hero.getName(), hero.getHp(), hero.getMaxHp(), status);
            }
            System.out.println();
        }
    }
}