
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

### Information about the previous fork

This version of BossbarLib is maintained and updated for the arim repository. It started because
TigerHix's repo shutdown a while ago, and was causing some headaches for dependent plugin build processes.

The original BossbarLib contained some API mistakes. It was based on a singleton plugin
instance, permitting only 1 plugin to use it at a time. Also, there were exposed NMS internals.
for instance.

### Information about this fork

This version adds an additional value to the wither's DataWatcher, which causes its shield to be invisible.

Particles will still appear, theres not much that can be done about this since its hardcoded.

## The BossbarLib API

## Dependencies
<table>
<thead>
  <tr>
    <th class="tg-0pky">Project Ownership</th>
    <th class="tg-0pky">Diffuse's</th>
    <th class="tg-0pky">Arims</th>
    <th class="tg-0pky">Tigerhix's</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td class="tg-0pky">Dependency</td>
    <td class="tg-0pky">&lt;dependency&gt;<br>	&lt;groupId&gt;me.tigerhix.lib&lt;/groupId&gt;<br>	&lt;artifactId&gt;bossbar&lt;/artifactId&gt;<br>	&lt;version&gt;${INSERT_LATEST_VERSION}&lt;/version&gt;<br>&lt;/dependency&gt;</td>
    <td class="tg-0pky">&lt;dependency&gt;<br>	&lt;groupId&gt;me.tigerhix.lib&lt;/groupId&gt;<br>	&lt;artifactId&gt;bossbar&lt;/artifactId&gt;<br>	&lt;version&gt;arim-2.0.4&lt;/version&gt;<br>&lt;/dependency&gt;</td>
    <td class="tg-0pky">&lt;dependency&gt;<br>	&lt;groupId&gt;me.tigerhix.lib&lt;/groupId&gt;<br>	&lt;artifactId&gt;bossbar&lt;/artifactId&gt;<br>	&lt;version&gt;arim-1.0.2&lt;/version&gt;<br>&lt;/dependency&gt;</td>
  </tr>
  <tr>
    <td class="tg-0pky">Repository</td>
    <td class="tg-0pky">&lt;repository&gt;<br>	&lt;id&gt;bossbarlib-diffuse&lt;/id&gt;<br>	&lt;url&gt;https://raw.githubusercontent.com/DiffuseHyperion/BossbarLib/repository&lt;/url&gt;<br>&lt;/repository&gt;</td>
    <td class="tg-0pky">&lt;repository&gt;<br>	&lt;id&gt;arim-repo&lt;/id&gt;<br>	&lt;url&gt;https://www.arim.space/maven/&lt;/url&gt;<br>&lt;/repository&gt;</td>
    <td class="tg-0pky">&lt;repository&gt;<br>	&lt;id&gt;arim-repo&lt;/id&gt;<br>	&lt;url&gt;https://www.arim.space/maven/&lt;/url&gt;<br>&lt;/repository&gt;</td>
  </tr>
</tbody>
</table>

how do u put code blocks in html lmfao

You may find the latest version in the pom.xml of this project.

## Usage

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
* Version dependent (currently, it supports Spigot 1.8.8 only). Bad news, the default implementation of BossbarLib optimizes the wither entity by extending `EntityMonster`, a class in NMS package - which means you have to update BossbarLib every time when there is a Minecraft version update. The good news is, unless Mojang have developed new glitches for the bossbar again, making BossbarLib up-to-date usually wouldn't take so long.

