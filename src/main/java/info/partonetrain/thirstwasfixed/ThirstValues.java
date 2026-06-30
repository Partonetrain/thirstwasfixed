package info.partonetrain.thirstwasfixed;

import cn.mlus.thirst.foundation.common.event.RegisterThirstValueEvent;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;
import org.millenaire.item.ModItems;

public class ThirstValues {
    public static void load(){
        NeoForge.EVENT_BUS.register(ThirstValues.class);
    }

    @SubscribeEvent
    public static void compat(RegisterThirstValueEvent event) {
        if(ModList.get().isLoaded("ars_nouveau") && Config.AN_FLASK_RESTORES_THIRST.get()){
            event.addDrink(ItemsRegistry.POTION_FLASK.asItem(), 6, 8);
            event.addDrink(ItemsRegistry.POTION_FLASK_AMPLIFY.asItem(), 6, 8);
            event.addDrink(ItemsRegistry.POTION_FLASK_EXTEND_TIME.asItem(), 6, 8);
        }

        if(ModList.get().isLoaded("millenaire") && Config.MILLENAIRE_DRINKS.get()){
            event.addDrink(ModItems.AYRAN.asItem(), 6, 8);
            event.addDrink(ModItems.CALVA.asItem(), 6, 8);
            event.addDrink(ModItems.CACAUHAA.asItem(), 6, 8);
            event.addDrink(ModItems.CIDER.asItem(), 6, 8);
            event.addDrink(ModItems.SAKE.asItem(), 8, 12);
            event.addDrink(ModItems.WINEBASIC.asItem(), 6, 8);
            event.addDrink(ModItems.WINEFANCY.asItem(), 8, 12);
        }
    }
}
