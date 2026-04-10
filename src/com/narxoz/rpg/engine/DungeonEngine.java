package com.narxoz.rpg.engine;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.publisher.EventPublisher;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.List;

public class DungeonEngine {
    private final List<Hero> heroes;
    private final DungeonBoss boss;
    private final EventPublisher publisher;
    private int rounds = 0;
    private static final int MAX_ROUNDS = 40;
    private boolean strategySwitched = false;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, EventPublisher publisher) {
        this.heroes = heroes;
        this.boss = boss;
        this.publisher = publisher;
    }

    public EncounterResult runEncounter() {
        System.out.println("=== THE CURSED DUNGEON BOSS ENCOUNTER STARTS ===\n");

        while (boss.isAlive() && isAnyHeroAlive() && rounds < MAX_ROUNDS) {
            rounds++;

            for (Hero hero : heroes) {
                if (hero.isAlive()) {
                    hero.attack(boss);
                }
            }

            if (rounds == 7 && !strategySwitched) {
                strategySwitched = true;
                heroes.get(0).setStrategy(new DefensiveStrategy());
                System.out.println("\n=== MID-BATTLE STRATEGY SWITCH ===");
                System.out.println(heroes.get(0).getName() + " switched to Defensive strategy mid-fight!");
            }

            if (boss.isAlive()) {
                for (Hero hero : heroes) {
                    if (hero.isAlive()) {
                        boss.attack(hero);
                        checkHeroStatus(hero);
                    }
                }
            }
        }

        boolean heroesWon = !boss.isAlive();
        int surviving = (int) heroes.stream().filter(Hero::isAlive).count();

        if (rounds >= MAX_ROUNDS) {
            System.out.println("[ENGINE] Encounter ended due to round limit.");
        }

        publisher.notifyObservers(new GameEvent(GameEventType.BOSS_DEFEATED, "Dungeon Boss", rounds));

        return new EncounterResult(heroesWon, rounds, surviving);
    }

    private boolean isAnyHeroAlive() {
        return heroes.stream().anyMatch(Hero::isAlive);
    }

    private void checkHeroStatus(Hero hero) {
        if (!hero.isAlive()) {
            publisher.notifyObservers(new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0));
        } else if ((double) hero.getHp() / hero.getMaxHp() <= 0.3) {
            publisher.notifyObservers(new GameEvent(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp()));
        }
    }
}