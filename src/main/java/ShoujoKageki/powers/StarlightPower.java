package ShoujoKageki.powers;


import ShoujoKagekiCore.shine.DisposableVariable;
import ShoujoKagekiCore.base.BasePower;
import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static ShoujoKageki.ModInfo.makeID;

public class StarlightPower extends BasePower {
    public static final Logger logger = LogManager.getLogger(StarlightPower.class.getName());

    private static final String RAW_ID = StarlightPower.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public StarlightPower(int amount) {
        super(POWER_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        if (DisposableVariable.isDisposableCard(card)) {
            addToBot(new DrawCardAction(amount));
            this.flash();
        }
    }
}
