package edu.unf.soc.monster_strategy_battle;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.util.debug.Debug;

import java.util.Arrays;

/**
 * Created by thenickpick on 11/27/2015.
 */
public abstract class OutputScene extends Scene implements IOnSceneTouchListener {

    protected MainActivity mainActivity;
    private Font sansSmall;

    private String outputText;
    private boolean ready; //finished writing out
    private boolean complete; //load next scene
    protected boolean animating = false; //onSceneReady is still running
    private int gameOutputTextCounter;

    private final Text gameOutputText;

    public OutputScene(String outputText, MainActivity mainActivity, Font sansSmall) {
        super();
        this.outputText = outputText;
        this.ready = false;
        this.complete = false;
        this.mainActivity = mainActivity;
        this.sansSmall = sansSmall;

        this.setBackgroundEnabled(false);

        this.setPosition(0, 0);
        setOnSceneTouchListener(this);

        Rectangle gameOutputBox = new Rectangle(
                10,
                700,
                mainActivity.mCamera.getWidth() - 20,
                90,
                mainActivity.getVertexBufferObjectManager()
        );
        gameOutputBox.setAlpha(0.6f);
        gameOutputBox.setColor(0f, 0f, 0f);

        char[] array = new char[outputText.length()];
        Arrays.fill(array, ' ');
        String startingOutputText = new String(array);

        gameOutputText = new Text(
                15,
                15,
                sansSmall,
                startingOutputText,
                mainActivity.getVertexBufferObjectManager()
        );

        final String handlerOutputText = outputText;

        gameOutputText.registerUpdateHandler(new TimerHandler(0.1f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                // draw the next frame
                if (!ready && gameOutputTextCounter <= handlerOutputText.length()) {
                    gameOutputText.setText(handlerOutputText.substring(0, gameOutputTextCounter));
                    gameOutputTextCounter++;
                }

                // if the string is done, load up the next one
                if (gameOutputTextCounter > handlerOutputText.length()) {
                    ready = true;

                    // run the callback
                    animating = true;
                    onSceneReady();
                }
            }
        }));

        gameOutputBox.attachChild(gameOutputText);
        this.attachChild(gameOutputBox);

    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, final TouchEvent pSceneTouchEvent)
    {
        if (pSceneTouchEvent.isActionDown())
        {
            if(!this.ready) {
                this.ready = true;
                this.gameOutputText.setText(outputText);
                gameOutputTextCounter = outputText.length();

                // run the callback
                this.animating = true;
                onSceneReady();

            } else if(!this.animating) {
                this.complete = true;
                mainActivity.drawGameOutputQueue();
            }
        }
        return false;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    abstract public void onSceneReady();
}
