package edu.unf.soc.monster_strategy_battle;

import org.andengine.engine.handler.physics.PhysicsHandler;
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

    // 62 frames of animation
    private long[] releaseFrameDuration = new long[62];
    private long[] attackFrameDuration = new long[62];
    private long[] damageFrameDuration = new long[62];
    private long[] faintFrameDuration = new long[62];

    private int[] releaseFrames = {0};
    private int[] attackFrames = {0};
    private int[] damageFrames = {0};
    private int[] faintFrames = {0};

    private ITiledTextureRegion preview;

    public Monster(final float pX, final float pY, final ITiledTextureRegion textureRegion, final VertexBufferObjectManager vertexBufferObjectManager) {
        super(pX, pY, textureRegion, vertexBufferObjectManager);

//        Arrays.fill(this.releaseFrameDuration, 10L);
//        Arrays.fill(this.attackFrameDuration, 10L);
//        Arrays.fill(this.damageFrameDuration, 10L);
//        Arrays.fill(this.faintFrameDuration, 10L);
    }

    public Monster(final ITiledTextureRegion textureRegion, final VertexBufferObjectManager vertexBufferObjectManager, String name, ArrayList<MonsterType> types, ArrayList<Attack> attacks) {
        super(0, 0, textureRegion, vertexBufferObjectManager);
        this.name = name;
        this.types = types;
        this.attacks = attacks;
        this.preview = textureRegion;

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

        if(player) {
            this.setPosition(40, 280);
            this.setScaleX(-1);
        } else {
            this.setPosition(220, 60);
        }

        this.animate(releaseFrameDuration, 0, 61, true);
    }

    public void attack() {
        this.animate(attackFrameDuration, attackFrames, true);
    }

    public void damage(int damage) {

        if(damage > this.currentHP){
            damage = currentHP;
        }

        this.currentHP -= damage;
        this.animate(damageFrameDuration, damageFrames, true);

        Debug.d(this.getName() + " lost " + damage + "HP!!");
        Debug.d(this.getName() + "'s current HP: " + currentHP + "");

        if(this.isFainted()) {
            this.currentHP = 0;
            this.faint();
        }
    }

    public void faint() {
        Debug.d(this.getName() + " fainted!!!");
        this.animate(faintFrameDuration, faintFrames, true);
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
}
