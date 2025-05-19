package info.partonetrain.thirstwasfixed;

import dev.ftb.mods.ftbultimine.integration.FTBUltiminePlugin;
import dev.ftb.mods.ftbultimine.integration.FTBUltiminePlugins;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModAttachment;
import net.minecraft.world.entity.player.Player;

public class ThirstWasFixedUltiminePlugin implements FTBUltiminePlugin {


    public ThirstWasFixedUltiminePlugin() {
        //requiredThirst = Config.ULTIMINE_REQUIRES_THIRST.get();
        ThirstWasFixedMod.LOGGER.info("FTB Ultimine Plugin loaded");
	}

    @Override
    public void init() {
        //called from FTBUltiminePlugins::init
        //for some reason this isn't called in production but is in dev ??
        //ThirstWasFixedMod.LOGGER.info("init requiredThirst = " + requiredThirst);
        //ThirstWasFixedMod.LOGGER.info("now requiredThirst = " + requiredThirst);
    }

    @Override
    public boolean canUltimine(Player player) {
        IThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
        //ThirstWasFixedMod.LOGGER.info("thirst = " + thirst.getThirst() + ", requiredThirst = " + requiredThirst);
        if(thirst.getThirst() < Config.ULTIMINE_REQUIRES_THIRST.get()){
            //ThirstWasFixedMod.LOGGER.info("false");
            return false;
        }
        //ThirstWasFixedMod.LOGGER.info("true");
        return true;
    }
}
