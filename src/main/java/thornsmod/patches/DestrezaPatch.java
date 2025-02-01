package thornsmod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import thornsmod.ThornsMod;
import thornsmod.character.ThornsCharacter;
import thornsmod.modes.DestrezaMode;

public class DestrezaPatch {
    public static AbstractPlayer playerRender;
    public static Hitbox destrezaBar;
    //public static float hideSPTimer;

    private static final UIStrings uiStrings;
    public static final float BAR_Y_OFFSET_DIST;
    public static final float BAR_HEIGHT;
    public static final float BG_OFFSET_X;
    public static final float TEXT_OFFSET_Y;
    public static final float TIP_X_THRESHOLD;
    public static final float TIP_OFFSET_Y;
    public static final float TIP_OFFSET_R_X;
    public static final float TIP_OFFSET_L_X;
    public static final Color ShadowColor;
    public static final Color BGColor;
    public static final Color BarColor;
    public static final Color BarActiveColor;

    public DestrezaPatch() {}

    /*private static void updateDestrezaHoverFade() {
        if (DestrezaPatch.destrezaBar.hovered) {
            DestrezaPatch.hideSPTimer -= Gdx.graphics.getDeltaTime() * 4.0F;
            if (DestrezaPatch.hideSPTimer < 0.2F) {
                DestrezaPatch.hideSPTimer = 0.2F;
            }
        } else {
            DestrezaPatch.hideSPTimer += Gdx.graphics.getDeltaTime() * 4.0F;
            if (DestrezaPatch.hideSPTimer > 1.0F) {
                DestrezaPatch.hideSPTimer = 1.0F;
            }
        }
    }*/

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ThornsMod.makeID("DestrezaBar"));
        BAR_Y_OFFSET_DIST = 8.0f * Settings.scale;
        BAR_HEIGHT = 16.0f * Settings.scale;
        BG_OFFSET_X = 31.0f * Settings.scale;
        TEXT_OFFSET_Y = -23.0f * Settings.scale;
        TIP_X_THRESHOLD = 1544.0F * Settings.scale;
        TIP_OFFSET_Y = -12.0f * Settings.scale;
        TIP_OFFSET_R_X = 20.0F * Settings.scale;
        TIP_OFFSET_L_X = -380.0F * Settings.scale;
        ShadowColor = new Color(0, 0, 0, 0.5f);
        BGColor = new Color(0, 0, 0, 0.5f);
        BarColor = new Color(0.65f, 0.75f, 0.35f, 1.0f);
        BarActiveColor = new Color(0.8f, 0.6f, 0.2f, 1.0f);
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard",
            paramtypez = {AbstractCard.class, AbstractMonster.class, int.class}
    )
    public static class PlayerUseCardPatch {
        public PlayerUseCardPatch() {}

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance, AbstractCard c, AbstractMonster m, int energyOnUse) {
            if (__instance instanceof ThornsCharacter) {
                // destreza update
                if (!c.dontTriggerOnUseCard) {
                    if (c.type == AbstractCard.CardType.ATTACK && !ThornsCharacter.destrezaActive) {
                        ++ThornsCharacter.destrezaAttackCounter;
                    }

                    // destreza aoe damage
                    if (ThornsCharacter.destrezaActive && c.type == AbstractCard.CardType.ATTACK) {
                        DamageInfo info = new DamageInfo(__instance, Math.round(c.damage * DestrezaMode.DESTREZA_AOE), DamageInfo.DamageType.THORNS);

                        if (m != null && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                            for (AbstractMonster other : AbstractDungeon.getMonsters().monsters) {
                                if (other != m && !other.isDeadOrEscaped()) {
                                    AbstractDungeon.actionManager.addToBottom(new SFXAction(ThornsMod.makeID("DESTREZA_ATTACK")));
                                    AbstractDungeon.actionManager.addToBottom(new DamageAction(other, info, AbstractGameAction.AttackEffect.NONE));
                                }
                            }

                            // AbstractDungeon.actionManager.addToBottom(new DamageAllButOneEnemyAction(source, target, amount.stream().mapToInt(i -> i).toArray(), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                        }
                    }
                }

                // destreza update
                if (ThornsCharacter.destrezaAttackCounter == ThornsCharacter.DESTREZA_COST) {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(new DestrezaMode()));
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "refreshHitboxLocation"
    )
    public static class CreatureHitboxLocationPatch {
        public CreatureHitboxLocationPatch() {}

        @SpirePostfixPatch
        public static void Postfix(AbstractCreature __instance) {
            if (__instance instanceof ThornsCharacter && AbstractDungeon.player != null) {
                if (DestrezaPatch.destrezaBar == null) {
                    DestrezaPatch.destrezaBar = new Hitbox(__instance.hb_w, 24.0f * Settings.scale);
                }

                DestrezaPatch.destrezaBar.move(__instance.hb.cX, __instance.hb.cY - __instance.hb_h / 2.0F);
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "update"
    )
    public static class PlayerUpdatePatch {
        public PlayerUpdatePatch() {}

        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance) {
            if (__instance instanceof ThornsCharacter && AbstractDungeon.player != null && DestrezaPatch.destrezaBar != null) {
                DestrezaPatch.destrezaBar.update();
                //DestrezaPatch.updateDestrezaHoverFade();
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "render",
            paramtypez = {SpriteBatch.class}
    )
    public static class PlayerRenderPatch {
        public PlayerRenderPatch() {}

        @SpirePostfixPatch
        public static void Postfix(AbstractCreature __instance, SpriteBatch sb) {
            if (__instance instanceof ThornsCharacter && AbstractDungeon.player != null && (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && !__instance.isDead) {
                if (!Settings.hideCombatElements) {
                    if (!__instance.isDeadOrEscaped()) {
                        if (DestrezaPatch.destrezaBar != null) {
                            DestrezaPatch.destrezaBar.render(sb);
                        }

                        float x = __instance.hb.cX - (__instance.hb.width / 2.0f);
                        float y = __instance.hb.cY - (__instance.hb.height / 2.0f) + (5.0f * DestrezaPatch.BAR_Y_OFFSET_DIST - DestrezaPatch.BAR_HEIGHT);

                        sb.setColor(DestrezaPatch.ShadowColor.cpy());
                        sb.draw(ImageMaster.HB_SHADOW_L, x - DestrezaPatch.BAR_HEIGHT, y - DestrezaPatch.BG_OFFSET_X + (3.0f * Settings.scale), DestrezaPatch.BAR_HEIGHT, DestrezaPatch.BAR_HEIGHT);
                        sb.draw(ImageMaster.HB_SHADOW_B, x, y - DestrezaPatch.BG_OFFSET_X + (3.0f * Settings.scale), __instance.hb.width, DestrezaPatch.BAR_HEIGHT);
                        sb.draw(ImageMaster.HB_SHADOW_R, x + __instance.hb.width, y - DestrezaPatch.BG_OFFSET_X + (3.0f * Settings.scale), DestrezaPatch.BAR_HEIGHT, DestrezaPatch.BAR_HEIGHT);

                        sb.setColor(DestrezaPatch.BGColor.cpy());
                        sb.draw(ImageMaster.HEALTH_BAR_L, x - DestrezaPatch.BAR_HEIGHT, y - DestrezaPatch.BG_OFFSET_X, DestrezaPatch.BAR_HEIGHT, DestrezaPatch.BAR_HEIGHT);
                        sb.draw(ImageMaster.HEALTH_BAR_B, x, y - DestrezaPatch.BG_OFFSET_X, __instance.hb.width, DestrezaPatch.BAR_HEIGHT);
                        sb.draw(ImageMaster.HEALTH_BAR_R, x + __instance.hb.width, y - DestrezaPatch.BG_OFFSET_X, DestrezaPatch.BAR_HEIGHT, DestrezaPatch.BAR_HEIGHT);

                        float barValue;
                        if (ThornsCharacter.destrezaActive) {
                            sb.setColor(DestrezaPatch.BarActiveColor.cpy());
                            barValue = __instance.hb.width;
                        } else {
                            sb.setColor(DestrezaPatch.BarColor.cpy());
                            barValue = ((float) ThornsCharacter.destrezaAttackCounter / (float) ThornsCharacter.DESTREZA_COST) * __instance.hb.width;
                        }

                        sb.draw(ImageMaster.HEALTH_BAR_L, x - DestrezaPatch.BAR_HEIGHT, y - DestrezaPatch.BG_OFFSET_X + 3.0F * Settings.scale, DestrezaPatch.BAR_HEIGHT, DestrezaPatch.BAR_HEIGHT);
                        sb.draw(ImageMaster.HEALTH_BAR_B, x, y - DestrezaPatch.BG_OFFSET_X + 3.0F * Settings.scale, barValue, DestrezaPatch.BAR_HEIGHT);
                        sb.draw(ImageMaster.HEALTH_BAR_R, x + barValue, y - DestrezaPatch.BG_OFFSET_X + 3.0F * Settings.scale, DestrezaPatch.BAR_HEIGHT, DestrezaPatch.BAR_HEIGHT);

                        /*Color spTextColor = Color.WHITE.cpy();
                        spTextColor.a = DestrezaPatch.hideSPTimer;
                        if (!ThornsCharacter.destrezaActive) {
                            FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, ThornsCharacter.destrezaAttackCounter + "/" + ThornsCharacter.DESTREZA_COST, __instance.hb.cX, y + DestrezaPatch.TEXT_OFFSET_Y, spTextColor);
                        }*/

                        if (DestrezaPatch.destrezaBar.hovered && !AbstractDungeon.isScreenUp) {
                            if (!ThornsCharacter.destrezaActive) {
                                TipHelper.renderGenericTip(__instance.hb.cX + __instance.hb.width / 2.0F + TIP_OFFSET_R_X, __instance.hb.cY + TIP_OFFSET_Y, uiStrings.TEXT[0], uiStrings.TEXT[1] + ThornsCharacter.destrezaAttackCounter + uiStrings.TEXT[2]);
                            } else {
                                TipHelper.renderGenericTip(__instance.hb.cX + __instance.hb.width / 2.0F + TIP_OFFSET_R_X, __instance.hb.cY + TIP_OFFSET_Y, uiStrings.TEXT[0], ThornsCharacter.timesDestrezaEntered <= 1 ? uiStrings.TEXT[3] : uiStrings.TEXT[4]);
                            }
                        }
                    }

                    sb.setColor(Color.WHITE);
                }
            }
        }
    }
}
