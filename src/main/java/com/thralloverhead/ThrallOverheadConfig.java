
package com.thralloverhead;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("thrallTimer")
public interface ThrallOverheadConfig extends Config
{
    @ConfigItem(
        keyName = "showBar",
        name = "Bar",
        description = "Display the bar above summoned thralls",
        position = 0
    )
    default boolean showBar()
    {
        return true;
    }

    @Range(min = 20, max = 100)
    @ConfigItem(
        keyName = "barWidth",
        name = "Bar Width",
        description = "Width of the thrall timer bar (20-100)",
        position = 1
    )
    default int barWidth()
    {
        return 40;
    }

    @Range(min = 2, max = 15)
    @ConfigItem(
        keyName = "barHeight",
        name = "Bar Height",
        description = "Height of the thrall timer bar (2-15)",
        position = 2
    )
    default int barHeight()
    {
        return 5;
    }

    @Range(min = 0, max = 100)
    @ConfigItem(
        keyName = "barOffset",
        name = "Bar Vertical Offset",
        description = "Adjust the vertical position of the bar (0-100)",
        position = 3
    )
    default int barOffset()
    {
        return 35;
    }

    @ConfigItem(
        keyName = "showBorder",
        name = "Border",
        description = "Display a border around the thrall timer bar",
        position = 4
    )
	default boolean showBorder()
	{
		return false;
	}

    @ConfigItem(
        keyName = "borderColor",
        name = "Border color",
        description = "Border color of duration bar",
        position = 5
    )
    default Color borderColor()
    {
        return Color.WHITE;
    }

    @ConfigItem(
        keyName = "fillColor",
        name = "Fill color",
        description = "Border fill color of duration bar",
        position = 5
    )
    default Color fillColor()
    {
        return Color.BLACK;
    }

    @ConfigItem(
        keyName = "mageColor",
        name = "Mage (Ghost)",
        description = "Bar color for mage thrall",
        position = 6
    )
    default Color mageColor()
    {
        return new Color(32, 181, 191);
    }

    @ConfigItem(
        keyName = "rangeColor",
        name = "Range (Skeleton)",
        description = "Bar color for range thrall",
        position = 7
    )
    default Color rangeColor()
    {
        return new Color(200, 220, 88);
    }

    @ConfigItem(
        keyName = "meleeColor",
        name = "Melee (Zombie)",
        description = "Bar color for melee thralls",
        position = 8
    )
    default Color meleeColor()
    {
        return new Color(180, 40, 40);
    }

    @ConfigItem(
        keyName = "flashWarning",
        name = "Flash Warning",
        description = "Flash the background when a thrall is about to expire",
        position = 9
    )
    default boolean flashWarning()
    {
        return true;
    }

    @ConfigItem(
        keyName = "flashWarningThrall",
        name = "Flash thrall outline",
        description = "Flash the outline of the thrall when it's about to expire",
        position = 10
    )
    default boolean flashWarningThrall()
    {
        return false;
    }

    @Range(min = 1, max = 30)
    @ConfigItem(
        keyName = "warningThreshold",
        name = "Warning Threshold",
        description = "Number of ticks remaining before the warning starts (1-30)",
        position = 11
    )
    default int warningThreshold()
    {
        return 10;
    }

    @ConfigItem(
        keyName = "showTimer",
        name = "Show a timer (seconds)",
        description = "Display remaining time in seconds beside the bar or above the thrall",
        position = 12
    )
    default boolean showTimer()
    {
        return false;
    }
}
