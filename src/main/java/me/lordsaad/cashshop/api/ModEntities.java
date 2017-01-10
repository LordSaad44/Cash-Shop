package me.lordsaad.cashshop.api;

import me.lordsaad.cashshop.Cashshop;
import me.lordsaad.cashshop.client.render.entity.ModelNPC;
import me.lordsaad.cashshop.client.render.entity.RenderNPC;
import me.lordsaad.cashshop.common.entity.EntityNPC;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LordSaad.
 */
public class ModEntities {
    private static int i = 0;

    public static void init() {
        EntityRegistry.registerModEntity(EntityNPC.class, "npc", i++, Cashshop.instance, 64, 3, true);
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        RenderingRegistry.registerEntityRenderingHandler(EntityNPC.class, manager -> new RenderNPC(manager, new ModelNPC()));
    }
}
