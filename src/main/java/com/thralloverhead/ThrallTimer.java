
package com.thralloverhead;

public class ThrallTimer
{
    private final int duration;
    private int ticksRemaining;

    public ThrallTimer(int duration)
    {
        if (duration <= 0)
        {
            throw new IllegalArgumentException("Duration must be greater than zero");
        }

        this.duration = duration;
        this.ticksRemaining = duration;
    }

    public void tick()
    {
        if (!isExpired())
        {
            ticksRemaining--;
        }
    }

    public int getTicksRemaining()
    {
        return ticksRemaining;
    }

    public int getDuration()
    {
        return duration;
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
