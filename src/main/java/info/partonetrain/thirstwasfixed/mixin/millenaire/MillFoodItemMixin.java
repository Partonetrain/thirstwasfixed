package info.partonetrain.thirstwasfixed.mixin.millenaire;

import cn.mlus.thirst.content.thirst.PlayerThirst;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.millenaire.item.MillFoodItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MillFoodItem.class)
public class MillFoodItemMixin {
    @Inject(method = "finishUsingItem", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V", ordinal = 0))
    public void cle$finishUsingItem_Drink(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir){
        MillFoodItem self = (MillFoodItem) (Object) this;
        //if(self.drink){ //this is private
        if(self.getUseAnimation(stack) == UseAnim.DRINK){
            if(entity instanceof ServerPlayer serverPlayer){
                PlayerThirst.drink(stack, serverPlayer);
            }
        }
    }
}
