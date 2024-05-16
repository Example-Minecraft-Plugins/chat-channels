package me.davipccunha.tests.chatchannels;

import lombok.Getter;
import me.davipccunha.tests.chatchannels.cache.LastTellCache;
import me.davipccunha.tests.chatchannels.command.GlobalCommand;
import me.davipccunha.tests.chatchannels.command.LocalCommand;
import me.davipccunha.tests.chatchannels.command.ReplyCommand;
import me.davipccunha.tests.chatchannels.command.TellCommand;
import me.davipccunha.tests.chatchannels.listener.AsyncPlayerChatListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ChatChannelsPlugin extends JavaPlugin {
    private final LastTellCache lastTellCache = new LastTellCache();

    @Override
    public void onEnable() {
        this.init();
        getLogger().info("Chat plugin loaded!");
    }

    public void onDisable() {
        getLogger().info("Chat plugin unloaded!");
    }

    private void init() {
        saveDefaultConfig();
        this.registerListeners(
            new AsyncPlayerChatListener()
        );
        this.registerCommands();
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pluginManager = getServer().getPluginManager();
        for (Listener listener : listeners) pluginManager.registerEvents(listener, this);
    }

    private void registerCommands() {
        getCommand("global").setExecutor(new GlobalCommand());
        getCommand("local").setExecutor(new LocalCommand(getConfig().getInt("local-radius")));
        getCommand("responder").setExecutor(new ReplyCommand(this.lastTellCache));
        getCommand("tell").setExecutor(new TellCommand(this.lastTellCache));
    }
}
