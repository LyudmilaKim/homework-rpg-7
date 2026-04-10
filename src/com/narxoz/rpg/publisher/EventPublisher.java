package com.narxoz.rpg.publisher;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameObserver;
import java.util.ArrayList;
import java.util.List;

public class EventPublisher {
    private final List<GameObserver> observers = new ArrayList<>();

    public void addObserver(GameObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void notifyObservers(GameEvent event) {
        for (GameObserver observer : new ArrayList<>(observers)) {
            observer.onEvent(event);
        }
    }
}