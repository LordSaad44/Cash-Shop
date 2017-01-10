package me.lordsaad.cashshop.api;

import com.teamwizardry.librarianlib.common.util.ConfigPropertyInt;

/**
 * Created by LordSaad.
 */
public class ConfigValues {

    @ConfigPropertyInt(modid = Constants.MOD_ID, category = "general", id = "started_amount", comment = "The initial amount of money a player starts with", defaultValue = 10000)
    public static int starterAmount;
}
