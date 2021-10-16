package flamepixels;

import processing.core.PApplet;
import java.util.ArrayList;

/**
 * Class Flame pour projet FlamePixel. Tous les calculs sont fait localement
 * dans une zone qui délimite la flamme. La flamme consiste a un ensemble de
 * cercles qui apparait a la position de la flamme. Plus de cercles sont
 * superposes, plus la flamme est vive. Les cercles sont naturellement pousses
 * vers le haut et leur taille diminue progressivement
 * @author Xavier
 */
public class Flame {
  /** PApplet parent */
  private PApplet app;
  /** Position x de la flamme */
  private int x;
  /** Position y de la flamme */
  private int y;
  /** Origine x de la zone de la flamme */
  private int Ox;
  /** Origine y de la zone de la flamme */
  private int Oy;
  /** Liste de cercles qui montent dans la zone */
  private ArrayList<LightCircle> circles;
  /**
   * Division de la zone en points critiques auxquels on associe une valeur de
   * proximite d'un cercle
   */
  private float[][] proxValues;
  /** Largeur de la flamme */
  private final int flameWidth = 300; // Changer manuellement pour faire des flammes plus petites
  /** Hauteur de la flamme */
  private final int flameHeight = flameWidth * 2;
  /** Espace entre les points critiques */
  private final int spacing = flameWidth / 60;
  /** Nombre de lignes du proxValues */
  private final int rows = flameWidth / spacing;
  /** Nombre de colonnes du proxValues */
  private final int cols = flameHeight / spacing;

  /** 
   * Constructeur de la flamme
   * @param app PApplet parent
   * @param x Position x de la flamme
   * @param y Position y de la flamme
   */
  public Flame(PApplet app, int x, int y) {
    this.app = app;
    this.x = x;
    this.y = y;
    Ox = x - (flameWidth / 2);
    Oy = y - PApplet.floor(flameHeight * 0.78f);
    circles = new ArrayList<>();
    proxValues = new float[rows + 1][cols + 1];
  }

  /** Entretient continuellement la flamme */
  public void generate() {
    if (app.random(1) < 0.75f) {
      int tailleMin = flameWidth / 12;
      int tailleMax = flameWidth / 6;
      LightCircle c = new LightCircle(app, x, y, app.random(tailleMin, tailleMax));
      c.scaleVel(flameWidth / 300f);
      circles.add(c);
    }
  }

  /** 
   * Fait evoluer les cercles de la liste, et met a jour la liste des cercles.
   * Les cercles trop petits ou hors zone sont supprimes
   */
  public void update() {
    // Fait evoluer les cercles de la liste
    for (LightCircle c : circles) {
      //c.show();
      c.update();
    }

    // Met a jour la liste de cercles. Les cercles trop petits
    // ou hors zone sont supprimes
    for (int i = circles.size() - 1; i >= 0; i--) {
      LightCircle c = circles.get(i);
      if (c.pos.y < Oy - c.rad || c.rad <= flameWidth / 15) {
        circles.remove(i);
      }
    }
  }

  /** Met a jour le tableau de valeurs des points critiques */
  public void computeProximity() {
    for (int i = 0; i <= rows; i++) {
      for (int j = 0; j <= cols; j++) {
        // Calcul de la valeur definissant la proximite
        // du point i, j par rapport a tous les cercles
        float sum = 0;
        for (LightCircle c : circles) {
          float num = ((c.rad / 2) * (c.rad / 2));
          float den1 = (Ox + i * spacing - c.pos.x) * (Ox + i * spacing - c.pos.x);
          float den2 = (Oy + j * spacing - c.pos.y) * (Oy + j * spacing - c.pos.y);
          sum += num / (den1 + den2);
        }
        proxValues[i][j] = sum;
      }
    }
  }

  /** 
   * Affiche la flamme. Le coeur de la flamme est affiche totalement, et la
   * bordure de la flamme est legement transparent.
   */
  public void show() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // Recuperation des coins du carre de points critiques
        float a = proxValues[i][j];
        float b = proxValues[i + 1][j];
        float c = proxValues[i + 1][j + 1];
        float d = proxValues[i][j + 1];

        // Si le carre est suffisamment proche 
        // des cercles, on le trace
        float prox = (a + b + c + d) / 4;
        if (prox > 1) {

          if (prox > 1) app.fill(191, 231, 0, 155); // Bordure de la flamme
          if (prox > 2) app.fill(191, 231, 0, 205); // Intermediaire
          if (prox > 6) app.fill(191, 231, 0, 255); // Coeur de la flamme
          
          app.noStroke();
          float Ax = Ox + i * spacing;
          float Ay = Oy + j * spacing;
          app.rect(Ax, Ay, spacing, spacing);
        }
      }
    }
  }

  /** Montre la zone delimitant la flamme */
  public void showLimits() {
    app.noFill();
    app.stroke(191, 231, 0);
    app.rect(Ox, Oy, flameWidth, flameHeight);
  }
}
