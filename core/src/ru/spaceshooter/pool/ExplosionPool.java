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
package ru.spaceshooter.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.spaceshooter.base.SpritesPool;
import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.sprite.Explosion;
import ru.spaceshooter.utils.Assets;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureAtlas atlas;
    private Sound sound;
    private GameScreen screen;

    public ExplosionPool(TextureAtlas atlas, GameScreen screen) {
        this.atlas = atlas;
        this.screen = screen;
        sound = Assets.getInstance().getAssetManager().get("sounds/explosion.wav");
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, sound, screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
