package info.partonetrain.thirstwasfixed.mixin.a_n;

import cn.mlus.thirst.content.thirst.PlayerThirst;
import com.hollingsworth.arsnouveau.common.network.PacketConsumePotion;
import cn.mlus.thirst.foundation.common.capability.ModAttachment;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PacketConsumePotion.class)
public class PacketConsumePotionMixin {

    //regular potion
    @Inject(method = "onServerReceived", at = @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/common/util/PotionUtil;applyContents(Lnet/minecraft/world/item/alchemy/PotionContents;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)V"))
    private void thirstwasfixed$onServerReceived(MinecraftServer minecraftServer, ServerPlayer player, CallbackInfo ci){
        if(Config.AN_ALCHEMISTS_CROWN.get()){
            PlayerThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
            if(thirst.getThirst() < 20){
                thirst.drink(6, 8);
            }
        }
    }

    //potion flask
    @Inject(method = "onServerReceived", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;finishUsingItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    private void thirstwasfixed$onServerReceived2(MinecraftServer minecraftServer, ServerPlayer player, CallbackInfo ci){
        if(Config.AN_ALCHEMISTS_CROWN.get()){
            PlayerThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
            if(thirst.getThirst() < 20){
                thirst.drink(6, 8);
            }
        }
    }
}
