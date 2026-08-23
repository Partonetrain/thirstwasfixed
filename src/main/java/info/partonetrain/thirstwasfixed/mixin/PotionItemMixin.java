package info.partonetrain.thirstwasfixed.mixin;

import cn.mlus.thirst.content.registry.ThirstComponent;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PotionItem.class)
public class PotionItemMixin extends Item {
    public PotionItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(Config.ENFORCE_WATER_CONTAINER_ITEM_PURITY.get()){
            var potion = stack.get(DataComponents.POTION_CONTENTS);
            if(potion != null) {
                if (potion.is(Potions.WATER)) {
                    Integer thirst = stack.get(ThirstComponent.PURITY); //Integer is nullable
                    if(thirst == null){
                        stack.set(ThirstComponent.PURITY, 3);
                    }
                }
            }
        }
    }
}
