package isensehostility.interactions.entity.ai;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.player.Player;

public class SquidAttackGoal extends MeleeAttackGoal {
    private final Squid squid;

    public SquidAttackGoal(Squid squid, double speed, boolean hasToSee) {
        super(squid, speed, hasToSee);
        this.squid = squid;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target, double distance) {
        double d0 = this.getAttackReachSqr(target);
        if (distance <= d0 && this.getTicksUntilNextAttack() <= 0) {
            this.resetAttackCooldown();
            this.mob.swing(InteractionHand.MAIN_HAND);
            if (target instanceof Player player) {
                if (player.getVehicle() != null) {
                    player.getVehicle().hurt(DamageSource.mobAttack(squid), (float) squid.getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
            } else {
                this.mob.doHurtTarget(target);
            }
        }
    }
}
