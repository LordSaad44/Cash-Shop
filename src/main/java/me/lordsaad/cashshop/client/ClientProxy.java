package me.lordsaad.cashshop.client;

import com.teamwizardry.librarianlib.common.network.PacketHandler;
import me.lordsaad.cashshop.api.ModEntities;
import me.lordsaad.cashshop.client.core.HudHandler;
import me.lordsaad.cashshop.common.CommonProxy;
import me.lordsaad.cashshop.common.network.PacketWalletChange;
import me.lordsaad.cashshop.common.network.WalletPacketHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by LordSaad.
 */

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        HudHandler.INSTANCE.getClass();
        ModEntities.initModels();

	    WalletPacketHandler.registerMessages();
	    PacketHandler.register(PacketWalletChange.class, Side.SERVER);
    }
}
