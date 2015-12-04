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
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.AnimatedSprite;
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

    public Camera mCamera;

    private ITextureRegion mBackgroundTextureRegion;
    private ITextureRegion mBattleBackgroundTextureRegion;
    private ITextureRegion mNewGameMenuItemTexture;
    private ITextureRegion mLoadGameMenuItemTexture;
    private ITextureRegion mPreviewAdrienneTexture;
    private ITextureRegion mPreviewBrodyTexture;
    private ITextureRegion mPreviewZackTexture;
    private ITextureRegion mMainMenuItemTexture;

    private ITextureRegion mChooseAdrienneTexture;
    private ITextureRegion mChooseBrodyTexture;
    private ITextureRegion mChooseZackTexture;
    private ITextureRegion mBackMenuItemTexture;

    private ITextureRegion mEasyMenuItemTexture;
    private ITextureRegion mHardMenuItemTexture;

    private ITextureRegion mExitGameMenuItemTexture;

    private ITextureRegion mAttackBattleMenuItemTexture;
    private ITextureRegion mSwitchBattleMenuItemTexture;
    private ITextureRegion mExitBattleMenuItemTexture;
    private ITextureRegion mBlankAttackMenuItemTexture;

    private Scene scene = new Scene();
    private MenuScene mainMenu = new MenuScene();
    private MenuScene characterSelectMenu = new MenuScene();
    private MenuScene difficultySelectMenu = new MenuScene();
    private MenuScene characterPreviewMenu = new MenuScene();
    private int playerIndex;
    public int activePlayerMonsterIndex = 0;
    private int opponentIndex;
    public int activeOpponentMonsterIndex = 0;

    public Scene gameScene = new Scene();
    private MenuScene battleMenu = new MenuScene();
    private MenuScene attackMenu = new MenuScene();
    private MenuScene monsterMenu = new MenuScene();
    private MenuScene victoryMenu = new MenuScene();
    private MenuScene defeatMenu = new MenuScene();
    private Scene nextChildScene = new Scene();

    public Music menuMusic;
    public Music battleMusic;
    public Music victoryMusic;
    public Music defeatMusic;
    public Sound menuCursorSound;
    private ITexture fontTextureAtlas;
    private Font comicSansFull, comicSansH1, comicSansH2, sansSmall;

    private ArrayList<ArrayList<Monster>> characterTeams = new ArrayList<>();
    private TiledTextureRegion mSlugsparkTexture;
    private TiledTextureRegion mGolpinTexture;
    private TiledTextureRegion mOddsectTexture;

    private TiledTextureRegion mBeeveeTexture;
    private TiledTextureRegion mJynchampTexture;
    private TiledTextureRegion mBonekingTexture;

    private TiledTextureRegion mScykansTexture;
    private TiledTextureRegion mRidochanTexture;
    private TiledTextureRegion mRhydonTexture;

    private Text activeOpponentMonsterName;
    private Text activeOpponentMonsterLevel;
    private Text activePlayerMonsterName;
    private Text activePlayerMonsterLevel;
    private Text activePlayerMonsterHP;

    private boolean startNextTurn; //whether or not next turn is interrupted by game state

    public static ArrayList<OutputScene> gameOutputQueue = new ArrayList<>();
    public Rectangle activePlayerMonsterHPBar;
    public Rectangle activeOpponentMonsterHPBar;
    public Sound damageSound;
    public Sound criticalSound;
    private Sound beeveeSound;
    private Sound bonekingSound;
    private Sound golpinSound;
    private Sound jynchampSound;
    private Sound oddsectSound;
    private Sound rhyterSound;
    private Sound ridochanSound;
    private Sound scykansSound;
    private Sound slugsparkSound;

    private boolean hardMode = false;

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

            ITexture selectAdrienneTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/selectAdrienne.png");
                }
            });

            ITexture selectBrodyTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/selectBrody.png");
                }
            });

            ITexture selectZackTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/selectZack.png");
                }
            });

            ITexture chooseAdrienneTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/iChooseAdrienne.png");
                }
            });

            ITexture chooseBrodyTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/iChooseBrody.png");
                }
            });

            ITexture chooseZackTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/iChooseZack.png");
                }
            });

            ITexture mainMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/mainMenu.png");
                }
            });

            ITexture easyModeMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/easyMenu.png");
                }
            });

            ITexture hardModeMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/hardMenu.png");
                }
            });

            ITexture backMenuItemTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return getAssets().open("graphics/backMenu.png");
                }
            });

            fontTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1596, 2016, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            sansSmall = FontFactory.createFromAsset(
                    this.getFontManager(),
                    fontTextureAtlas,
                    this.getAssets(),
                    "fonts/sans.ttf",
                    14f,
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
                    30f,
                    true,
                    Color.WHITE
            );

            this.mNewGameMenuItemTexture = TextureRegionFactory.extractFromTexture(newGameMenuItemTexture);
            this.mLoadGameMenuItemTexture = TextureRegionFactory.extractFromTexture(loadGameMenuItemTexture);
            this.mExitGameMenuItemTexture = TextureRegionFactory.extractFromTexture(exitGameMenuItemTexture);
            this.mBlankAttackMenuItemTexture = TextureRegionFactory.extractFromTexture(blankAttackMenuItemTexture);

            this.mPreviewAdrienneTexture = TextureRegionFactory.extractFromTexture(selectAdrienneTexture);
            this.mPreviewBrodyTexture = TextureRegionFactory.extractFromTexture(selectBrodyTexture);
            this.mPreviewZackTexture = TextureRegionFactory.extractFromTexture(selectZackTexture);
            this.mMainMenuItemTexture = TextureRegionFactory.extractFromTexture(mainMenuItemTexture);

            this.mAttackBattleMenuItemTexture = TextureRegionFactory.extractFromTexture(attackBattleMenuItemTexture);
            this.mSwitchBattleMenuItemTexture = TextureRegionFactory.extractFromTexture(switchBattleMenuItemTexture);
            this.mExitBattleMenuItemTexture = TextureRegionFactory.extractFromTexture(exitBattleMenuItemTexture);

            this.mChooseAdrienneTexture = TextureRegionFactory.extractFromTexture(chooseAdrienneTexture);
            this.mChooseBrodyTexture = TextureRegionFactory.extractFromTexture(chooseBrodyTexture);
            this.mChooseZackTexture = TextureRegionFactory.extractFromTexture(chooseZackTexture);
            this.mBackMenuItemTexture = TextureRegionFactory.extractFromTexture(backMenuItemTexture);

            this.mEasyMenuItemTexture = TextureRegionFactory.extractFromTexture(easyModeMenuItemTexture);
            this.mHardMenuItemTexture = TextureRegionFactory.extractFromTexture(hardModeMenuItemTexture);

            BitmapTextureAtlas alakazamTexture = new BitmapTextureAtlas(this.getTextureManager(), 1596, 2016,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mJynchampTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(alakazamTexture, this.getAssets(),
                    "graphics/monsters/sprites/jynchamp.png", 0, 0, 7, 9);

            BitmapTextureAtlas blastoiseTexture = new BitmapTextureAtlas(this.getTextureManager(), 996, 1134,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mBeeveeTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(blastoiseTexture, this.getAssets(),
                    "graphics/monsters/sprites/beevee.png", 0, 0, 7, 9);

            BitmapTextureAtlas charizardTexture = new BitmapTextureAtlas(this.getTextureManager(), 1211, 1593,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mSlugsparkTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(charizardTexture, this.getAssets(),
                    "graphics/monsters/sprites/pikabro.png", 0, 0, 7, 9);

            BitmapTextureAtlas dragoniteTexture = new BitmapTextureAtlas(this.getTextureManager(), 1029, 1350,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mRidochanTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(dragoniteTexture, this.getAssets(),
                    "graphics/monsters/sprites/nidochu.png", 0, 0, 7, 9);

            BitmapTextureAtlas hitmonchanTexture = new BitmapTextureAtlas(this.getTextureManager(), 1638, 1764,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mBonekingTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(hitmonchanTexture, this.getAssets(),
                    "graphics/monsters/sprites/boneking.png", 0, 0, 7, 9);

            BitmapTextureAtlas raichuTexture = new BitmapTextureAtlas(this.getTextureManager(), 1491, 1764,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mGolpinTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(raichuTexture, this.getAssets(),
                    "graphics/monsters/sprites/golpin.png", 0, 0, 7, 9);

            BitmapTextureAtlas rhydonTexture = new BitmapTextureAtlas(this.getTextureManager(), 1561, 1242,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mRhydonTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(rhydonTexture, this.getAssets(),
                    "graphics/monsters/sprites/rhyter.png", 0, 0, 7, 9);

            BitmapTextureAtlas venusaurTexture = new BitmapTextureAtlas(this.getTextureManager(), 875, 1188,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mScykansTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(venusaurTexture, this.getAssets(),
                    "graphics/monsters/sprites/scykans.png", 0, 0, 7, 9);

            BitmapTextureAtlas starmieTexture = new BitmapTextureAtlas(this.getTextureManager(), 1218, 1395,
                    TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            this.mOddsectTexture = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(starmieTexture, this.getAssets(),
                    "graphics/monsters/sprites/oddsect.png", 0, 0, 7, 9);

            this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
            this.mBattleBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(battleBackgroundTexture);

            backgroundTexture.load();
            battleBackgroundTexture.load();
            blankAttackMenuItemTexture.load();
            newGameMenuItemTexture.load();
            loadGameMenuItemTexture.load();
            exitGameMenuItemTexture.load();
            easyModeMenuItemTexture.load();
            hardModeMenuItemTexture.load();
            attackBattleMenuItemTexture.load();
            switchBattleMenuItemTexture.load();
            exitBattleMenuItemTexture.load();
            selectAdrienneTexture.load();
            selectBrodyTexture.load();
            selectZackTexture.load();
            chooseAdrienneTexture.load();
            chooseBrodyTexture.load();
            chooseZackTexture.load();
            mainMenuItemTexture.load();
            backMenuItemTexture.load();

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

            // music
            menuMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "music/menu.mp3");
            menuMusic.setVolume(.75f);
            menuMusic.setLooping(true);

            battleMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "music/battle.mp3");
            battleMusic.setVolume(.75f);
            battleMusic.setLooping(true);

            victoryMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "music/victory.mp3");
            victoryMusic.setVolume(.75f);
            victoryMusic.setLooping(true);

            defeatMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "music/defeat.mp3");
            defeatMusic.setVolume(.75f);
            defeatMusic.setLooping(true);

            // sounds
            menuCursorSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/menu.wav");
            damageSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/damage.wav");
            criticalSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/critical.wav");
            beeveeSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/beevee.wav");
            bonekingSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/boneking.wav");
            golpinSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/golpin.wav");
            jynchampSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/jynchamp.wav");
            oddsectSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/oddsect.wav");
            rhyterSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/rhyter.wav");
            scykansSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/scykans.wav");
            ridochanSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/ridochan.wav");
            slugsparkSound = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "sounds/slugspark.wav");

        } catch (IOException e) {
            Debug.e(e);
        }
    }

    @Override
    protected Scene onCreateScene() {

        onCreateMainScene();

        return this.scene;
    }

    protected void onCreateMainScene() {

        this.scene.detachChildren();

        this.scene = new Scene();

        // Setup background
        Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
        this.scene.attachChild(backgroundSprite);

        Text title = new Text(130, 20, comicSansH1, "Mobile Monsters", getVertexBufferObjectManager());
        this.scene.attachChild(title);

        // Generate Character Teams
        this.generateTeams();

        // Create mainMenu
        this.createMainMenu();

        // Create characterSelectMenu
        this.createCharacterSelectMenu();

        // Create difficultySelectMenu
        this.createDifficultySelectMenu();

        // Start at the Main Menu
        this.scene.setChildScene(mainMenu);
        if (victoryMusic.isPlaying()) {
            victoryMusic.pause();
        }
        if (defeatMusic.isPlaying()) {
            defeatMusic.pause();
        }
        if (battleMusic.isPlaying()) {
            battleMusic.pause();
        }
        menuMusic.play();
    }

    private void generateTeams() {

        this.characterTeams.clear();
        this.activePlayerMonsterIndex = 0;
        this.activeOpponentMonsterIndex = 0;

        MonsterType bug = new MonsterType("Bug");
        MonsterType fire = new MonsterType("Fire");
        MonsterType water = new MonsterType("Water");
        MonsterType flying = new MonsterType("Flying");
        MonsterType electric = new MonsterType("Electric");
        MonsterType psychic = new MonsterType("Psychic");
        MonsterType dragon = new MonsterType("Dragon");
        MonsterType grass = new MonsterType("Grass");
        MonsterType poison = new MonsterType("Poison");
        MonsterType ice = new MonsterType("Ice");
        MonsterType normal = new MonsterType("Normal");
        MonsterType fighting = new MonsterType("Fighting");
        MonsterType ghost = new MonsterType("Ghost");
        MonsterType rock = new MonsterType("Rock");
        MonsterType ground = new MonsterType("Ground");

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
        Attack signalBeam = new Attack("Signal Beam", 90, 100, bug);
        Attack strength = new Attack("Strength", 90, 100, normal);
        Attack solarBeam = new Attack("Solar Beam", 90, 100, grass);
        Attack sludgeBomb = new Attack("Sludge Bomb", 90, 100, poison);
        Attack electricHug = new Attack("Electric Hug", 70, 100, electric);
        Attack wetHug = new Attack("Wet Hug", 70, 100, water);
        Attack confusingGrin = new Attack("Confusing Grin", 75, 100, psychic);
        Attack tailSmack = new Attack("Tail Smack", 85, 100, normal);
        Attack venomousCrunch = new Attack("Venomous Crunch", 95, 100, poison);
        Attack pinch = new Attack("Pinch", 80, 100, bug);
        Attack bite = new Attack("Bite", 70, 100, normal);
        Attack rockThrow = new Attack("Rock Throw", 90, 80, normal);
        Attack slimySpit = new Attack("Slimy Spit", 65, 100, water);
        Attack sourScent = new Attack("Sour Scent", 90, 100, grass);
        Attack burrow = new Attack("Burrow", 75, 100, ground);
        Attack mindControl = new Attack("Burrow", 100, 100, psychic);
        Attack spook = new Attack("Spook", 95, 100, ghost);
        Attack chargeBlast = new Attack("Charge Blast", 95, 100, electric);
        Attack icyStare = new Attack("Icy Stare", 95, 100, ice);
        Attack noogie = new Attack("Noogie", 80, 100, fighting);
        Attack terribleSinging = new Attack("Terrible Singing", 85, 100, psychic);
        Attack bearHug = new Attack("Bear Hug", 70, 100, normal);
        Attack stopDropAndRoll = new Attack("Stop, Drop and Roll", 70, 100, fire);
        Attack bonesaber = new Attack("Bonesaber", 100, 100, ground);
        Attack liftedMountain = new Attack("Lifted Mountain", 100, 100, rock);
        Attack staticSockRub = new Attack("Static Sock Rub", 70, 100, electric);
        Attack doggyPile = new Attack("Doggy Pile", 85, 100, fighting);
        Attack scatterThorns = new Attack("Scatter Thorns", 60, 100, grass);
        Attack hiss = new Attack("Hiss", 60, 100, normal);
        Attack wallSneak = new Attack("Wall Sneak", 70, 100, ghost);
        Attack wallSmash = new Attack("Wall Smash", 80, 100, rock);
        Attack hornDrill = new Attack("Horn Drill", 60, 100, ground);
        Attack tapShoulder = new Attack("Tap Shoulder", 50, 100, normal);
        Attack yearsOfDragon = new Attack("1000 Years of Dragon", 110, 100, dragon);
        Attack hellfire = new Attack("Hellfire", 110, 100, fire);
        Attack mentalPrison = new Attack("Mental Prison", 95, 100, psychic);
        Attack walkAway = new Attack("Walk Away", 10, 100, normal);

        ArrayList<MonsterType> slugsparkTypes = new ArrayList<>();
        slugsparkTypes.add(electric);
        slugsparkTypes.add(water);

        ArrayList<Attack> slugsparkAttacks = new ArrayList<>();
        slugsparkAttacks.add(electricHug);
        slugsparkAttacks.add(wetHug);
        slugsparkAttacks.add(confusingGrin);
        slugsparkAttacks.add(tailSmack);

        Monster slugspark = new Monster(
                this.mSlugsparkTexture,
                this.getVertexBufferObjectManager(),
                "Slugspark",
                slugsparkTypes,
                slugsparkAttacks,
                slugsparkSound,
                this
        );

        ArrayList<MonsterType> golpinTypes = new ArrayList<>();
        golpinTypes.add(bug);
        golpinTypes.add(poison);

        ArrayList<Attack> golpinAttacks = new ArrayList<>();
        golpinAttacks.add(venomousCrunch);
        golpinAttacks.add(pinch);
        golpinAttacks.add(bite);
        golpinAttacks.add(rockThrow);
        Monster golpin = new Monster(
                this.mGolpinTexture,
                this.getVertexBufferObjectManager(),
                "Golpin",
                golpinTypes,
                golpinAttacks,
                golpinSound,
                this
        );

        ArrayList<MonsterType> oddsectTypes = new ArrayList<>();
        oddsectTypes.add(water);
        oddsectTypes.add(grass);

        ArrayList<Attack> oddsectAttacks = new ArrayList<>();
        oddsectAttacks.add(slimySpit);
        oddsectAttacks.add(sourScent);
        oddsectAttacks.add(pinch);
        oddsectAttacks.add(burrow);
        Monster oddsect = new Monster(
                this.mOddsectTexture,
                this.getVertexBufferObjectManager(),
                "Oddsect",
                oddsectTypes,
                oddsectAttacks,
                oddsectSound,
                this
        );

        ArrayList<MonsterType> beeveeTypes = new ArrayList<>();
        beeveeTypes.add(psychic);

        ArrayList<Attack> beeveeAttacks = new ArrayList<>();
        beeveeAttacks.add(mindControl);
        beeveeAttacks.add(chargeBlast);
        beeveeAttacks.add(spook);
        beeveeAttacks.add(icyStare);

        Monster beevee = new Monster(
                this.mBeeveeTexture,
                this.getVertexBufferObjectManager(),
                "Beevee",
                beeveeTypes,
                beeveeAttacks,
                beeveeSound,
                this
        );

        ArrayList<MonsterType> jynchampTypes = new ArrayList<>();
        jynchampTypes.add(psychic);
        jynchampTypes.add(fighting);

        ArrayList<Attack> jynchampAttacks = new ArrayList<>();
        jynchampAttacks.add(noogie);
        jynchampAttacks.add(terribleSinging);
        jynchampAttacks.add(bearHug);
        jynchampAttacks.add(stopDropAndRoll);

        Monster jynchamp = new Monster(
                this.mJynchampTexture,
                this.getVertexBufferObjectManager(),
                "Jynchamp",
                jynchampTypes,
                jynchampAttacks,
                jynchampSound,
                this
        );

        ArrayList<MonsterType> bonekingTypes = new ArrayList<>();
        bonekingTypes.add(ground);

        ArrayList<Attack> bonekingAttacks = new ArrayList<>();
        bonekingAttacks.add(bonesaber);
        bonekingAttacks.add(doggyPile);
        bonekingAttacks.add(liftedMountain);
        bonekingAttacks.add(staticSockRub);

        Monster boneking = new Monster(
                this.mBonekingTexture,
                this.getVertexBufferObjectManager(),
                "Boneking",
                bonekingTypes,
                bonekingAttacks,
                bonekingSound,
                this
        );


        ArrayList<MonsterType> scykansTypes = new ArrayList<>();
        scykansTypes.add(grass);
        scykansTypes.add(poison);

        ArrayList<Attack> scykansAttacks = new ArrayList<>();
        scykansAttacks.add(venomousCrunch);
        scykansAttacks.add(scatterThorns);
        scykansAttacks.add(hiss);
        scykansAttacks.add(burrow);

        Monster scykans = new Monster(
                this.mScykansTexture,
                this.getVertexBufferObjectManager(),
                "Scykans",
                scykansTypes,
                scykansAttacks,
                scykansSound,
                this
        );

        ArrayList<MonsterType> rhyterTypes = new ArrayList<>();
        rhyterTypes.add(ghost);
        rhyterTypes.add(rock);

        ArrayList<Attack> rhyterAttacks = new ArrayList<>();
        rhyterAttacks.add(wallSneak);
        rhyterAttacks.add(wallSmash);
        rhyterAttacks.add(tapShoulder);
        rhyterAttacks.add(hornDrill);

        Monster rhyter = new Monster(
                this.mRhydonTexture,
                this.getVertexBufferObjectManager(),
                "Rhyter",
                rhyterTypes,
                rhyterAttacks,
                rhyterSound,
                this
        );

        ArrayList<MonsterType> ridochanTypes = new ArrayList<>();
        ridochanTypes.add(dragon);
        ridochanTypes.add(fire);

        ArrayList<Attack> ridochanAttacks = new ArrayList<>();
        ridochanAttacks.add(yearsOfDragon);
        ridochanAttacks.add(hellfire);
        ridochanAttacks.add(mentalPrison);
        ridochanAttacks.add(walkAway);

        Monster ridochan = new Monster(
                this.mRidochanTexture,
                this.getVertexBufferObjectManager(),
                "Ridochan",
                ridochanTypes,
                ridochanAttacks,
                ridochanSound,
                this
        );

        ArrayList<Monster> adrienne = new ArrayList<>();
        adrienne.add(golpin);
        adrienne.add(oddsect);
        adrienne.add(boneking);

        ArrayList<Monster> brody = new ArrayList<>();
        brody.add(jynchamp);
        brody.add(slugspark);
        brody.add(beevee);

        ArrayList<Monster> zack = new ArrayList<>();
        zack.add(scykans);
        zack.add(rhyter);
        zack.add(ridochan);

        this.characterTeams.add(adrienne);
        this.characterTeams.add(brody);
        this.characterTeams.add(zack);
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
        this.mainMenu.setY(300);
        this.mainMenu.setX(105);

        final IMenuItem newGameMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(0, mNewGameMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem exitGameMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(2, mExitGameMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        this.mainMenu.addMenuItem(newGameMenuItem);
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
                new SpriteMenuItem(3, mPreviewAdrienneTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem oakMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(4, mPreviewBrodyTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem rocketMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(5, mPreviewZackTexture, getVertexBufferObjectManager()
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

        return this.characterSelectMenu;
    }

    private MenuScene createDifficultySelectMenu() {
        this.difficultySelectMenu = new MenuScene(mCamera);
        this.difficultySelectMenu.setY(255);
        this.difficultySelectMenu.setX(105);

        final IMenuItem easyMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(8, mEasyMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem hardMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(16, mHardMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);
        final IMenuItem mainMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(6, mMainMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        this.difficultySelectMenu.addMenuItem(easyMenuItem);
        this.difficultySelectMenu.addMenuItem(hardMenuItem);
        this.difficultySelectMenu.addMenuItem(mainMenuItem);

        this.difficultySelectMenu.buildAnimations();
        this.difficultySelectMenu.setBackgroundEnabled(false);

        this.difficultySelectMenu.setOnMenuItemClickListener(this);

        return this.difficultySelectMenu;
    }

    private MenuScene createCharacterPreviewMenu() {
        this.characterPreviewMenu = new MenuScene(mCamera);
        this.characterPreviewMenu.setY(300);
        this.characterPreviewMenu.setX(105);

        ITextureRegion characterTexture;

        switch (this.playerIndex) {
            case 1:
                characterTexture = mChooseBrodyTexture;
                break;
            case 2:
                characterTexture = mChooseZackTexture;
                break;
            case 0:
            default:
                characterTexture = mChooseAdrienneTexture;
                break;
        }

        ArrayList<Monster> team = this.characterTeams.get(playerIndex);

        final IMenuItem selectCharacterMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(7, characterTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        final IMenuItem backMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(8, mBackMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        final AnimatedSprite monster1 = new AnimatedSprite(
                0 - team.get(0).getWidth()/2f,
                -120 - team.get(0).getHeight()/2f,
                team.get(0).getTiledTextureRegion(),
                getVertexBufferObjectManager()
        );
        monster1.animate(Monster.releaseFrameDuration, 0, 61, true);
        monster1.setScale(.7f);

        final AnimatedSprite monster2 = new AnimatedSprite(
                0 - team.get(1).getWidth()/2f,
                30 - team.get(1).getHeight()/2f,
                team.get(1).getTiledTextureRegion(),
                getVertexBufferObjectManager()
        );
        monster2.animate(Monster.releaseFrameDuration, 0, 61, true);
        monster2.setScale(.7f);

        final AnimatedSprite monster3 = new AnimatedSprite(
                0 - team.get(2).getWidth()/2f,
                180 - team.get(2).getHeight()/2f,
                team.get(2).getTiledTextureRegion(),
                getVertexBufferObjectManager()
        );
        monster3.animate(Monster.releaseFrameDuration, 0, 61, true);
        monster3.setScale(.7f);

        final Text monster1Name = new Text(95, -180, this.comicSansH1, team.get(0).getName(), this.getVertexBufferObjectManager());
        final Text monster2Name = new Text(95, -30, this.comicSansH1, team.get(1).getName(), this.getVertexBufferObjectManager());
        final Text monster3Name = new Text(95, 120, this.comicSansH1, team.get(2).getName(), this.getVertexBufferObjectManager());

        final Text monster1Type = new Text(95, -145, this.comicSansH1, team.get(0).getTypeString(), this.getVertexBufferObjectManager());
        final Text monster2Type = new Text(95, 5, this.comicSansH1, team.get(1).getTypeString(), this.getVertexBufferObjectManager());
        final Text monster3Type = new Text(95, 155, this.comicSansH1, team.get(2).getTypeString(), this.getVertexBufferObjectManager());

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

        return this.characterPreviewMenu;
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

    private MenuScene createVictoryMenu() {
        this.victoryMenu = new MenuScene(mCamera);
        this.victoryMenu.setX(105);
        this.victoryMenu.setY(160);

        final IMenuItem exitGameMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(11, mExitBattleMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        this.victoryMenu.addMenuItem(exitGameMenuItem);

        this.victoryMenu.buildAnimations();
        this.victoryMenu.setBackgroundEnabled(false);

        this.victoryMenu.setOnMenuItemClickListener(this);

        return this.victoryMenu;
    }

    private MenuScene createDefeatMenu() {
        this.defeatMenu = new MenuScene(mCamera);
        this.defeatMenu.setX(105);
        this.defeatMenu.setY(160);

        final IMenuItem exitGameMenuItem = new ScaleMenuItemDecorator(
                new SpriteMenuItem(11, mExitBattleMenuItemTexture, getVertexBufferObjectManager()
                ), 1.2f, 1);

        this.defeatMenu.addMenuItem(exitGameMenuItem);

        this.defeatMenu.buildAnimations();
        this.defeatMenu.setBackgroundEnabled(false);

        this.defeatMenu.setOnMenuItemClickListener(this);

        return this.defeatMenu;
    }

    public void drawGameOutputQueue() {

        OutputScene outputScene = null;
        if (!gameOutputQueue.isEmpty()) {
            outputScene = gameOutputQueue.remove(0);
            this.gameScene.setChildScene(outputScene);
        } else {
            this.gameScene.setChildScene(nextChildScene);
        }


    }

    public void setupBattleMenu() {

        this.attackMenu.clearMenuItems();

        AttackMenuItemDecorator attackMenuItem;
        Text attackName;

        ArrayList<Attack> attacks = this.characterTeams.get(playerIndex).get(activePlayerMonsterIndex).getAttacks();
        for (int i = 0; i < attacks.size(); i++) {
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
        this.setNextChildScene(attackMenu);
    }

    public void setupMonsterMenu(boolean exit) {

        this.monsterMenu.clearMenuItems();

        MonsterMenuItemDecorator monsterMenuItem;
        Text monsterName;

        ArrayList<Monster> monsters = this.characterTeams.get(playerIndex);

        for (int i = 0; i < monsters.size(); i++) {
            if (i == activePlayerMonsterIndex) {
                continue;
            }

            if (monsters.get(i).isFainted()) {
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

        if (exit) {
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
        this.setNextChildScene(monsterMenu);
    }

    public Scene onCreateBattleScene() {

        this.createBattleMenu();
        this.createAttackMenu();
        this.createMonsterMenu();
        this.createVictoryMenu();
        this.createDefeatMenu();

        Sprite battleBackgroundSprite = new Sprite(0, 0, this.mBattleBackgroundTextureRegion, getVertexBufferObjectManager());
        this.gameScene.attachChild(battleBackgroundSprite);

        this.opponentIndex = (new Random()).nextInt(this.characterTeams.size());
        while (this.opponentIndex == this.playerIndex) {
            this.opponentIndex = (new Random()).nextInt(this.characterTeams.size());
        }

        this.activePlayerMonsterName = new Text(320, 320, sansSmall, "                        ", getVertexBufferObjectManager());
        this.activePlayerMonsterLevel = new Text(320, 340, sansSmall, "      ", getVertexBufferObjectManager());
        this.activePlayerMonsterHP = new Text(320, 380, sansSmall, "          ", getVertexBufferObjectManager());

        Entity activePlayerMonsterHPBarGroup = new Entity(320, 360);
        Rectangle activePlayerMonsterHPBarContainer = new Rectangle(0, 0, 100, 18, getVertexBufferObjectManager());
        activePlayerMonsterHPBarContainer.setColor(0, 0, 0);
        this.activePlayerMonsterHPBar = new Rectangle(2, 2, 96, 14, getVertexBufferObjectManager());
        this.activePlayerMonsterHPBar.setColor(0, 1, 0);
        activePlayerMonsterHPBarContainer.attachChild(activePlayerMonsterHPBar);
        activePlayerMonsterHPBarGroup.attachChild(activePlayerMonsterHPBarContainer);
        this.gameScene.attachChild(activePlayerMonsterHPBarGroup);


        this.activeOpponentMonsterName = new Text(100, 100, sansSmall, "                        ", getVertexBufferObjectManager());
        this.activeOpponentMonsterLevel = new Text(100, 120, sansSmall, "      ", getVertexBufferObjectManager());

        Entity activeOpponentMonsterHPBarGroup = new Entity(100, 140);
        Rectangle activeOpponentMonsterHPBarContainer = new Rectangle(0, 0, 100, 18, getVertexBufferObjectManager());
        activeOpponentMonsterHPBarContainer.setColor(0, 0, 0);
        this.activeOpponentMonsterHPBar = new Rectangle(2, 2, 96, 14, getVertexBufferObjectManager());
        this.activeOpponentMonsterHPBar.setColor(0, 1, 0);
        activeOpponentMonsterHPBarContainer.attachChild(activeOpponentMonsterHPBar);
        activeOpponentMonsterHPBarGroup.attachChild(activeOpponentMonsterHPBarContainer);
        this.gameScene.attachChild(activeOpponentMonsterHPBarGroup);


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

        if(hardMode) {
            ArrayList<Monster> monsters = this.characterTeams.get(opponentIndex);
            Monster opponentMonster = monsters.get(activeOpponentMonsterIndex);
            Monster activeMonster = this.characterTeams.get(playerIndex).get(activeOpponentMonsterIndex);

            float effectiveness;

            ArrayList<Float> effectivenesses = new ArrayList<>();

            // Check if the current monster is weak to the player
            for(Monster monster : monsters) {
                effectiveness = 1f;
                if(!monster.isFainted()) {
                    for(MonsterType playerType : activeMonster.getTypes()) {
                        for (MonsterType opponentType : opponentMonster.getTypes()) {
                            if (opponentType.getWeaknesses().indexOf(playerType.getName()) >= 0) {
                                effectiveness *= 2;
                            }

                            if (opponentType.getResistances().indexOf(playerType.getName()) >= 0) {
                                effectiveness /= 2;
                            }

                            if (opponentType.getImmunities().indexOf(playerType.getName()) >= 0) {
                                effectiveness = 0f;
                            }
                        }
                    }
                    effectivenesses.add(effectiveness);
                } else {
                    effectivenesses.add(100f);
                }
            }

            int minIndex = effectivenesses.indexOf(Collections.min(effectivenesses));

            if(minIndex != activeOpponentMonsterIndex) {
                return minIndex;
            } else {
                return -1;
            }
        } else {

            // If the opponent does switch, we select a random available monster
            ArrayList<Monster> monsters = this.characterTeams.get(opponentIndex);

            ArrayList<Monster> availableMonsters = new ArrayList<>();

            for (int i = 0; i < monsters.size(); i++) {
                if (!monsters.get(i).isFainted() && i != activeOpponentMonsterIndex) {
                    availableMonsters.add(monsters.get(i));
                }
            }

            if (availableMonsters.isEmpty()) {
                // if there are no available monsters, then we cannot switch
                return -1;
            } else {
                // randomize the monster switch
                int randomAvailableMonster = (new Random()).nextInt(availableMonsters.size());
                return monsters.indexOf(availableMonsters.get(randomAvailableMonster));
            }
        }
    }

    public int opponentChooseAttack() {

        ArrayList<Monster> monsters = this.characterTeams.get(opponentIndex);
        Monster opponentMonster = monsters.get(activeOpponentMonsterIndex);
        Monster activeMonster = this.characterTeams.get(playerIndex).get(activeOpponentMonsterIndex);

        if(hardMode) {

            float effectiveness;

            ArrayList<Float> effectivenesses = new ArrayList<>();

            // Check all attacks for effectiveness, including STAB
            for(Attack attack : opponentMonster.getAttacks()) {
                effectiveness = 1f;

                // STAB
                if(opponentMonster.getTypes().indexOf(attack.getType()) > -1) {
                    effectiveness = 1.5f;
                }

                for(MonsterType playerType : activeMonster.getTypes()) {
                    if (attack.getType().getWeaknesses().indexOf(playerType.getName()) >= 0) {
                        effectiveness *= 2;
                    }

                    if (attack.getType().getResistances().indexOf(playerType.getName()) >= 0) {
                        effectiveness /= 2;
                    }

                    if (attack.getType().getImmunities().indexOf(playerType.getName()) >= 0) {
                        effectiveness = 0f;
                    }
                }
                effectivenesses.add(effectiveness);
            }

            return effectivenesses.indexOf(Collections.max(effectivenesses));
        } else {
            return new Random().nextInt(opponentMonster.getAttacks().size());
        }
    }

    public int opponentChooseSwitchMonster() {

        // EASY opponent switching
        if(!this.hardMode) {
            // Opponent has a 15% chance of deciding to switch
            if (Math.random() >= .15) {
                return -1;
            }
        }

        return this.opponentMustSwitchMonster();
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

        menuCursorSound.play();
        switch (pMenuItem.getID()) {
            case 0:
                // choose difficulty
                this.scene.setChildScene(this.difficultySelectMenu);
                break;
            case 1:
                // load game
                break;
            case 2:
                // exit game
                System.exit(0);
                break;
            case 3:
                // preview Adrienne
                this.playerIndex = 0;
                this.createCharacterPreviewMenu();
                this.scene.setChildScene(this.characterPreviewMenu);
                break;
            case 4:
                // preview Brody
                this.playerIndex = 1;
                this.createCharacterPreviewMenu();
                this.scene.setChildScene(this.characterPreviewMenu);
                break;
            case 5:
                // preview Zack
                this.playerIndex = 2;
                this.createCharacterPreviewMenu();
                this.scene.setChildScene(this.characterPreviewMenu);
                break;
            case 6:
                // back to main menu
                this.scene.setChildScene(this.mainMenu);
                break;
            case 7:
                // I choose you!
                menuMusic.pause();

                this.scene.detachChildren();

                this.onCreateBattleScene();

                this.setNextChildScene(battleMenu);

                battleMusic.seekTo(0);
                battleMusic.play();

                mEngine.setScene(this.gameScene);
                break;

            case 9:
                // choosing an attack
                this.setupBattleMenu();

                break;

            case 10:
                // switching monsters
                this.setupMonsterMenu(true);
                break;


            case 11:
                // running away
                battleMusic.pause();

                this.gameScene.detachChildren();

                this.onCreateMainScene();
                mEngine.setScene(this.scene);
                this.scene.setChildScene(this.mainMenu);
                menuMusic.seekTo(0);
                menuMusic.play();

                break;


            case 14:
                // switch monsters

            case 12:
                // attack was chosen
                Monster activeMonster = this.characterTeams
                        .get(playerIndex).get(activePlayerMonsterIndex);
                Monster opponentMonster = this.characterTeams
                        .get(opponentIndex).get(activeOpponentMonsterIndex);

                this.startNextTurn = true;

                // process the turn
                boolean monsterSwitched = pMenuItem.getID() == 14;

                if (monsterSwitched) {
                    // chose a monster to switch to
                    this.switchMonster(
                            ((MonsterMenuItemDecorator) pMenuItem).getMonsterIndex(),
                            this.activePlayerMonsterIndex,
                            true
                    );
                    activeMonster = this.characterTeams.get(playerIndex).get(activePlayerMonsterIndex);
                }

                // determine if opponent must switch
                int switchMonster = this.opponentChooseSwitchMonster();
                if (switchMonster != -1) {

                    // chose a monster to switch to
                    this.switchMonster(
                            switchMonster,
                            this.activeOpponentMonsterIndex,
                            false
                    );
                    opponentMonster = this.characterTeams.get(opponentIndex).get(activeOpponentMonsterIndex);
                }

                //opponent is attacking
                boolean opponentWillAttack = switchMonster == -1;

                //player is attacking
                boolean playerWillAttack = !monsterSwitched;

                //determine opponent's attack
                int opponentAttackIndex = 0;
                if(opponentWillAttack) {
                    opponentAttackIndex = opponentChooseAttack();
                }

                if (activeMonster.getCurrentSpeed() > opponentMonster.getCurrentSpeed()
                        || activeMonster.getCurrentSpeed() == opponentMonster.getCurrentSpeed()
                        && Math.random() > .5) {

                    //player goes first
                    if (playerWillAttack) {
                        processAttack(
                                activeMonster,
                                opponentMonster,
                                ((AttackMenuItemDecorator) pMenuItem).getAttackIndex(),
                                true
                        );
                    }
                    if (opponentWillAttack && !opponentMonster.isFainted()) {
                        processAttack(
                                opponentMonster,
                                activeMonster,
                                opponentAttackIndex,
                                false
                        );
                    }
                } else {
                    //opponent goes first
                    if (opponentWillAttack) {
                        processAttack(
                                opponentMonster,
                                activeMonster,
                                opponentAttackIndex,
                                false
                        );
                    }

                    if (playerWillAttack && !activeMonster.isFainted()) {
                        processAttack(
                                activeMonster,
                                opponentMonster,
                                ((AttackMenuItemDecorator) pMenuItem).getAttackIndex(),
                                true
                        );
                    }
                }

                if (this.startNextTurn) {
                    this.setNextChildScene(battleMenu);
                }

                break;

            case 13:
                // return from the attack or monster menu
                this.setNextChildScene(battleMenu);
                break;

            case 15:
                // forced switch
                this.switchMonster(
                        ((MonsterMenuItemDecorator) pMenuItem).getMonsterIndex(),
                        this.activePlayerMonsterIndex,
                        true
                );

                this.setNextChildScene(battleMenu);

            case 8:
                // setup Easy Mode
                this.hardMode = false;
                this.scene.setChildScene(this.characterSelectMenu);
                break;
            case 16:
                // setup Hard Mode
                this.hardMode = true;
                this.scene.setChildScene(this.characterSelectMenu);

            default:
                break;
        }

        this.drawGameOutputQueue();

        return true;
    }

    private void processAttack(Monster monster, Monster targetMonster, int attackIndex, boolean player) {

//        Damage Calculations
//        http://www.serebii.net/games/damage.shtml
//
//        ((((2 * Level / 5 + 2) * AttackStat * AttackPower / DefenseStat) / 50) + 2) * STAB * Weakness/Resistance * RandomNumber / 100
//
//        A = attacker's Level
//        B = attacker's Attack or Special
//        C = attack Power
//        D = defender's Defense or Special
//        X = same-Type attack bonus (1 or 1.5)
//        Y = Type modifiers (40, 20, 10, 5, 2.5, or 0)
//        Z = a random number between 217 and 255

        Attack attack = monster
                .getAttacks()
                .get(attackIndex);

        int attackersLevel = monster.getLevel();
        int criticalHit = (Math.random() <= .15) ? 2 : 1;
        int attackPower = attack.getPower();
        boolean isSpecial = attack.isSpecial();
        int monsterAttackPower = isSpecial ? monster.getCurrentSpecial() : monster.getCurrentAttack();
        int targetDefensePower = isSpecial ? targetMonster.getCurrentSpecial() : targetMonster.getCurrentDefense();
        float STAB = monster.getTypes().indexOf(attack.getType()) >= 0 ? 1.5f : 1;
        int randomNumber = (new Random()).nextInt(100 - 85 + 1) + 85;

        float effectiveness = 1f;
        for (MonsterType type : targetMonster.getTypes()) {
            if (type.getWeaknesses().indexOf(attack.getType().getName()) >= 0) {
                effectiveness *= 2;
            }

            if (type.getResistances().indexOf(attack.getType().getName()) >= 0) {
                effectiveness /= 2;
            }

            if (type.getImmunities().indexOf(attack.getType().getName()) >= 0) {
                effectiveness = 0f;
            }
        }

        // put damage on the opponent monster
        float damage_calculation = ((((2 * (attackersLevel * criticalHit) / 5 + 2)
                * monsterAttackPower * attackPower / targetDefensePower) / 50) + 2) * STAB * effectiveness * randomNumber / 100;


        if (effectiveness != 0) {
            targetMonster.takeDamage((int) Math.ceil(damage_calculation), player);
        }

        // chose an attack
        queueAttackOutput(
                (player ? "" : "Opponent's ") + monster.getName() + " used " + attack.getName() + "!",
                monster,
                targetMonster,
                attack,
                criticalHit == 2 && effectiveness != 0,
                player
        );


        if(effectiveness == 0) {
            this.queueSimpleOutput("It does not affect...");
        } else {
            if (effectiveness > 1f) {
                this.queueSimpleOutput("It's super effective!!!");
            } else if (effectiveness < 1f) {
                this.queueSimpleOutput("It's not very effective...");
            }

            if (criticalHit == 2) {
                this.queueSimpleOutput("Critical Hit!!!");
            }
        }


        if (targetMonster.isFainted()) {

            this.queueFaintOutput(targetMonster.getName() + " fainted!!!", targetMonster, player);

            if (player) {
                int mustSwitchMonster = this.opponentMustSwitchMonster();
                if (mustSwitchMonster == -1) {
                    //handle victory
                    queueVictoryOutput("You win!!!");

                    this.startNextTurn = false;
                    this.setNextChildScene(victoryMenu);
                } else {
                    this.switchMonster(mustSwitchMonster, activeOpponentMonsterIndex, false);
                }
            } else {
                if (!playerDefeated()) {
                    this.startNextTurn = false;
                    this.setupMonsterMenu(false);
                    this.setNextChildScene(monsterMenu);
                } else {
                    //handle defeat
                    queueDefeatOutput("You lost... Try again!");

                    this.startNextTurn = false;
                    this.setNextChildScene(defeatMenu);
                }
            }
        }
    }

    public void updateMonsterInformation(Monster monster, boolean player) {

        if (player) {
            this.activePlayerMonsterIndex = this.characterTeams.get(playerIndex).indexOf(monster);
            this.activePlayerMonsterName.setText(monster.getName());
            this.activePlayerMonsterLevel.setText("Lv " + monster.getLevel());
            this.activePlayerMonsterHP.setText(monster.getCurrentHP() + " / " + monster.getMaxHP());
        } else {
            this.activeOpponentMonsterIndex = this.characterTeams.get(opponentIndex).indexOf(monster);
            this.activeOpponentMonsterLevel.setText("Lv " + monster.getLevel());
            this.activeOpponentMonsterName.setText(monster.getName());
        }
    }

    private void switchMonster(int newMonsterIndex, int oldMonsterIndex, boolean player) {
        int switchingPlayerIndex = player ? playerIndex : opponentIndex;

        Monster oldMonster;

        if (oldMonsterIndex >= 0) {

            oldMonster = this.characterTeams
                    .get(switchingPlayerIndex)
                    .get(oldMonsterIndex);

            if (!oldMonster.isFainted()) {
                queueReturnOutput(
                        (
                                player ?
                                        this.characterTeams
                                                .get(switchingPlayerIndex)
                                                .get(oldMonsterIndex)
                                                .getName() + ", come back!" :
                                        "Opponent called back " +
                                                this.characterTeams
                                                        .get(switchingPlayerIndex)
                                                        .get(oldMonsterIndex)
                                                        .getName()),

                        this.characterTeams
                                .get(switchingPlayerIndex)
                                .get(oldMonsterIndex),

                        player
                );
            }
        }

        if(player) {
            this.activePlayerMonsterIndex = newMonsterIndex;
        } else {
            this.activeOpponentMonsterIndex = newMonsterIndex;
        }

        queueReleaseOutput(
                (player ? "Player " : "Opponent ") + "Switched to: " +
                        this.characterTeams
                                .get(switchingPlayerIndex)
                                .get(newMonsterIndex)
                                .getName(),

                this.characterTeams
                        .get(switchingPlayerIndex)
                        .get(newMonsterIndex),

                player
        );

    }

    private boolean playerDefeated() {
        ArrayList<Monster> monsters = this.characterTeams.get(playerIndex);

        for (int i = 0; i < monsters.size(); i++) {
            if (!monsters.get(i).isFainted()) {
                return false;
            }
        }

        return true;
    }

    public void queueFaintOutput(String gameOutputText, Monster monster, boolean player) {
        OutputScene outputScene = new FaintOutputScene(gameOutputText, this, sansSmall, monster, player);
        gameOutputQueue.add(outputScene);
        Debug.d("Game Output", gameOutputText);
    }

    public void queueAttackOutput(String gameOutputText, Monster monster, Monster targetMonster, Attack attack, boolean critical, boolean player) {
        OutputScene outputScene = new AttackOutputScene(gameOutputText, this, sansSmall, monster, targetMonster, attack, critical, player);
        gameOutputQueue.add(outputScene);
        Debug.d("Game Output", gameOutputText);
    }

    public void queueReleaseOutput(String gameOutputText, Monster monster, boolean player) {
        OutputScene outputScene = new ReleaseOutputScene(gameOutputText, this, sansSmall, monster, player);
        gameOutputQueue.add(outputScene);
        Debug.d("Game Output", gameOutputText);
    }

    public void queueReturnOutput(String gameOutputText, Monster monster, boolean player) {
        OutputScene outputScene = new ReturnOutputScene(gameOutputText, this, sansSmall, monster, player);
        gameOutputQueue.add(outputScene);
        Debug.d("Game Output", gameOutputText);
    }

    public void queueSimpleOutput(String gameOutputText) {
        OutputScene outputScene = new SimpleOutputScene(gameOutputText, this, sansSmall);
        gameOutputQueue.add(outputScene);
        Debug.d("Game Output", gameOutputText);
    }

    public void queueVictoryOutput(String gameOutputText) {
        OutputScene outputScene = new VictoryOutputScene(gameOutputText, this, sansSmall);
        gameOutputQueue.add(outputScene);
        Debug.d("Game Output", gameOutputText);
    }

    public void queueDefeatOutput(String gameOutputText) {
        OutputScene outputScene = new DefeatOutputScene(gameOutputText, this, sansSmall);
        gameOutputQueue.add(outputScene);
        Debug.d("Game Output", gameOutputText);
    }

    public void setNextChildScene(Scene nextChildScene) {
        this.nextChildScene = nextChildScene;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }

}
