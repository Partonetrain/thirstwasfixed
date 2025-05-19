package info.partonetrain.thirstwasfixed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.ghen.thirst.content.thirst.PlayerThirst;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionItem.class)
public class PotionItemMixin {
    //fix for https://github.com/ghen-git/Thirst-Mod/issues/209
//    @Inject(method = "finishUsingItem", at = @At("TAIL"))
//    public void onFinishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving, CallbackInfoReturnable<ItemStack> cir, @Local Player player)
//    {
//        if(Config.FIX_209.get() && player != null)
//        {
//            PlayerThirst.drink(stack, player);
//        }
//    }
}
