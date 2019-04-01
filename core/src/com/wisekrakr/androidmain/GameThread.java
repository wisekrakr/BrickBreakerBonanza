package com.wisekrakr.androidmain;


import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.factories.EntityFactory;
import com.wisekrakr.androidmain.retainers.TimeKeeper;
import com.wisekrakr.androidmain.systems.ComponentMapperSystem;
import com.wisekrakr.androidmain.systems.PhysicsSystem;

public class GameThread {

    private final EntityFactory entityFactory;
    private LevelGenerationSystem levelGenerationSystem;
    private AndroidGame game;
    private PooledEngine engine;
    private TimeKeeper timeKeeper;
    private ComponentMapperSystem componentMapperSystem;

    protected GameThread(AndroidGame game) {
        this.game = game;

        timeKeeper = new TimeKeeper();

        engine = game.getEngine();

        entityFactory = new EntityFactory(game, engine);
        levelGenerationSystem = new LevelGenerationSystem(game, entityFactory);
        componentMapperSystem = new ComponentMapperSystem();

        init();

    }

    private void init() {
        engine.addSystem(new PhysicsSystem(entityFactory.world));
    }

    public EntityFactory getEntityFactory(){return entityFactory;}

    public LevelGenerationSystem getLevelGenerationSystem(){return levelGenerationSystem;}

    public TimeKeeper getTimeKeeper() {
        return timeKeeper;
    }

    public ComponentMapperSystem getComponentMapperSystem() {
        return componentMapperSystem;
    }
}
