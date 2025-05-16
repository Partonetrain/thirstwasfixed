package info.partonetrain.thirstwasfixed.mixin.ftbum;

import dev.architectury.event.EventResult;
import dev.architectury.utils.value.IntValue;
import dev.ftb.mods.ftbultimine.FTBUltimine;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModAttachment;
import info.partonetrain.thirstwasfixed.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FTBUltimine.class)
public class FTBUltimineMixin {
//    @Inject(method = "blockBroken", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;causeFoodExhaustion(F)V"), cancellable = true)
//    public void thirstwasfixed$blockBroken(Level world, BlockPos pos, BlockState state, ServerPlayer player, IntValue xp, CallbackInfoReturnable<EventResult> cir){
//        IThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
//        thirst.addExhaustion(player, Config.ULTIMINE_USES_THIRST.get() * 0.005F);
//        if(thirstwasfixed$isTooThirsty(player)){
//            cir.setReturnValue(EventResult.interruptFalse());
//        }
//    }
//
//    @Unique
//    private static boolean thirstwasfixed$isTooThirsty(ServerPlayer player) {
//        IThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
//        return thirst.getThirst() < Config.ULTIMINE_REQUIRES_THIRST.get();
//    }
}
