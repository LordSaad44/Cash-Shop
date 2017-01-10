package me.lordsaad.cashshop.client.core;

import com.teamwizardry.librarianlib.client.core.ClientTickHandler;
import com.teamwizardry.librarianlib.client.sprite.Sprite;
import com.teamwizardry.librarianlib.client.sprite.Texture;
import com.teamwizardry.librarianlib.common.util.ConfigPropertyInt;
import com.teamwizardry.librarianlib.common.util.math.Vec2d;
import me.lordsaad.cashshop.api.ConfigValues;
import me.lordsaad.cashshop.api.Constants;
import me.lordsaad.cashshop.api.capability.IWalletCapability;
import me.lordsaad.cashshop.api.capability.WalletCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by LordSaad.
 */
public class HudHandler {

    @ConfigPropertyInt(modid = Constants.MOD_ID, category = "general", id = "hud_position", comment = "0=top_left, 1=top_center, 2=top_right, 3=bottom_left, 4=bottom_center, 5=bottom_right, 6=left_center, 7=right_center", defaultValue = 0)
    public static int hudPosition = 0;

    public static final Sprite coinBackground = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/guis/coin_background.png")).getSprite("bar", 80, 15);
    public static final Sprite coin = new Texture(new ResourceLocation(Constants.MOD_ID, "textures/items/currency_coin.png")).getSprite("coin", 20, 20);

    public static HudHandler INSTANCE = new HudHandler();

    private HudHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void hudRender(RenderGameOverlayEvent.Post event) {
        int finalWidth = coinBackground.getWidth() + 5 + coin.getWidth();
        int finalHeight = coinBackground.getHeight();
        
        Position enumPos = Position.TOP_LEFT;
        for (Position posID : Position.values())
            if (posID.id == hudPosition) {
                enumPos = posID;
                break;
            }

        ScaledResolution resolution = event.getResolution();
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        Vec2d position;

        switch (enumPos) {
            default:
            case TOP_LEFT:
                position = Vec2d.ZERO;
                break;
            case TOP_CENTER:
                position = new Vec2d((width / 2) - (finalWidth / 2), 0);
                break;
            case TOP_RIGHT:
                position = new Vec2d(width - finalWidth, 0);
                break;
            case BOTTOM_LEFT:
                position = new Vec2d(0, height - finalHeight);
                break;
            case BOTTOM_CENTER:
                position = new Vec2d((width / 2) - (finalWidth / 2), height - finalHeight);
                break;
            case BOTTOM_RIGHT:
                position = new Vec2d(width - finalWidth, height - finalHeight);
                break;
            case LEFT_CENTER:
                position = new Vec2d(0, (height / 2) - (finalHeight / 2));
                break;
            case RIGHT_CENTER:
                position = new Vec2d(width - finalWidth, (height / 2) - (finalHeight / 2));
                break;
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();

        coinBackground.getTex().bind();
        coinBackground.draw((int) ClientTickHandler.getPartialTicks(), position.getXf(), position.getYf());
        coin.getTex().bind();
        coin.draw((int) ClientTickHandler.getPartialTicks(), position.getXf() + coinBackground.getWidth(), position.getYf() - 4);

        int wallet = WalletCapabilityProvider.get(Minecraft.getMinecraft().player).getWallet();


        int nbWidth = (coinBackground.getWidth() / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(wallet + "") / 2);

        Minecraft.getMinecraft().fontRendererObj.drawString(wallet + "", position.getXf() + nbWidth, position.getYf() + (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2), 0, false);

        GlStateManager.popMatrix();
    }

    private enum Position {
        TOP_LEFT(0),
        TOP_CENTER(1),
        TOP_RIGHT(2),
        BOTTOM_LEFT(3),
        BOTTOM_CENTER(4),
        BOTTOM_RIGHT(5),
        LEFT_CENTER(6),
        RIGHT_CENTER(7);

        int id;

        Position(int id) {
            this.id = id;
        }
    }
}
