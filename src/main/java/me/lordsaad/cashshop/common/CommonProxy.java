package me.lordsaad.cashshop.common;

import com.teamwizardry.librarianlib.common.network.PacketHandler;
import com.teamwizardry.librarianlib.common.util.EasyConfigHandler;
import me.lordsaad.cashshop.Cashshop;
import me.lordsaad.cashshop.api.*;
import me.lordsaad.cashshop.api.capability.CapabilityWallet;
import me.lordsaad.cashshop.client.gui.GuiHandler;
import me.lordsaad.cashshop.common.core.EventHandler;
import me.lordsaad.cashshop.common.network.PacketWalletChange;
import me.lordsaad.cashshop.common.network.WalletPacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by LordSaad.
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ModTab.init();
		ModBlocks.init();
		ModItems.init();
		ModEntities.init();
		CapabilityWallet.register();

		EasyConfigHandler.init();

		WalletPacketHandler.registerMessages();
		NetworkRegistry.INSTANCE.registerGuiHandler(Cashshop.instance, new GuiHandler());
		PacketHandler.register(PacketWalletChange.class, Side.SERVER);
	}

	public void init(FMLInitializationEvent event) {
		ModRecipes.init();
	}

	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(ModCapabilities.class);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
}
