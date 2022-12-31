package me.tigerhix.lib.bossbar;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public abstract class WitherBossbar implements Bossbar {

    protected static final float MAX_HEALTH = 300;

    protected boolean spawned;
    protected Location spawnLocation;

    protected String name;
    protected float health;

    public WitherBossbar(String message, Location spawnLocation) {
        this.spawnLocation = spawnLocation;
        this.name = message;
        this.health = MAX_HEALTH;
    }

    @Override
    public String getMessage() {
        return name;
    }

    @Override
    public void setMessage(String message) {
        this.name = ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public float getPercentage() {
        return health / MAX_HEALTH;
    }

    @Override
    public void setPercentage(float percentage) {
        percentage = Maths.clamp(percentage, 0f, 1f);
        if (percentage * MAX_HEALTH == 0) {
            health = 1;
        } else {
            health = percentage * MAX_HEALTH;
        }
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

}
