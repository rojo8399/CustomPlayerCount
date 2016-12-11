package me.rojo8399.customplayercount.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.rojo8399.customplayercount.CustomPlayerCount;

public class cpcCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		src.sendMessage(Text.builder(CustomPlayerCount.PLUGIN_NAME + " - Version: " + CustomPlayerCount.PLUGIN_VERSION).color(TextColors.AQUA).build());
		return CommandResult.success();
	}

}
