package com.seretan.evelina;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameState {
    private GameState(){
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("medieval-ambient-236809.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1.0f);
        backgroundMusic.play();
    };
    private Music backgroundMusic;
    private static GameState gameState;
    public boolean isPlayMusic = true;

    public static GameState getInstance() {
        if (gameState == null) {
            gameState = new GameState();
        }
        return gameState;
    }
    public enum Language{
        RUSSIAN,ROMANIAN,ENGLISH;
    }
    public void playMenuMusic() {

        if (!isPlayMusic) {
            backgroundMusic.setVolume(1.0f);
            backgroundMusic.play();

            System.out.println("s-a setat volumul la 100");
        }
    }
    public void stopMenuMusic(){

            backgroundMusic.setVolume(0);
        System.out.println("s-a setat volumul la 0");
    }
//    public boolean isPlayMusic(){
//
//      return true;
//    }

    public void getResolution(){}

}
