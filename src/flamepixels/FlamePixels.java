package flamepixels;

import processing.core.PApplet;

/**
 * Class FlamePixels pour projet FlamePixels
 * @author Xavier
 */
public class FlamePixels extends PApplet {
  /** Flamme */
  Flame flame;

  /** Point d'entree de l'application */
  public static void main(String[] args) {
    PApplet.main(FlamePixels.class.getName());
  }

  /** Setup du PApplet */
  @Override
  public void settings() {
    size(300, 600);
  }

  /** Setup de la fenetre */
  @Override
  public void setup() {
    surface.setLocation(1500, 180);
    flame = new Flame(this, width / 2, floor(height * 0.78f));
  }

  /** Gere l'animation */
  @Override
  public void draw() {
    background(39, 39, 34);
    flame.generate();
    flame.computeProximity();
    flame.show();
    //flame.showLimits();
    flame.update();
  }

}
