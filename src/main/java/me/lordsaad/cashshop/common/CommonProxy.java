package me.lordsaad.cashshop.common;

import me.lordsaad.cashshop.Cashshop;
import me.lordsaad.cashshop.api.*;
import me.lordsaad.cashshop.client.gui.GuiHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by LordSaad.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModTab.init();
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(Cashshop.instance, new GuiHandler());
    }

    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}