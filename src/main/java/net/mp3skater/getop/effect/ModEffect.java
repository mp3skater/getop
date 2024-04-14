package net.mp3skater.getop.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mp3skater.getop.GetOP;

public class ModEffect {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GetOP.MOD_ID);

    public static final RegistryObject<MobEffect> FREEZE = MOB_EFFECTS.register("freeze",
            () -> new FreezeEffect(MobEffectCategory.HARMFUL, 3124690));

    public static final RegistryObject<MobEffect> FEATHER_FALL_EFFECT = MOB_EFFECTS.register("feather_fall_effect",
            () -> new FeatherfallEffect(MobEffectCategory.BENEFICIAL, 177857));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
