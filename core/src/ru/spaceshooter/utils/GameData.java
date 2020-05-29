package ru.spaceshooter.utils;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private int frags;
    private int level;
    private int maxHp;
    private int hp;
    private float mainShipX;
    private List<Float> starV;

    public void saveGameData(int frags, int level, int maxHp, int hp, float mainShipX) {
        this.frags = frags;
        this.level = level;
        this.maxHp = maxHp;
        this.hp = hp;
        this.mainShipX = mainShipX;
        starV = new ArrayList<>();
    }

    public void saveStarV (float vy) {
        starV.add(vy);
    }

    public int getFrags() {
        return frags;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getHp() {
        return hp;
    }

    public float getMainShipX() {
        return mainShipX;
    }

    public List<Float> getStarV() {
        return starV;
    }
}
