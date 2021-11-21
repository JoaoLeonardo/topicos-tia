package com.gdx.tia.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.gdx.tia.controller.*;
import com.gdx.tia.screens.GameScreen;

public class Stage extends World {

    AgentController agentController;
    BulletController bulletController;
    EnemyController enemyControllerController;
    HudController hudController;

    public Stage(Batch batch, GameScreen gameScreen) {
        super(batch, gameScreen);
    }

    @Override
    public Vector2 getPlayerPosition() {
        return new Vector2(agentController.getAgentSprite().getX(), agentController.getAgentSprite().getY());
    }

    @Override
    public void create() {
        super.create();

        bulletController = new BulletController();
        agentController = new AgentController(bulletController);
        enemyControllerController = new EnemyController();
        hudController = new HudController();

        actionControllerList.add(bulletController);
        actionControllerList.add(agentController);
        actionControllerList.add(enemyControllerController);
        actionControllerList.add(hudController);

        super.notifyCreation();
    }

    @Override
    public void render() {
        if (agentController.getAgent().alive) super.render();
        else super.complete();
    }

    @Override
    public void dispose() {
        agentController.dispose();
        hudController.dispose();
    }

}
