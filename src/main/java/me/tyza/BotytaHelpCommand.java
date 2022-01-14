package me.tyza;

import me.tyza.Botyta;
import me.tyza.BotytaCommand;


import me.tyza.BotytaCommand;
import me.tyza.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class BotytaHelpCommand implements BotytaCommand {

    private final CommandManager commandManager;

    public BotytaHelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        if (args.isEmpty()) {
            generateAndSendEmbed(event);
            return;
        }

        String joined = String.join("", args);

        BotytaCommand command = commandManager.getCommand(joined);

        if (command == null) {
            event.getChannel().sendMessage("The command `" + joined + "` does not exist\n" +
                    "Use `" + Botyta.PREFIX + getInvoke() + "` for a list of commands").queue();
            return;
        }

        String message = "Command help for `" + command.getInvoke() + "`\n" + command.getHelp();

        event.getChannel().sendMessage(message).queue();
    }

    private void generateAndSendEmbed(GuildMessageReceivedEvent event) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("\uD83E\uDD7E All my commands:");
        builder.setColor(0xFF7F00);
        builder.setFooter("For more info see https://github.com/Raykza/Botyta");

        StringBuilder descriptionBuilder = builder.getDescriptionBuilder();

        commandManager.getCommands().forEach(
                (command) -> descriptionBuilder.append('`').append(command.getInvoke()).append("`\n")
        );

        event.getChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Shows a list of all the commands.\n" +
                "Usage: `" + Botyta.PREFIX + getInvoke() + " [command]`";
    }

    @Override
    public String getInvoke() {
        return "help";
    }
}