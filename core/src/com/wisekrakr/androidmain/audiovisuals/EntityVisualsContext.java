package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.Entity;

public interface EntityVisualsContext {

    void visualizeColoredEntity(Entity entity);

    void drawObjectViaAtlas(Entity entity, String atlasPath, String regionPath, float width, float height);
    void drawObjectViaFileName(Entity entity, String fileName, float width, float height);
    void animate(Entity entity);
}
