
package com.thralloverhead;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("thrallTimer")
public interface ThrallOverheadConfig extends Config
{
    @ConfigItem(
        keyName = "showTimer",
        name = "Show Thrall Timer",
        description = "Display the timer above summoned thralls"
    )
    default boolean showTimer()
    {
        return true;
    }

    @Range(min = 20, max = 100)
    @ConfigItem(
        keyName = "barWidth",
        name = "Bar Width",
        description = "Width of the thrall timer bar (20-100)"
    )
    default int barWidth()
    {
        return 40;
    }

    @Range(min = 2, max = 15)
    @ConfigItem(
        keyName = "barHeight",
        name = "Bar Height",
        description = "Height of the thrall timer bar (2-15)"
    )
    default int barHeight()
    {
        return 5;
    }

	@ConfigItem(
    keyName = "showBorder",
    name = "Show Border",
    description = "Display a border around the thrall timer bar"
	)
	default boolean showBorder()
	{
		return false;
	}

    @Range(min = 0, max = 100)
    @ConfigItem(
        keyName = "barOffset",
        name = "Bar Vertical Offset",
        description = "Adjust the vertical position of the timer (0-100)"
    )
    default int barOffset()
    {
        return 35;
    }

    @ConfigItem(
        keyName = "flashWarning",
        name = "Flash Warning",
        description = "Flash the background when a thrall is about to expire"
    )
    default boolean flashWarning()
    {
        return true;
    }

    @Range(min = 1, max = 30)
    @ConfigItem(
        keyName = "warningThreshold",
        name = "Warning Threshold",
        description = "Number of ticks remaining before the warning starts (1-30)"
    )
    default int warningThreshold()
    {
        return 10;
    }
}
