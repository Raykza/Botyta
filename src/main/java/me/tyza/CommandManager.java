package me.tyza;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, BotytaCommand> commands = new HashMap<>();

    CommandManager() {
        addCommand(new BotytaPingCommand());
        addCommand(new BotytaTPSCommand());
        addCommand(new BotytaHelpCommand(this));
    }

    private void addCommand(BotytaCommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<BotytaCommand> getCommands() {
        return commands.values();
    }

    public BotytaCommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    void handleCommand(GuildMessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Botyta.PREFIX), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(event, args);
        } else {
            // TODO: Log "unknown issued command" here
        }
    }
}