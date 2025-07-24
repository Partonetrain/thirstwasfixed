package info.partonetrain.thirstwasfixed;

import dev.ghen.thirst.foundation.common.capability.IThirst;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class ParCoolHelper {

    public static AttributeModifier staminaBonus = null;
    public static AttributeModifier staminaPenalty = null;

    public static void initModifiers(){
        staminaBonus = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(ThirstWasFixedMod.MODID, "stamina_bonus"), Config.PARCOOL_STAMINA_BONUS_VALUE.get(), AttributeModifier.Operation.ADD_VALUE);
        staminaPenalty = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(ThirstWasFixedMod.MODID, "stamina_penalty"), Config.PARCOOL_STAMINA_PENALTY_VALUE.get(), AttributeModifier.Operation.ADD_VALUE);
    }

    public static void applyModifiers(Player player, IThirst thirst){
        if(Config.PARCOOL_STAMINA_BONUS_REQUIREMENT.get() != 0){
            if(thirst.getThirst() >= Config.PARCOOL_STAMINA_BONUS_REQUIREMENT.get()){
                if(player.getAttribute(com.alrex.parcool.api.Attributes.STAMINA_RECOVERY).getModifier(staminaBonus.id()) != null){
                }else{
                    player.getAttribute(com.alrex.parcool.api.Attributes.STAMINA_RECOVERY).addPermanentModifier(staminaBonus);
                }
            }
            else{
                if(player.getAttribute(com.alrex.parcool.api.Attributes.STAMINA_RECOVERY).getModifier(staminaBonus.id()) == null){
                }
                else{
                    player.getAttribute(com.alrex.parcool.api.Attributes.STAMINA_RECOVERY).removeModifier(staminaBonus.id());
                }
            }
        }

        if(Config.PARCOOL_STAMINA_PENALTY_REQUIREMENT.get() != 0){
            if(thirst.getThirst() < Config.PARCOOL_STAMINA_PENALTY_REQUIREMENT.get()){
                if(player.getAttribute(com.alrex.parcool.api.Attributes.STAMINA_RECOVERY).getModifier(staminaPenalty.id()) != null){
                }else{
                    player.getAttribute(com.alrex.parcool.api.Attributes.STAMINA_RECOVERY).addPermanentModifier(staminaPenalty);
                }
            }
            else{
                if(player.getAttribute(com.alrex.parcool.api.Attributes.STAMINA_RECOVERY).getModifier(staminaPenalty.id()) == null){
                }
                else{
                    player.getAttribute(com.alrex.parcool.api.Attributes.STAMINA_RECOVERY).removeModifier(staminaPenalty.id());
                }
            }
        }
    }
}
