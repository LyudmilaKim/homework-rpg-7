package com.narxoz.rpg;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.publisher.EventPublisher;
import com.narxoz.rpg.strategy.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EventPublisher publisher = new EventPublisher();

        List<Hero> heroes = new ArrayList<>();
        heroes.add(new Hero("Warrior", 280, 62, 34, new AggressiveStrategy()));
        heroes.add(new Hero("Mage",    230, 80, 25, new BalancedStrategy()));
        heroes.add(new Hero("Tank",    320, 52, 58, new DefensiveStrategy()));

        // Финальные параметры босса — хороший баланс
        DungeonBoss boss = new DungeonBoss("Dungeon Boss", 1650, 37, 28, publisher);

        BattleLogger logger = new BattleLogger();
        AchievementTracker achievements = new AchievementTracker();
        PartySupport support = new PartySupport(heroes);
        HeroStatusMonitor monitor = new HeroStatusMonitor(heroes);
        LootDropper loot = new LootDropper();

        publisher.addObserver(logger);
        publisher.addObserver(achievements);
        publisher.addObserver(support);
        publisher.addObserver(monitor);
        publisher.addObserver(loot);

        DungeonEngine engine = new DungeonEngine(heroes, boss, publisher);
        EncounterResult result = engine.runEncounter();

        System.out.println("\n=== ENCOUNTER RESULT ===");
        System.out.println("Heroes won: " + result.isHeroesWon());
        System.out.println("Rounds played: " + result.getRoundsPlayed());
        System.out.println("Surviving heroes: " + result.getSurvivingHeroes());
    }
}