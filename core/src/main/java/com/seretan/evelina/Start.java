package com.seretan.evelina;
import com.badlogic.gdx.Game;
import com.seretan.evelina.gameScreens.MainMenuScreen;


public class Start extends Game {

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }

}
