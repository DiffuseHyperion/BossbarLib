package me.tigerhix.BossbarLib;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Bossbar API main class.
 * 
 * @author A248
 *
 */
public interface BossbarLib {

	/**
	 * Instantiates the library from a plugin instance. <br>
	 * Programmers only need 1 library instance. <br>
	 * <br>
	 * <code>20L</code> is used as the interval between automatic bossbar updates. <br>
	 * To specify this interval see {@link #createFor(Plugin, long)}.
	 * 
	 * @param instance the plugin to use
	 * @return a library instance
	 */
	static BossbarLib createFor(Plugin instance) {
	    return createFor(instance, 20L);
	}
	
	/**
	 * Same as {@link #createFor(Plugin)} but allows the programmer
	 * to specify a delay interval explicitly.
	 * 
	 * @param instance the plugin to use
	 * @param delayInterval the delay interval in milliseconds
	 * @return a library instance
	 */
	static BossbarLib createFor(Plugin instance, long delayInterval) {
		return new WitherBossbarHandler(instance, delayInterval);
	}
	
    /**
     * Returns the bossbar of a player. If the player does not have a bossbar, a new instance will be created and returned.
     *
     * @param player player
     * @return bossbar
     */
    Bossbar getBossbar(Player player);

    /**
     * Returns either the player already has a bossbar.
     *
     * @param player player
     * @return availability of bossbar
     */
    boolean hasBossbar(Player player);

    /**
     * Clear and remove the bossbar of the player.
     *
     * @param player player
     */
    void clearBossbar(Player player);

}
