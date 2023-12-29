package ShoujoKageki.cards.bag;

import ShoujoKageki.ModInfo;
import ShoujoKageki.actions.GainCardOrIgnoreAction;
import ShoujoKageki.cards.BaseCard;
import ShoujoKageki.util.Utils2;
import ShoujoKageki.variables.DisposableVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class Sunlight extends BaseCard {

    public static final String ID = ModInfo.makeID(Sunlight.class.getSimpleName());

    public Sunlight() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 9;
        DisposableVariable.setBaseValue(this, LOW_SHINE_CNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }

    @Override
    public void triggerOnDisposed() { // 特殊处理，最后一击击杀敌人
        super.triggerOnDisposed();

        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
//            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
//            } else {
//                RewardItem cardReward = new RewardItem();
//                cardReward.cards.clear();
//                cardReward.cards.add(Utils2.makeCardCopyOnlyWithUpgrade(this));
//                cardReward.text = cardStrings.EXTENDED_DESCRIPTION[0];
//                AbstractDungeon.getCurrRoom().addCardReward(cardReward);
//            }

            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(Utils2.makeCardCopyOnlyWithUpgrade(this),
                    Settings.WIDTH / 2f, Settings.HEIGHT / 2f));
        } else {
            addToBot(new GainCardOrIgnoreAction(this.makeStatEquivalentCopy()));
        }
    }
}
