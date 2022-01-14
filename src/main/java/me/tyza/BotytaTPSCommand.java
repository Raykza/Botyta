package me.tyza;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

import java.util.List;

public class BotytaTPSCommand implements BotytaCommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {
        final MinecraftServer server = (Bukkit.getServer() instanceof CraftServer) ? ((CraftServer) Bukkit.getServer()).getServer() : null;

        assert server != null;
        String[] recentTPS = BotytaUtils.tpsToString(server.recentTps);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("\uD83E\uDD7E Recent TPS readings:");
        embed.setDescription(recentTPS[0]+"\n\n"+
                             recentTPS[1]+"\n\n"+
                             recentTPS[2]);
        embed.setColor(BotytaUtils.meanTPStoColor(server.recentTps));

        event.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Returns the most recent TPS readings from the Minecraft Server.\n"+
               "Usage: `" + Botyta.PREFIX + this.getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "tps";
    }
}
