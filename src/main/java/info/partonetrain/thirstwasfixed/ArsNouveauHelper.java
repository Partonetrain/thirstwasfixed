package info.partonetrain.thirstwasfixed;

import com.hollingsworth.arsnouveau.common.items.data.MultiPotionContents;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public class ArsNouveauHelper {
    public static boolean isContentsEmptyOrWater(MultiPotionContents multiPotionContents) {
        return multiPotionContents.contents() == PotionContents.EMPTY || multiPotionContents.contents().is(Potions.WATER) || multiPotionContents.contents().potion().isEmpty();
    }

    public static boolean isStackFlask(ItemStack stack){
        return stack.is(ItemsRegistry.POTION_FLASK.asItem()) ||
                stack.is(ItemsRegistry.POTION_FLASK_AMPLIFY.asItem()) ||
                stack.is(ItemsRegistry.POTION_FLASK_EXTEND_TIME.asItem());

    }

}
