package fun.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.tomtom.boxing.Physics;

public class ScoreBar {

    Physics physics;
    BitmapFont font = new BitmapFont();
    Batch batch;

    public ScoreBar(Physics physics, Batch batch) {
        this.physics = physics;
        this.batch = batch;
    }

    public void display(){
        batch.begin();
        font.draw(batch,"BLACK "+physics.getBlackPoints()+"     WHITE "+physics.getWhitePoints(), 300, 800);
        batch.end();
    }
}
