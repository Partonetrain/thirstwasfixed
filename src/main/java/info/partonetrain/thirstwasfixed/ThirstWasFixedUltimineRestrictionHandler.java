package info.partonetrain.thirstwasfixed;

import dev.ftb.mods.ftbultimine.api.restriction.RestrictionHandler;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModAttachment;
import net.minecraft.world.entity.player.Player;

public class ThirstWasFixedUltimineRestrictionHandler implements RestrictionHandler {

    public ThirstWasFixedUltimineRestrictionHandler() {
        ThirstWasFixedMod.LOGGER.info("FTB Ultimine Restriction Handler loaded");
	}

    @Override
    public boolean canUltimine(Player player) {
        IThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
        if(thirst.getThirst() < Config.ULTIMINE_REQUIRES_THIRST.get()){
            return false;
        }
        return true;
    }
}
