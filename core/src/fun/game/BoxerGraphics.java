package fun.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
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

    public enum BW{
//        BLACK(new ArrayList<Texture>(Arrays.asList(black, black,black0,black0, black1, black2, black3,black3)),
//                new ArrayList<Texture>(Arrays.asList(black, black, black, black0, blackL1, blackL2, blackL3)),
//                new ArrayList<Texture>(Arrays.asList(rblack, rblack,rblack,rblack, rblack1, rblack2, rblack3,rblack3)),
//                new ArrayList<Texture>(Arrays.asList(rblack, rblack,rblack,rblack, rblackL1, rblackL2, rblackL3,rblackL3))),
//
//        WHITE(new ArrayList<Texture>(Arrays.asList(white, white,white,white, white1, white2, white3,white3)),
//                new ArrayList<Texture>(Arrays.asList(white, white,white,white, whiteL1, whiteL2, whiteL3,whiteL3)),
//                new ArrayList<Texture>(Arrays.asList(rwhite, rwhite,rwhite,rwhite, rwhite1, rwhite2, rwhite3,rwhite3)),
//                new ArrayList<Texture>(Arrays.asList(rwhite, rwhite,rwhite,rwhite, rwhiteL1, rwhiteL2, rwhiteL3,rwhiteL3)));

        BLACK(new ArrayList<Texture>(Arrays.asList(black, black0,black0,black1, black1, black2, black3,black3)),
                new ArrayList<Texture>(Arrays.asList(black, black, black0, black0, blackL1, blackL2, blackL3)),
                new ArrayList<Texture>(Arrays.asList(rblack, rblack1, rblack1, rblack2, rblack2, rblack3, rblack3, rblack3)),
                new ArrayList<Texture>(Arrays.asList(rblack, rblack, rblackL1, rblackL1, rblackL2, rblackL2, rblackL3, rblackL3))),

        WHITE(new ArrayList<Texture>(Arrays.asList(white, white,white1,white1, white2, white2, white3,white3)),
                new ArrayList<Texture>(Arrays.asList(white, white,white1,white1, whiteL2, whiteL2, whiteL3,whiteL3)),
                new ArrayList<Texture>(Arrays.asList(rwhite, rwhite,rwhite1,rwhite1, rwhite2, rwhite2, rwhite3,rwhite3)),
                new ArrayList<Texture>(Arrays.asList(rwhite, rwhite,rwhite1,rwhite1, rwhiteL2, rwhiteL2, rwhiteL3,rwhiteL3)));


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


    Camera cam;

    public BoxerGraphics(Camera camera) {
        batch = new SpriteBatch();
        this.cam = camera;
//        batch.setProjectionMatrix(cam.combined);
        white = new Texture(Gdx.files.internal("whiteBoxer.png"));
        black = new Texture(Gdx.files.internal("blackBoxer.png"));
        black0 = new Texture(Gdx.files.internal("blackBoxer.png"));

        black1 = new Texture(Gdx.files.internal("blackBoxerb1.png"));
        black2 = new Texture(Gdx.files.internal("blackBoxerb2.png"));
        black3 = new Texture(Gdx.files.internal("blackBoxerb3.png"));

        rblack1 = new Texture(Gdx.files.internal("RBlackBoxerb1.png"));
        rblack2 = new Texture(Gdx.files.internal("RBlackBoxerb2.png"));
        rblack3 = new Texture(Gdx.files.internal("RBlackBoxerb3.png"));

        rblack = new Texture(Gdx.files.internal("RBlackBoxer.png"));

        blackL1 = new Texture(Gdx.files.internal("blackBoxerbL1.png"));
        blackL2 = new Texture(Gdx.files.internal("blackBoxerbL2.png"));
        blackL3 = new Texture(Gdx.files.internal("blackBoxerbL3.png"));

        rwhite = new Texture(Gdx.files.internal("RWhiteBoxer.png"));

        rblackL1 = new Texture(Gdx.files.internal("RBlackBoxerbL1.png"));
        rblackL2 = new Texture(Gdx.files.internal("RBlackBoxerbL2.png"));
        rblackL3 = new Texture(Gdx.files.internal("RBlackBoxerbL3.png"));

        rwhiteL1 = new Texture(Gdx.files.internal("RWhiteBoxerbL1.png"));
        rwhiteL2 = new Texture(Gdx.files.internal("RWhiteBoxerbL2.png"));
        rwhiteL3 = new Texture(Gdx.files.internal("RWhiteBoxerbL3.png"));

        rwhite1 = new Texture(Gdx.files.internal("RWhiteBoxerb1.png"));
        rwhite2 = new Texture(Gdx.files.internal("RWhiteBoxerb2.png"));
        rwhite3 = new Texture(Gdx.files.internal("RWhiteBoxerb3.png"));

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
            reverseOffset = boxerTexture.getWidth()-20;
        }else{
            reverseOffset = 20;
        }

//        batch.draw(boxerTexture, boxerState.getX()-reverseOffset, boxerState.getY()); // FIXME - that '60' ain't that great
//        batch.draw(boxerTexture, 0, 0); // FIXME - that '60' ain't that great

        Vector3 vec=new Vector3(boxerState.getX(), boxerState.getY(),0);
        cam.project(vec);

        batch.draw(boxerTexture, vec.x-reverseOffset, vec.y-boxerTexture.getHeight()/2); // FIXME - that '60' ain't that great


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
