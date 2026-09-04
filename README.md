# ModernCommandSyntax

<p>
  <img src="https://img.shields.io/badge/Version-1.0.0-blue.svg" alt="Version">
  <img src="https://img.shields.io/badge/CraftBukkit-1.2.5_R5.1--SNAPSHOT-green" alt="CraftBukkit">
  <img src="https://img.shields.io/badge/Java-8-orange.svg" alt="Java">
</p>

Brings modern Minecraft command syntax to Bukkit 1.2.5.

ModernCommandSyntax adds modern-style commands and conveniences to the classic Bukkit 1.2.5 experience while keeping compatibility with the original server and its mechanics.

## Features

### Modern `/time` command

Gets, sets or adds to the world time.

```text
/time
/time day
/time night
/time noon
/time midnight
/time <time>

/time set <time>
/time add <time>
```

The following shortcuts are available:

| Shortcut   | Time  |
|------------|-------|
| `day`      | 1000  |
| `noon`     | 6000  |
| `night`    | 13000 |
| `midnight` | 18000 |
| `d`        | 1000  |
| `n`        | 13000 |

`/time` without arguments displays the current world time.

### Modern `/weather` command

Changes the world weather.

```text
/weather clear
/weather rain
/weather thunder

/weather clear <duration>
/weather rain <duration>
/weather thunder <duration>
```

When no duration is specified, a random duration within Minecraft's vanilla ranges is generated:

| Weather   | Minimum Duration | Maximum Duration |
|-----------|------------------|------------------|
| `clear`   | 12000            | 180000           |
| `rain`    | 12000            | 24000            |
| `thunder` | 3600             | 15600            |

### Modern `/gamemode` command

Changes the game mode of a player.

```text
/gamemode survival
/gamemode survival Steve
/gamemode s Steve
/gamemode 0 Steve

/gamemode creative
/gamemode creative Steve
/gamemode c Steve
/gamemode 1 Steve
```

Supported game modes and aliases:

| Game mode  | Aliases  |
|------------|----------|
| `survival` | `s`, `0` |
| `creative` | `c`, `1` |

The player argument is optional when the command is executed by a player.

### CCC Integration

ModernCommandSyntax is compatible with [CCC](https://github.com/Bukkit-1-2-5-Revival/CCC) and can use its API to format command messages. When CCC is installed, ModernCommandSyntax uses CCC's configured `INFO`, `ERROR`, and `SUCCESS` message formatting. CCC is optional and ModernCommandSyntax remains fully functional without it.

## Development

> **Note:** The CraftBukkit server JAR is **not** included in this repository. You must provide your own copy of `craftbukkit-1.2.5-R5.1-SNAPSHOT.jar` before building the project.

Install the CraftBukkit 1.2.5 R5.1-SNAPSHOT JAR into your local Maven repository:

```bash
mvn install:install-file \
  -Dfile=/full/path/to/craftbukkit-1.2.5-R5.1-SNAPSHOT.jar \
  -DgroupId=org.bukkit \
  -DartifactId=craftbukkit \
  -Dversion=1.2.5-R5.1-SNAPSHOT \
  -Dpackaging=jar \
  -DgeneratePom=true
```

ModernCommandSyntax uses the CCC API for message formatting. A compiled CCC JAR is therefore required as a dependency when building ModernCommandSyntax. Create a `lib` folder in this project's source directory and place the CCC JAR inside it. The CCC JAR is not included in this repository and must be provided separately.

Then build the project:

```bash
mvn package
```

