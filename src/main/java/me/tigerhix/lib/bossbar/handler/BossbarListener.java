package me.tigerhix.lib.bossbar.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BossbarListener implements Listener {

	private final WitherBossbarHandler handler;
	
	BossbarListener(WitherBossbarHandler handler) {
		this.handler = handler;
	}
	
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onPlayerQuit(PlayerQuitEvent event) {
        handler.clearBossbar(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onPlayerTeleport(PlayerTeleportEvent event) {
        handler.teleport(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onPlayerRespawn(PlayerRespawnEvent event) {
        handler.teleport(event.getPlayer());
    }
	
}
