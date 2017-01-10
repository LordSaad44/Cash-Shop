package me.lordsaad.cashshop.common.network;

import me.lordsaad.cashshop.api.Constants;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Saad on 8/17/2016.
 */
public class WalletPacketHandler {

	public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MOD_ID);

	public static void registerMessages() {
		INSTANCE.registerMessage(MessageUpdateCapabilities.CapsMessageHandler.class, MessageUpdateCapabilities.class, 0, Side.CLIENT);
	}
}
