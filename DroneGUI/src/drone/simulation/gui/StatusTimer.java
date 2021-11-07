package drone.simulation.gui;

import javafx.animation.AnimationTimer;
import lombok.Getter;


public abstract class StatusTimer extends AnimationTimer {
	@Getter
    private boolean running;// variable to hold if the timer is running or not

    /**
     * start the animation and set the running to true
     */
    @Override
    public void start() {
        super.start();// start
        running = true;//it runs
    }

    /**
     * stop the animation adn set the running to false
     */
    @Override
    public void stop() {
        super.stop();// stop
        running = false;// it is not running
    }
}
