package flamepixels;

import processing.core.PApplet;
import processing.core.PVector;

/** 
 * Class LightCircle pour projet FlamesPixel
 * @author Xavier 
 */
public class LightCircle {
  /** PApplet parent */
  private PApplet app;
  /** Rayon du cercle */
  float rad;
  /** Position du cercle */
  PVector pos;
  /** Velocite du cercle */
  private PVector vel;
  /** Acceleration. Pousse le cercle vers le haut */
  private final PVector acc = new PVector(0f, -0.12f);

  /** 
   * Constructeur du cercle. Initialise en bas du canvas, avec direction
   * unitaire aleatoire
   * @param app PApplet parent
   * @param x Coordonnee x du cercle
   * @param y Coordonnee y du cercle
   * @param r Rayon du cercle.
   */
  public LightCircle(PApplet app, float x, float y, float r) {
    this.app = app;
    rad = r;
    pos = new PVector(x, y);
    vel = PVector.fromAngle(app.random(PApplet.HALF_PI) + PApplet.QUARTER_PI);
  }

  /** 
   * Scaling de la velocite
   * @param scaling Echelonnage de la velocite du cercle
   */
  public void scaleVel(float scaling) {
    vel.mult(scaling);
  }

  /** Deplace le cercle vers le haut et reduit son rayon */
  public void update() {
    vel.add(acc);
    pos.add(vel);
    rad *= 0.99;
  }

  /** Affiche le cercle */
  public void show() {
    app.noFill();
    app.stroke(191, 231, 0);
    app.ellipse(pos.x, pos.y, rad * 2, rad * 2);
  }
}
