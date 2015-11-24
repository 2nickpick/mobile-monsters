package edu.unf.soc.monster_strategy_battle;

import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;

/**
 * Created by thenickpick on 11/23/2015.
 */
public class MonsterMenuItemDecorator extends ScaleMenuItemDecorator {

    private int monsterIndex;

    public MonsterMenuItemDecorator(IMenuItem spriteMenuItem, float pSelectedScale, int pUnselectedScale) {
        super(spriteMenuItem, pSelectedScale, pUnselectedScale);
    }


    public int getMonsterIndex() {
        return monsterIndex;
    }

    public void setMonsterIndex(int monsterIndex) {
        this.monsterIndex = monsterIndex;
    }
}
