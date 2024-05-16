package me.davipccunha.tests.chatchannels.cache;

import java.util.HashMap;

public class LastTellCache {
    private final HashMap<String, String> lastTell = new HashMap<>();

    public void setLastTell(String receiver, String sender) {
        lastTell.put(receiver, sender);
    }

    public String getLastTell(String receiver) {
        return lastTell.get(receiver);
    }

    public void removeLastTell(String receiver) {
        lastTell.remove(receiver);
    }

    public boolean hasLastTell(String receiver) {
        return lastTell.containsKey(receiver);
    }
}
