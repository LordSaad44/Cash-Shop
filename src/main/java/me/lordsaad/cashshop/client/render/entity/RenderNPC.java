package me.lordsaad.cashshop.client.render.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.teamwizardry.librarianlib.LibrarianLib;
import me.lordsaad.cashshop.api.Constants;
import me.lordsaad.cashshop.common.entity.EntityNPC;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Saad on 8/21/2016.
 */
public class RenderNPC extends RenderLiving<EntityNPC> {

    public static final ResourceLocation SPIRIT_TEX = new ResourceLocation(Constants.MOD_ID, "textures/entities/example_npc.png");

    public RenderNPC(RenderManager renderManager, ModelBase modelBase) {
        super(renderManager, modelBase, 0.0f);
    }

    @Override
    public boolean canRenderName(EntityNPC entity) {
        return false;
    }

    @NotNull
    @Override
    protected ResourceLocation getEntityTexture(@NotNull EntityNPC entity) {
        ResourceLocation location = new ResourceLocation(Constants.MOD_ID, "textures/entities/" + entity.getDataManager().get(EntityNPC.DATA_JSON_FILE) + ".png");
        return location;
        //return SPIRIT_TEX;
    }
}
