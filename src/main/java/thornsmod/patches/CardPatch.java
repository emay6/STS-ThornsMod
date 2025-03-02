package thornsmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ThornsPower;
import thornsmod.ThornsMod;

public class CardPatch {
    public CardPatch() {}

    @SpirePatch(
            clz = ThornsPower.class,
            method = "onAttacked"
    )
    public static class ThornsPowerPatch {
        public ThornsPowerPatch() {}

        public static int Replace(ThornsPower __instance, DamageInfo info, int damageAmount) {
            if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != __instance.owner) {
                __instance.flash();
                if (__instance.owner.hasPower(ThornsMod.makeID("SeaOfThorns"))) {
                    AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction((AbstractPlayer) __instance.owner, __instance.amount, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                } else {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, new DamageInfo(__instance.owner, __instance.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }
            }

            return damageAmount;
        }
    }
}
