package edu.unf.soc.monster_strategy_battle;

/*
    Character Select
 */

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.io.InputStream;

public class CharacterSelectActivity extends SimpleBaseGameActivity implements MenuScene.IOnMenuItemClickListener {

    public static int CAMERA_WIDTH = 480;
    public static int CAMERA_HEIGHT = 800;

    private Camera mCamera;

    private ITextureRegion mBackgroundTextureRegion;
    private ITextureRegion mNewGameMenuItemTexture;
    private ITextureRegion mLoadGameMenuItemTexture;

    private Scene scene = new Scene();
    private MenuScene menu = new MenuScene();

    @Override
    protected void onCreateResources() {
        try {

            // Set up bitmap textures
            ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/background.jpg");
                }
            });

            ITexture newGameMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/newGame.png");
                }
            });

            ITexture loadGameMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/loadGame.png");
                }
            });

            this.mNewGameMenuItemTexture = TextureRegionFactory.extractFromTexture(newGameMenuItemTexture);
            this.mLoadGameMenuItemTexture = TextureRegionFactory.extractFromTexture(loadGameMenuItemTexture);

            this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);

            backgroundTexture.load();
            newGameMenuItemTexture.load();
            loadGameMenuItemTexture.load();

        } catch (IOException e) {
            Debug.e(e);
        }
    }

    @Override
    protected Scene onCreateScene() {

        // Setup background
        Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
        this.scene.attachChild(backgroundSprite);

        // Create menu
        this.createMenu();

        return this.scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
    }

    private MenuScene createMenu() {
        this.menu = new MenuScene(mCamera);

        final IMenuItem newGameMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(0, mNewGameMenuItemTexture, getVertexBufferObjectManager()
                ), 2, 1);
        final IMenuItem loadGameMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(1, mLoadGameMenuItemTexture, getVertexBufferObjectManager()
                ), 2, 1);

        newGameMenuItem.setPosition(CAMERA_WIDTH / 2 - newGameMenuItem.getWidth() / 2, 100);
        loadGameMenuItem.setPosition(CAMERA_WIDTH / 2 - loadGameMenuItem.getWidth() / 2, 200);
        this.menu.addMenuItem(newGameMenuItem);
        this.menu.addMenuItem(loadGameMenuItem);

        this.menu.buildAnimations();
        this.menu.setBackgroundEnabled(false);

        this.menu.setOnMenuItemClickListener(this);
        this.scene.setChildScene(menu);

        return this.menu;
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

        switch(pMenuItem.getID())
        {
            case 0:
                // new game
                return true;
            case 1:
                // load game
                return true;
            default:
                return false;
        }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        System.exit(0);
    }

}
