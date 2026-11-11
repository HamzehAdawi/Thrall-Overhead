
package com.thralloverhead;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.NPC;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

public class ThrallOverheadOverlay extends Overlay
{
    private final ThrallOverheadConfig config;

    private static final Color WARNING_COLOR = Color.RED;
    private static final long FLASH_INTERVAL_MS = 200;

    @Getter
    @Setter
    private NPC thrall;

    @Setter
    private ThrallTimer thrallTimer;

    @Inject
    public ThrallOverheadOverlay(ThrallOverheadConfig config)
    {
        this.config = config;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if ((!config.showBar() && !config.showTimer() && !config.flashWarningThrall())
            || thrall == null || thrallTimer == null || thrallTimer.isExpired())
        {
            return null;
        }

        int barWidth = config.barWidth();
        int barHeight = config.barHeight();
        int barOffset = config.barOffset();

        Point canvasPoint = thrall.getCanvasTextLocation(
            graphics,
            "",
            thrall.getLogicalHeight() + barOffset
        );

        if (canvasPoint == null)
        {
            return null;
        }

        int x = canvasPoint.getX() - barWidth / 2;
        int y = canvasPoint.getY();

        // Flash the background red during the final few ticks
        boolean warning = (config.flashWarning() || config.flashWarningThrall())
            && thrallTimer.getTicksRemaining() <= config.warningThreshold();

        boolean flashRed = warning &&
            (System.currentTimeMillis() / FLASH_INTERVAL_MS) % 2 == 0;

        // Background
        if (config.showBar())
        {
            // Flash duration bar
            graphics.setColor(flashRed && config.flashWarning() ? WARNING_COLOR : config.fillColor());
            graphics.fillRect(x, y, barWidth, barHeight);

            // Remaining duration
            int progressWidth = (int) (barWidth * thrallTimer.getProgress());

            graphics.setColor(getThrallColor());
            graphics.fillRect(x, y, progressWidth, barHeight);

            // Border
            if (config.showBorder())
            {
                graphics.setColor(config.borderColor());
                graphics.drawRect(x, y, barWidth, barHeight);
            }
        }

        // Thrall flash
        if (config.flashWarningThrall() && flashRed)
        {
            OverlayUtil.renderPolygon(graphics, thrall.getConvexHull(), WARNING_COLOR);
        }

        // Optional Timer
        if (config.showTimer())
        {
            int secondsRemaining = (int) Math.ceil(thrallTimer.getSecondsRemaining());
            String timerText = (secondsRemaining - 1) + "";

            FontMetrics metrics = graphics.getFontMetrics();

            graphics.setColor(warning && config.flashWarning() && !flashRed
                ? WARNING_COLOR : Color.WHITE);

            graphics.drawString(
                timerText,
                config.showBar() ? x + barWidth + 4 : canvasPoint.getX() - metrics.stringWidth(timerText) / 2,
                config.showBar() ? y + (barHeight + metrics.getAscent() - metrics.getDescent()) / 2 : y - 6
            );
        }

        return null;
    }

    private Color getThrallColor()
    {
        int summonedThrall = thrall.getId();

        if (Thralls.GHOSTS.contains(summonedThrall))
        {
            return config.mageColor();
        }

        if (Thralls.SKELETONS.contains(summonedThrall))
        {
            return config.rangeColor();
        }

        if (Thralls.ZOMBIES.contains(summonedThrall))
        {
            return config.meleeColor();
        }

        return Color.GREEN;
    }
}
