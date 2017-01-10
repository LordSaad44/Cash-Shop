package me.lordsaad.cashshop.client.gui;

import me.lordsaad.cashshop.client.gui.purse.ContainerPurse;
import me.lordsaad.cashshop.client.gui.purse.GuiPurse;
import me.lordsaad.cashshop.client.gui.trade.GuiTrade;
import me.lordsaad.cashshop.common.entity.EntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by LordSaad.
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new ContainerPurse(player.inventory);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new GuiPurse(player.inventory);

        if (ID == 1) {
            EntityNPC npc = (EntityNPC) world.getEntityByID(x);
            if (npc != null)
            return new GuiTrade(npc.getDataManager().get(EntityNPC.DATA_JSON_FILE));
        }
        return null;
    }
}
