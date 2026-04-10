package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;
import java.util.Random;

public class PartySupport implements GameObserver {
    private final List<Hero> heroes;
    private final Random random = new Random();

    public PartySupport(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.HERO_LOW_HP) {
            List<Hero> living = heroes.stream()
                    .filter(Hero::isAlive)
                    .toList();

            if (!living.isEmpty()) {
                Hero target = living.get(random.nextInt(living.size()));
                int healAmount = 25;
                target.heal(healAmount);
                System.out.println("[PARTY SUPPORT] " + target.getName() + " was healed for " + healAmount + " HP");
            }
        }
    }
}