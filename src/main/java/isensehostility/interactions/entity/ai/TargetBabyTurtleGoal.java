package isensehostility.interactions.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Turtle;

public class TargetBabyTurtleGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public TargetBabyTurtleGoal(Mob attacker, Class<T> target, boolean hasToSee) {
        super(attacker, target, hasToSee);
    }

    @Override
    protected void findTarget() {
        this.target = this.mob.level.getNearestEntity(this.mob.level.getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()),
                entity -> entity instanceof Turtle && entity.isBaby()),
                this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }
}
