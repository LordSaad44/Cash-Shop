package me.lordsaad.cashshop.client.gui.purse;

import com.teamwizardry.librarianlib.client.sprite.Sprite;
import me.lordsaad.cashshop.api.Constants;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by LordSaad.
 */
public class GuiPurse extends GuiContainer {

    public static Sprite background = new Sprite(new ResourceLocation(Constants.MOD_ID, "textures/guis/purse.png"));
    private final InventoryPlayer playerInventory;

    public GuiPurse(InventoryPlayer playerInv) {
        super(new ContainerPurse(playerInv));
        this.playerInventory = playerInv;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = "Purse";
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background.getTex().getLoc());
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}
