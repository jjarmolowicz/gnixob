package fun.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tomtom.boxing.BoxerVisualState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoxerGraphics {

    SpriteBatch batch;
    static Texture white;

    static Texture black;

    static Texture black0;

    static Texture black1;
    static Texture black2;
    static Texture black3;

    static Texture rblack1;
    static Texture rblack2;
    static Texture rblack3;

    static Texture blackL1;
    static Texture blackL2;
    static Texture blackL3;

    static Texture rblack;

    static Texture rblackL1;
    static Texture rblackL2;
    static Texture rblackL3;

    static Texture rwhite;

    static Texture rwhiteL1;
    static Texture rwhiteL2;
    static Texture rwhiteL3;

    static Texture rwhite1;
    static Texture rwhite2;
    static Texture rwhite3;

    static Texture whiteL1;
    static Texture whiteL2;
    static Texture whiteL3;

    static Texture white1;
    static Texture white2;
    static Texture white3;

    List<Texture> dupa = new ArrayList<Texture>(Arrays.asList(black,black1));

    public enum BW{
        BLACK(new ArrayList<Texture>(Arrays.asList(black, black,black0,black1, black2, black3, black3,black3)),
                new ArrayList<Texture>(Arrays.asList(black, black, black, black0, blackL1, blackL2, blackL3)),
                new ArrayList<Texture>(Arrays.asList(rblack, rblack,rblack,rblack1, rblack2, rblack3, rblack3,rblack3)),
                new ArrayList<Texture>(Arrays.asList(rblack, rblack,rblack,rblackL1, rblackL2, rblackL3, rblackL3,rblackL3))),

        WHITE(new ArrayList<Texture>(Arrays.asList(white, white,white,white1, white2, white3, white3,white3)),
                new ArrayList<Texture>(Arrays.asList(white, white,white,white1, white2, white3, white3,white3)),
                new ArrayList<Texture>(Arrays.asList(rwhite, rwhite,rwhite,rwhite1, rwhite2, rwhite3, rwhite3,rwhite3)),
                new ArrayList<Texture>(Arrays.asList(rwhite, rwhite,rwhite,rwhiteL1, rwhiteL2, rwhiteL3, rwhiteL3,rwhiteL3)));
        List<Texture> statesL;
        List<Texture> statesR;

        List<Texture> statesRL;
        List<Texture> statesRR;

        BW(List<Texture> statesR, List<Texture> statesL, List<Texture> statesRR, List<Texture> statesRL) {
            this.statesL = statesL;
            this.statesR = statesR;

            this.statesRL = statesRL;
            this.statesRR = statesRR;
        }
    }


    public BoxerGraphics() {
        batch = new SpriteBatch();
        white = new Texture(Gdx.files.internal("whiteBoxer.png"));
        black = new Texture(Gdx.files.internal("blackBoxer.png"));
        black0 = new Texture(Gdx.files.internal("blackBoxer.png"));

        black1 = new Texture(Gdx.files.internal("blackBoxerb1.png"));
        black2 = new Texture(Gdx.files.internal("blackBoxerb2.png"));
        black3 = new Texture(Gdx.files.internal("blackBoxerb3.png"));

        rblack1 = new Texture(Gdx.files.internal("RblackBoxerb1.png"));
        rblack2 = new Texture(Gdx.files.internal("RblackBoxerb2.png"));
        rblack3 = new Texture(Gdx.files.internal("RblackBoxerb3.png"));

        rblack = new Texture(Gdx.files.internal("RblackBoxer.png"));

        blackL1 = new Texture(Gdx.files.internal("blackBoxerbL1.png"));
        blackL2 = new Texture(Gdx.files.internal("blackBoxerbL2.png"));
        blackL3 = new Texture(Gdx.files.internal("blackBoxerbL3.png"));

        rwhite = new Texture(Gdx.files.internal("RWhiteBoxer.png"));

        rblackL1 = new Texture(Gdx.files.internal("RblackBoxerbL1.png"));
        rblackL2 = new Texture(Gdx.files.internal("RblackBoxerbL2.png"));
        rblackL3 = new Texture(Gdx.files.internal("RblackBoxerbL3.png"));

        rwhiteL1 = new Texture(Gdx.files.internal("RwhiteBoxerbL1.png"));
        rwhiteL2 = new Texture(Gdx.files.internal("RwhiteBoxerbL2.png"));
        rwhiteL3 = new Texture(Gdx.files.internal("RwhiteBoxerbL3.png"));

        rwhite1 = new Texture(Gdx.files.internal("RwhiteBoxerb1.png"));
        rwhite2 = new Texture(Gdx.files.internal("RwhiteBoxerb2.png"));
        rwhite3 = new Texture(Gdx.files.internal("RwhiteBoxerb3.png"));

        whiteL1 = new Texture(Gdx.files.internal("whiteBoxerbL1.png"));
        whiteL2 = new Texture(Gdx.files.internal("whiteBoxerbL2.png"));
        whiteL3 = new Texture(Gdx.files.internal("whiteBoxerbL3.png"));

        white1 = new Texture(Gdx.files.internal("whiteBoxerb1.png"));
        white2 = new Texture(Gdx.files.internal("whiteBoxerb2.png"));
        white3 = new Texture(Gdx.files.internal("whiteBoxerb3.png"));
    }

    public void reder(BoxerVisualState boxerState, BW blackOrWhite){
        batch.begin();

        Texture boxerTexture = null;
        boxerTexture = getTexture(boxerState, blackOrWhite);
        int reverseOffset = 0;
        if (boxerState.reversed()){
            reverseOffset = boxerTexture.getWidth();
        }


        batch.draw(boxerTexture, boxerState.getX()*60-reverseOffset, boxerState.getY()*60); // FIXME - that '60' ain't that great

        System.out.println(boxerState.getX());
        batch.end();
    }

    private Texture getTexture(BoxerVisualState boxerState, BW blackOrWhite) {
        Texture boxerTexture;
        if (boxerState.leftArmOffset()>boxerState.rightArmOffset()){
            if (boxerState.reversed()){
                boxerTexture= blackOrWhite.statesRL.get(boxerState.leftArmOffset());
            }else{
                boxerTexture= blackOrWhite.statesL.get(boxerState.leftArmOffset());
            }
        }else{
            if (boxerState.reversed()){
                boxerTexture= blackOrWhite.statesRR.get(boxerState.rightArmOffset());
            }else{
                boxerTexture= blackOrWhite.statesR.get(boxerState.rightArmOffset());
            }
        }
        return boxerTexture;
    }
}
