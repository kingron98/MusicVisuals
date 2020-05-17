package D19126659;

import processing.core.*;

public class Bass 
{
    MyVisual n;

    // Constructor
    Bass(MyVisual n)
    {
        this.n = n;
    }

    void display()
    {
        n.noStroke();
        n.fill(PApplet.map(n.getAmplitude() * 255, 0, 255, n.fBase, n.fSet), 255, 255, n.getAmplitude() * 100);
        // three different circles for illusion of frequency waves
        n.ellipse(n.width / 2, n.height / 2, 400, 200 + (1000 * n.getAmplitude()));
        n.ellipse(n.width / 2, n.height / 2, 400, 200 + (1200 * n.getAmplitude()));
        n.ellipse(n.width / 2, n.height / 2, 400, 200 + (1400 * n.getAmplitude()));

        n.fill(PApplet.map(n.getAmplitude() * 255, 0, 255, n.fBase, n.fSet), 255, 255, n.getAmplitude() * 100);
        n.ellipse(n.width / 2, n.height / 2, 200 + (1000 * n.getAmplitude()), 400);
        n.ellipse(n.width / 2, n.height / 2, 200 + (1200 * n.getAmplitude()), 400);
        n.ellipse(n.width / 2, n.height / 2, 200 + (1400 * n.getAmplitude()), 400);

        n.fill(0, 200 + (50 * n.getAmplitude()));
        n.ellipse(n.width / 2, n.height / 2, 300 + (300 * n.getSmoothedAmplitude()), 300 + (300 * n.getSmoothedAmplitude()));

        n.fill(255, 50 + (500 * n.getAmplitude()));
        n.textSize(60);
        n.textAlign(PApplet.CENTER, PApplet.CENTER);
        n.text("Chill", (n.width / 2) - 5, n.height / 2, 200 + (1000 * n.getSmoothedAmplitude()));
    }
}
