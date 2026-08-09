package info.partonetrain.thirstwasfixed.mixin.millenaire;

import cn.mlus.thirst.api.ThirstHelper;
import cn.mlus.thirst.foundation.common.capability.IThirst;
import cn.mlus.thirst.foundation.common.capability.ModAttachment;
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
    @Inject(method = "finishUsingItem", at= @At(value = "HEAD"))
    public void thirstwasfixed$finishUsingItem_Drink(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir){
        MillFoodItem self = (MillFoodItem) (Object) this;
        if(self.getUseAnimation(stack) == UseAnim.DRINK){
            if(entity instanceof ServerPlayer serverPlayer){
                IThirst thirst = serverPlayer.getData(ModAttachment.PLAYER_THIRST);
                thirst.drink(ThirstHelper.getThirst(self.getDefaultInstance()), ThirstHelper.getQuenched(self.getDefaultInstance()));
                //diff results in production vs dev
            }
        }
    }
}
