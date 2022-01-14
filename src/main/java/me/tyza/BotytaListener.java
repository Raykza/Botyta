package me.tyza;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotytaListener extends ListenerAdapter implements EventListener {

    private final CommandManager commandManager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);


    public BotytaListener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        logger.info(String.format("Logged as %#s", event.getJDA().getSelfUser()));
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();
        String messageRaw = message.getContentRaw();

        if (event.isFromType(ChannelType.TEXT)) {

            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();

            logger.info(String.format("(%s)[%s]<%#s>: %s", guild.getName(), textChannel.getName(), author, content));
        }
        else if (event.isFromType(ChannelType.PRIVATE)) {
            logger.info(String.format("[PRIV]<%#s>: %s", author, content));
        }
    }

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        if(!event.getName().equals("ping")) return;
        long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true) // reply or acknowledge
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong! - %d ms", System.currentTimeMillis() - time) // then edit original
                ).queue(); // Queue both reply and edit
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        User author = event.getAuthor();
        Message message = event.getMessage();
        if(author.isBot() || message.isWebhookMessage()) return;

        String content = message.getContentDisplay();
        String messageRaw = message.getContentRaw();

        if(messageRaw.startsWith(Botyta.PREFIX)) {
            logger.info("[Botyta] Discord user issued command " + messageRaw);
            commandManager.handleCommand(event);
        }

    }

}
