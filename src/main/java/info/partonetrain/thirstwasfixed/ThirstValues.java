package info.partonetrain.thirstwasfixed;

import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.NeoForge;

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
    }
}
