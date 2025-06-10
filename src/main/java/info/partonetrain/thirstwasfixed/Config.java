package info.partonetrain.thirstwasfixed;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER;
    public final static ModConfigSpec SPEC;

//    public static ModConfigSpec.BooleanValue FIX_209;
    public static ModConfigSpec.BooleanValue FIX_CAULDRONS;
    public static ModConfigSpec.BooleanValue DRINK_CAULDRONS;
    public static ModConfigSpec.IntValue RAINWATER_PURITY;
    public static ModConfigSpec.IntValue DRIPSTONE_PURITY;
    public static ModConfigSpec.IntValue THIRST_BONUS_REQUIREMENT;
    public static ModConfigSpec.DoubleValue THIRST_BONUS_VALUE;

    public static ModConfigSpec.BooleanValue AN_FLASK_RESTORES_THIRST;
    public static ModConfigSpec.BooleanValue AN_FLASK_PICKS_UP_WATER;
    public static ModConfigSpec.BooleanValue AN_FLASK_PICKS_UP_CAULDRON;
    public static ModConfigSpec.BooleanValue AN_FLASK_EMPTY;
    public static ModConfigSpec.IntValue AN_FLASK_SIZE;
    public static ModConfigSpec.BooleanValue AN_ALCHEMISTS_CROWN;

    public static ModConfigSpec.BooleanValue AE_EVERFULL_FILL_FROM;
    public static ModConfigSpec.BooleanValue AE_EVERFULL_DRINK_FROM;
    public static ModConfigSpec.BooleanValue AE_EVERFULL_FLASK;
    public static ModConfigSpec.BooleanValue AE_EVERFULL_REQUESTS_SOURCE;
    public static ModConfigSpec.IntValue AE_EVERFULL_PURITY;
    public static ModConfigSpec.IntValue ULTIMINE_REQUIRES_THIRST;
    //public static ModConfigSpec.IntValue ULTIMINE_USES_THIRST;

    public static ModConfigSpec.BooleanValue SUPPS_FIX_FAUCETS;

    static
    {
        BUILDER = new ModConfigSpec.Builder();
        register(BUILDER);
        SPEC = BUILDER.build();
    }

    public static void register(ModConfigSpec.Builder builder){
        BUILDER.push("General");
//        FIX_209 = BUILDER
//                .comment("Whether or not to fix https://github.com/ghen-git/Thirst-Mod/issues/209")
//                .comment("As of 2.1.3 this has been fixed so you shouldn't need it")
//                .define("Fix #209", false);
        FIX_CAULDRONS = BUILDER
                .comment("Whether or not to set water cauldrons without a purity state to the default purity (set in Thirst config) on chunk load")
                .comment("You should only turn this on if there are already cauldrons in your world that have a purity state of 0 (invalid), otherwise there may be significant performance impact for no reason")
                .comment("This shouldn't be needed for anything else that stores water; water cauldrons are just unusual since their purity is stored in the blockstate")
                .define("Fix Cauldrons", false);
        DRINK_CAULDRONS = BUILDER
                .comment("Whether or not to allow drinking from water cauldrons by right-clicking them with an empty hand")
                .comment("The amount of thirst/quenchness restored is set in the Thirst config handDrinkingHydration and handDrinkingQuenched, and these values are used even if canDrinkByHand is false")
                .define("Drink Cauldrons", true);
        RAINWATER_PURITY = BUILDER
                .comment("The purity of rain water when it fills a cauldron")
                .defineInRange("Rainwater Purity", 3, 0, 3);
        DRIPSTONE_PURITY = BUILDER
                .comment("The purity of water when Pointed Dripstone fills a cauldron")
                .defineInRange("Dripstone Purity", 0, 0, 3);
        THIRST_BONUS_REQUIREMENT = BUILDER
                .comment("The amount of thirst required to gain a speed bonus")
                .comment("If 0, there will be no speed bonus")
                .defineInRange("Thirst Bonus Requirement", 0, 0, 20);
        THIRST_BONUS_VALUE = BUILDER
                .comment("The amount of speed bonus to give when the Thirst Bonus Requirement is met")
                .comment("This uses the add_multiplied_total operation, so 0.1 = 10% speed increase")
                .defineInRange("Thirst Bonus Value", 0.1, 0, 1);
        BUILDER.pop();

        BUILDER.push("Ars Nouveau");
        AN_FLASK_RESTORES_THIRST = BUILDER
                .comment("Whether or not the Ars Nouveau Potion Flask should restore thirst when consumed")
                .define("Potion Flasks Restore Thirst", true);
        AN_FLASK_PICKS_UP_WATER = BUILDER
                .comment("Whether or not the Ars Nouveau Potion Flask should pick up water from water source blocks like Glass Bottles")
                .comment("Note: Potion flasks, like regular potions, do not store purity, and are always considered 100% pure")
                .define("Potion Flasks Water Blocks", true);
        AN_FLASK_PICKS_UP_CAULDRON = BUILDER
                .comment("Whether or not the Ars Nouveau Potion Flask should pick up water from Cauldrons like Glass Bottles")
                .define("Potion Flasks Cauldron", true);
        AN_FLASK_EMPTY = BUILDER
                .comment("If true, a potion flask's contents can be discarded by shift-right-clicking it in your off hand while your main hand is empty")
                .define("Empty Potion Flasks", true);
        AN_FLASK_SIZE = BUILDER.comment("The capacity of the Ars Nouveau Potion Flask")
                .comment("This is the number of potions (or water) it can hold")
                .comment("If left at 8, the capacity will not be touched (in case Ars Nouveau makes it configurable in the future)")
                .comment("If you change this, you should also change Ars Nouveau's lang file (the keys ars_nouveau.page1.potion_flask and tooltip.potion_flask) to match")
                .defineInRange("Potion Flask Size", 8, 1, 64);
        AN_ALCHEMISTS_CROWN = BUILDER
                .comment("Whether or not using the Ars Nouveau Alchemist's Crown should restore thirst")
                .define("Alchemist's Crown Restore Thirst", true);
        BUILDER.pop();

        BUILDER.push("Ars Elemental");
        AE_EVERFULL_PURITY = BUILDER
                .comment("The purity of the water provided by the Everfull Urn (AKA Urn Of Endless Waters)")
                .comment("Water that is bucketed/bottled from, or transferred to a cauldron will have this purity")
                .comment("(In other cases, the purity will be the default purity set in the Thirst config)")
                .defineInRange("Everfull Urn Purity", 2, 0, 3);
        AE_EVERFULL_FILL_FROM = BUILDER
                .comment("Whether or not water with purity can be taken from the Everfull Urn with bottles and buckets")
                .comment("If Potion Flasks Everfull Urn is true, potion flasks will also be able to pick up water from the Everfull Urn")
                .define("Fill from Everfull Urn", true);
        AE_EVERFULL_DRINK_FROM = BUILDER
                .comment("Whether or not the Everfull Urn can be drunk from directly by right-clicking it with an empty hand")
                .comment("The amount of thirst/quenchness restored is set in the Thirst config handDrinkingHydration and handDrinkingQuenched, and these values are used even if canDrinkByHand is false")
                .define("Drink from Everfull Urn", true);
        AE_EVERFULL_FLASK = BUILDER
                .comment("Whether or not the Flask can be filled from the Everfull Urn")
                .comment("Requires Fill from Everfull Urn to be true")
                .define("Fill Flask from Everfull Urn", true);
        AE_EVERFULL_REQUESTS_SOURCE = BUILDER
                .comment("Whether or not filling a bucket/bottle/flask from the Everfull Urn consumes nearby Source")
                .comment("Determined by \"waterUrnCost\" in the Ars Elemental config")
                .comment("Requires \"Fill from Everfull Urn\" to be true")
                .define("Everfull Urn Requests Source", true);
        BUILDER.pop();

        BUILDER.push("FTB Ultimine");
        ULTIMINE_REQUIRES_THIRST = BUILDER
                .comment("The amount of thirst required to use FTB Ultimine")
                .defineInRange("Ultimine Requires Thirst", 0, 0, 20);
        /*
        * Unfortunately this can't work because Mixins aren't capable of breaking loops
        * it *would* go here:
        * https://github.com/FTBTeam/FTB-Ultimine/blob/de88ba49bcad8d7d6c4dbc766cd310f3521a4cf5/common/src/main/java/dev/ftb/mods/ftbultimine/FTBUltimine.java#L244
        ULTIMINE_USES_THIRST = BUILDER
                .comment("The amount of thirst used by per block mined with FTB Ultimine")
                .comment("Thirst is always automatically deducted with Hunger, so if you want only Thirst to be deducted, set exhaustion_per_block to 0 in the FTB Ultimine config")
                .defineInRange("Thirst Exhaustion Per Block", 0, 0, 20);

         */
        BUILDER.pop();

//        BUILDER.push("Supplementaries");
//        SUPPS_FIX_FAUCETS = BUILDER
//                .comment("Whether or not to apply a patch to Supplementaries faucets to allow them to propagate thirst to Cauldrons")
//                .comment("If false, faucets will simply not set the purity of the cauldron")
//                .define("Fix Faucets", true);
//        BUILDER.pop();

    }

}
