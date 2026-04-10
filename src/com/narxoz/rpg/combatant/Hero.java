package com.narxoz.rpg.combatant;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.strategy.BalancedStrategy;
import com.narxoz.rpg.strategy.CombatStrategy;

public class Hero {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private CombatStrategy strategy;

    public Hero(String name, int hp, int attackPower, int defense, CombatStrategy strategy) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strategy = strategy != null ? strategy : new BalancedStrategy();
    }

    public void attack(DungeonBoss boss) {
        int damage = strategy.calculateDamage(attackPower);
        int defenseValue = boss.getStrategy().calculateDefense(boss.getDefense());
        int actualDamage = Math.max(1, damage - defenseValue);
        boss.takeDamage(actualDamage);
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public void setStrategy(CombatStrategy strategy) {
        this.strategy = strategy;
    }

    public CombatStrategy getStrategy() { return strategy; }
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public boolean isAlive() { return hp > 0; }
}