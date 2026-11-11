
package com.thralloverhead;

import lombok.Getter;

@Getter
public class ThrallTimer
{
    private final int duration;
    private int ticksRemaining;
    private final long startTime;
    private final long durationNanos;

    public ThrallTimer(int duration)
    {
        if (duration <= 0)
        {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }

        this.duration = duration;
        this.ticksRemaining = duration;
        this.startTime = System.nanoTime();
        this.durationNanos = duration * 600_000_000L;
    }

    public void tick()
    {
        if (!isExpired())
        {
            ticksRemaining--;
        }
    }

    public double getSecondsRemaining()
    {
        long elapsed = System.nanoTime() - startTime;
        return Math.max(0, (durationNanos - elapsed) / 1_000_000_000.0);
    }

    public boolean isExpired()
    {
        return ticksRemaining <= 0;
    }

    public double getProgress()
    {
        return (double) ticksRemaining / duration;
    }
}
