package com.gdx.tia.element;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.gdx.tia.controller.AgentController;
import com.gdx.tia.controller.BulletController;
import com.gdx.tia.controller.EnemyController;
import com.gdx.tia.screens.GameScreen;

public class Stage extends World {

    AgentController agentController;
    BulletController bulletController;
    EnemyController enemyControllerController;

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
        agentController = new AgentController(this, bulletController);
        enemyControllerController = new EnemyController(this, agentController);

        actionControllerList.add(bulletController);
        actionControllerList.add(agentController);
        actionControllerList.add(enemyControllerController);

        super.notifyCreation();
    }

    @Override
    public void dispose() {
        agentController.dispose();
    }

}
