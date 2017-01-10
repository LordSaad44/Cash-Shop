package me.lordsaad.cashshop.common.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.teamwizardry.librarianlib.LibrarianLib;
import me.lordsaad.cashshop.Cashshop;
import me.lordsaad.cashshop.api.Constants;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Saad on 8/17/2016.
 */
public class EntityNPC extends EntityLiving {

    public String jsonFile;
    public static final DataParameter<String> DATA_JSON_FILE = EntityDataManager.createKey(EntityNPC.class, DataSerializers.STRING);

    public EntityNPC(World worldIn) {
        super(worldIn);
    }

    public EntityNPC(World worldIn, String jsonFile) {
        super(worldIn);
        this.jsonFile = jsonFile;

        this.getDataManager().set(DATA_JSON_FILE, jsonFile);
        this.getDataManager().setDirty(DATA_JSON_FILE);

        InputStream stream = LibrarianLib.PROXY.getResource(Constants.MOD_ID, "npcs/" + jsonFile + ".json");
        if (stream != null) {
            InputStreamReader reader = new InputStreamReader(stream);
            JsonElement json = new JsonParser().parse(reader);

            setAlwaysRenderNameTag(true);
            setCustomNameTag(json.getAsJsonObject().getAsJsonPrimitive("name") + "");
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(DATA_JSON_FILE, "null");
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (isEntityAlive() && !player.isSneaking()) {
            if (world.isRemote) {
                player.openGui(Cashshop.instance, 1, world, getEntityId(), (int) player.posY, (int) player.posZ);
            }
            return true;
        } else {
            return super.processInteract(player, hand, stack);
        }
    }


    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("json_file"))
            jsonFile = compound.getString("json_file");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (jsonFile != null)
            compound.setString("json_file", jsonFile);
    }
}
