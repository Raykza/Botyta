package me.tyza;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public interface BotytaCommand {
    void handle(GuildMessageReceivedEvent event, List<String> args);
    String getHelp();
    String getInvoke();
}
