package me.tigerhix.BossbarLib;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class WitherBossbarHandler implements BossbarLib {

    static {
        NMS.registerCustomEntity("WitherBoss", BossbarWither.class, 64);
    }

    private final Plugin plugin;
    private final Map<UUID, CraftWitherBossbar> spawnedWithers = new HashMap<>();

    WitherBossbarHandler(Plugin plugin, long delayInterval) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new BossbarListener(this), plugin);
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> plugin.getServer().getOnlinePlayers()
				.forEach(this::teleport), 0L, delayInterval);
    }

    void teleport(final Player player) {
        if (hasBossbar(player)) {
        	 plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> updateBossbar(player), 2L);
        }
    }

	private void updateBossbar(Player player) {
        CraftWitherBossbar bossbar = spawnedWithers.get(player.getUniqueId());
        if (bossbar != null) {
        	bossbar.update(player);
        }
    }

    private CraftWitherBossbar newBossbar(String message, float percentage) {
    	CraftWitherBossbar bossbar = new CraftWitherBossbar(message, null);
    	bossbar.setMessage(message);
    	bossbar.setPercentage(percentage);
        return bossbar;
    }

    @Override
	public void clearBossbar(Player player) {
        CraftWitherBossbar bossbar = spawnedWithers.remove(player.getUniqueId());
        if (bossbar == null || !bossbar.isSpawned() || bossbar.getDestroyPacket() == null) {
            return;
        }
        NMS.sendPacket(player, bossbar.getDestroyPacket());
    }

    @Override
	public WitherBossbar getBossbar(Player player) {
    	return spawnedWithers.computeIfAbsent(player.getUniqueId(), (uuid) -> newBossbar(ChatColor.BOLD + "", 1f));
    }

    @Override
	public boolean hasBossbar(Player player) {
        return spawnedWithers.containsKey(player.getUniqueId());
    }

}
