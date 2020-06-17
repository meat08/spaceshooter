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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.math.Rect;
import ru.spaceshooter.screen.GameScreen;

public class ExplosionCircle extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.12f;

    private float animateTimer;
    private Sound sound;
    private GameScreen screen;
    private Rect worldBounds;

    public ExplosionCircle(TextureAtlas atlas, GameScreen screen, Rect worldBounds) {
        super(atlas.findRegion("circleExplosion"), 4, 4, 16);
        this.sound = Gdx.audio.newSound(Gdx.files.internal("sounds/big_explosion.wav"));
        this.screen = screen;
        this.worldBounds = worldBounds;
    }

    private void pew() {
        if (screen.isSoundOn()) {
            sound.play(screen.getVolumeSound());
        }
    }

    public void generate(Vector2 pos) {
        destroyed = false;
        screen.setMainShipDestroy(true);
        setHeightProportion(worldBounds.getHeight()*2);
        this.pos.set(pos);
        pew();
        frame = 0;
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
                screen.setMainShipDestroy(false);
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }

    public void dispose() {
        sound.dispose();
    }
}
