package edu.unf.soc.monster_strategy_battle;

import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by thenickpick on 11/23/2015.
 */
public class AttackMenuItemDecorator extends ScaleMenuItemDecorator {

    private int attackIndex;

    public AttackMenuItemDecorator(IMenuItem spriteMenuItem, float pSelectedScale, int pUnselectedScale) {
        super(spriteMenuItem, pSelectedScale, pUnselectedScale);
    }


    public int getAttackIndex() {
        return attackIndex;
    }

    public void setAttackIndex(int attackIndex) {
        this.attackIndex = attackIndex;
    }
}
