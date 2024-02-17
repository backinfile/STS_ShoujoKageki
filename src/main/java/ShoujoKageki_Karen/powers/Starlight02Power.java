package ShoujoKageki_Karen.powers;


import ShoujoKageki.base.BasePower;
import ShoujoKageki_Karen.variables.DisposableVariable;
import ShoujoKageki_Karen.variables.patch.DisposableField;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static ShoujoKageki_Karen.KarenPath.makeID;

public class Starlight02Power extends BasePower {
    public static final Logger logger = LogManager.getLogger(Starlight02Power.class.getName());

    private static final String RAW_ID = Starlight02Power.class.getSimpleName();
    public static final String POWER_ID = makeID(RAW_ID);

    public Starlight02Power(int amount) {
        super(POWER_ID, RAW_ID, PowerType.BUFF, AbstractDungeon.player, AbstractDungeon.player, amount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.purgeOnUse) return;
        if (!DisposableVariable.isDisposableCard(card)) return;

        DisposableField.forceDispose.set(card, true);

        for (int i = 0; i < amount; i++) {
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster) action.target;
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 250.0F * Settings.scale * (i + 1);
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }
            tmp.applyPowers();
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
        }
    }
}
