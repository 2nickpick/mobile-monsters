package edu.unf.soc.monster_strategy_battle;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class Monster extends GameObject {

    private String name;
    private ArrayList<MonsterType> types = new ArrayList<>();
    private ArrayList<Attack> attacks = new ArrayList<>();
    private int baseHP, currentHP, maxHP;
    private int baseAttack, currentAttack;
    private int baseDefense, currentDefense;
    private int baseSpecial, currentSpecial;
    private int baseSpeed, currentSpeed;
    private int level = 50;

    private Sound cry;

    // 62 frames of animation
    public static long[] releaseFrameDuration = new long[] {
            50L, 50L, 50L, 50L, 50L, 50L, 50L,
            50L, 50L, 50L, 50L, 50L, 50L, 50L,
            50L, 50L, 50L, 50L, 50L, 50L, 50L,
            50L, 50L, 50L, 50L, 50L, 50L, 50L,
            50L, 50L, 50L, 50L, 50L, 50L, 50L,
            50L, 50L, 50L, 50L, 50L, 50L, 50L,
            50L, 50L, 50L, 50L, 50L, 50L, 50L,
            50L, 50L, 50L, 50L, 50L, 50L, 50L,
            50L, 50L, 50L, 50L, 50L, 50L
    };
    private long[] attackFrameDuration = new long[3];
    private long[] damageFrameDuration = new long[3];
    private long[] faintFrameDuration = new long[3];

    private int[] releaseFrames = {0};
    private int[] attackFrames = {0};
    private int[] damageFrames = {0};
    private int[] faintFrames = {0};

    private ITiledTextureRegion preview;

    private MainActivity mainActivity;

    public Monster(final float pX, final float pY, final ITiledTextureRegion textureRegion, final VertexBufferObjectManager vertexBufferObjectManager) {
        super(pX, pY, textureRegion, vertexBufferObjectManager);
    }

    public Monster(final ITiledTextureRegion textureRegion, final VertexBufferObjectManager vertexBufferObjectManager, String name, ArrayList<MonsterType> types, ArrayList<Attack> attacks, Sound cry, MainActivity mainActivity) {
        super(0, 0, textureRegion, vertexBufferObjectManager);
        this.name = name;
        this.types = types;
        this.attacks = attacks;
        this.preview = textureRegion;
        this.mainActivity = mainActivity;
        this.cry = cry;
        this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        // setup stats based on monster name
        switch(name) {
            case "Charizard":
                this.baseHP = 78;
                this.baseAttack = 104;
                this.baseDefense = 78;
                this.baseSpecial = 130;
                this.baseSpeed = 100;

                break;
            case "Blastoise":
                this.baseHP = 79;
                this.baseAttack = 103;
                this.baseDefense = 120;
                this.baseSpecial = 125;
                this.baseSpeed = 78;
                break;
            case "Venusaur":
                this.baseHP = 80;
                this.baseAttack = 100;
                this.baseDefense = 123;
                this.baseSpecial = 121;
                this.baseSpeed = 80;
                break;
            case "Hitmonchan":
                this.baseHP = 50;
                this.baseAttack = 105;
                this.baseDefense = 79;
                this.baseSpecial = 80;
                this.baseSpeed = 76;
                break;
            case "Alakazam":
                this.baseHP = 55;
                this.baseAttack = 50;
                this.baseDefense = 65;
                this.baseSpecial = 145;
                this.baseSpeed = 150;
                break;
            case "Rhydon":
                this.baseHP = 105;
                this.baseAttack = 130;
                this.baseDefense = 120;
                this.baseSpecial = 45;
                this.baseSpeed = 40;
                break;
            case "Dragonite":
                this.baseHP = 91;
                this.baseAttack = 134;
                this.baseDefense = 95;
                this.baseSpecial = 100;
                this.baseSpeed = 80;
                break;
            case "Starmie":
                this.baseHP = 60;
                this.baseAttack = 75;
                this.baseDefense = 85;
                this.baseSpecial = 100;
                this.baseSpeed = 115;
                break;
            case "Raichu":
                this.baseHP = 60;
                this.baseAttack = 90;
                this.baseDefense = 55;
                this.baseSpecial = 85;
                this.baseSpeed = 110;
                break;
        }

        Random random = new Random();
        this.currentHP = (int)Math.ceil((random.nextInt(31) + 2 * this.baseHP ) * (this.level / 100f) + 60);
        this.maxHP = this.currentHP;

        this.currentAttack = (int)Math.ceil((random.nextInt(31) + 2 * this.baseAttack) * (this.level / 100f) + 5);
        this.currentDefense = (int)Math.ceil((random.nextInt(31) + 2 * this.baseDefense) * (this.level / 100f) + 5);
        this.currentSpecial = (int)Math.ceil((random.nextInt(31) + 2 * this.baseSpecial) * (this.level / 100f) + 5);
        this.currentSpeed = (int)Math.ceil((random.nextInt(31) + 2 * this.baseSpeed) * (this.level / 100f) + 5);
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

    public String getTypeString() {

        String typeString = "";
        for(int i = 0; i < this.types.size(); i++) {
            typeString += this.types.get(i).getName();
            if(i != this.types.size() - 1) {
                typeString += ", ";
            }
        }

        return typeString;
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

    public int getBaseHP() {
        return baseHP;
    }

    public void setBaseHP(int baseHP) {
        this.baseHP = baseHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public boolean isFainted() {
        return this.currentHP <= 0;
    }

    public String toString() {
        return this.name + "\tHP:" + this.currentHP;
    }

    public void release(Scene scene, boolean player) {
        this.detachSelf();
        scene.attachChild(this);
        this.cry();

        this.clearEntityModifiers();
        this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        this.registerEntityModifier(new AlphaModifier(1.5f, 0, 1));

        Rectangle monsterHPBar = player ? mainActivity.activePlayerMonsterHPBar : mainActivity.activeOpponentMonsterHPBar;

        monsterHPBar.setWidth((float)this.currentHP / this.maxHP * 96);

        if(!this.isInDanger()) {
            monsterHPBar.setColor(0, 1, 0);
        } else {
            monsterHPBar.setColor(1, 0, 0);
        }

        if(player) {
            this.setPosition(150 - this.getWidth()/2f, 280);
            this.setScaleX(-1);
        } else {
            this.setPosition(350 - this.getWidth()/2f, 60);
        }

        this.animate(releaseFrameDuration, 0, 61, true);
    }

    public void recall(Scene scene, boolean player) {
        this.cry();

        this.clearEntityModifiers();
        this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        this.registerEntityModifier(new AlphaModifier(1.5f, 1, 0));
    }

    public void attack(boolean player) {

        //attack animation
//        this.animate(releaseFrameDuration, 0, 61, true);

    }

    public void damage(boolean critical, final boolean player) {

        if(critical) {
            mainActivity.criticalSound.play();
        } else {
            mainActivity.damageSound.play();
        }

        final LoopEntityModifier blinker =
                new LoopEntityModifier(
                    new SequenceEntityModifier(
                            new FadeOutModifier(0.1f),
                            new FadeInModifier(0.1f)
                ),
                2);

        this.registerEntityModifier(blinker);

        final Rectangle monsterHPBar = player ? mainActivity.activePlayerMonsterHPBar : mainActivity.activeOpponentMonsterHPBar;
        final float desiredHPBarWidth = (float) currentHP / maxHP * 96;
        monsterHPBar.setWidth(desiredHPBarWidth);

        if(this.isInDanger()) {
            this.danger(player);
        }
    }

    public void takeDamage(int damage, boolean player) {

        if(damage > this.currentHP){
            damage = currentHP;
        }

        this.currentHP -= damage;

        Debug.d(this.getName() + " lost " + damage + "HP!!");
        Debug.d(this.getName() + "'s current HP: " + currentHP + "");

        if(this.isFainted()) {
            this.currentHP = 0;
        }
    }

    public void faint(boolean player) {
        this.cry();
        this.clearEntityModifiers();
        this.registerEntityModifier(new AlphaModifier(1.5f, 0, 255));
    }

    public boolean isInDanger() {
        return this.currentHP < Math.round(this.maxHP / 4) && this.currentHP > 0;
    }

    public void danger(boolean player) {
        this.setColor(1f, 125f / 255, 125f / 255);

        if(player) {
            mainActivity.activePlayerMonsterHPBar.setColor(1, 0, 0);
        } else {
            mainActivity.activeOpponentMonsterHPBar.setColor(1, 0, 0);
        }

    }

    public int getCurrentAttack() {
        return currentAttack;
    }

    public void setCurrentAttack(int currentAttack) {
        this.currentAttack = currentAttack;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(int currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getCurrentSpecial() {
        return currentSpecial;
    }

    public void setCurrentSpecial(int currentSpecial) {
        this.currentSpecial = currentSpecial;
    }

    public int getCurrentDefense() {
        return currentDefense;
    }

    public void setCurrentDefense(int currentDefense) {
        this.currentDefense = currentDefense;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void cry() {
        cry.play();
    }
}
