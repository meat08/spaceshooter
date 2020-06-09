package ru.spaceshooter.base;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ru.spaceshooter.screen.GameScreen;
import ru.spaceshooter.screen.MenuScreen;

public class MainMenu {

    private Stage stage;
    private Game game;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private FileHandle fileHandle;
    private Skin skin;
    private Table tableRoot;
    private Label labelMain;
    private Label labelPause;
    private Label labelGameOver;
    private Table tableButtons;
    private TextButton btnNewGame;
    private TextButton btnConf;
    private TextButton btnLoad;
    private TextButton btnExit;
    private Table tableConf;
    private Button btnMusic;
    private Slider sliderMusic;
    private Button btnSound;
    private Slider sliderSound;
    private TextButton btnBack;
    private Button btnAccel;
    private Slider sliderAccel;
    private TextButton btnResume;
    private TextButton btnSave;

    private Value widthRoot;
    private Value heightRoot;
    private Value labelPad;
    private Value confButtonSize;
    private Value sliderWidth;

    public MainMenu(InputMultiplexer multiplexer, Game game, MenuScreen menuScreen, FileHandle fileHandle) {
        this.game = game;
        this.menuScreen = menuScreen;
        this.fileHandle = fileHandle;
        create(multiplexer);
        show();
    }

    public MainMenu(InputMultiplexer multiplexer, GameScreen gameScreen, FileHandle fileHandle) {
        this.gameScreen = gameScreen;
        this.fileHandle = fileHandle;
        create(multiplexer);
        show();
    }

    private void create(InputMultiplexer multiplexer) {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        multiplexer.addProcessor(0, stage);
        tableRoot = new Table();
        labelMain = new Label("Space shooter", skin, "titleBig");
        labelPause = new Label("ПАУЗА", skin, "title-plain-big");
        labelGameOver = new Label("ИГРА ОКОНЧЕНА", skin, "title-plain-big");
        labelGameOver.setColor(Color.RED);
        tableButtons = new Table();
        btnNewGame = new TextButton("Новая игра", skin, "round");
        btnConf = new TextButton("Настройки", skin, "round");
        btnLoad = new TextButton("Загрузить", skin, "round");
        btnExit = new TextButton("Выход", skin, "round");
        tableConf = new Table();
        btnMusic = new Button(skin, "music");
        sliderMusic = new Slider(0.1f, 1f, 0.05f, false, skin);
        btnSound = new Button(skin, "sound");
        sliderSound = new Slider(0.1f, 1f, 0.05f, false, skin);
        btnSound = new Button(skin, "sound");
        sliderAccel = new Slider(0.5f, 1.5f, 0.05f, false, skin);
        btnAccel = new Button(skin, "accel");
        btnBack = new TextButton("Назад", skin, "round");
        btnResume = new TextButton("Продолжить", skin, "round");
        btnSave = new TextButton("Сохранить игру", skin, "round");
    }

    private void show() {
        setPercentSize();
        stage.addActor(tableRoot);
        newGame();
        buttonListener();
    }

    private void newGame() {
        tableButtons.clear();
        tableRoot.clear();
        tableButtons.defaults().padBottom(2.0f);
        if (menuScreen != null) {
            labelMain.setFontScale(getLabelScale(labelMain));
            tableRoot.add(labelMain).padBottom(labelPad).row();
            btnNewGame.getLabel().setFontScale(getButtonScale(btnNewGame));
            tableButtons.add(btnNewGame).width(widthRoot).height(heightRoot).row();
            btnLoad.getLabel().setFontScale(getButtonScale(btnLoad));
            tableButtons.add(btnLoad).width(widthRoot).height(heightRoot).row();
        } else if (gameScreen != null) {
            labelPause.setFontScale(getLabelScale(labelPause));
            tableButtons.add(labelPause).padBottom(2f).row();
            btnResume.getLabel().setFontScale(getButtonScale(btnResume));
            tableButtons.add(btnResume).width(widthRoot).height(heightRoot).row();
            btnSave.getLabel().setFontScale(getButtonScale(btnSave));
            tableButtons.add(btnSave).width(widthRoot).height(heightRoot).row();
        }
        btnConf.getLabel().setFontScale(getButtonScale(btnConf));
        btnExit.getLabel().setFontScale(getButtonScale(btnExit));
        tableButtons.add(btnConf).width(widthRoot).height(heightRoot).row();
        tableButtons.add(btnExit).width(widthRoot).height(heightRoot).row();
        tableRoot.add(tableButtons).row();

        tableConf.defaults().padBottom(20.0f);
        Table tmpTableMusic = new Table();
        tmpTableMusic.add(btnMusic).size(confButtonSize).pad(12f);
        tmpTableMusic.add(sliderMusic).width(sliderWidth);
        Table tmpTableSound = new Table();
        tmpTableSound.add(btnSound).size(confButtonSize).pad(12f);
        tmpTableSound.add(sliderSound).width(sliderWidth);
        Table tmpTableAccel = new Table();
        tmpTableAccel.add(btnAccel).size(confButtonSize).pad(12f);
        tmpTableAccel.add(sliderAccel).width(sliderWidth);
        tableConf.add(tmpTableMusic).height(heightRoot).row();
        tableConf.add(tmpTableSound).height(heightRoot).row();
        tableConf.add(tmpTableAccel).height(heightRoot).row();
        btnBack.getLabel().setFontScale(getButtonScale(btnBack));
        tableConf.add(btnBack).width(widthRoot).height(heightRoot).row();
    }

    private void buttonListener() {
        btnNewGame.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                if (menuScreen != null) {
                    game.setScreen(new GameScreen());
                } else if (gameScreen != null) {
                    newGame();
                    gameScreen.startNewGame();
                }
            }
        });
        btnLoad.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                newGame();
                loadGame();
            }
        });
        btnConf.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                showConf();
                setConfState();
            }
        });
        btnBack.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                savePreferences();
            }
        });
        btnResume.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                if (gameScreen != null) {
                    gameScreen.resumeGame();
                }
            }
        });
        btnSave.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                if (gameScreen != null) {
                    gameScreen.saveGame();
                }
            }
        });
        btnExit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
        sliderMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setMusicVolume(sliderMusic.getValue());
            }
        });
        btnMusic.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                musicOnOff();
            }
        });
        sliderSound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSoundVolume(sliderSound.getValue());
            }
        });
        btnSound.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundOnOff();
            }
        });
        sliderAccel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setSenseAccel(sliderAccel.getValue());
            }
        });
        btnAccel.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                accelOnOff();
            }
        });
    }

    private float getLabelScale(Label label) {
        return (Gdx.graphics.getWidth() - label.getPrefWidth())/Gdx.graphics.getWidth() + 1f;
    }

    private float getButtonScale(Button button) {
        return (Gdx.graphics.getHeight() * 0.075f - button.getPrefHeight())/(Gdx.graphics.getHeight() * 0.075f) + 1f < 0f ?
                1f : (Gdx.graphics.getHeight() * 0.075f - button.getPrefHeight())/(Gdx.graphics.getHeight() * 0.075f) + 1f;
    }

    private void setPercentSize() {
        widthRoot = Value.percentWidth(0.75f, tableRoot);
        heightRoot = Value.percentHeight(0.075f, tableRoot);
        labelPad = Value.percentHeight(0.45f, tableRoot);
        confButtonSize = Value.percentHeight(0.085f, tableRoot);
        sliderWidth = Value.percentWidth(0.65f, tableRoot);
    }

    private void loadGame() {
        if (fileHandle.exists()) {
            if (gameScreen == null) {
                GameScreen gameScreen = new GameScreen();
                game.setScreen(gameScreen);
                gameScreen.loadGame();
            } else {
                gameScreen.loadGame();
            }
        }
    }

    private void musicOnOff() {
        if (gameScreen != null) {
            gameScreen.setMusicOn();
            gameScreen.musicOnOff();
        } else if (menuScreen != null) {
            menuScreen.setMusicOn();
            menuScreen.musicOnOff();
        }
    }

    private void setMusicVolume(float volume) {
        if (gameScreen != null) {
            gameScreen.setVolumeMusic(volume);
        } else if (menuScreen != null) {
            menuScreen.setVolumeMusic(volume);
        }
    }

    private void soundOnOff() {
        if (gameScreen != null) {
            gameScreen.setSoundOn();
        } else if (menuScreen != null) {
            menuScreen.setSoundOn();
        }
    }

    private void setSoundVolume(float volume) {
        if (gameScreen != null) {
            gameScreen.setVolumeSound(volume);
        } else if (menuScreen != null) {
            menuScreen.setVolumeSound(volume);
        }
    }

    private void accelOnOff() {
        if (gameScreen != null) {
            gameScreen.setAccelerometerOn();
        } else if (menuScreen != null) {
            menuScreen.setAccelerometerOn();
        }
    }

    private void setSenseAccel(float sense) {
        if (gameScreen != null) {
            gameScreen.setSenseAccel(sense);
        } else if (menuScreen != null) {
            menuScreen.setSenseAccel(sense);
        }
    }

    private void showConf() {
        tableButtons.remove();
        tableRoot.add(tableConf).row();
    }

    private void setConfState() {
        if (gameScreen != null) {
            gameScreen.setState(State.CONFIG);
        } else if (menuScreen != null) {
            menuScreen.setState(State.CONFIG);
        }
    }

    private void savePreferences() {
        if (gameScreen != null) {
            gameScreen.exitConfig();
            gameScreen.flushPreference();
        } else if (menuScreen != null) {
            menuScreen.exitConfig();
            menuScreen.flushPreference();
        }
    }

    public void hideConf() {
        tableConf.remove();
        tableRoot.add(tableButtons).row();
    }

    public void gameOver() {
        tableButtons.clear();
        labelGameOver.setFontScale(getLabelScale(labelGameOver));
        tableButtons.add(labelGameOver).padBottom(2f).row();
        btnNewGame.getLabel().setFontScale(getButtonScale(btnNewGame));
        tableButtons.add(btnNewGame).width(widthRoot).height(heightRoot).row();
        btnLoad.getLabel().setFontScale(getButtonScale(btnLoad));
        tableButtons.add(btnLoad).width(widthRoot).height(heightRoot).row();
        btnExit.getLabel().setFontScale(getButtonScale(btnExit));
        tableButtons.add(btnExit).width(widthRoot).height(heightRoot).row();
    }

    public void resize() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        tableRoot.setSize(stage.getWidth(), stage.getHeight());
        tableRoot.setFillParent(true);
        setPercentSize();
        sliderAccel.getStyle().knob.setMinHeight(40f);
        sliderAccel.getStyle().knob.setMinWidth(40f);
        sliderAccel.getStyle().background.setMinHeight(30f);
        sliderAccel.getStyle().knobBefore.setMinHeight(28f);
    }

    public void update() {
        if (gameScreen != null) {
            btnMusic.setChecked(gameScreen.isMusicOn());
            btnSound.setChecked(gameScreen.isSoundOn());
            btnAccel.setChecked(gameScreen.isAccelerometerOn());
            sliderMusic.setValue(gameScreen.getVolumeMusic());
            sliderSound.setValue(gameScreen.getVolumeSound());
            sliderAccel.setValue(gameScreen.getSenseAccel());
        } else if (menuScreen != null) {
            btnMusic.setChecked(menuScreen.isMusicOn());
            btnSound.setChecked(menuScreen.isSoundOn());
            btnAccel.setChecked(menuScreen.isAccelerometerOn());
            sliderMusic.setValue(menuScreen.getVolumeMusic());
            sliderSound.setValue(menuScreen.getVolumeSound());
            sliderAccel.setValue(menuScreen.getSenseAccel());
        }
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
