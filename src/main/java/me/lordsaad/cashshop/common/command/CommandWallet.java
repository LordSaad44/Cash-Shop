package me.lordsaad.cashshop.common.command;

import me.lordsaad.cashshop.api.Constants;
import me.lordsaad.cashshop.api.capability.CapabilityWallet;
import me.lordsaad.cashshop.common.network.PacketWalletSync;
import me.lordsaad.cashshop.common.network.WalletPacketHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
			sender.sendMessage(new TextComponentString(player.getName() + " has " + player.getCapability(CapabilityWallet.WALLET, null).getWallet() + " in their account."));
		} else {
			if (args.length < 2) throw new WrongUsageException(getUsage(sender));
			int amount = parseInt(args[2]);

			if (args[1].equalsIgnoreCase("set"))
				WalletPacketHandler.INSTANCE.sendTo(new PacketWalletSync(amount), (EntityPlayerMP) player);
			else if (args[1].equalsIgnoreCase("add"))
				WalletPacketHandler.INSTANCE.sendTo(new PacketWalletSync(player.getCapability(CapabilityWallet.WALLET, null).getWallet() + amount), (EntityPlayerMP) player);
			else if (args[1].equalsIgnoreCase("remove"))
				WalletPacketHandler.INSTANCE.sendTo(new PacketWalletSync(player.getCapability(CapabilityWallet.WALLET, null).getWallet() - amount), (EntityPlayerMP) player);
			else throw new WrongUsageException(getUsage(sender));
		}
	}
}
