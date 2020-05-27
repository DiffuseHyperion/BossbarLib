# BossbarLib
A simple, clean and performant bossbar library.

## Introduction

### Original description by TigerHix

**Another bossbar library? Seriously?**

Yes, there have been a lot of libraries utilizing the bossbar to display messages. However, none of them satisfies my need. BarAPI did well in pre-1.8, but not anymore in the latest versions; and the replacements I have found would either display streams of annoying smoke particles from places to places, or random flickering and disappearances that happens a lot.

**Why choose BossbarLib?**

* It works.
* Almost unobservable smoke particles.
* The bossbar wouldn't disappear unless you request to do so.
* Performant and no flickerings. Unlike some libraries, BossbarLib just wouldn't spawn a new entity every time a new message is displayed.
* Clean, object-oriented approach that a ten-year-old could understand.
* Can be used as a standalone plugin, or be shaded into your project.

### Information about this fork

This version of BossbarLib is maintained and updated for the arim repository. It started because
TigerHix's repo shutdown a while ago, and was causing some headaches for dependent plugin build processes.

The original BossbarLib contained some API mistakes. It was based on a singleton plugin
instance, permitting only 1 plugin to use it at a time. Also, there were exposed NMS internals.
for instance.

## The BossbarLib API

The new API permits multiple instances. It is used in production, so you can be more sure it is stable.

### Usage

**Getting an instance**
For your own plugin, you only need 1 instance. (If other plugins want to use BossbarLib, they'll use their own instances).
It's quite simple to instantiate. You just need a `JavaPlugin`.
```java
JavaPlugin plugin = //... ;
BossbarLib barlib = BossbarLib.createFor(plugin);
```
I recommend saving the library instance to a field variable into your `onEnable()`

**Using the library**

This assumes your library instance is called `barlib`.
```java
Player player = //... ;
Bossbar bar = barlib.getBossbar(player);
bar.setMessage("Look at this bossbar!");
bar.setPercentage(0.5f); // percentages must be between 0 and 1
```

There is no need to call any sort of `update` method because BossbarLib handles this for you.
Players with bossbars enabled will have them updated automatically on an interval.
To change this interval pass it when you create the library instance.
```java
JavaPlugin plugin = //... ;
long delayInterval = 20L; // 20L is the default if you don't specify the delay interval
BossbarLib barlib = BossbarLib.createFor(plugin, delayInterval);
```

### Dependency

```xml
<dependency>
	<groupId>me.tigerhix.lib</groupId>
	<artifactId>bossbar</artifactId>
	<version>${INSERT_LATEST_VERSION}</version>
</dependency>
```
You may find the latest version in the pom.xml of this project.

### Maven repo

```xml
<repository>
	<id>arim-repo</id>
	<url>https://www.arim.space/maven/</url>
</repository>
```

## If you want to use the old API

You may want to use the old BossbarLib API. It is unsupported, however, it builds and executes fine.

### Maven dependency

In this case, your dependency becomes:
```xml
<dependency>
	<groupId>me.tigerhix.lib</groupId>
	<artifactId>bossbar</artifactId>
	<version>arim-1.0.2</version>
</dependency>
```

Note the version. 1.x is for the old BossbarLib API. 2.x is for the new API.

The repository is still the same as before (arim-repo).

### Documentation for original API

First, you have to decide whether you use BossbarLib as a standalone plugin, or you just go shade it into your own plugin. For the latter case, you have to add following code to your onEnable():

```java
BossbarLib.setPluginInstance(this);
```

To let BossbarLib holds a reference to your plugin and hence able to schedule tasks, register events, etc.

Setting the bossbar of a player is simple:

```java
BossbarLib.getHandler().getBossbar(player).setMessage(ChatColor.BOLD + "I love cookies.").setPercentage(1f);
BossbarLib.getHandler().updateBossbar(player);
```

And of course, to clear the bossbar:

```java
BossbarLib.getHandler().clearBossbar(player);
```

Do note that, `getBossbar(Player)` will instantiate a bossbar for that player if the player does not have one. For determining whether or not a player has a bossbar displayed, use:

```java
BossbarLib.getHandler().hasBossbar(player);
```

instead.

## Implementation notes by TigerHix

**Is BossbarLib perfect?**
Unfortunately, not quite. Here are some worth-mentioning problems:
* Wither shields when the health is under 50%. This is completely client-side, and there are no ways to avoid it. There are some workarounds, though: you can make a custom resource pack with `entity/wither/wither.png`, `entity/wither/wither_armor.png`, ``entity/wither/invulnerable.png` set to a blank, transparent image, so that players wouldn't be able to see the goddamn wither even though it is rendered. The second workaround is easier: just never set the health of a bossbar under 50%. If you want to use the bossbar as a timer, make the message to indicate the time left instead. 
* Version dependent (currently, it supports Spigot 1.8.8 only). Bad news, the default implementation of BossbarLib optimizes the wither entity by extending `EntityMonster`, a class in NMS package - which means you have to update BossbarLib every time when there is a Minecraft version update. The good news is, unless Mojang have developed new glitches for the bossbar again, making BossbarLib up-to-date usually wouldn't take so long.

