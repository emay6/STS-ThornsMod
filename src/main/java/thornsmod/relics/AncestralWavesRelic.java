package thornsmod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import thornsmod.ThornsMod;
import thornsmod.character.ThornsCharacter;

public class AncestralWavesRelic extends BaseRelic {
    private static final String NAME = "AncestralWavesRelic";
    public static final String ID = ThornsMod.makeID(NAME);
    private static final int HP_GAINED = 6;

    private boolean prevRoomNotCombat;

    public AncestralWavesRelic() {
        super(ID, NAME, ThornsCharacter.Meta.CARD_COLOR, RelicTier.STARTER, LandingSound.MAGICAL);
        this.prevRoomNotCombat = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + HP_GAINED + DESCRIPTIONS[1];
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        if (this.prevRoomNotCombat) {
            this.flash();

            // no clue what this does just copying ironclad
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            AbstractPlayer p = AbstractDungeon.player;
            if (p.currentHealth > 0) {
                p.heal(6);
            }
        }

        this.prevRoomNotCombat = !(AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom);
    }

    public AbstractRelic makeCopy() {
        return new AncestralWavesRelic();
    }
}
