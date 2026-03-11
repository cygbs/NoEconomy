package net.ypixel.noeconomy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class NoEconomy extends JavaPlugin implements Economy {

    private boolean debugMode = false;

    // 辅助方法：获取调用插件的名称并打印日志（仅在debug模式下）
    private void logCall(String methodName, Object... args) {
        if (!debugMode) return; // 非调试模式直接返回

        String pluginName = getCallingPlugin();
        StringBuilder argStr = new StringBuilder();
        for (Object arg : args) {
            if (argStr.length() > 0) argStr.append(", ");
            argStr.append(arg == null ? "null" : arg.toString());
        }
        getLogger().info("<" + pluginName + "> called " + methodName + "(" + argStr + ")");
    }

    // 辅助方法：从堆栈中分析出真正的调用插件
    private String getCallingPlugin() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            String className = element.getClassName();
            // 跳过自身类
            if (className.startsWith("net.ypixel.noeconomy.")) {
                continue;
            }
            // 跳过 Vault 内部类（中介）
            if (className.startsWith("net.milkbowl.vault.")) {
                continue;
            }
            try {
                Class<?> clazz = Class.forName(className);
                // 使用 JavaPlugin 提供的静态方法获取加载该类的插件
                JavaPlugin plugin = JavaPlugin.getProvidingPlugin(clazz);
                if (plugin != null) {
                    return plugin.getName();
                }
            } catch (ClassNotFoundException | IllegalArgumentException e) {
                // 忽略无法加载的类或非插件类
            }
        }
        return "Unknown";
    }

    @Override
    public void onEnable() {
        // 保存默认配置
        saveDefaultConfig();
        // 加载配置文件
        FileConfiguration config = getConfig();
        debugMode = config.getBoolean("debug", false); // 默认false

        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe("Vault not found! Disabling.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getServicesManager().register(Economy.class, this, this, ServicePriority.Highest);
        getLogger().info("NoEconomy enabled. All players now have infinite money!");
        if (debugMode) {
            getLogger().info("Debug mode is enabled.");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getServicesManager().unregister(Economy.class, this);
        getLogger().info("NoEconomy disabled.");
    }

    // ==================== 基础信息 ====================
    @Override
    public boolean hasBankSupport() {
        logCall("hasBankSupport");
        return false;
    }

    @Override
    public int fractionalDigits() {
        logCall("fractionalDigits");
        return -1;
    }

    @Override
    public String format(double amount) {
        logCall("format", amount);
        if (amount == Double.MAX_VALUE) {
            return "infinite";
        }
        return String.format("%.2f", amount);
    }

    @Override
    public String currencyNamePlural() {
        logCall("currencyNamePlural");
        return "dollars";
    }

    @Override
    public String currencyNameSingular() {
        logCall("currencyNameSingular");
        return "dollar";
    }

    // ==================== 账户管理 (String) ====================
    @Override
    public boolean hasAccount(String playerName) {
        logCall("hasAccount", playerName);
        return true;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        logCall("hasAccount", playerName, worldName);
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        logCall("createPlayerAccount", playerName);
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        logCall("createPlayerAccount", playerName, worldName);
        return true;
    }

    // ==================== 账户管理 (OfflinePlayer) ====================
    @Override
    public boolean hasAccount(OfflinePlayer player) {
        logCall("hasAccount", player.getName());
        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        logCall("hasAccount", player.getName(), worldName);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        logCall("createPlayerAccount", player.getName());
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        logCall("createPlayerAccount", player.getName(), worldName);
        return true;
    }

    // ==================== 余额查询 (String) ====================
    @Override
    public double getBalance(String playerName) {
        logCall("getBalance", playerName);
        return Double.MAX_VALUE;
    }

    @Override
    public double getBalance(String playerName, String world) {
        logCall("getBalance", playerName, world);
        return getBalance(playerName);
    }

    @Override
    public boolean has(String playerName, double amount) {
        logCall("has", playerName, amount);
        return true;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        logCall("has", playerName, worldName, amount);
        return true;
    }

    // ==================== 余额查询 (OfflinePlayer) ====================
    @Override
    public double getBalance(OfflinePlayer player) {
        logCall("getBalance", player.getName());
        return Double.MAX_VALUE;
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        logCall("getBalance", player.getName(), world);
        return Double.MAX_VALUE;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        logCall("has", player.getName(), amount);
        return true;
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        logCall("has", player.getName(), worldName, amount);
        return true;
    }

    // ==================== 交易操作 (String) ====================
    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        logCall("withdrawPlayer", playerName, amount);
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        logCall("withdrawPlayer", playerName, worldName, amount);
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        logCall("depositPlayer", playerName, amount);
        return new EconomyResponse(amount, getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        logCall("depositPlayer", playerName, worldName, amount);
        return depositPlayer(playerName, amount);
    }

    // ==================== 交易操作 (OfflinePlayer) ====================
    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        logCall("withdrawPlayer", player.getName(), amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        logCall("withdrawPlayer", player.getName(), worldName, amount);
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        logCall("depositPlayer", player.getName(), amount);
        return new EconomyResponse(amount, getBalance(player), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        logCall("depositPlayer", player.getName(), worldName, amount);
        return depositPlayer(player, amount);
    }

    // ==================== 银行相关 (不支持) ====================
    @Override
    public EconomyResponse createBank(String name, String player) {
        logCall("createBank", name, player);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        logCall("deleteBank", name);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        logCall("bankBalance", name);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        logCall("bankHas", name, amount);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        logCall("bankWithdraw", name, amount);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        logCall("bankDeposit", name, amount);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        logCall("isBankOwner", name, playerName);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        logCall("isBankMember", name, playerName);
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        logCall("createBank", name, player.getName());
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        logCall("isBankOwner", name, player.getName());
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        logCall("isBankMember", name, player.getName());
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks not supported");
    }

    @Override
    public List<String> getBanks() {
        logCall("getBanks");
        return new ArrayList<>();
    }
}
