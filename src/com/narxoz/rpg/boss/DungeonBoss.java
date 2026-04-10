package com.narxoz.rpg.boss;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.publisher.EventPublisher;
import com.narxoz.rpg.strategy.BossPhase1Strategy;
import com.narxoz.rpg.strategy.BossPhase2Strategy;
import com.narxoz.rpg.strategy.BossPhase3Strategy;
import com.narxoz.rpg.strategy.CombatStrategy;

public class DungeonBoss implements GameObserver {
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private CombatStrategy strategy;
    private int currentPhase = 1;
    private final EventPublisher publisher;

    public DungeonBoss(String name, int hp, int attackPower, int defense, EventPublisher publisher) {
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.publisher = publisher;
        this.strategy = new BossPhase1Strategy();
        publisher.addObserver(this);
    }

    public void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
        checkPhaseChange();
    }

    private void checkPhaseChange() {
        double hpPercent = (double) hp / maxHp * 100;
        int newPhase = 1;
        if (hpPercent <= 30) newPhase = 3;
        else if (hpPercent <= 60) newPhase = 2;

        if (newPhase != currentPhase) {
            currentPhase = newPhase;
            publisher.notifyObservers(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, getName(), newPhase));
        }
    }

    public void attack(Hero hero) {
        int rawDamage = strategy.calculateDamage(attackPower);
        int defenseValue = hero.getStrategy().calculateDefense(hero.getDefense());
        int actualDamage = Math.max(8, rawDamage - defenseValue);   // минимум 8 урона

        hero.takeDamage(actualDamage);
        publisher.notifyObservers(new GameEvent(GameEventType.ATTACK_LANDED, getName(), actualDamage));
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            switch (event.getValue()) {
                case 2 -> strategy = new BossPhase2Strategy();
                case 3 -> strategy = new BossPhase3Strategy();
            }
        }
    }

    public String getName() { return "Dungeon Boss"; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public boolean isAlive() { return hp > 0; }
    public CombatStrategy getStrategy() { return strategy; }
    public int getDefense() { return defense; }
}