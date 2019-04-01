package com.wisekrakr.androidmain.helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.wisekrakr.androidmain.MyAssetManager;


public class SpriteHelper {
    private SpriteHelper() {
        // fake constructor to prevent instantiation
    }

    public static Sprite entitySprite(MyAssetManager myAssetManager, String spritePath, Float width, Float height) {
        Texture texture = myAssetManager.assetManager.get(spritePath);
        Sprite sprite = new Sprite(texture);

        float rotation = sprite.getRotation() * 180 / MathUtils.PI;

        sprite.setOrigin(width/2, height/2);

        sprite.setRotation(rotation);

        return sprite;

    }

    public static TextureRegion entitySpriteAtlas(Entity entity, MyAssetManager myAssetManager, String atlasPath, String regionPath, Body body, SpriteBatch targetBatch, Float width, Float height) {
        TextureRegion region = new TextureRegion();
        if (entity != null) {
            TextureAtlas atlas = myAssetManager.assetManager.get(atlasPath);

            region = atlas.findRegion(regionPath);
            Sprite sprite = new Sprite(region);

            float rotation = body.getAngle() * 180 / MathUtils.PI;

            sprite.setBounds(body.getPosition().x - width / 2, body.getPosition().y - height / 2, width, height);

            sprite.setOrigin(width / 2, height / 2);

            sprite.setRotation(rotation);

            sprite.draw(targetBatch);
        }

        return region;
    }



}
