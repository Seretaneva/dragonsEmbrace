package com.seretan.evelina;

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


public class ChapterOneGameScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture chapterOneBackground;
    private Game game;

    public ChapterOneGameScreen (Game game){
        this.game = game;
    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        chapterOneBackground = new Texture("mainMenuBackground.png");
       Image backgroundImage = new Image(new TextureRegionDrawable(chapterOneBackground));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
        Button backButton = initBackButton();
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(backButton).size(200,100).padBottom(20);
        stage.addActor(table);
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
chapterOneBackground.dispose();
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
    public Button initBackButton(){
        Button backButton = initButton(loadLanguage(),"back");
        stage.addActor(backButton);
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
}
