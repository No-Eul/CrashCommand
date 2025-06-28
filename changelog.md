# Crash Command 1.1.0

## Changes
### Commands
`crash`
* Changed 2nd command branch `client|server` to optional. ([`9a7c5dc`](https://github.com/No-Eul/CrashCommand/commit/9a7c5dc59b28b16fa6e8d0e17487f87aa6d3b312))
  * It will be executed depending on environment if no argument is specified.
  * If it's executed on client, regardless server host or player, with this mod, the result will be like `crash client`.
  * If it's executed on server console, or by server player has proper permission but without the mod, the result will be like `crash server`.

## Fixes
### From 1.0.0
* Game crashes when join singleplayer world or start server. ([`fbb1f66`](https://github.com/No-Eul/CrashCommand/commit/fbb1f664cf13d5ba5e97686e8b1ef7368603410d))
