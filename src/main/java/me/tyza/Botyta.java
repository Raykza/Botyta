package me.tyza;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Botyta extends ListenerAdapter implements Listener {
    public Main plugin;
    private JDA jda;
    private final ArrayList<TextChannel> botytaChannels;
    private final String[] botynames = {"botyta","ʙᴏᴛʏᴛᴀ","ᵇᵒᵗʸᵗᵃ","ｂｏｔｙｔａ"};
    private final String[] botystrings;
    private final String prefix = "é Botyta"+" ";
    private ConsoleCommandSender console = null;

    /* Instantiate main plugin, start bot. */
    public Botyta(Main plugin, String key, String[] strings) {
        this.plugin = plugin;
        this.botytaChannels = new ArrayList<TextChannel>();
        this.botystrings = strings;
        this.startBot(key);
    }

    /* Initialize the bot. Wait until it's done. Send message to guilds */
    private void startBot(String key) {
        /* Create bot with apikey */
        try {
            JDABuilder builder;
            builder = JDABuilder.createDefault(key);
            builder.addEventListeners(this);
            builder.setActivity(Activity.listening("é Botyta"));

            jda = builder.build();
            jda.upsertCommand("ping","Will return Pong! with the milisecond delay of the bot").queue();
        } catch (LoginException logex) {
            logex.printStackTrace();
        }
          catch(java.lang.NullPointerException nullex) {
            System.err.println("JDABuilder probably couldn't build. Is the API Key valid?");
        }

        /* Wait until all threads are done */
        try {
            jda.awaitReady();
        } catch (InterruptedException intex) {
            intex.printStackTrace();
        }

        /* Populate botytaChannels and send a message to those channels */
        for(Guild g : this.jda.getGuilds()) {
            for (TextChannel t : g.getTextChannels()) {
                if (BotytaUtils.stringContainsItemFromList(t.getName(),botynames)) {
                    botytaChannels.add(t);
                    t.sendMessage(botystrings[0]).queue(); // Botyta Activado
                }
            }
        }
    }

    public void disableBot() {
        this.sendToBotytaChannel(botystrings[1]); // Botyta desactivado
    }

    public void sendToBotytaChannel(String msg) {
        for(TextChannel t : botytaChannels) {
            t.sendMessage(msg).queue();
        }
    }

    public void setConsoleCommandSender(ConsoleCommandSender consoleCommandSender) {
        this.console = consoleCommandSender;
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
        OffsetDateTime then = OffsetDateTime.now();
        String messageRaw = event.getMessage().getContentRaw();

        if(messageRaw.startsWith(prefix)) {
            String[] args = messageRaw.substring(prefix.length(), messageRaw.length()).split(" ");
            System.out.println("[Botyta] Discord user issued command " + messageRaw);
            System.out.println("[Botyta] Parsed arguments: " + Arrays.toString(args));

            String command = args[0];

            if (command.isEmpty()) return;
            else {
                switch (command) {

                    case "tps":
                        break;

                    case "ping":
                        event.getMessage().reply("Pong! - " + OffsetDateTime.now().compareTo(then) + " ms").queue();
                        break;



                    default:
                        System.out.println("[Botyta] Issued unknown command " + args[0]);
                        break;
                }
            }
        }

    }
}
