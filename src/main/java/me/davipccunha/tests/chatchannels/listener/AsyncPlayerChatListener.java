package me.davipccunha.tests.chatchannels.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        final Player player = event.getPlayer();
        final String message = event.getMessage();
        if (player == null || message == null) return;

        Bukkit.dispatchCommand(player, "local " + message);
    }
}
