package info.partonetrain.thirstwasfixed;

import dev.ftb.mods.ftbultimine.integration.FTBUltiminePlugin;
import dev.ftb.mods.ftbultimine.integration.FTBUltiminePlugins;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModAttachment;
import net.minecraft.world.entity.player.Player;

public class ThirstWasFixedUltiminePlugin implements FTBUltiminePlugin {

    public static int requiredThirst = 0;

    public ThirstWasFixedUltiminePlugin() {
	}

    @Override
    public void init() {
        //called from FTBUltiminePlugins::init
        requiredThirst = Config.ULTIMINE_REQUIRES_THIRST.get();
    }

    @Override
    public boolean canUltimine(Player player) {
        IThirst thirst = player.getData(ModAttachment.PLAYER_THIRST);
        if(thirst.getThirst() < requiredThirst){
            return false;
        }
        return true;
    }
}
