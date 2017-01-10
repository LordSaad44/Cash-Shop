package me.lordsaad.cashshop.common.item;

import com.teamwizardry.librarianlib.common.base.item.ItemMod;
import me.lordsaad.cashshop.common.entity.EntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Created by LordSaad.
 */
public class ItemMagicCoin extends ItemMod {

    public ItemMagicCoin() {
        super("magic_coin");
        setMaxStackSize(1);
    }

    @NotNull
    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (pos != null) {
            if (playerIn.isSneaking() && !worldIn.isRemote) {
                EntityNPC npc = new EntityNPC(worldIn, stack.getDisplayName());
                npc.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                worldIn.spawnEntity(npc);
            }
        }
        return EnumActionResult.PASS;
    }
}
