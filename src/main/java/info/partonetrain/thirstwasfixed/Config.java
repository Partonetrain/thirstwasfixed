package info.partonetrain.thirstwasfixed;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER;
    public final static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue FIX_209;
    public static ModConfigSpec.BooleanValue FIX_CAULDRONS;
    public static ModConfigSpec.BooleanValue AN_FLASK_RESTORES_THIRST;
    public static ModConfigSpec.BooleanValue AN_FLASK_PICKS_UP_WATER;
    public static ModConfigSpec.BooleanValue AN_FLASK_EMPTY;
    public static ModConfigSpec.IntValue AN_FLASK_SIZE;
    public static ModConfigSpec.BooleanValue AN_EVERFULL_FILL_FROM;
    public static ModConfigSpec.BooleanValue AN_EVERFULL_REQUESTS_SOURCE;
    public static ModConfigSpec.IntValue AE_EVERFULL_PURITY;

    static
    {
        BUILDER = new ModConfigSpec.Builder();
        register(BUILDER);
        SPEC = BUILDER.build();
    }

    public static void register(ModConfigSpec.Builder builder){
        BUILDER.push("General");
        FIX_209 = BUILDER
                .comment("Whether or not to fix https://github.com/ghen-git/Thirst-Mod/issues/209")
                .comment("As of 2.1.3 this has been fixed so you shouldn't need it")
                .define("Fix #209", false);
        FIX_CAULDRONS = BUILDER
                .comment("Whether or not to set water cauldrons without a purity state to the default purity (set in Thirst config) on chunk load")
                .comment("You should only turn this on if there are already cauldrons in your world that are missing a purity state, otherwise there may be significant performance impact for no reason")
                .comment("This shouldn't be needed for anything else that stores water; water cauldrons are just unusual since their purity is stored in the blockstate")
                .define("Fix Cauldrons", false);
        BUILDER.pop();

        BUILDER.push("Ars Nouveau");
        AN_FLASK_RESTORES_THIRST = BUILDER
                .comment("Whether or not the Ars Nouveau Potion Flask should restore thirst when consumed")
                .define("Potion Flasks Restore Thirst", true);
        AN_FLASK_PICKS_UP_WATER = BUILDER
                .comment("Whether or not the Ars Nouveau Potion Flask should pick up water from water source blocks or cauldrons like Glass Bottles")
                .comment("Note: Potion flasks, like regular potions, do not store purity, and are always considered 100% pure")
                .define("Potion Flasks Water Blocks", true);
        AN_FLASK_EMPTY = BUILDER
                .comment("If true, a potion flask's contents can be discarded by shift-right-clicking it in your off hand while your main hand is empty")
                .define("Empty Potion Flasks", true);
        AN_FLASK_SIZE = BUILDER.comment("The capacity of the Ars Nouveau Potion Flask")
                .comment("This is the number of potions (or water) it can hold")
                .comment("If left at 8, the capacity will not be touched (in case Ars Nouveau makes it configurable in the future)")
                .comment("If you change this, you should also change Ars Nouveau's lang file (the keys ars_nouveau.page1.potion_flask and tooltip.potion_flask) to match")
                .defineInRange("Potion Flask Size", 8, 1, 64);
        BUILDER.pop();

        BUILDER.push("Ars Elemental");
        AE_EVERFULL_PURITY = BUILDER
                .comment("The purity of the water provided by the Everfull Urn")
                .comment("Water that is bucketed/bottled from, or transferred to a cauldron will have this purity")
                .comment("(In other cases, the purity will be the default purity set in the Thirst config)")
                .defineInRange("Everfull Urn Purity", 2, 1, 3);
        AN_EVERFULL_FILL_FROM = BUILDER
                .comment("Whether or not water with purity can be taken from the Everfull Urn with bottles and buckets")
                .comment("If Potion Flasks Water Blocks is true, potion flasks will also be able to pick up water from the Everfull Urn")
                .define("Fill from Everfull Urn", true);
        AN_EVERFULL_REQUESTS_SOURCE = BUILDER
                .comment("Whether or not filling a bucket/bottle/flask from the Everfull Urn consumes nearby Source")
                .comment("Determined by \"waterUrnCost\" in the Ars Elemental config")
                .comment("Requires \"Fill from Everfull Urn\" to be true")
                .define("Everfull Urn Requests Source", true);
        BUILDER.pop();
    }

}
