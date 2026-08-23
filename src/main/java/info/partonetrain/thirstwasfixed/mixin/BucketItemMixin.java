package info.partonetrain.thirstwasfixed.mixin;

import cn.mlus.thirst.content.registry.ThirstComponent;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BucketItem.class)
public class BucketItemMixin extends Item {

    public BucketItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(Config.ENFORCE_WATER_CONTAINER_ITEM_PURITY.get()){
            if(stack.is(Items.WATER_BUCKET)) {
                Integer thirst = stack.get(ThirstComponent.PURITY); //Integer is nullable
                if(thirst == null){
                    stack.set(ThirstComponent.PURITY, 3);
                }
            }
        }
    }
}
