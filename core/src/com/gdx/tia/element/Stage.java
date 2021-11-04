package com.gdx.tia.element;

import com.badlogic.gdx.assets.AssetManager;
import com.gdx.tia.controller.AgentController;
import com.gdx.tia.controller.BulletController;

public class Stage extends World {

    AgentController agentController;
    BulletController bulletController;

    public Stage(AssetManager assetManager) {
        super(assetManager);
    }

    @Override
    public void create() {
        super.create();

        bulletController = new BulletController(this);
        agentController = new AgentController(this, bulletController);

        actionControllerList.add(bulletController);
        actionControllerList.add(agentController);

        super.notifyCreation();
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        agentController.dispose();
    }

}
