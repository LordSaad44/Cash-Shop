package me.lordsaad.cashshop.common.command;

import me.lordsaad.cashshop.api.Constants;
import me.lordsaad.cashshop.api.capability.WalletCapabilityProvider;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.NotNull;

/**
 * Created by LordSaad.
 */
public class CommandWallet extends CommandBase {
	@NotNull
	@Override
	public String getName() {
		return "wallet";
	}

	@NotNull
	@Override
	public String getUsage(@NotNull ICommandSender sender) {
		return Constants.MOD_ID + ":command.wallet.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public void execute(@NotNull MinecraftServer server, @NotNull ICommandSender sender, @NotNull String[] args) throws CommandException {
		if (args.length < 2) throw new WrongUsageException(getUsage(sender));
		EntityPlayer player = getPlayer(server, sender, args[0]);

		if (args[1].equalsIgnoreCase("get")) {
			sender.sendMessage(new TextComponentString(player.getName() + " has " + WalletCapabilityProvider.get(player).getWallet() + " in their account."));
		} else {
			if (args.length < 3) throw new WrongUsageException(getUsage(sender));
			int amount = parseInt(args[2]);

			if (args[2].equalsIgnoreCase("set"))
				WalletCapabilityProvider.get(player).setWallet(amount, player);
			else if (args[2].equalsIgnoreCase("add"))
				WalletCapabilityProvider.get(player).setWallet(WalletCapabilityProvider.get(player).getWallet() + amount, player);
			else if (args[2].equalsIgnoreCase("remove"))
				WalletCapabilityProvider.get(player).setWallet(WalletCapabilityProvider.get(player).getWallet() - amount, player);
			else throw new WrongUsageException(getUsage(sender));
		}
	}
}
