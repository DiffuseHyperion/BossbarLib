package me.tigerhix.lib.bossbar;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class WitherBossbarHandler implements BossbarLib {

    static {
        NMS.registerCustomEntity("WitherBoss", BossbarWither.class, 64);
    }

    private final Plugin plugin;
    private final Map<UUID, WitherBossbar> spawnedWithers = new HashMap<>();

    public WitherBossbarHandler(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new BossbarListener(this), plugin);
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> plugin.getServer().getOnlinePlayers()
				.forEach(this::teleport), 0L, 20L);
    }

    void teleport(final Player player) {
        if (hasBossbar(player)) {
        	 plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> updateBossbar(player), 2L);
        }
    }

    private WitherBossbar newBossbar() {
        return newBossbar(ChatColor.BOLD + "", 1f);
    }

    private WitherBossbar newBossbar(String message, float percentage) {
        return new CraftWitherBossbar(message, null).setMessage(message).setPercentage(percentage);
    }

    @Override
	public void clearBossbar(Player player) {
        WitherBossbar bossbar = spawnedWithers.remove(player.getUniqueId());
        if (bossbar == null || !bossbar.isSpawned() || bossbar.getDestroyPacket() == null) {
            return;
        }
        NMS.sendPacket(player, bossbar.getDestroyPacket());
    }

    @Override
	public WitherBossbar getBossbar(Player player) {
    	return spawnedWithers.computeIfAbsent(player.getUniqueId(), (uuid) -> newBossbar());
    }

    @Override
	public boolean hasBossbar(Player player) {
        return spawnedWithers.containsKey(player.getUniqueId());
    }

    @Override
	public void updateBossbar(Player player) {
        WitherBossbar bossbar = spawnedWithers.get(player.getUniqueId());
        if (bossbar == null) {
            return;
        }
        if (!bossbar.isSpawned()) {
            bossbar.setSpawned(true);
            bossbar.setSpawnLocation(player.getLocation().add(player.getEyeLocation().getDirection().multiply(20)));
            NMS.sendPacket(player, bossbar.getSpawnPacket());
        }
        NMS.sendPacket(player, bossbar.getMetaPacket(bossbar.getWatcher()));
        NMS.sendPacket(player, bossbar.getTeleportPacket(player.getLocation().add(player.getEyeLocation().getDirection().multiply(20))));
    }

}
