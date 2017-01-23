package me.lordsaad.cashshop.common.core;

import me.lordsaad.cashshop.api.capability.CapabilityWallet;
import me.lordsaad.cashshop.common.network.PacketWalletSync;
import me.lordsaad.cashshop.common.network.WalletPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by LordSaad.
 */
public class EventHandler {

	@SubscribeEvent
	public void death(PlayerEvent.Clone event) {
		int wallet = event.getOriginal().getCapability(CapabilityWallet.WALLET, null).getWallet();
		event.getEntity().getCapability(CapabilityWallet.WALLET, null).setWallet(wallet);
		WalletPacketHandler.INSTANCE.sendTo(new PacketWalletSync(wallet), (EntityPlayerMP) event.getEntity());
	}
}
