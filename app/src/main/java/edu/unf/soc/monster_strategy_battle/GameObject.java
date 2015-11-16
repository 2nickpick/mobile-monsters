package edu.unf.soc.monster_strategy_battle;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class GameObject extends AnimatedSprite {

    public PhysicsHandler mPhysicsHandler;

    public GameObject(final float pX, final float pY, final ITiledTextureRegion textureRegion, final VertexBufferObjectManager vertexBufferObjectManager) {
        super(pX, pY, textureRegion, vertexBufferObjectManager);
        this.mPhysicsHandler = new PhysicsHandler(this);
        this.registerUpdateHandler(this.mPhysicsHandler);
    }

    protected void onManagedUpdate(float pSecondsElapsed) {
        move();

        super.onManagedUpdate(pSecondsElapsed);
    }

    public abstract void move();

}
