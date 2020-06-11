package ru.spaceshooter.utils;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private int frags;
    private int level;
    private int maxHp;
    private int hp;
    private int lives;
    private float mainShipX;
    private List<Float> starV;

    public void saveGameData(int frags, int level, int maxHp, int hp, float mainShipX, int lives) {
        this.frags = frags;
        this.level = level;
        this.maxHp = maxHp;
        this.hp = hp;
        this.mainShipX = mainShipX;
        this.lives = lives;
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

    public int getLives() {
        return lives;
    }

    public List<Float> getStarV() {
        return starV;
    }
}
