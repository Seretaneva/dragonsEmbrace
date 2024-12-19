package com.seretan.evelina.gameChapters;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.seretan.evelina.gameScreens.MainMenuScreen;


public class ChapterOneGameScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture chapterOneBackground;
    private Game game;
    private Texture textBoxTexture;
    private Label authorName;
    private JsonValue dialogues;
    private int currentDialogueIndex = 0;
    private Label textLabel;
    private int textBoxHeight;
    private int textBoxWidth;


    public ChapterOneGameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        loadChapter();
        batch = new SpriteBatch();
        viewport = new FitViewport(800, 480);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        chapterOneBackground = new Texture("gameBackgrounds/palace.jpg");
        Image backgroundImage = new Image(new TextureRegionDrawable(chapterOneBackground));
        backgroundImage.setFillParent(true);

        Button backButton = initBackButton();

        textBoxTexture = new Texture("textBox.png");
        Image textBoxImage = new Image(new TextureRegionDrawable(textBoxTexture));
        textBoxHeight = 500;
        textBoxWidth = 900;
        textBoxImage.setSize(textBoxWidth, textBoxHeight);
        textBoxImage.setPosition(-80, -100);
        textBoxImage.getColor().a = 0;
        textBoxImage.addAction(Actions.parallel(
            Actions.moveTo(-90, -120, 1f),
            Actions.fadeIn(1.5f)

        ));
        textBoxImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateDialogue();
            }

        });

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE || keycode == Input.Keys.ENTER) {
                    updateDialogue();
                    return true;
                }
                return false;
            }
        });

        Label.LabelStyle labelStyle = labelTextStyle();

        JsonValue dialogue = dialogues.get(currentDialogueIndex);
        String character = dialogue.getString("character");
        String text = dialogue.getString("text");


        textLabel = new Label(text, labelStyle);
        textLabel.setWrap(true);
        textLabel.setSize(650, 30);

        textLabel.setPosition(50, 60);
        textLabel.setFontScale(0.6f);
        textLabel.getColor().a = 0;
        textLabel.addAction(Actions.parallel(
            Actions.moveTo(50, 60, 2f),
            Actions.fadeIn(2.5f)
        ));


        authorName = new Label(character, labelStyle);
        authorName.setPosition(50, 130);
        authorName.getColor().a = 0;
        authorName.addAction(Actions.parallel(
            Actions.moveTo(50, 130, 2f),
            Actions.fadeIn(2.5f)
        ));

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.top();
        table.add(backButton).size(100, 50);
        table.padLeft(540);

        stage.addActor(backgroundImage);
        stage.addActor(textBoxImage);

        stage.addActor(table);
        stage.addActor(authorName);
        stage.addActor(textLabel);
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
        chapterOneBackground.dispose();
        textBoxTexture.dispose();
        stage.dispose();
    }

    private void loadChapter() {
        JsonReader reader = new JsonReader();
        dialogues = reader.parse(Gdx.files.internal("chapterJsons/chapterOne.json")).get("dialogues");
        System.out.println();
    }

    private void updateDialogue() {
        if (currentDialogueIndex < dialogues.size) {
            JsonValue dialogue = dialogues.get(currentDialogueIndex);
            String character = dialogue.getString("character");
            String text = dialogue.getString("text");
            authorName.setText(character);
            textLabel.setText(text);
            currentDialogueIndex++;
        }
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

    private Label.LabelStyle labelTextStyle() {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont labelFont = new BitmapFont(Gdx.files.internal("fonts/candara/candara.fnt"));
        labelStyle.font = labelFont;
        labelStyle.fontColor = Color.WHITE;
        return labelStyle;
    }
}
