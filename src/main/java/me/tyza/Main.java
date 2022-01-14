/* This is the Main class of the Botyta plugin.
 * It's important that we have separate Main and a proper Botyta class,
 * as we need JavaPlugin and org.bukkit.event.Listener, to control Minecraft stuff in the plugin,
 * but we need to extend ListenerAdapter for managing stuff on Discord over JDA.
 *
 * This implementation of class composition takes care of both Listeners.
 * */

package me.tyza;

import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class Main extends JavaPlugin implements Listener {

    public Botyta botyta;
    public ConsoleCommandSender console;
    public ProtocolManager protocolManager;
    public PropertiesManager propertiesManager;


    @Override
    public void onEnable() {
        propertiesManager = new PropertiesManager();
        propertiesManager.load(new File("botyta.properties"));

        String[] stringargs = {"**Botyta ACTIVADO**", "**Botyta DESACTIVADO**"};

        stringargs[0] = propertiesManager.getProperty("string_botyta_on");
        stringargs[1] = propertiesManager.getProperty("string_botyta_off");

        botyta = new Botyta(this, propertiesManager.getProperty("api"), stringargs);
        console = Bukkit.getServer().getConsoleSender();
        console.sendMessage("[Botyta] Botyta enabled!");

        Objects.requireNonNull(getCommand("btps")).setExecutor(new MinecraftTPSCommand());
        Bukkit.dispatchCommand(console,"timings on");
    }

    @Override
    public void onDisable() {
        console.sendMessage("[Botyta] Botyta disabled!");
        botyta.disableBot();
    }
}
