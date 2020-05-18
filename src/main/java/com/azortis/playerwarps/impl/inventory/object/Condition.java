package com.azortis.playerwarps.impl.inventory.object;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Condition<T> {
    private Set<Predicate<T>> requirements;
    private Action<T> action;

    public Condition() {
        requirements = new HashSet<>();

    }
    public Set<Predicate<T>> getRequirements() {
        return requirements;
    }

    public Condition<T> setRequirements(Set<Predicate<T>> requirements) {
        this.requirements = requirements;
        return this;
    }

    public Condition<T> addRequirements(Predicate<T> requirement) {
        requirements.add(requirement);
        return this;
    }

    public Action<T> getAction() {
        return action;
    }

    public Condition<T> setAction(Action<T> action) {
        this.action = action;
        return this;
    }

    public void test(T event) {
        for(Predicate<T> predicate : requirements) {
            if(!predicate.test(event)) return;
        }
        action.action(event);
    }
}
