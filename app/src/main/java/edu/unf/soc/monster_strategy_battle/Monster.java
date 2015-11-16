package edu.unf.soc.monster_strategy_battle;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.ArrayList;

public class Monster extends GameObject {

    private String name;
    private ArrayList<MonsterType> types = new ArrayList<>();
    private ArrayList<Attack> attacks = new ArrayList<>();

    private ITiledTextureRegion preview;

    public Monster(final float pX, final float pY, final ITiledTextureRegion textureRegion, final VertexBufferObjectManager vertexBufferObjectManager) {
        super(pX, pY, textureRegion, vertexBufferObjectManager);
    }

    public Monster(final ITiledTextureRegion textureRegion, final VertexBufferObjectManager vertexBufferObjectManager, String name, ArrayList<MonsterType> types, ArrayList<Attack> attacks) {
        super(0, 0, textureRegion, vertexBufferObjectManager);
        this.name = name;
        this.types = types;
        this.attacks = attacks;
    }

    protected void onManagedUpdate(float pSecondsElapsed) {
        move();

        super.onManagedUpdate(pSecondsElapsed);
    }

    public void move() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MonsterType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<MonsterType> types) {
        this.types = types;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(ArrayList<Attack> attacks) {
        this.attacks = attacks;
    }

    public ITiledTextureRegion getPreview() {
        return preview;
    }

    public void setPreview(ITiledTextureRegion preview) {
        this.preview = preview;
    }
}
