package info.partonetrain.thirstwasfixed;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Set;

@EventBusSubscriber(modid = ThirstWasFixedMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue FIX_209;
    private static final ModConfigSpec.IntValue MAGIC_NUMBER;

    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION;
    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean fix209;
    public static int magicNumber;
    public static String magicNumberIntroduction;
    public static Set<Item> items;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        fix209 = FIX_209.get();
        magicNumber = MAGIC_NUMBER.get();
        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
    }

    static
    {
        BUILDER.push("General");
        FIX_209 = BUILDER
            .comment("Whether or not to fix https://github.com/ghen-git/Thirst-Mod/issues/209")
            .comment("If stacked potions/water bottles are giving you double the thirst, then it must have been fixed, and you should set this to false")
            .define("Fix #209", true);

        MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

        MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

        BUILDER.push("Ars Nouveau");


        BUILDER.push("Ars Elemental");
    }
}
