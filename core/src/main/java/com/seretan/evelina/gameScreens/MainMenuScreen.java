package com.seretan.evelina;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class MainMenuScreen implements Screen {

    private final Game game;
    private Stage stage;
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture background;

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        if (GameState.getInstance().isPlayMusic) {
            GameState.getInstance().playMenuMusic();
        }
        background = new Texture("main_bg.png");
        Image backgroundImage = new Image(new TextureRegionDrawable(background));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Button newGameButton = initNewGameButton();
        Button continueButton = initContinueButton();
        Button chaptersButton = initChapterButton();
        Button optionsButton = initOptionButton();
        Button exitButton = initExitButton();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(newGameButton).size(200, 100).padBottom(20);
        table.row();

        table.add(continueButton).size(200, 100).padBottom(20);
        table.row();
        table.add(chaptersButton).size(200, 100).padBottom(20);
        table.row();
        table.add(optionsButton).size(200, 100).padBottom(20);
        table.row();
        table.add(exitButton).size(200, 100).padBottom(20);

        stage.addActor(table);
    }

    private String loadLanguage() {
        Preferences preferences = Gdx.app.getPreferences("settings");
        return preferences.getString("language", "en");
    }

    public Button initButton(String language, String buttonName) {
        String upPath = language + "/" + buttonName + "01.png";
        String downPath = language + "/" + buttonName + "03.png";
        Texture upTexture = new Texture(upPath);
        Texture downTexture = new Texture(downPath);
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(upTexture);
        buttonStyle.down = new TextureRegionDrawable(downTexture);
        Button button = new Button(buttonStyle);
        return button;
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
    }

    public Button initOptionButton() {
        Button optionButton = initButton(loadLanguage(), "option");

        optionButtonEvent(optionButton);
        stage.addActor(optionButton);
        return optionButton;
    }

    public void optionButtonEvent(Button button) {
        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsMenuScreen(game));
            }
        });
    }

    public Button initNewGameButton() {

        Button newGameButton = initButton(loadLanguage(), "newGame");
        stage.addActor(newGameButton);
        newGameButtonEvent(newGameButton);
        return newGameButton;
    }

    public void newGameButtonEvent(Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChapterOneGameScreen(game));
            }
        });
    }

    public Button initContinueButton() {

        Button continueButton = initButton(loadLanguage(), "continue");
        stage.addActor(continueButton);
        continueButtonEvent(continueButton);
        return continueButton;
    }

    public void continueButtonEvent(Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChapterOneGameScreen(game));
            }
        });
    }

    public Button initChapterButton() {
        Button chapterButton = initButton(loadLanguage(), "chapters");
        stage.addActor(chapterButton);
        chapterButtonEvent(chapterButton);
        return chapterButton;
    }

    public void chapterButtonEvent(Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChapterOneGameScreen(game));
            }
        });
    }

    public Button initExitButton() {
        Button exitButton = initButton(loadLanguage(), "exit");
        stage.addActor(exitButton);
        exitButtonEvent(exitButton);
        return exitButton;
    }

    public void exitButtonEvent(Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
}


