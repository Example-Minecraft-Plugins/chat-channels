package me.davipccunha.tests.chatchannels.command;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.chatchannels.util.ChatUtils;
import me.davipccunha.utils.messages.ErrorMessages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class LocalCommand implements CommandExecutor {
    private final int radius;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ErrorMessages.EXECUTOR_NOT_PLAYER.getMessage());
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUso: /local <mensagem>");
            return false;
        }

        final Player player = (Player) sender;
        final Set<Player> nearbyPlayers = this.getNearbyPlayers(player);

        if (nearbyPlayers.isEmpty()) {
            player.sendMessage("§cNão há jogadores próximos para ver sua mensagem.");
            return true;
        }

        final String message = String.join(" ", args);
        final boolean colorPermission = player.hasPermission("chat.messages.color");

        final String formattedMessage = ChatUtils.formatLocalMessage(player, message, colorPermission);

        final boolean highlightPermission = player.hasPermission("chat.messages.highlight");

        for (Player player_ : nearbyPlayers) {
            if (highlightPermission) player_.sendMessage(" ");
            player_.sendMessage(formattedMessage);
            if (highlightPermission) player_.sendMessage(" ");
        }

        return true;
    }

    private Set<Player> getNearbyPlayers(Player player) {
        Stream<Player> nearbyPlayers = player.getWorld().getNearbyEntities(
                        player.getLocation(), this.radius, this.radius, this.radius
                ).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity);

        return nearbyPlayers.collect(Collectors.toSet());
    }
}
