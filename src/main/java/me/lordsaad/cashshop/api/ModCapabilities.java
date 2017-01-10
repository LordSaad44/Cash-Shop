package me.lordsaad.cashshop.api;

import me.lordsaad.cashshop.api.capability.DefaultWalletCapability;
import me.lordsaad.cashshop.api.capability.IWalletCapability;
import me.lordsaad.cashshop.api.capability.WalletCapabilityProvider;
import me.lordsaad.cashshop.api.capability.WalletCapabilityStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by LordSaad.
 */
public class ModCapabilities {

	public static void preInit() {
		CapabilityManager.INSTANCE.register(IWalletCapability.class, new WalletCapabilityStorage(), DefaultWalletCapability.class);
	}

	@SubscribeEvent
	public void onAddCapabilities(AttachCapabilitiesEvent.Entity e) {
		if (e.getEntity() instanceof EntityPlayer) {
			WalletCapabilityProvider cap = new WalletCapabilityProvider(new DefaultWalletCapability());
			e.addCapability(new ResourceLocation(Constants.MOD_ID, "capabilities"), cap);
		}
	}
}
