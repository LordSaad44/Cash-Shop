package me.lordsaad.cashshop.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

/**
 * Created by LordSaad.
 */
public class CommandToggleTrade extends CommandBase {
	@NotNull
	@Override
	public String getName() {
		return "toggletrade";
	}

	@NotNull
	@Override
	public String getUsage(@NotNull ICommandSender sender) {
		return "Will enable/disable a trade";
	}

	@Override
	public void execute(@NotNull MinecraftServer server, @NotNull ICommandSender sender, @NotNull String[] args) throws CommandException {

	}
}
