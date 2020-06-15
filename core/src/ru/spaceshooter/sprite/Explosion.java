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

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.spaceshooter.base.Sprite;
import ru.spaceshooter.screen.GameScreen;

public class Explosion extends Sprite {

    private float animateTimer;
    private Sound sound;
    private GameScreen screen;

    public Explosion(TextureAtlas atlas, Sound sound, GameScreen screen) {
        super(atlas.findRegion("explosion"), 4, 4, 16);
        this.sound = sound;
        this.screen = screen;
    }

    private void pew() {
        if (screen.isSoundOn() & screen.isNotNuked()) {
            sound.play(screen.getVolumeSound());
        }
    }

    public void set(float height, Vector2 pos) {
        setHeightProportion(height);
        this.pos.set(pos);
        pew();
        frame = 0;
    }

    public void set(float height, float halfWidth, Vector2 pos) {
        setHeightProportion(height);
        setWidth(halfWidth);
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
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
