
package com.thralloverhead;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.NPC;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class ThrallOverheadOverlay extends Overlay
{
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color BORDER_COLOR = Color.WHITE;

    private static final Color GHOST_COLOR = new Color(32, 181, 191);
    private static final Color SKELETON_COLOR = new Color(200, 220, 88);
    private static final Color ZOMBIE_COLOR = new Color(180, 40, 40);

    private static final Color WARNING_COLOR = Color.RED;

    private static final long FLASH_INTERVAL_MS = 200;

    private static final List<Integer> GHOST_IDS = List.of(10878, 10879, 10880);
    private static final List<Integer> SKELETON_IDS = List.of(10881, 10882, 10883);
    private static final List<Integer> ZOMBIE_IDS = List.of(10884, 10885, 10886);

    private final ThrallOverheadConfig config;

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
        if (!config.showTimer() || thrall == null || thrallTimer == null || thrallTimer.isExpired())
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
        boolean warning = config.flashWarning()
            && thrallTimer.getTicksRemaining() <= config.warningThreshold();

        boolean flashRed = warning &&
            (System.currentTimeMillis() / FLASH_INTERVAL_MS) % 2 == 0;

        // Background
        graphics.setColor(flashRed ? WARNING_COLOR : BACKGROUND_COLOR);
        graphics.fillRect(x, y, barWidth, barHeight);

        // Remaining duration
        int progressWidth = (int) (barWidth * thrallTimer.getProgress());

        graphics.setColor(getThrallColor());
        graphics.fillRect(x, y, progressWidth, barHeight);

        // Border
        if (config.showBorder())
        {
            graphics.setColor(BORDER_COLOR);
            graphics.drawRect(x, y, barWidth, barHeight);
        }
        return null;
    }

    private Color getThrallColor()
    {
        int summonedThrall = thrall.getId();

        if (GHOST_IDS.contains(summonedThrall))
        {
            return GHOST_COLOR;
        }

        if (SKELETON_IDS.contains(summonedThrall))
        {
            return SKELETON_COLOR;
        }

        if (ZOMBIE_IDS.contains(summonedThrall))
        {
            return ZOMBIE_COLOR;
        }

        return Color.GREEN;
    }
}
