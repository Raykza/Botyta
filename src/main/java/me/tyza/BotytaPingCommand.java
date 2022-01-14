package me.tyza;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class BotytaPingCommand implements BotytaCommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {
        event.getJDA().getRestPing().queue(time -> {
            long gatePing = event.getJDA().getGatewayPing();
            long restPing = time;

            String rest_emoji = BotytaUtils.pingToEmoji(restPing);
            String gate_emoji = BotytaUtils.pingToEmoji(gatePing);

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("\uD83E\uDD7E Pong!");
            embed.setDescription(rest_emoji+" REST ping: "+restPing+"ms\n\n" +
                    gate_emoji+" Gateway ping: "+gatePing+"ms");
            embed.setColor(BotytaUtils.pingToColor((restPing + gatePing) / 2L));

            event.getChannel().sendMessage(embed.build()).queue();
        });
    }

    @Override
    public String getHelp() {
        return "Pong!\n"+
                "Usage: `" + Botyta.PREFIX + this.getInvoke() + "`";
    }

    @Override
    public String getInvoke() {
        return "ping";
    }
}
