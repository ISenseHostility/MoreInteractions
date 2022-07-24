package isensehostility.interactions.event;

import isensehostility.interactions.Interactions;
import isensehostility.interactions.config.InteractionsConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Interactions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCommon {

    @SubscribeEvent
    public static void onAttributesModified(EntityAttributeModificationEvent event) {
        double squidDamage = InteractionsConfig.squidDamage.get();

        event.add(EntityType.SQUID, Attributes.ATTACK_DAMAGE, squidDamage);
    }
}
