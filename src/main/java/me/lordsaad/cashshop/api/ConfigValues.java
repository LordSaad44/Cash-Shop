package me.lordsaad.cashshop.api;

import com.teamwizardry.librarianlib.common.util.ConfigPropertyInt;

/**
 * Created by LordSaad.
 */
public class ConfigValues {

    @ConfigPropertyInt(modid = Constants.MOD_ID, category = "general", id = "started_amount", comment = "The initial amount of money a player starts with", defaultValue = 10000)
    public static int starterAmount;

	@ConfigPropertyInt(modid = Constants.MOD_ID, category = "general", id = "normal_currency_amount", comment = "The amount a single normal currency coin is worth for.", defaultValue = 15)
	public static int normalCurrencyAmount;

	@ConfigPropertyInt(modid = Constants.MOD_ID, category = "general", id = "small_currency_amount", comment = "The amount a single small currency coin is worth for.", defaultValue = 5)
	public static int smallCurrencyAmount;

	@ConfigPropertyInt(modid = Constants.MOD_ID, category = "general", id = "large_currency_amount", comment = "The amount a single large currency coin is worth for.", defaultValue = 25)
	public static int largeCurrencyAmount;
}
