package edu.unf.soc.monster_strategy_battle;

/*
    Main Menu
 */

import android.graphics.Color;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends SimpleBaseGameActivity implements MenuScene.IOnMenuItemClickListener {

    public static int CAMERA_WIDTH = 480;
    public static int CAMERA_HEIGHT = 800;

    private Camera mCamera;

    private ITextureRegion mBackgroundTextureRegion;
    private ITextureRegion mNewGameMenuItemTexture;
    private ITextureRegion mLoadGameMenuItemTexture;
    private ITextureRegion mPreviewRedTexture;
    private ITextureRegion mPreviewOakTexture;
    private ITextureRegion mPreviewRocketTexture;
    private ITextureRegion mMainMenuItemTexture;

    private ITextureRegion mChooseRedTexture;
    private ITextureRegion mChooseOakTexture;
    private ITextureRegion mChooseRocketTexture;
    private ITextureRegion mBackMenuItemTexture;

    private ITextureRegion mPreviewCharizardTexture;
    private ITextureRegion mPreviewRaichuTexture;
    private ITextureRegion mPreviewStarmieTexture;

    private ITextureRegion mExitGameMenuItemTexture;

    private Scene scene = new Scene();
    private MenuScene mainMenu = new MenuScene();
    private MenuScene characterSelectMenu = new MenuScene();
    private MenuScene characterPreviewMenu = new MenuScene();
    private int characterSelected;

    private Music menuMusic;
    private Sound menuCursorSound;
    private ITexture fontTextureAtlas;
    private Font comicSansFull, comicSansH1, comicSansH2;

    private ArrayList<ArrayList<Monster>> characterTeams = new ArrayList<>();
    private TiledTextureRegion mCharizardTexture;
    private TiledTextureRegion mRaichuTexture;
    private TiledTextureRegion mStarmieTexture;

    @Override
    protected void onCreateResources() {
        try {

            BitmapTextureAtlas debugTexture = new BitmapTextureAtlas(this.getTextureManager(), 1536, 960,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);

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

            ITexture exitGameMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/exitGame.png");
                }
            });

            ITexture selectRedTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/selectRed.png");
                }
            });

            ITexture selectOakTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/selectOak.png");
                }
            });

            ITexture selectRocketTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/selectRocket.png");
                }
            });

            ITexture chooseRedTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/iChooseRed.png");
                }
            });

            ITexture chooseOakTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/iChooseOak.png");
                }
            });

            ITexture chooseRocketTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/iChooseRocket.png");
                }
            });

            ITexture mainMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/mainMenu.png");
                }
            });

            ITexture backMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/backMenu.png");
                }
            });

            ITexture charizardPreviewTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/monsters/charizard.png");
                }
            });

            ITexture raichuPreviewTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/monsters/raichu.png");
                }
            });

            ITexture starmiePreviewTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/monsters/starmie.png");
                }
            });

            fontTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            comicSansFull = FontFactory.createFromAsset(
                    this.getFontManager(),
                    fontTextureAtlas,
                    this.getAssets(),
                    "fonts/comicsans.ttf",
                    45f,
                    true,
                    Color.WHITE
            );

            comicSansH1 = FontFactory.createFromAsset(
                    this.getFontManager(),
                    fontTextureAtlas,
                    this.getAssets(),
                    "fonts/comicsans.ttf",
                    38f,
                    true,
                    Color.WHITE
            );

            comicSansH2 = FontFactory.createFromAsset(
                    this.getFontManager(),
                    fontTextureAtlas,
                    this.getAssets(),
                    "fonts/comicsans.ttf",
                    30f,
                    true,
                    Color.WHITE
            );

            this.mNewGameMenuItemTexture = TextureRegionFactory.extractFromTexture(newGameMenuItemTexture);
            this.mLoadGameMenuItemTexture = TextureRegionFactory.extractFromTexture(loadGameMenuItemTexture);
            this.mExitGameMenuItemTexture = TextureRegionFactory.extractFromTexture(exitGameMenuItemTexture);

            this.mPreviewRedTexture = TextureRegionFactory.extractFromTexture(selectRedTexture);
            this.mPreviewOakTexture = TextureRegionFactory.extractFromTexture(selectOakTexture);
            this.mPreviewRocketTexture = TextureRegionFactory.extractFromTexture(selectRocketTexture);
            this.mMainMenuItemTexture = TextureRegionFactory.extractFromTexture(mainMenuItemTexture);

            this.mChooseRedTexture = TextureRegionFactory.extractFromTexture(chooseRedTexture);
            this.mChooseOakTexture = TextureRegionFactory.extractFromTexture(chooseOakTexture);
            this.mChooseRocketTexture = TextureRegionFactory.extractFromTexture(chooseRocketTexture);
            this.mBackMenuItemTexture = TextureRegionFactory.extractFromTexture(backMenuItemTexture);

            this.mPreviewCharizardTexture = TextureRegionFactory.extractFromTexture(charizardPreviewTexture);
            this.mPreviewRaichuTexture = TextureRegionFactory.extractFromTexture(raichuPreviewTexture);
            this.mPreviewStarmieTexture = TextureRegionFactory.extractFromTexture(starmiePreviewTexture);

            this.mPreviewCharizardTexture = TextureRegionFactory.extractFromTexture(charizardPreviewTexture);
            this.mPreviewRaichuTexture = TextureRegionFactory.extractFromTexture(raichuPreviewTexture);
            this.mPreviewStarmieTexture = TextureRegionFactory.extractFromTexture(starmiePreviewTexture);

            this.mCharizardTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(debugTexture, this.getAssets(),
                    "graphics/debug.png", 0, 0, 32, 20);

            this.mRaichuTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(debugTexture, this.getAssets(),
                    "graphics/debug.png", 0, 0, 32, 20);

            this.mStarmieTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(debugTexture, this.getAssets(),
                    "graphics/debug.png", 0, 0, 32, 20);

            this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);

            backgroundTexture.load();
            newGameMenuItemTexture.load();
            loadGameMenuItemTexture.load();
            exitGameMenuItemTexture.load();
            selectRedTexture.load();
            selectOakTexture.load();
            selectRocketTexture.load();
            chooseRedTexture.load();
            chooseOakTexture.load();
            chooseRocketTexture.load();
            mainMenuItemTexture.load();
            backMenuItemTexture.load();

            charizardPreviewTexture.load();
            raichuPreviewTexture.load();
            starmiePreviewTexture.load();

            comicSansFull.load();
            comicSansH1.load();
            comicSansH2.load();

            // music
            menuMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "music/menu.mp3");
            menuMusic.setVolume(.75f);

            // sounds
            menuCursorSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/menu.wav");

        } catch (IOException e) {
            Debug.e(e);
        }
    }

    @Override
    protected Scene onCreateScene() {

        // Setup background
        Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
        this.scene.attachChild(backgroundSprite);

        // Generate Character Teams
        this.generateTeams();

        // Create mainMenu
        this.createMainMenu();

        // Create characterSelectMenu
        this.createCharacterSelectMenu();

        // Start at the Main Menu
        this.scene.setChildScene(this.mainMenu);
        menuMusic.setLooping(true);
        menuMusic.play();

        return this.scene;
    }

    private void generateTeams() {

        MonsterType fire = new MonsterType("Fire", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType water = new MonsterType("Water", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType flying = new MonsterType("Flying", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType electric = new MonsterType("Electric", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType psychic = new MonsterType("Psychic", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        Attack flamethrower = new Attack("Flamethrower", 95, 100, fire);
        Attack wingAttack = new Attack("Wing Attack", 95, 100, flying);
        Attack surf = new Attack("Surf", 95, 100, water);
        Attack psychicAttack = new Attack("Psychic", 100, 100, psychic);
        Attack thunderbolt = new Attack("Thunderbolt", 90, 100, electric);

        ArrayList<MonsterType> charizardTypes = new ArrayList<>();
        charizardTypes.add(fire);
        charizardTypes.add(flying);

        ArrayList<Attack> charizardAttacks = new ArrayList<>();
        charizardAttacks.add(flamethrower);
        charizardAttacks.add(wingAttack);
        Monster charizard = new Monster(
                this.mCharizardTexture,
                this.getVertexBufferObjectManager(),
                "Charizard",
                charizardTypes,
                charizardAttacks
        );

        ArrayList<MonsterType> raichuTypes = new ArrayList<>();
        raichuTypes.add(electric);

        ArrayList<Attack> raichuAttacks = new ArrayList<>();
        raichuAttacks.add(thunderbolt);
        raichuAttacks.add(wingAttack);
        Monster raichu = new Monster(
                this.mRaichuTexture,
                this.getVertexBufferObjectManager(),
                "Raichu",
                raichuTypes,
                raichuAttacks
        );

        ArrayList<MonsterType> starmieTypes = new ArrayList<>();
        starmieTypes.add(water);
        starmieTypes.add(psychic);

        ArrayList<Attack> starmieAttacks = new ArrayList<>();
        starmieAttacks.add(surf);
        starmieAttacks.add(psychicAttack);
        Monster starmie = new Monster(
                this.mStarmieTexture,
                this.getVertexBufferObjectManager(),
                "Starmie",
                starmieTypes,
                starmieAttacks
        );

        ArrayList<Monster> red = new ArrayList<Monster>();
        red.add(charizard);
        red.add(raichu);
        red.add(starmie);

        ArrayList<Monster> oak = new ArrayList<Monster>();
        red.add(charizard);
        red.add(raichu);
        red.add(starmie);

        ArrayList<Monster> rocket = new ArrayList<Monster>();
        red.add(charizard);
        red.add(raichu);
        red.add(starmie);

        this.characterTeams.add(red);
        this.characterTeams.add(oak);
        this.characterTeams.add(rocket);
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        EngineOptions options = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);

        options.getAudioOptions().setNeedsMusic(true);
        options.getAudioOptions().setNeedsSound(true);

        return options;
    }

    private MenuScene createMainMenu() {
        this.mainMenu = new MenuScene(mCamera);
        this.mainMenu.setY(255);
        this.mainMenu.setX(105);

        final IMenuItem newGameMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(0, mNewGameMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem loadGameMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(1, mLoadGameMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem exitGameMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(2, mExitGameMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        this.mainMenu.addMenuItem(newGameMenuItem);
        this.mainMenu.addMenuItem(loadGameMenuItem);
        this.mainMenu.addMenuItem(exitGameMenuItem);

        this.mainMenu.buildAnimations();
        this.mainMenu.setBackgroundEnabled(false);

        this.mainMenu.setOnMenuItemClickListener(this);

        return this.mainMenu;
    }

    private MenuScene createCharacterSelectMenu() {
        this.characterSelectMenu = new MenuScene(mCamera);
        this.characterSelectMenu.setY(210);
        this.characterSelectMenu.setX(105);

        final IMenuItem redMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(3, mPreviewRedTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem oakMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(4, mPreviewOakTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem rocketMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(5, mPreviewRocketTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem mainMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(6, mMainMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        this.characterSelectMenu.addMenuItem(redMenuItem);
        this.characterSelectMenu.addMenuItem(oakMenuItem);
        this.characterSelectMenu.addMenuItem(rocketMenuItem);
        this.characterSelectMenu.addMenuItem(mainMenuItem);

        this.characterSelectMenu.buildAnimations();
        this.characterSelectMenu.setBackgroundEnabled(false);

        this.characterSelectMenu.setOnMenuItemClickListener(this);

        return this.mainMenu;
    }

    private MenuScene createCharacterPreviewMenu() {
        this.characterPreviewMenu = new MenuScene(mCamera);
        this.characterPreviewMenu.setY(300);
        this.characterPreviewMenu.setX(105);

        ITextureRegion characterTexture;
        switch(this.characterSelected) {
            case 1:
                characterTexture = mChooseOakTexture;
                break;
            case 2:
                characterTexture = mChooseRocketTexture;
                break;
            case 0:
            default:
                characterTexture = mChooseRedTexture;
                break;
        }

        ArrayList<Monster> team = this.characterTeams.get(characterSelected);

        ITextureRegion monster1Texture = mPreviewCharizardTexture;
        ITextureRegion monster2Texture = mPreviewRaichuTexture;
        ITextureRegion monster3Texture = mPreviewStarmieTexture;

        final IMenuItem selectCharacterMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(7, characterTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        final IMenuItem backMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(8, mBackMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        final Sprite monster1 = new Sprite(-60, -260, monster1Texture, getVertexBufferObjectManager());
        final Sprite monster2 = new Sprite(-60, -110, monster2Texture, getVertexBufferObjectManager());
        final Sprite monster3 = new Sprite(-60, 40, monster3Texture, getVertexBufferObjectManager());

        final Text monster1Name = new Text(95, -260, this.comicSansH1, "Charizard", this.getVertexBufferObjectManager());
        final Text monster2Name = new Text(95, -110, this.comicSansH1, "Raichu", this.getVertexBufferObjectManager());
        final Text monster3Name = new Text(95, 40, this.comicSansH1, "Starmie", this.getVertexBufferObjectManager());

        final Text monster1Type = new Text(95, -225, this.comicSansH1, "Fire/Flying", this.getVertexBufferObjectManager());
        final Text monster2Type = new Text(95, -75, this.comicSansH1, "Electric", this.getVertexBufferObjectManager());
        final Text monster3Type = new Text(95, 75, this.comicSansH1, "Water/Psychic", this.getVertexBufferObjectManager());

        this.characterPreviewMenu.addMenuItem(selectCharacterMenuItem);
        this.characterPreviewMenu.addMenuItem(backMenuItem);

        this.characterPreviewMenu.attachChild(monster1);
        this.characterPreviewMenu.attachChild(monster1Name);
        this.characterPreviewMenu.attachChild(monster1Type);
        this.characterPreviewMenu.attachChild(monster2);
        this.characterPreviewMenu.attachChild(monster2Name);
        this.characterPreviewMenu.attachChild(monster2Type);
        this.characterPreviewMenu.attachChild(monster3);
        this.characterPreviewMenu.attachChild(monster3Name);
        this.characterPreviewMenu.attachChild(monster3Type);

        this.characterPreviewMenu.buildAnimations();
        this.characterPreviewMenu.setBackgroundEnabled(false);

        this.characterPreviewMenu.setOnMenuItemClickListener(this);

        return this.mainMenu;
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

        menuCursorSound.play();
        switch(pMenuItem.getID())
        {
            case 0:
            case 8:
                // new game
                this.scene.setChildScene(this.characterSelectMenu);
                return true;
            case 1:
                // load game
                return true;
            case 2:
                // exit game
                System.exit(0);
                return true;
            case 3:
                // preview RED
                this.characterSelected = 0;
                this.createCharacterPreviewMenu();
                this.scene.setChildScene(this.characterPreviewMenu);
                return true;
            case 4:
                // preview OAK
                this.characterSelected = 1;
                this.createCharacterPreviewMenu();
                this.scene.setChildScene(this.characterPreviewMenu);
                return true;
            case 5:
                // preview ROCKET
                this.characterSelected = 2;
                this.createCharacterPreviewMenu();
                this.scene.setChildScene(this.characterPreviewMenu);
                return true;
            case 6:
                // back to main menu
                this.scene.setChildScene(this.mainMenu);
                return true;
            case 7:
                // I choose you!
                Debug.d("Character Selected!!");
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
