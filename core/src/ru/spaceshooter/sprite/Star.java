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
package ru.spaceshooter.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.math.Rnd;

public class Star extends Sprite {

    private Vector2 v;
    private Rect worldBounds;
    private float animateTimer;
    private float animateInterval;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        v = new Vector2();
        setVStart();
        worldBounds = new Rect();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.008f);
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
        animateInterval = Rnd.nextFloat(0.5f, 1.5f);
    }

    @Override
    public void update(float delta) {
        setScale(getScale() - 0.008f);
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            setScale(1f);
            animateTimer = 0f;
        }
        pos.mulAdd(v, delta);
        checkBounds();
    }

    public void addVY(float vy) {
        if (this.v.y > -0.55f) {
            this.v.add(0f, -vy);
        }
    }

    public void setVStart() {
        float vx = Rnd.nextFloat(-0.005f, 0.005f);
        float vy = Rnd.nextFloat(-0.2f, -0.05f);
        v.set(vx, vy);
    }

    public float getVY() {
        return v.y;
    }

    public void setVY(float vy) {
        this.v.y = vy;
    }

    private void checkBounds() {
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
    }
}
