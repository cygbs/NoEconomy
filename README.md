# NoEconomy

[![License](https://img.shields.io/github/license/cygbs/NoEconomy)](LICENSE)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.13--1.21-blue)](https://www.spigotmc.org/)
[![Vault](https://img.shields.io/badge/Vault-Required-yellow)](https://www.spigotmc.org/resources/vault.34315/)

**NoEconomy** is a lightweight Vault economy provider that gives every player an infinite balance (`Double.MAX_VALUE`). It allows any plugin depending on Vault's economy API to function without actual money management – perfect for creative servers, private friend servers, or testing environments where you don't want to worry about configuring a real economy.

## How It Works

NoEconomy registers itself with Vault as the active economy provider. Whenever a plugin (like a shop, auction house, or quest plugin) checks a player's balance, NoEconomy returns `Double.MAX_VALUE`. All withdrawal and deposit requests are instantly marked as successful, but the player's balance never actually changes. This effectively makes all purchases, sell transactions, 和 economy-related actions always possible.

## Features

- **Zero configuration** – Just drop the jar into your plugins folder.
- **Always returns `Double.MAX_VALUE`** – Players have more money than any plugin could ever ask for.
- **All transactions succeed** – No need to worry about insufficient funds.
- **Lightweight** – No database, no files, no overhead.
- **Vault-compatible** – Works with any plugin that uses Vault's economy API.

## Installation

1. Make sure you have **Vault** installed on your server. ([Download Vault](https://www.spigotmc.org/resources/vault.34315/))
2. Download the latest NoEconomy jar from the [Releases](https://github.com/cygbs/NoEconomy/releases) page.
3. Place the jar into your server's `plugins/` folder.
4. Restart your server (or use a plugin manager to load it).
5. NoEconomy will automatically register itself with Vault. No further steps needed!

## Configuration

NoEconomy does **not** require any configuration. It works out of the box.  
*(If you wish to change the fake balance value in the future, a config option may be added. Feel free to open an issue if you need this feature.)*

## Compatibility

NoEconomy is designed to be as compatible as possible. It has been tested with:

- Spigot / Paper 1.13 – 1.21
- Vault 1.7.x
- Popular plugins like EssentialsX, ChestShop, QuickShop, and others.

However, because NoEconomy simulates infinite money without actually changing balances, **some plugins may behave unexpectedly** if they:

- Rely on balance changes to trigger events (e.g., ranking based on money).
- Perform double-checks by reading the balance after a transaction.
- Expect balances to decrease over time.

If you encounter issues with a specific plugin, please [open an issue](https://github.com/cygbs/NoEconomy/issues) and describe the problem.

## Important Notes / Known Limitations

- **No actual money tracking** – Since no money is ever deducted or added, any plugin that depends on a player's accumulated wealth (like an economy-based leaderboard) will not work correctly.
- **Potential arithmetic overflow** – Some plugins might attempt to add to a player's balance, resulting in `Double.MAX_VALUE + 1` which becomes `Infinity`. While rare, this could cause errors in those plugins.
- **Not for survival or trading servers** – If you want a real economy with player-driven markets, this plugin is **not** for you. Use it only when you want to remove economic barriers entirely.

## Building from Source

NoEconomy uses Maven. To build the plugin yourself:

```bash
git clone https://github.com/cygbs/NoEconomy.git
cd NoEconomy
mvn clean package
```

The compiled jar will be in the `target/` directory.

## License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! If you have ideas for improvements or find a bug, please open an issue or submit a pull request.

---

**NoEconomy – because sometimes you just want to skip the economy.**
```
