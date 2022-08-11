package isensehostility.interactions.event;

import isensehostility.interactions.Interactions;
import isensehostility.interactions.config.InteractionsConfig;
import isensehostility.interactions.entity.ai.*;
import isensehostility.interactions.enums.Severity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Interactions.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommon {

    @SubscribeEvent
    public static void onEntityInteraction(PlayerInteractEvent.EntityInteract event) {
        boolean shearEnabled = InteractionsConfig.shearWithSwordEnabled.get();
        boolean pluckEnabled = InteractionsConfig.pluckFeathersEnabled.get();
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();

        if (shearEnabled) {
            Entity target = event.getTarget();

            if (isSheep(target) && isSword(stack)) {
                LivingEntity sheep = (LivingEntity) target;

                if (sheep.hurtTime == 0 && sheep.isAlive()) {
                    if (sheep instanceof Sheep mcSheep && !mcSheep.isSheared()) {
                        mcSheep.shear(SoundSource.PLAYERS);
                        sheep.hurt(DamageSource.playerAttack(player), 1);
                        stack.setDamageValue(stack.getDamageValue() + 1);
                    }

                    else if (!(sheep instanceof Sheep)) {
                        sheep.spawnAtLocation(new ItemStack(Items.WHITE_WOOL, 2));
                        sheep.hurt(DamageSource.playerAttack(player), 1);
                        stack.setDamageValue(stack.getDamageValue() + 1);
                    }
                }
            }
        }

        if (pluckEnabled) {
            Entity target = event.getTarget();

            if (isChicken(target) && isShears(stack)) {
                LivingEntity chicken = (LivingEntity) target;

                if (chicken.hurtTime == 0 && chicken.isAlive()) {
                    chicken.spawnAtLocation(new ItemStack(Items.FEATHER));
                    chicken.hurt(DamageSource.playerAttack(player), 1);
                    stack.setDamageValue(stack.getDamageValue() + 1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemUsed(LivingEntityUseItemEvent.Finish event) {
        boolean foodPoisoningEnabled = InteractionsConfig.foodPoisoningEnabled.get();

        if (foodPoisoningEnabled) {
            double foodPoisoningTime = InteractionsConfig.foodPoisoningTime.get();
            int foodPoisoningChance = InteractionsConfig.foodPoisoningChance.get();
            Severity foodPoisoningSeverity = InteractionsConfig.foodPoisoningSeverity.get();
            LivingEntity entity = event.getEntity();
            ItemStack item = event.getItem();

            if (entity instanceof Player player && !player.isCreative() && item.is(Interactions.Tags.GIVES_FOOD_POISONING) && entity.getRandom().nextInt(100) < foodPoisoningChance) {
                if (foodPoisoningSeverity == Severity.AUTO) {
                    switch (entity.getLevel().getDifficulty()) {
                        case EASY -> applyFoodPoisoning(entity, foodPoisoningTime, Severity.WEAK);
                        case NORMAL -> applyFoodPoisoning(entity, foodPoisoningTime, Severity.MILD);
                        case HARD -> applyFoodPoisoning(entity, foodPoisoningTime, Severity.SEVERE);
                        default -> {

                        }
                    }
                } else {
                    applyFoodPoisoning(entity, foodPoisoningTime, foodPoisoningSeverity);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntitySpawned(EntityJoinLevelEvent event) {
        boolean squidTargetTurtleEnabled = InteractionsConfig.squidTargetTurtleEnabled.get();
        boolean squidTargetBoatEnabled = InteractionsConfig.squidTargetBoatEnabled.get();

        if (squidTargetTurtleEnabled || squidTargetBoatEnabled) {
            if (event.getEntity() instanceof Squid squid) {
                squid.goalSelector.removeAllGoals();
                squid.goalSelector.addGoal(0, new SquidMoveToTargetGoal(squid));
                squid.goalSelector.addGoal(1, new SquidAttackGoal(squid, 2.0D, true));
                squid.goalSelector.addGoal(2, new SquidRandomMoveGoal(squid));
                squid.goalSelector.addGoal(3, new SquidFleeGoal(squid));
                if (squidTargetTurtleEnabled) {
                    squid.targetSelector.addGoal(0, new TargetBabyTurtleGoal<>(squid, Turtle.class, false));
                }
                if (squidTargetBoatEnabled) {
                    squid.targetSelector.addGoal(1, new TargetBoatGoal<>(squid, Player.class, false));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerHitBlock(PlayerInteractEvent.LeftClickBlock event) {
        boolean touchFireEnabled = InteractionsConfig.touchFireEnabled.get();
        double touchFireTime = InteractionsConfig.touchFireTime.get();
        boolean touchCactusEnabled = InteractionsConfig.touchCactusEnabled.get();
        double touchCactusDamage = InteractionsConfig.touchCactusDamage.get();
        Player player = event.getEntity();

        if (touchFireEnabled) {
            if (player.getLevel().getBlockState(event.getPos()).getBlock() == Blocks.FIRE && player.getMainHandItem().getItem() == Items.AIR) {
                player.setRemainingFireTicks((int) (touchFireTime * 20));
            }
        }

        if (touchCactusEnabled) {
            if (player.getLevel().getBlockState(event.getPos()).getBlock() == Blocks.CACTUS && player.getMainHandItem().getItem() == Items.AIR) {
                player.hurt(DamageSource.CACTUS, (float) touchCactusDamage);
            }
        }
    }

    @SubscribeEvent
    public static void onItemBroken(PlayerDestroyItemEvent event) {
        boolean brokenToolDropsEnabled = InteractionsConfig.brokenToolDropsEnabled.get();
        ItemStack originalStack = event.getOriginal();

        if (brokenToolDropsEnabled) {
            if (originalStack.getItem() instanceof TieredItem item) {
                ItemStack[] itemsToDrop = item.getTier().getRepairIngredient().getItems();

                event.getEntity().spawnAtLocation(itemsToDrop[0]);
            }
        }
    }

    private static void applyFoodPoisoning(LivingEntity target, double timeInSeconds, Severity severity) {
        target.addEffect(new MobEffectInstance(MobEffects.HUNGER, (int) (timeInSeconds * 20), severity.getEffectStrength()));
    }

    private static boolean isSheep(Entity entity) {
        return entity instanceof Sheep || Interactions.Tags.isEntityInTag(entity, Interactions.Tags.IS_SHEEP);
    }

    private static boolean isChicken(Entity entity) {
        return entity instanceof Chicken || Interactions.Tags.isEntityInTag(entity, Interactions.Tags.IS_CHICKEN);
    }

    private static boolean isSword(ItemStack stack) {
        return stack.getItem() instanceof SwordItem || stack.is(Interactions.Tags.IS_SWORD) || stack.canPerformAction(ToolActions.SWORD_DIG);
    }

    private static boolean isShears(ItemStack stack) {
        return stack.getItem() instanceof ShearsItem || stack.is(Interactions.Tags.IS_SHEARS) || stack.canPerformAction(ToolActions.SHEARS_HARVEST);
    }
}
