package com.azortis.playerwarps.hook;

public interface IHook<T> {
    boolean initialize();
    T getHookData();
}
