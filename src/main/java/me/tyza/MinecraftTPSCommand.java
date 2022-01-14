package me.tyza;

import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.SystemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.jetbrains.annotations.NotNull;

public class MinecraftTPSCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender,
                             @NotNull Command command,
                             @NotNull java.lang.String s,
                             @NotNull java.lang.String[] strings) {
        commandSender.sendMessage("Printing report...");
        final MinecraftServer server = (Bukkit.getServer() instanceof CraftServer) ? ((CraftServer) Bukkit.getServer()).getServer() : null;

        StringBuilder sb = new StringBuilder(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: ");

        assert server != null;
        for (double tps : server.recentTps)
        {
            sb.append(BotytaUtils.tpsToString(tps));
            sb.append(", ");
        }
        commandSender.sendMessage(server.getServerIp() +' '+ sb.substring(0, sb.length() - 2));
        return true;
    }
}
