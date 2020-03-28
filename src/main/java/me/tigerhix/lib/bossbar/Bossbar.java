package me.tigerhix.lib.bossbar;

public interface Bossbar {

    /**
     * Returns the message. The message won't have any '§' colour codes because they will have been replaced.
     *
     * @return message
     */
    String getMessage();

    /**
     * Set the message. Formatting codes are automatically translated from '{@literal &}' colour codes.
     *
     * @param message message
     * @return this
     */
    void setMessage(String message);

    /**
     * Returns the amount of health in a percentage of [0~1]. 0 is the minimum value, while 1 is the maximum.
     *
     * @return percentage
     */
    float getPercentage();

    /**
     * Set the amount of health in a percentage of [0~1]. 0 is the minimum value, while 1 is the maximum.
     *
     * @param percentage percentage
     * @return this
     */
    void setPercentage(float percentage);

}
