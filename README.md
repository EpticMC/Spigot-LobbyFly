# Spigot-LobbyFly
Spigot Plugin to allow flight on a Hub-/Lobbyserver when a specific item is held

This Spigot Plugin enables fly for a player as long as he holds an item. 

This plugin is tested on 1.8.X. It will enable fly for a player as long as he holds 
an specific item which is set in the config (Default: Netherstar)

If the Item ID is invalid the plugin uses 399 (NetherStar) instead without changing the config. <br>
Also users with permission `lobbyfly.admin` and OPs get a notification that the ID is invalid

If a user is OP, in Gamemode 1, or has the permission `lobbyfly.bypass` his fly won't be affected by the plugin

---
### Features:
- Specify item in config
- prevent errors on invalid items
- command support for `/lfly` and `/lobbyfly`
- help menu on `/lfly` and `/lfly help`
- option to change the prefix of the plugin (with color support)
- command to deny users (`/lfly deny (username)`)
- command to undeny users (`/lfly undeny (username)`)
- command to list denied users (`/lfly denylist`)
- command to see if a user is denied (`/lfly status (username)`)
- permission node `lobbyfly.bypass` for deactivating the plugin
- permission node `lobbyfly.admin` for getting notifications on errors and using the plugin
