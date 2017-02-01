package me.lordsaad.cashshop.api;

import me.lordsaad.cashshop.api.capability.CapabilityWallet;
import me.lordsaad.cashshop.api.capability.WalletSerializer;
import me.lordsaad.cashshop.common.network.PacketWalletSync;
import me.lordsaad.cashshop.common.network.WalletPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by LordSaad.
 */
public class ModCapabilities {

	@SubscribeEvent
	public static void attachCaps(AttachCapabilitiesEvent.Entity event) {
		if (event.getEntity() instanceof EntityPlayer)
			event.addCapability(WalletSerializer.CAP_KEY, new WalletSerializer());
	}

	@SubscribeEvent
	public static void syncWalletCap(EntityJoinWorldEvent event) {
		if (!(event.getEntity() instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) event.getEntity();
		if (!player.world.isRemote) {
			WalletPacketHandler.INSTANCE.sendTo(new PacketWalletSync(player.getCapability(CapabilityWallet.WALLET, null).getAmount()), (EntityPlayerMP) player);
		}
	}
}
