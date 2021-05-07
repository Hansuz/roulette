package wheel.tdd;

import java.util.Random;

public class RouletteWheel {
    private boolean spinning = false;
    private final WheelObserver wheelObserver;
    private long spinForMs;
    private long currentMs = 0;
    private final Random random = new Random();

    public static void spin(final WheelObserver wheelObserver, final int spinDuration) {
        RouletteWheel rouletteWheel = new RouletteWheel(wheelObserver);
        rouletteWheel.spin(spinDuration);
    }

    RouletteWheel(final WheelObserver wheelObserver) {
        this.wheelObserver = wheelObserver;
    }

    /*
        Start to spin the wheel
    */
    void spin(final long spinForMs) {
        this.spinning = true;
        this.spinForMs = spinForMs;
    }

    /*
        If the time is higher or equal we stop spinning
    */
    void tick(final long timeMs) {
        currentMs = timeMs;
        if (spinning && currentMs >= spinForMs)
        {
            spinning = false;
            final int location = random.nextInt(37);
            this.wheelObserver.stopped(location);
        }
    }
}
