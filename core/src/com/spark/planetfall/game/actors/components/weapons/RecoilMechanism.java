package com.spark.planetfall.game.actors.components.weapons;

import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.constants.Constant;

public class RecoilMechanism {

    private float hipfireCOF;
    private float adsCOF;
    private float COFBloom;
    private int tolerance;
    private Vector2 recoilMagnitude;
    protected float COF;
    protected int kicks;

    public RecoilMechanism(float hipfireCOF, float adsCOF, float COFBloom, Vector2 recoilMagnitude, int tolerance) {

        this.hipfireCOF = hipfireCOF;
        this.adsCOF = adsCOF;
        this.COFBloom = COFBloom;
        this.recoilMagnitude = recoilMagnitude;
        this.tolerance = tolerance;

        this.COF = hipfireCOF;
        this.kicks = 0;

    }


    public Vector2[] recoil(float angle) {

        Vector2 recoilforce = recoilMagnitude.cpy();
        float siderecoil = recoilforce.x;
        recoilforce.x = 0;
        recoilforce.setAngle(angle - 180);

        Vector2 siderecoilvec = new Vector2(0, siderecoil);
        if (Math.random() >= 0.5) {
            if (kicks < tolerance) {
                siderecoilvec.setAngle(angle + 90);
                kicks += 1;
            } else {
                siderecoilvec.setAngle(angle - 90);
                kicks -= 1;
            }
        } else {
            if (kicks > -tolerance) {
                siderecoilvec.setAngle(angle - 90);
                kicks -= 1;
            } else {
                siderecoilvec.setAngle(angle + 90);
                kicks += 1;
            }
        }

        Vector2[] toreturn = {recoilforce, siderecoilvec};
        return toreturn;
    }

    public void fire() {

        COF += COFBloom;

    }

    //ticks for delta time
    public void update(float delta, boolean firing, boolean aiming) {

        if (!firing) {
            Vector2 cLerp = new Vector2(COF, 0);
            if (aiming) {
                cLerp.lerp(new Vector2(this.adsCOF, 0), Constant.WEAPON_BLOOM_LERP);
            } else {
                cLerp.lerp(new Vector2(this.hipfireCOF, 0), Constant.WEAPON_BLOOM_LERP);
            }
            COF = cLerp.x;
        }

        if (!aiming) {
            if (COF < this.hipfireCOF) {
                this.COF += this.hipfireCOF;

            }
        }

    }

    public RecoilMechanism copy() {

        return new RecoilMechanism(this.hipfireCOF, this.adsCOF, this.COFBloom, this.recoilMagnitude.cpy(), this.tolerance);

    }

    public float getCOF() {
        return COF;
    }

    public void setWeapon(Weapon weapon) {
    }

}