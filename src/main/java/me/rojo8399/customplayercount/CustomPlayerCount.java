package me.rojo8399.customplayercount;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

import me.rojo8399.customplayercount.commands.cpcCommand;
import me.rojo8399.customplayercount.config.Config;
import me.rojo8399.customplayercount.listeners.Ping;

@Plugin(id = CustomPlayerCount.PLUGIN_ID, name = CustomPlayerCount.PLUGIN_NAME, version = CustomPlayerCount.PLUGIN_VERSION, authors = {
		"rojo8399" })
public class CustomPlayerCount {

	public static final String PLUGIN_ID = "cpc";
	public static final String PLUGIN_NAME = "Custom Player Count";
	public static final String PLUGIN_VERSION = "1.0";

	private static CustomPlayerCount instance;

	@Inject
	private Logger logger;
	
	@Inject PluginContainer plugin;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	@Listener
	public void onGamePreInitializationEvent(GamePreInitializationEvent event) {
		instance = this;
		plugin = Sponge.getPluginManager().getPlugin(PLUGIN_ID).get();

		// Create Configuration Directory for CustomPlayerCount
		if (!Files.exists(configDir)) {
			try {
				Files.createDirectories(configDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Config.getConfig().setup();
	}

	@Listener
	public void onGameInitializationEvent(GameInitializationEvent event) {
		Sponge.getEventManager().registerListeners(this, new Ping());
		
		CommandSpec cpcCommandSpec = CommandSpec.builder().description(Text.of("CustomPlayerCount Version Viewer"))
				.executor(new cpcCommand()).build();

		Sponge.getCommandManager().register(plugin, cpcCommandSpec, "customplayercount", "cpc");
	}

	@Listener
	public void onReload(GameReloadEvent event) {
		Config.getConfig().load();
		event.getCause().first(Player.class).ifPresent(p -> p.sendMessage(Text.builder().color(TextColors.GREEN).append(Text.of("Reloading CustomPlayerCount...")).build()));
		getLogger().info("Reloading CustomPlayerCount...");
	}

	public Logger getLogger() {
		return logger;
	}

	public static CustomPlayerCount getInstance() {
		return instance;
	}

	public Path getConfigDir() {
		return configDir;
	}
}
