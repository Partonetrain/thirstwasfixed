package info.partonetrain.thirstwasfixed.mixin;

import cn.mlus.thirst.content.registry.ThirstComponent;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionContents.class)
public class PotionContentsMixin {

    @ModifyReturnValue(method = "createItemStack", at=@At("RETURN"))
    private static ItemStack thirstwasfixed$createItemStack(ItemStack original){
        if(Config.ENFORCE_WATER_POTION_PURITY.getAsBoolean() && original.is(Items.POTION)){
            var potion = original.get(DataComponents.POTION_CONTENTS);
            if(potion != null) {
                if (potion.is(Potions.WATER)) {
                    Integer thirst = original.get(ThirstComponent.PURITY);
                    if(thirst == null){
                        original.set(ThirstComponent.PURITY, 3);
                    }
                }
            }
        }
        return original;
    }
}
