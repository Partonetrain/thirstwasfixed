package info.partonetrain.thirstwasfixed.mixin;

import dev.ghen.thirst.content.thirst.PlayerThirst;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import info.partonetrain.thirstwasfixed.Config;
import info.partonetrain.thirstwasfixed.ParCoolHelper;
import info.partonetrain.thirstwasfixed.ThirstWasFixedMod;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerThirst.class)
public class PlayerThirstMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    public void thirstwasfixed$tick(Player player, CallbackInfo ci){
        IThirst self = (IThirst) (Object) this;
        if(Config.THIRST_BONUS_REQUIREMENT.get() != 0){
            if(self.getThirst() >= Config.THIRST_BONUS_REQUIREMENT.get()){
                if(player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(ThirstWasFixedMod.thirstSpeedModifier.id()) != null){
                    //ThirstWasFixedMod.LOGGER.info("Thirst bonus already applied");
                }else{
                    //ThirstWasFixedMod.LOGGER.info("Thirst bonus applied");
                    player.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(ThirstWasFixedMod.thirstSpeedModifier);
                }
            }
            else{
                if(player.getAttribute(Attributes.MOVEMENT_SPEED).getModifier(ThirstWasFixedMod.thirstSpeedModifier.id()) == null){
                    //ThirstWasFixedMod.LOGGER.info("Thirst bonus already removed");
                }
                else{
                    //ThirstWasFixedMod.LOGGER.info("Thirst bonus removed");
                    player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(ThirstWasFixedMod.thirstSpeedModifier.id());
                }
            }
        }
        if(ModList.get().isLoaded("parcool")){
            ParCoolHelper.applyModifiers(player, self);
        }
    }
}
