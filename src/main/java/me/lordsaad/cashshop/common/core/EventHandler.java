package me.lordsaad.cashshop.common.core;

import com.teamwizardry.librarianlib.common.network.PacketHandler;
import me.lordsaad.cashshop.api.capability.CapabilityWallet;
import me.lordsaad.cashshop.common.network.PacketWalletChange;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by LordSaad.
 */
public class EventHandler {

	@SubscribeEvent
	public void death(PlayerEvent.Clone event) {
		int wallet = event.getOriginal().getCapability(CapabilityWallet.WALLET, null).getAmount();
		PacketHandler.NETWORK.sendToServer(new PacketWalletChange(wallet));
	}
}
