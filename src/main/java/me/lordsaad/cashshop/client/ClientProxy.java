package me.lordsaad.cashshop.client;

import me.lordsaad.cashshop.api.ModEntities;
import me.lordsaad.cashshop.client.core.HudHandler;
import me.lordsaad.cashshop.common.CommonProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by LordSaad.
 */

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        HudHandler.INSTANCE.getClass();
        ModEntities.initModels();
    }
}
