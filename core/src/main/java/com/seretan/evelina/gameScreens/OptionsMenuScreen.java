package com.seretan.evelina.gameScreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
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
import com.seretan.evelina.gameSettings.GameState;

public class OptionsMenuScreen implements Screen {

    private final Game game;
    private final String[] languages = {"en", "ro", "ru"};
    private final Preferences preferences;
    private Stage stage;
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture background;
    private Texture leftArrowUp;
    private Texture leftArrowDown;
    private Texture rightArrowUp;
    private Texture rightArrowDown;
    private Texture roLanguage;
    private Texture ruLanguage;
    private Texture enLanguage;
    private Texture currentLanguageTexture;
    private int currentLanguageIndex = 0;
    private Button languageButton;

    public OptionsMenuScreen(Game game) {
        this.game = game;
        preferences = Gdx.app.getPreferences("settings");
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        background = new Texture("gameBackgrounds/main_bg.png");
        Image backgroundImage = new Image(new TextureRegionDrawable(background));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        String savedLanguage = preferences.getString("language", "en");
        currentLanguageIndex = getLanguageIndex(savedLanguage);
        roLanguage = new Texture("ro/roButton.png");
        ruLanguage = new Texture("ru/ruButton.png");
        enLanguage = new Texture("en/enButton.png");


        Button soundButton = initSoundButton();
        languageButton = initLanguageButton();
        Button leftArrow = initLeftArrowButton();
        Button rightArrow = initRightArrowButton();
        Button backButton = initBackButton();

        table.center();
        table.add(soundButton).size(200, 100).padBottom(20).colspan(3);
        table.row();

        table.add(leftArrow).size(50, 50);
        table.add(languageButton).size(200, 100).padRight(10).padLeft(10);
        table.add(rightArrow).size(50, 50);
        table.row();

        table.add(backButton).size(200, 100).padTop(20).colspan(3);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
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

    public Button initBackButton() {
        Button backButton = initButton(loadLanguage(), "back");
        backButtonEvent(backButton);
        return backButton;
    }

    public void backButtonEvent(Button button) {
        button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
    }

    public Button initSoundButton() {
        Button soundButton = initButton(loadLanguage(), "sound");
        soundButtonEvent(soundButton);
        return soundButton;
    }

    public void soundButtonEvent(Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameState.getInstance().isPlayMusic) {
                    GameState.getInstance().stopMenuMusic();
                    GameState.getInstance().isPlayMusic = false;
                } else {
                    GameState.getInstance().playMenuMusic();
                    GameState.getInstance().isPlayMusic = true;
                }
            }
        });
    }

    public Button initLanguageButton() {
        updateLanguageTexture();
        Button.ButtonStyle languageButtonStyle = new Button.ButtonStyle();
        languageButtonStyle.up = new TextureRegionDrawable(currentLanguageTexture);
        return  new Button(languageButtonStyle);
    }

    public Button initLeftArrowButton() {
        leftArrowUp = new Texture("arrowsButtons/arrowLeft.png");
        leftArrowDown = new Texture("arrowsButtons/arrowLeftDown.png");
        Button.ButtonStyle leftArrowStyle = new Button.ButtonStyle();
        leftArrowStyle.up = new TextureRegionDrawable(leftArrowUp);
        leftArrowStyle.down = new TextureRegionDrawable(leftArrowDown);
        Button leftArrowButton = new Button(leftArrowStyle);
        leftArrowEvent(leftArrowButton);
        return leftArrowButton;
    }

    public void leftArrowEvent(Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentLanguageIndex = (currentLanguageIndex - 1 + languages.length) % languages.length;
                updateLanguageTexture();
                saveLanguage();
                game.setScreen(new OptionsMenuScreen(game));
            }
        });
    }

    public Button initRightArrowButton() {
        rightArrowUp = new Texture("arrowsButtons/arrowRight.png");
        rightArrowDown = new Texture("arrowsButtons/arrowRightDown.png");
        Button.ButtonStyle rightArrowStyle = new Button.ButtonStyle();
        rightArrowStyle.up = new TextureRegionDrawable(rightArrowUp);
        rightArrowStyle.down = new TextureRegionDrawable(rightArrowDown);
        Button rightArrowButton = new Button(rightArrowStyle);
        rightArrowEvent(rightArrowButton);
        return rightArrowButton;
    }

    public void rightArrowEvent(Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentLanguageIndex = (currentLanguageIndex + 1) % languages.length;
                updateLanguageTexture();
                saveLanguage();
                game.setScreen(new OptionsMenuScreen(game));
            }
        });
    }

    private void updateLanguageTexture() {
        String currentLanguage = languages[currentLanguageIndex];
        if (currentLanguage.equals("ro")) {
            currentLanguageTexture = roLanguage;
        } else if (currentLanguage.equals("ru")) {
            currentLanguageTexture = ruLanguage;
        } else if (currentLanguage.equals("en")) {
            currentLanguageTexture = enLanguage;
        }
        if (languageButton != null) {
            languageButton.getStyle().up = new TextureRegionDrawable(currentLanguageTexture);
        }
    }

    private void saveLanguage() {
        preferences.putString("language", languages[currentLanguageIndex]);
        preferences.flush();
    }

    private int getLanguageIndex(String lang) {
        for (int i = 0; i < languages.length; i++) {
            if (languages[i].equals(lang)) return i;
        }
        return 0;
    }
}
