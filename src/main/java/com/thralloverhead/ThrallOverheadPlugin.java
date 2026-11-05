
package com.thralloverhead;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
    name = "Thrall Overhead"
)
public class ThrallOverheadPlugin extends Plugin
{
    protected static final String RESURRECT_THRALL_MESSAGE_START = "You resurrect a ";
    protected static final String RESURRECT_THRALL_MESSAGE_END = " thrall.</col>";
    protected static final int LESSER_GHOST_ID = 10878;
    protected static final int SUPERIOR_GHOST_ID = 10879;
    protected static final int GREATER_GHOST_ID = 10880;
    protected static final int LESSER_SKELETON_ID= 10881;
    protected static final int SUPERIOR_SKELETON_ID= 10882;
    protected static final int GREATER_SKELETON_ID= 10883;
    protected static final int LESSER_ZOMBIE_ID = 10884;
    protected static final int SUPERIOR_ZOMBIE_ID = 10885;
    protected static final int GREATER_ZOMBIE_ID = 10886;

    @Inject
    private Client client;

    @Inject
    private ThrallOverheadConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ThrallOverheadOverlay overlay;

    private ThrallTimer thrallTimer;

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
        log.debug("Thrall Timer started!");
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);

        thrallTimer = null;
        overlay.setThrallTimer(null);
        overlay.setThrall(null);

        log.debug("Thrall Timer stopped!");
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        String message = event.getMessage();

        if (message.contains(RESURRECT_THRALL_MESSAGE_START)
            && message.endsWith(RESURRECT_THRALL_MESSAGE_END))
        {
            int duration = client.getBoostedSkillLevel(Skill.MAGIC);

            // Combat Achievement master tier doubles thrall duration.
            if (client.getVarbitValue(VarbitID.CA_TIER_STATUS_MASTER) == 2)
            {
                duration *= 2;
            }

            thrallTimer = new ThrallTimer(duration);
            overlay.setThrallTimer(thrallTimer);

            log.debug("Thrall summoned. Duration: {} ticks", duration);
        }
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (thrallTimer == null)
        {
            return;
        }

        thrallTimer.tick();

        if (thrallTimer.isExpired())
        {
            thrallTimer = null;
            overlay.setThrallTimer(null);
        }
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event)
    {
        NPC npc = event.getNpc();

        log.info("NPC spawned: {} | ID: {}", npc.getName(), npc.getId());

        if (isThrall(npc))
        {
            overlay.setThrall(npc);
            log.info("Thrall detected! ID: {}", npc.getId());
        }
    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned event)
    {
        if (event.getNpc() == overlay.getThrall())
        {
            overlay.setThrall(null);
            log.debug("Thrall despawned.");
        }
    }

    private boolean isThrall(NPC npc)
    {
        switch (npc.getId())
        {
            case LESSER_GHOST_ID:
            case SUPERIOR_GHOST_ID:
            case GREATER_GHOST_ID:
            case LESSER_SKELETON_ID:
            case SUPERIOR_SKELETON_ID:
            case GREATER_SKELETON_ID:
            case LESSER_ZOMBIE_ID:
            case SUPERIOR_ZOMBIE_ID:
            case GREATER_ZOMBIE_ID:
                return true;

            default:
                return false;
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() == GameState.LOGGED_IN)
        {
            log.debug("Thrall Timer logged in.");
        }
    }

    @Provides
    ThrallOverheadConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ThrallOverheadConfig.class);
    }
}
