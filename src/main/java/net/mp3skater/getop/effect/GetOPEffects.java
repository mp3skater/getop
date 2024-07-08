package net.mp3skater.getop.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mp3skater.getop.GetOP;

public class GetOPEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GetOP.MOD_ID);

    public static final RegistryObject<MobEffect> FREEZE = MOB_EFFECTS.register("freeze",
            () -> new FreezeEffect(MobEffectCategory.HARMFUL, 3124690));

    public static final RegistryObject<MobEffect> FEATHER_FALL_EFFECT = MOB_EFFECTS.register("feather_fall_effect",
            () -> new FeatherfallEffect(MobEffectCategory.BENEFICIAL, 177857));

    public static final RegistryObject<MobEffect> GUM_SKIN_EFFECT = MOB_EFFECTS.register("gum_skin_effect",
            () -> new GumSkinEffect(MobEffectCategory.BENEFICIAL, 0xc195c3));

    public static final RegistryObject<MobEffect> HARDENED_SKIN_EFFECT = MOB_EFFECTS.register("hardened_skin_effect",
            () -> new HardenedSkinEffect(MobEffectCategory.BENEFICIAL, 0x363a4b));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
