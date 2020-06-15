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
package ru.spaceshooter.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {

    private final List<T> activeObjects = new ArrayList<>();
    private final List<T> freeObjects = new ArrayList<>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (Sprite sprite : activeObjects) {
            if (sprite.isDestroyed()) {
                continue;
            }
            sprite.update(delta);
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {
        for (Sprite sprite : activeObjects) {
            if (sprite.isDestroyed()) {
                continue;
            }
            sprite.draw(batch);
        }
    }

    public void freeAllDestroyed() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T object = activeObjects.get(i);
            if (object.isDestroyed()) {
                free(object);
                i--;
            }
        }
    }

    public void freeAllActive() {
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }

    private void free(T object) {
        object.flushDestroy();
        if (activeObjects.remove(object)) {
            freeObjects.add(object);
        }
    }
}
