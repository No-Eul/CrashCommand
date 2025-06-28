# Crash Command 1.1.0

## Changes
### Commands
`crash`
* Changed 2nd command branch `client|server` to optional. ([`9a77880`](https://github.com/No-Eul/CrashCommand/commit/9a778805b892b0fa56117c7aecde362b76989ff1))
  * It will be executed depending on environment if no argument is specified.
  * If it's executed on client, regardless server host or player, with this mod, the result will be like `crash client`.
  * If it's executed on server console, or by server player has proper permission but without the mod, the result will be like `crash server`.

## Fixes
### From 1.0.0
* Game crashes when join singleplayer world or start server. ([`bb2a967`](https://github.com/No-Eul/CrashCommand/commit/bb2a967d1da996ce1c6c3f1b19f8c6415d93a0c1))
