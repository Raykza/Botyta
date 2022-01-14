package me.tyza;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Emoji;
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
import java.util.concurrent.atomic.AtomicLong;

public class Botyta {
    public Main plugin;
    private JDA jda;
    private final ArrayList<TextChannel> botytaChannels;
    private final String[] botynames = {"botyta","ʙᴏᴛʏᴛᴀ","ᵇᵒᵗʸᵗᵃ","ｂｏｔｙｔａ"};
    private final String[] botystrings;
    public static final String PREFIX = "é Botyta"+" ";
    private CommandManager commandManager;
    private BotytaListener botytaListener;

    /* Instantiate main plugin, start bot. */
    public Botyta(Main plugin, String key, String[] strings) {
        this.plugin = plugin;
        this.botytaChannels = new ArrayList<TextChannel>();
        this.botystrings = strings;
        this.commandManager = new CommandManager();
        this.botytaListener = new BotytaListener(this.commandManager);
        this.startBot(key);
    }

    /* Initialize the bot. Wait until it's done. Send message to guilds */
    private void startBot(String key) {
        /* Create bot with apikey */
        try {
            JDABuilder builder;
            builder = JDABuilder.createDefault(key);
            builder.addEventListeners(botytaListener);
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
        this.jda.shutdown();
    }

    public void sendToBotytaChannel(String msg) {
        for(TextChannel t : botytaChannels) {
            t.sendMessage(msg).queue();
        }
    }

}
