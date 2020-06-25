/*    Copyright (C) 2020  Ilya Mafov <i.mafov@gmail.com>
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package ru.spaceshooter.utils;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private int frags;
    private int level;
    private int maxHp;
    private int hp;
    private int lives;
    private int upgradeCount;
    private float mainShipX;
    private List<Float> starV;

    public void saveGameData(int frags, int level, int maxHp, int hp, float mainShipX, int lives, int upgradeCount) {
        this.frags = frags;
        this.level = level;
        this.maxHp = maxHp;
        this.hp = hp;
        this.mainShipX = mainShipX;
        this.lives = lives;
        this.upgradeCount = upgradeCount;
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

    public int getUpgradeCount() {
        return upgradeCount;
    }

    public List<Float> getStarV() {
        return starV;
    }
}
