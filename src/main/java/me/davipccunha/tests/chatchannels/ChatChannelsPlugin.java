package me.davipccunha.tests.chatchannels;

import lombok.Getter;
import me.davipccunha.tests.chatchannels.cache.LastTellCache;
import me.davipccunha.tests.chatchannels.command.GlobalCommand;
import me.davipccunha.tests.chatchannels.command.LocalCommand;
import me.davipccunha.tests.chatchannels.command.ReplyCommand;
import me.davipccunha.tests.chatchannels.command.TellCommand;
import me.davipccunha.tests.chatchannels.listener.AsyncPlayerChatListener;
import me.davipccunha.tests.moderation.api.ModerationAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ChatChannelsPlugin extends JavaPlugin {
    private final LastTellCache lastTellCache = new LastTellCache();
    private ModerationAPI moderationAPI;

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

        this.moderationAPI = Bukkit.getServicesManager().load(ModerationAPI.class);

        if (this.moderationAPI == null)
            Bukkit.getLogger().warning("ModerationAPI was not correctly loaded. Mute checks will not be performed.");
    }

    private void registerListeners(Listener... listeners) {
        PluginManager pluginManager = getServer().getPluginManager();
        for (Listener listener : listeners) pluginManager.registerEvents(listener, this);
    }

    private void registerCommands() {
        getCommand("global").setExecutor(new GlobalCommand(this));
        getCommand("local").setExecutor(new LocalCommand(this));
        getCommand("responder").setExecutor(new ReplyCommand(this.lastTellCache));
        getCommand("tell").setExecutor(new TellCommand(this.lastTellCache));
    }
}
