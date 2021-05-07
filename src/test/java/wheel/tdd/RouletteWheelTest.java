package wheel.tdd;

import org.junit.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.util.Set;
import java.util.HashSet;

public class RouletteWheelTest {

    /*
        Test if spin stop after 20 seconds
    */
    @Test
    public void Stopped20SAfterSpin() {
        WheelObserver wheelObserver = mock(WheelObserver.class);
        RouletteWheel wheel = new RouletteWheel(wheelObserver);

        wheel.spin(20000);
        wheel.tick(20000);

        verify(wheelObserver).stopped(anyInt());
    }


    /*
        Test if we have random ball location when wheel stopped
    */
    @Test
    public void RandomBallLocationStopped() {
        final boolean seenAll[] = new boolean[1];
        seenAll[0] = false;

        WheelObserver wheelObserver = new WheelObserver() {
            Set<Integer> seen = new HashSet<Integer>();
            public void stopped(final int location) {
                if (location < 0 || location > 36)
                    throw new IllegalArgumentException();
                seen.add(location);
                if (seen.size() == 37) seenAll[0] = true;
            }
        };
        RouletteWheel wheel = new RouletteWheel(wheelObserver);

        for (int x = 0; x < 1000; x++)
        {
            wheel.spin(0);
            wheel.tick(20000);
        }

        assertTrue(seenAll[0]);
    }

    /*
        Test specify ball location when wheel stopped
    */
    @Test
    public void SpecifyBallLocationStopped() {
        WheelObserver wheelObserver = mock(WheelObserver.class);
        RouletteWheel wheel = new RouletteWheel(wheelObserver);


        long spinFor20s = 20000;
        wheel.spin(spinFor20s);
        wheel.tick(20000);

        verify(wheelObserver, times(1)).stopped(anyInt());
    }

    /*
        Test specify ball location once wheel stopped
    */
    @Test
    public void SpecifyBallLocationOnceStopped() {
        WheelObserver wheelObserver = mock(WheelObserver.class);
        RouletteWheel wheel = new RouletteWheel(wheelObserver);

        long spinFor20s = 20000;
        wheel.spin(spinFor20s);
        wheel.tick(20000);
        wheel.tick(20001);

        verify(wheelObserver, times(1)).stopped(anyInt());
    }

    /*
        Test never notify stopped before spin end
    */
    @Test
    public void NotNotifyStoppedBeforeSpinEnd() {
        WheelObserver wheelObserver = mock(WheelObserver.class);
        RouletteWheel wheel = new RouletteWheel(wheelObserver);

        long spinFor20s = 20000;
        wheel.spin(spinFor20s);

        long timeEndMs = 10000;
        wheel.tick(timeEndMs);

        verify(wheelObserver, never() ).stopped(anyInt());
    }
    
}
