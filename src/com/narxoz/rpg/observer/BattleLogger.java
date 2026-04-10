package com.narxoz.rpg.observer;

public class BattleLogger implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {
        String message = switch (event.getType()) {
            case ATTACK_LANDED ->
                    event.getSourceName() + " landed an attack for " + event.getValue() + " damage!";
            case HERO_LOW_HP ->
                    event.getSourceName() + " is critically low on HP! (" + event.getValue() + " HP remaining)";
            case HERO_DIED ->
                    event.getSourceName() + " has died!";
            case BOSS_PHASE_CHANGED ->
                    event.getSourceName() + " entered Phase " + event.getValue() + "!";
            case BOSS_DEFEATED ->
                    "The Dungeon Boss has been defeated after " + event.getValue() + " rounds!";
        };
        System.out.println("[LOG] " + message);
    }
}