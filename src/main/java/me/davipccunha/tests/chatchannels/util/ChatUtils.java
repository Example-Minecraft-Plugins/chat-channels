package me.davipccunha.tests.chatchannels.util;

import me.davipccunha.tests.moderation.api.ModerationAPI;
import me.davipccunha.tests.moderation.api.model.Mute;
import me.davipccunha.utils.player.PermissionUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static String applyColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String formatGlobalMessage(CommandSender sender, String message, boolean applyColors) {
        final String prefix = sender instanceof Player ? PermissionUtils.getLuckPermsPrefix((Player) sender) : "§0";
        final String playerName = sender.getName();

        String formattedMessage = String.format("§7[%s] %s§3: §7%s", "G", prefix + playerName, message);
        return applyColors ? applyColor(formattedMessage) : formattedMessage;
    }

    public static String formatLocalMessage(Player sender, String message, boolean applyColors) {
        final String prefix = PermissionUtils.getLuckPermsPrefix(sender);
        final String playerName = sender.getName();

        String formattedMessage = String.format("§e[%s] %s§2: §e%s", "L", prefix + playerName, message);
        return applyColors ? applyColor(formattedMessage) : formattedMessage;
    }

    public static String formatTellMessage(CommandSender sender, String message, boolean sending) {
        final String playerName = sender.getName();

        return sending ?
                String.format("§8[DM] [Você §7➤ §8%s]: §9%s", playerName, message) :
                String.format("§8[DM] [%s §7➤ §8Você]: §9%s", playerName, message);
    }

    public static String formatStaffChatMessage(CommandSender sender, String message) {
        final String playerName = sender.getName();

        return String.format("§5[SC] %s: §d%s", playerName, message);
    }

    public static boolean checkMute(ModerationAPI api, Player player) {
        if (api == null) return false;

        final Mute mute = api.getMute(player.getName().toLowerCase());
        if (mute != null) {
            final boolean isExpired = System.currentTimeMillis() >= mute.getExpiresAt();

            if (isExpired) {
                api.unmute(player.getName().toLowerCase(), "CONSOLE");
            } else {
                final String mutedMessage = mute.getRestrictionMessage();
                player.sendMessage(mutedMessage);

                return true;
            }
        }

        return false;
    }
}
