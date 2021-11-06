package com.gdx.tia.element;

import com.badlogic.gdx.assets.AssetManager;
import com.gdx.tia.controller.AgentController;
import com.gdx.tia.controller.BulletController;
import com.gdx.tia.controller.EnemyController;

public class Stage extends World {

    AgentController agentController;
    BulletController bulletController;
    EnemyController enemyControllerController;

    public Stage(AssetManager assetManager) {
        super(assetManager);
    }

    @Override
    public void create() {
        super.create();

        bulletController = new BulletController(this);
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
