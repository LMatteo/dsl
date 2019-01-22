package io.github.mosser.arduinoml.kernel.behavioral;

public class TimeCondition implements Condition {
    private int time;

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
