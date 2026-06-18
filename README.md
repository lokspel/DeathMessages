# DeathMessages

A Paper plugin that recolorizes death, join, and quit messages with per-player toggles and blacklisting.

## Features

- **Custom colors** — Set separate MiniMessage colors for death messages (main text, player, killer, weapon), join messages, and quit messages.
- **Per-player toggle** — Each player can hide/show death messages from others or hide/show join/quit messages. Toggle controls what *you* see, not what others see of you.
- **Blacklist** — Blacklist a player so you no longer see their death messages. Others still see them, and the blacklisted player still sees their own death.
- **Global toggles** — Disable death, join, or quit messages globally via config.
- **Cooldown** — Per-player cooldown between death messages (configurable, default 10s).
- **Min-playtime** — Only show messages from players who have played for at least N minutes (configurable, 0 = disabled).
- **Folia support** — `folia-supported: true`.

## Commands

| Command | Aliases | Permission | Description |
|---------|---------|------------|-------------|
| `/deathmessages reload` | `/dm reload` | `deathmessages.reload` | Reload config and user data |
| `/deathmessages toggle` | `/dm toggle` | `deathmessages.toggle` | Toggle death messages on/off for yourself |
| `/deathmessages blacklist <player>` | `/dm blacklist <player>` | `deathmessages.blacklist` | Add/remove a player from the blacklist |
| `/toggleconnectionmsg` | `/togglejoins` | `deathmessages.toggleconnectionmsg` | Toggle join/quit messages on/off for yourself |
| `/deathmessagestoggle` | `/dmtoggle`, `/dmt` | `deathmessages.toggle` | Shortcut for toggling death messages |

## Permissions

| Node | Default | Description |
|------|---------|-------------|
| `deathmessages.use` | `true` | Allows using DeathMessages commands |
| `deathmessages.toggle` | `true` | Allows toggling death messages |
| `deathmessages.toggleconnectionmsg` | `true` | Allows toggling join/quit messages |
| `deathmessages.blacklist` | `true` | Allows blacklisting a player |
| `deathmessages.reload` | `op` | Allows reloading the plugin |

## Configuration

### `config.yml`

```yaml
Colors:
  Death:
    Main: "<dark_red>"
    Player: "<dark_aqua>"
    Killer: "<dark_aqua>"
    Weapon: "<gold>"
  Join:
    Main: "<gray>"
    Player: "<gray>"
  Quit:
    Main: "<gray>"
    Player: "<gray>"

Settings:
  Min-Playtime-Minutes: 0
  Death-Message-Cooldown-Seconds: 10
  Death-Messages-Enabled: true
  Join-Messages-Enabled: true
  Quit-Messages-Enabled: true

Commands:
  DeathMessages:
    Prefix: '&7[DeathMessages]&r '
    # ... messages ...
```

All colors use [MiniMessage format](https://docs.advntr.dev/minimessage/).

### `UserData.yml`

Stored in the plugin data folder. Automatically created when a player runs a toggle or blacklist command. Contains per-UUID data:
- `messages-enabled` (default: `true`)
- `connection-messages-enabled` (default: `true`)
- `is-blacklisted` (default: `false`)

## Building

Requires Java 21 and Maven.

```bash
mvn clean package
```

The output jar is `target/DeathMessages-1.0.jar`.

## Requirements

- Paper 1.26.1+ (api-version `26.1.2`)
- Java 21
