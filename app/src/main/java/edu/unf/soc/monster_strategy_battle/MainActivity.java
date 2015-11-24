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
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends SimpleBaseGameActivity implements MenuScene.IOnMenuItemClickListener {

    public static int CAMERA_WIDTH = 480;
    public static int CAMERA_HEIGHT = 800;

    private Camera mCamera;

    private ITextureRegion mBackgroundTextureRegion;
    private ITextureRegion mBattleBackgroundTextureRegion;
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

    private ITextureRegion mPreviewBlastoiseTexture;
    private ITextureRegion mPreviewHitmonchanTexture;
    private ITextureRegion mPreviewAlakazamTexture;

    private ITextureRegion mPreviewVenusaurTexture;
    private ITextureRegion mPreviewRhydonTexture;
    private ITextureRegion mPreviewDragoniteTexture;

    private ITextureRegion mExitGameMenuItemTexture;

    private ITextureRegion mAttackBattleMenuItemTexture;
    private ITextureRegion mSwitchBattleMenuItemTexture;
    private ITextureRegion mExitBattleMenuItemTexture;
    private ITextureRegion mBlankAttackMenuItemTexture;

    private Scene scene = new Scene();
    private MenuScene mainMenu = new MenuScene();
    private MenuScene characterSelectMenu = new MenuScene();
    private MenuScene characterPreviewMenu = new MenuScene();
    private int playerIndex;
    private int activePlayerMonsterIndex = 0;
    private int opponentIndex;
    private int activeOpponentMonsterIndex = 0;

    private Scene gameScene = new Scene();
    private MenuScene battleMenu = new MenuScene();
    private MenuScene attackMenu = new MenuScene();
    private MenuScene monsterMenu = new MenuScene();

    private Music menuMusic;
    private Music battleMusic;
    private Sound menuCursorSound;
    private ITexture fontTextureAtlas;
    private Font comicSansFull, comicSansH1, comicSansH2, sansSmall;

    private ArrayList<ArrayList<Monster>> characterTeams = new ArrayList<>();
    private TiledTextureRegion mCharizardTexture;
    private TiledTextureRegion mRaichuTexture;
    private TiledTextureRegion mStarmieTexture;

    private TiledTextureRegion mBlastoiseTexture;
    private TiledTextureRegion mAlakazamTexture;
    private TiledTextureRegion mHitmonchanTexture;

    private TiledTextureRegion mVenusaurTexture;
    private TiledTextureRegion mDragoniteTexture;
    private TiledTextureRegion mRhydonTexture;

    private Text activeOpponentMonsterName;
    private Text activeOpponentMonsterLevel;
    private Text activePlayerMonsterName;
    private Text activePlayerMonsterLevel;
    private Text activePlayerMonsterHP;

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

            // Set up bitmap textures
            ITexture battleBackgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/battleBackground.jpg");
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

            ITexture attackBattleMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/attackBattleMenu.png");
                }
            });

            ITexture switchBattleMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/switchBattleMenu.png");
                }
            });

            ITexture exitBattleMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/exitBattleMenu.png");
                }
            });

            ITexture blankAttackMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/blankAttackMenu.png");
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

            ITexture blastoisePreviewTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/monsters/blastoise.png");
                }
            });

            ITexture hitmonchanPreviewTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/monsters/hitmonchan.png");
                }
            });

            ITexture alakazamPreviewTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/monsters/alakazam.png");
                }
            });

            ITexture venusaurPreviewTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/monsters/venusaur.png");
                }
            });

            ITexture rhydonPreviewTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/monsters/rhydon.png");
                }
            });

            ITexture dragonitePreviewTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/monsters/dragonite.png");
                }
            });

            fontTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            sansSmall = FontFactory.createFromAsset(
                    this.getFontManager(),
                    fontTextureAtlas,
                    this.getAssets(),
                    "fonts/sans.ttf",
                    18f,
                    true,
                    Color.WHITE
            );

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
            this.mBlankAttackMenuItemTexture = TextureRegionFactory.extractFromTexture(blankAttackMenuItemTexture);

            this.mPreviewRedTexture = TextureRegionFactory.extractFromTexture(selectRedTexture);
            this.mPreviewOakTexture = TextureRegionFactory.extractFromTexture(selectOakTexture);
            this.mPreviewRocketTexture = TextureRegionFactory.extractFromTexture(selectRocketTexture);
            this.mMainMenuItemTexture = TextureRegionFactory.extractFromTexture(mainMenuItemTexture);

            this.mAttackBattleMenuItemTexture = TextureRegionFactory.extractFromTexture(attackBattleMenuItemTexture);
            this.mSwitchBattleMenuItemTexture = TextureRegionFactory.extractFromTexture(switchBattleMenuItemTexture);
            this.mExitBattleMenuItemTexture = TextureRegionFactory.extractFromTexture(exitBattleMenuItemTexture);

            this.mChooseRedTexture = TextureRegionFactory.extractFromTexture(chooseRedTexture);
            this.mChooseOakTexture = TextureRegionFactory.extractFromTexture(chooseOakTexture);
            this.mChooseRocketTexture = TextureRegionFactory.extractFromTexture(chooseRocketTexture);
            this.mBackMenuItemTexture = TextureRegionFactory.extractFromTexture(backMenuItemTexture);

            this.mPreviewCharizardTexture = TextureRegionFactory.extractFromTexture(charizardPreviewTexture);
            this.mPreviewRaichuTexture = TextureRegionFactory.extractFromTexture(raichuPreviewTexture);
            this.mPreviewStarmieTexture = TextureRegionFactory.extractFromTexture(starmiePreviewTexture);

            this.mPreviewBlastoiseTexture = TextureRegionFactory.extractFromTexture(blastoisePreviewTexture);
            this.mPreviewAlakazamTexture = TextureRegionFactory.extractFromTexture(alakazamPreviewTexture);
            this.mPreviewHitmonchanTexture = TextureRegionFactory.extractFromTexture(hitmonchanPreviewTexture);

            this.mPreviewVenusaurTexture = TextureRegionFactory.extractFromTexture(venusaurPreviewTexture);
            this.mPreviewDragoniteTexture = TextureRegionFactory.extractFromTexture(dragonitePreviewTexture);
            this.mPreviewRhydonTexture = TextureRegionFactory.extractFromTexture(rhydonPreviewTexture);


            BitmapTextureAtlas alakazamTexture = new BitmapTextureAtlas(this.getTextureManager(), 93, 70,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mAlakazamTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(alakazamTexture, this.getAssets(),
                    "graphics/monsters/sprites/alakazam.png", 0, 0, 1, 1);

            BitmapTextureAtlas blastoiseTexture = new BitmapTextureAtlas(this.getTextureManager(), 69, 65,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mBlastoiseTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(blastoiseTexture, this.getAssets(),
                    "graphics/monsters/sprites/blastoise.png", 0, 0, 1, 1);

            BitmapTextureAtlas charizardTexture = new BitmapTextureAtlas(this.getTextureManager(), 89, 91,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mCharizardTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(charizardTexture, this.getAssets(),
                    "graphics/monsters/sprites/charizard.png", 0, 0, 1, 1);

            BitmapTextureAtlas dragoniteTexture = new BitmapTextureAtlas(this.getTextureManager(), 80, 82,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mDragoniteTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(dragoniteTexture, this.getAssets(),
                    "graphics/monsters/sprites/dragonite.png", 0, 0, 1, 1);

            BitmapTextureAtlas hitmonchanTexture = new BitmapTextureAtlas(this.getTextureManager(), 34, 59,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mHitmonchanTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(hitmonchanTexture, this.getAssets(),
                    "graphics/monsters/sprites/hitmonchan.png", 0, 0, 1, 1);

            BitmapTextureAtlas raichuTexture = new BitmapTextureAtlas(this.getTextureManager(), 71, 73,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mRaichuTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(raichuTexture, this.getAssets(),
                    "graphics/monsters/sprites/raichu.png", 0, 0, 1, 1);

            BitmapTextureAtlas rhydonTexture = new BitmapTextureAtlas(this.getTextureManager(), 78, 67,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mRhydonTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(rhydonTexture, this.getAssets(),
                    "graphics/monsters/sprites/rhydon.png", 0, 0, 1, 1);

            BitmapTextureAtlas venusaurTexture = new BitmapTextureAtlas(this.getTextureManager(), 86, 71,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mVenusaurTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(venusaurTexture, this.getAssets(),
                    "graphics/monsters/sprites/venusaur.png", 0, 0, 1, 1);

            BitmapTextureAtlas starmieTexture = new BitmapTextureAtlas(this.getTextureManager(), 65, 59,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mStarmieTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(starmieTexture, this.getAssets(),
                    "graphics/monsters/sprites/starmie.png", 0, 0, 1, 1);

            this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
            this.mBattleBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(battleBackgroundTexture);

            backgroundTexture.load();
            battleBackgroundTexture.load();
            blankAttackMenuItemTexture.load();
            newGameMenuItemTexture.load();
            loadGameMenuItemTexture.load();
            exitGameMenuItemTexture.load();
            attackBattleMenuItemTexture.load();
            switchBattleMenuItemTexture.load();
            exitBattleMenuItemTexture.load();
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

            blastoisePreviewTexture.load();
            alakazamPreviewTexture.load();
            hitmonchanPreviewTexture.load();

            venusaurPreviewTexture.load();
            rhydonPreviewTexture.load();
            dragonitePreviewTexture.load();
            
            charizardTexture.load();
            raichuTexture.load();
            starmieTexture.load();

            blastoiseTexture.load();
            alakazamTexture.load();
            hitmonchanTexture.load();

            venusaurTexture.load();
            rhydonTexture.load();
            dragoniteTexture.load();

            sansSmall.load();
            comicSansFull.load();
            comicSansH1.load();
            comicSansH2.load();

            // music
            menuMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "music/menu.mp3");
            menuMusic.setVolume(.75f);

            battleMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "music/battle.mp3");
            battleMusic.setVolume(.75f);

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

        Sprite battleBackgroundSprite = new Sprite(0, 0, this.mBattleBackgroundTextureRegion, getVertexBufferObjectManager());
        this.gameScene.attachChild(battleBackgroundSprite);

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
        MonsterType dragon = new MonsterType("Dragon", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType grass = new MonsterType("Grass", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType poison = new MonsterType("Poison", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType ice = new MonsterType("Ice", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType normal = new MonsterType("Normal", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType fighting = new MonsterType("Fighting", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType ghost = new MonsterType("Ghost", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType rock = new MonsterType("Rock", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());
        MonsterType ground = new MonsterType("Ground", new ArrayList<MonsterType>(), new ArrayList<MonsterType>());

        Attack flamethrower = new Attack("Flamethrower", 95, 100, fire);
        Attack wingAttack = new Attack("Wing Attack", 95, 100, flying);
        Attack surf = new Attack("Surf", 95, 100, water);
        Attack psychicAttack = new Attack("Psychic", 100, 100, psychic);
        Attack thunderbolt = new Attack("Thunderbolt", 90, 100, electric);
        Attack earthquake = new Attack("Earthquake", 90, 100, ground);
        Attack stoneEdge = new Attack("Stone Edge", 90, 100, rock);
        Attack thunderPunch = new Attack("Thunder Punch", 90, 100, electric);
        Attack icePunch = new Attack("Ice Punch", 90, 100, ice);
        Attack firePunch = new Attack("Fire Punch", 90, 100, fire);
        Attack closeCombat = new Attack("Close Combat", 90, 100, fighting);
        Attack shadowBall = new Attack("Shadow Ball", 90, 100, ghost);
        Attack iceBeam = new Attack("Ice Beam", 90, 100, ice);
        Attack dragonRage = new Attack("Dragon Rage", 90, 100, dragon);
        Attack swift = new Attack("Swift", 90, 100, normal);
        Attack strength = new Attack("Strength", 90, 100, normal);
        Attack solarBeam = new Attack("Solar Beam", 90, 100, grass);
        Attack sludgeBomb = new Attack("Sludge Bomb", 90, 100, poison);

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

        ArrayList<MonsterType> blastoiseTypes = new ArrayList<>();
        blastoiseTypes.add(water);

        ArrayList<Attack> blastoiseAttacks = new ArrayList<>();
        blastoiseAttacks.add(surf);
        blastoiseAttacks.add(iceBeam);
        blastoiseAttacks.add(strength);
        blastoiseAttacks.add(earthquake);

        Monster blastoise = new Monster(
                this.mBlastoiseTexture,
                this.getVertexBufferObjectManager(),
                "Blastoise",
                blastoiseTypes,
                blastoiseAttacks
        );

        ArrayList<MonsterType> alakazamTypes = new ArrayList<>();
        alakazamTypes.add(psychic);

        ArrayList<Attack> alakazamAttacks = new ArrayList<>();
        alakazamAttacks.add(psychicAttack);
        alakazamAttacks.add(shadowBall);
        alakazamAttacks.add(iceBeam);
        alakazamAttacks.add(swift);

        Monster alakazam = new Monster(
                this.mAlakazamTexture,
                this.getVertexBufferObjectManager(),
                "Alakazam",
                alakazamTypes,
                alakazamAttacks
        );

        ArrayList<MonsterType> hitmonchanTypes = new ArrayList<>();
        hitmonchanTypes.add(fighting);

        ArrayList<Attack> hitmonchanAttacks = new ArrayList<>();
        hitmonchanAttacks.add(firePunch);
        hitmonchanAttacks.add(icePunch);
        hitmonchanAttacks.add(thunderPunch);
        hitmonchanAttacks.add(closeCombat);

        Monster hitmonchan = new Monster(
                this.mHitmonchanTexture,
                this.getVertexBufferObjectManager(),
                "Hitmonchan",
                hitmonchanTypes,
                hitmonchanAttacks
        );


        ArrayList<MonsterType> venusaurTypes = new ArrayList<>();
        venusaurTypes.add(grass);
        venusaurTypes.add(poison);

        ArrayList<Attack> venusaurAttacks = new ArrayList<>();
        venusaurAttacks.add(solarBeam);
        venusaurAttacks.add(sludgeBomb);
        venusaurAttacks.add(strength);
        venusaurAttacks.add(earthquake);

        Monster venusaur = new Monster(
                this.mVenusaurTexture,
                this.getVertexBufferObjectManager(),
                "Venusaur",
                venusaurTypes,
                venusaurAttacks
        );

        ArrayList<MonsterType> rhydonTypes = new ArrayList<>();
        rhydonTypes.add(rock);
        rhydonTypes.add(ground);

        ArrayList<Attack> rhydonAttacks = new ArrayList<>();
        rhydonAttacks.add(earthquake);
        rhydonAttacks.add(stoneEdge);
        rhydonAttacks.add(strength);
        rhydonAttacks.add(surf);

        Monster rhydon = new Monster(
                this.mRhydonTexture,
                this.getVertexBufferObjectManager(),
                "Rhydon",
                rhydonTypes,
                rhydonAttacks
        );

        ArrayList<MonsterType> dragoniteTypes = new ArrayList<>();
        dragoniteTypes.add(dragon);
        dragoniteTypes.add(flying);

        ArrayList<Attack> dragoniteAttacks = new ArrayList<>();
        dragoniteAttacks.add(dragonRage);
        dragoniteAttacks.add(earthquake);
        dragoniteAttacks.add(firePunch);
        dragoniteAttacks.add(surf);

        Monster dragonite = new Monster(
                this.mDragoniteTexture,
                this.getVertexBufferObjectManager(),
                "Dragonite",
                dragoniteTypes,
                dragoniteAttacks
        );

        ArrayList<Monster> red = new ArrayList<>();
        red.add(charizard);
        red.add(raichu);
        red.add(starmie);

        ArrayList<Monster> oak = new ArrayList<>();
        oak.add(blastoise);
        oak.add(alakazam);
        oak.add(hitmonchan);

        ArrayList<Monster> rocket = new ArrayList<>();
        rocket.add(venusaur);
        rocket.add(rhydon);
        rocket.add(dragonite);

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
        ITextureRegion monster1Texture, monster2Texture, monster3Texture;

        switch(this.playerIndex) {
            case 1:
                characterTexture = mChooseOakTexture;
                monster1Texture = mPreviewBlastoiseTexture;
                monster2Texture = mPreviewAlakazamTexture;
                monster3Texture = mPreviewHitmonchanTexture;
                break;
            case 2:
                characterTexture = mChooseRocketTexture;
                monster1Texture = mPreviewVenusaurTexture;
                monster2Texture = mPreviewRhydonTexture;
                monster3Texture = mPreviewDragoniteTexture;
                break;
            case 0:
            default:
                characterTexture = mChooseRedTexture;
                monster1Texture = mPreviewCharizardTexture;
                monster2Texture = mPreviewRaichuTexture;
                monster3Texture = mPreviewStarmieTexture;
                break;
        }

        ArrayList<Monster> team = this.characterTeams.get(playerIndex);


        final IMenuItem selectCharacterMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(7, characterTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        final IMenuItem backMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(8, mBackMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        final Sprite monster1 = new Sprite(-60, -260, monster1Texture, getVertexBufferObjectManager());
        final Sprite monster2 = new Sprite(-60, -110, monster2Texture, getVertexBufferObjectManager());
        final Sprite monster3 = new Sprite(-60, 40, monster3Texture, getVertexBufferObjectManager());

        final Text monster1Name = new Text(95, -260, this.comicSansH1, team.get(0).getName(), this.getVertexBufferObjectManager());
        final Text monster2Name = new Text(95, -110, this.comicSansH1, team.get(1).getName(), this.getVertexBufferObjectManager());
        final Text monster3Name = new Text(95, 40, this.comicSansH1, team.get(2).getName(), this.getVertexBufferObjectManager());

        final Text monster1Type = new Text(95, -225, this.comicSansH1, team.get(0).getTypeString(), this.getVertexBufferObjectManager());
        final Text monster2Type = new Text(95, -75, this.comicSansH1, team.get(1).getTypeString(), this.getVertexBufferObjectManager());
        final Text monster3Type = new Text(95, 75, this.comicSansH1, team.get(2).getTypeString(), this.getVertexBufferObjectManager());

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


    private MenuScene createBattleMenu() {
        this.battleMenu = new MenuScene(mCamera);
        this.battleMenu.setY(255);
        this.battleMenu.setX(105);

        final IMenuItem battleMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(9, mAttackBattleMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem switchMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(10, mSwitchBattleMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem exitBattleMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(11, mExitBattleMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        this.battleMenu.addMenuItem(battleMenuItem);
        this.battleMenu.addMenuItem(switchMenuItem);
        this.battleMenu.addMenuItem(exitBattleMenuItem);

        this.battleMenu.buildAnimations();
        this.battleMenu.setBackgroundEnabled(false);

        this.battleMenu.setOnMenuItemClickListener(this);

        return this.battleMenu;
    }

    private MenuScene createAttackMenu() {
        this.attackMenu = new MenuScene(mCamera);
        this.attackMenu.setX(105);

        this.attackMenu.buildAnimations();
        this.attackMenu.setBackgroundEnabled(false);

        this.attackMenu.setOnMenuItemClickListener(this);

        return this.attackMenu;
    }

    private MenuScene createMonsterMenu() {
        this.monsterMenu = new MenuScene(mCamera);
        this.monsterMenu.setX(105);

        this.monsterMenu.buildAnimations();
        this.monsterMenu.setBackgroundEnabled(false);

        this.monsterMenu.setOnMenuItemClickListener(this);

        return this.monsterMenu;
    }

    public void setupBattleMenu() {

        this.attackMenu.clearMenuItems();

        AttackMenuItemDecorator attackMenuItem;
        Text attackName;

        ArrayList<Attack> attacks = this.characterTeams.get(playerIndex).get(activePlayerMonsterIndex).getAttacks();
        for(int i = 0; i < attacks.size(); i++) {
            attackMenuItem = new AttackMenuItemDecorator(
                    new SpriteMenuItem(12, mBlankAttackMenuItemTexture, getVertexBufferObjectManager()
                    ), 1.2f, 1);

            attackMenuItem.setAttackIndex(i);

            attackName = new Text(105, 36, this.sansSmall, attacks.get(i).getName(), this.getVertexBufferObjectManager());
            attackMenuItem.attachChild(attackName);

            this.attackMenu.addMenuItem(attackMenuItem);
        }

        final IMenuItem exitAttacksMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(13, mBackMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        this.attackMenu.addMenuItem(exitAttacksMenuItem);

        this.attackMenu.setY(345 - 45 * attacks.size());

        this.attackMenu.buildAnimations();
        this.attackMenu.setBackgroundEnabled(false);

        this.attackMenu.setOnMenuItemClickListener(this);
        this.gameScene.setChildScene(attackMenu);
    }

    public void setupMonsterMenu(boolean exit) {

        this.monsterMenu.clearMenuItems();

        MonsterMenuItemDecorator monsterMenuItem;
        Text monsterName;

        ArrayList<Monster> monsters = this.characterTeams.get(playerIndex);

        for(int i = 0; i < monsters.size(); i++) {
            if(i == activePlayerMonsterIndex) {
                continue;
            }

            if(monsters.get(i).isFainted()) {
                continue;
            }

            monsterMenuItem = new MonsterMenuItemDecorator(
                    new SpriteMenuItem(exit ? 14 : 15, mBlankAttackMenuItemTexture, getVertexBufferObjectManager()
                    ), 1.2f, 1);

            monsterMenuItem.setMonsterIndex(i);

            monsterName = new Text(105, 36, this.sansSmall, monsters.get(i).getName(), this.getVertexBufferObjectManager());
            monsterMenuItem.attachChild(monsterName);

            this.monsterMenu.addMenuItem(monsterMenuItem);
        }

        if(!exit) {
            final IMenuItem exitAttacksMenuItem = new ScaleMenuItemDecorator(
                    new SpriteMenuItem(13, mBackMenuItemTexture, getVertexBufferObjectManager()
                    ), 1.2f, 1);

            this.monsterMenu.addMenuItem(exitAttacksMenuItem);
            this.monsterMenu.setY(345 - 45 * (monsters.size()));
        } else {
            this.monsterMenu.setY(345 - 45 * (monsters.size() - 1));
        }

        this.monsterMenu.buildAnimations();
        this.monsterMenu.setBackgroundEnabled(false);

        this.monsterMenu.setOnMenuItemClickListener(this);
        this.gameScene.setChildScene(monsterMenu);
    }

    public Scene onCreateBattleScene() {

        this.createBattleMenu();
        this.createAttackMenu();
        this.createMonsterMenu();

        this.opponentIndex = (new Random()).nextInt(this.characterTeams.size()-1);
        while(this.opponentIndex == this.playerIndex) {
            this.opponentIndex = (new Random()).nextInt(this.characterTeams.size()-1);
        }

        this.activePlayerMonsterName = new Text(320, 320, sansSmall, "My Super Long Monster Name", getVertexBufferObjectManager());
        this.activePlayerMonsterLevel = new Text(320, 340, sansSmall, "Lv 50", getVertexBufferObjectManager());
        this.activePlayerMonsterHP = new Text(320, 360, sansSmall, "9999 / 9999", getVertexBufferObjectManager());

        this.activeOpponentMonsterName = new Text(100, 100, sansSmall, "My Super Long Monster Name", getVertexBufferObjectManager());
        this.activeOpponentMonsterLevel = new Text(100, 120, sansSmall, "Lv 50", getVertexBufferObjectManager());


        // release opponent monster
        this.switchMonster(this.activeOpponentMonsterIndex, -1, false);

        // release player monster
        this.switchMonster(this.activePlayerMonsterIndex, -1, true);

        this.gameScene.attachChild(this.activePlayerMonsterName);
        this.gameScene.attachChild(this.activePlayerMonsterLevel);
        this.gameScene.attachChild(this.activePlayerMonsterHP);
        this.gameScene.attachChild(this.activeOpponentMonsterName);
        this.gameScene.attachChild(this.activeOpponentMonsterLevel);

        return this.gameScene;
    }

    public int opponentMustSwitchMonster() {

        // If the opponent does switch, we select a random available monster
        ArrayList<Monster> monsters = this.characterTeams.get(opponentIndex);

        ArrayList<Monster> availableMonsters = new ArrayList<>();

        for(int i = 0; i < monsters.size(); i++)
        {
            if(!monsters.get(i).isFainted() && i != activeOpponentMonsterIndex ) {
                availableMonsters.add(monsters.get(i));
            }
        }

        if(availableMonsters.isEmpty()) {
            // if there are no available monsters, then we cannot switch
            return -1;
        } else {
            // randomize the monster switch
            int randomAvailableMonster = (new Random()).nextInt(availableMonsters.size());
            return monsters.indexOf(availableMonsters.get(randomAvailableMonster));
        }
    }

    public int opponentChooseSwitchMonster() {

        // EASY opponent switching
        // Opponent has a 15% chance of deciding to switch
        if(Math.random() >= .15) {
            return -1;
        }

        return this.opponentMustSwitchMonster();
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
                this.playerIndex = 0;
                this.createCharacterPreviewMenu();
                this.scene.setChildScene(this.characterPreviewMenu);
                return true;
            case 4:
                // preview OAK
                this.playerIndex = 1;
                this.createCharacterPreviewMenu();
                this.scene.setChildScene(this.characterPreviewMenu);
                return true;
            case 5:
                // preview ROCKET
                this.playerIndex = 2;
                this.createCharacterPreviewMenu();
                this.scene.setChildScene(this.characterPreviewMenu);
                return true;
            case 6:
                // back to main menu
                this.scene.setChildScene(this.mainMenu);
                return true;
            case 7:
                // I choose you!
                menuMusic.stop();

                this.scene.detachChildren();

                this.onCreateBattleScene();

                this.gameScene.setChildScene(this.battleMenu);
                battleMusic.setLooping(true);
                battleMusic.play();

                mEngine.setScene(this.gameScene);
                return true;

            case 9:
                // choosing an attack
                this.setupBattleMenu();

                return true;

            case 10:
                // switching monsters
                this.setupMonsterMenu(true);
                return true;


            case 11:
                // running away
                battleMusic.stop();

                this.gameScene.detachChildren();

                this.onCreateScene();
                mEngine.setScene(this.scene);
                this.scene.setChildScene(this.mainMenu);
                menuMusic.setLooping(true);
                menuMusic.play();

                return true;


            case 14:
                // switch monsters

            case 12:
                // attack was chosen, calculate damage

                boolean monsterSwitched = pMenuItem.getID() == 14;
                boolean forcedSwitch = false;
                if(!monsterSwitched) {
                    // chose an attack
                    Debug.d(
                            this.characterTeams
                                    .get(playerIndex)
                                    .get(activePlayerMonsterIndex)
                                    .getAttacks()
                                    .get(((AttackMenuItemDecorator) pMenuItem).getAttackIndex())
                                    .getName()
                    );

                    // put damage on the opponent monster
                    this.characterTeams
                            .get(opponentIndex)
                            .get(activeOpponentMonsterIndex)
                            .damage(100);
                    this.updateMonsterInformation(activeOpponentMonsterIndex, false);

                    if(
                        this.characterTeams
                            .get(opponentIndex)
                            .get(activeOpponentMonsterIndex).isFainted()) {
                        int switchMonster = this.opponentMustSwitchMonster();
                        if(switchMonster == -1) {
                            //handle victory
                        }
                    }

                } else {
                    // chose a monster to switch to
                    this.switchMonster(
                        ((MonsterMenuItemDecorator) pMenuItem).getMonsterIndex(),
                        this.activePlayerMonsterIndex,
                        true
                    );

                }

                // determine opponent's next choice
                int switchMonster = this.opponentChooseSwitchMonster();
                if(switchMonster != -1) {

                    // chose a monster to switch to
                    this.switchMonster(
                        switchMonster,
                        this.activeOpponentMonsterIndex,
                        false
                    );

                } else {
                    // determine opponent's attack
                    ArrayList<Attack> opponentAttacks =
                        this.characterTeams
                            .get(opponentIndex)
                            .get(activeOpponentMonsterIndex)
                            .getAttacks();

                    Debug.d(
                            opponentAttacks
                                    .get((new Random()).nextInt(opponentAttacks.size()))
                                    .getName()
                    );

                    // put damage on the player
                    this.characterTeams
                            .get(playerIndex)
                            .get(activePlayerMonsterIndex)
                            .damage(100);
                    this.updateMonsterInformation(activePlayerMonsterIndex, true);

                    if(
                        this.characterTeams
                            .get(playerIndex)
                            .get(activePlayerMonsterIndex).isFainted()) {

                        if(playerDefeated()) {
                            //handle defeat

                        } else {
                            forcedSwitch = true;
                            this.setupMonsterMenu(false);
                        }
                    }
                }

                // start next turn
                if(!forcedSwitch) {
                    this.gameScene.setChildScene(battleMenu);
                }

                return false;

            case 13:
                // return from the attack or monster menu
                this.gameScene.setChildScene(battleMenu);
                return true;

            case 15:
                // forced switch

                this.switchMonster(
                        ((MonsterMenuItemDecorator) pMenuItem).getMonsterIndex(),
                        this.activePlayerMonsterIndex,
                        true
                );

                this.gameScene.setChildScene(battleMenu);

            default:
                return false;
        }
    }

    private void updateMonsterInformation(int monsterIndex, boolean player) {
        Monster monster;

        if(player) {
            monster = this.characterTeams.get(playerIndex).get(monsterIndex);
            this.activePlayerMonsterIndex = monsterIndex;
            this.activePlayerMonsterName.setText(monster.getName());
            this.activePlayerMonsterHP.setText(monster.getCurrentHP() + " / " + monster.getMaxHP());
        } else {
            monster = this.characterTeams.get(opponentIndex).get(monsterIndex);
            this.activeOpponentMonsterIndex = monsterIndex;
            this.activeOpponentMonsterName.setText(monster.getName());
        }
    }

    private void switchMonster(int newMonsterIndex, int oldMonsterIndex, boolean player) {
        int switchingPlayerIndex = player ? playerIndex : opponentIndex;

        if(oldMonsterIndex >= 0) {
            this.characterTeams
                    .get(switchingPlayerIndex)
                    .get(oldMonsterIndex)
                    .detachSelf();
        }

        this.updateMonsterInformation(newMonsterIndex, player);

        this.characterTeams
                .get(switchingPlayerIndex)
                .get(newMonsterIndex)
                .release(this.gameScene, player);

        Debug.d( (player ? "" : "Opponent ") + "Switched to: " +
                        this.characterTeams
                                .get(switchingPlayerIndex)
                                .get( newMonsterIndex )
        );
    }

    private boolean playerDefeated() {
        ArrayList<Monster> monsters = this.characterTeams.get(playerIndex);
        ArrayList<Monster> availableMonsters = new ArrayList();

        for(int i = 0; i < monsters.size(); i++) {
            if(!monsters.get(i).isFainted()) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        System.exit(0);
    }

}
