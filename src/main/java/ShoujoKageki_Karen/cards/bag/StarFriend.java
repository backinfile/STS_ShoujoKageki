package ShoujoKageki_Karen.cards.bag;

import ShoujoKageki_Karen.KarenPath;
import ShoujoKageki.base.BaseCard;
import ShoujoKageki_Karen.powers.ShineRewardPower;
import ShoujoKageki_Karen.reward.ShineCardReward;
import ShoujoKageki_Karen.variables.DisposableVariable;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

public class StarFriend extends BaseCard {

    public static final String ID = KarenPath.makeID(StarFriend.class.getSimpleName());

    public StarFriend() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.baseMagicNumber = this.magicNumber = 1;
        DisposableVariable.setBaseValue(this, LOW_SHINE_CNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster target = m;
        addToBot(new DamageCallbackAction(target, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, hp -> {
            if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower(MinionPower.POWER_ID)) {
                flash();
                if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    ShineCardReward.addShineCardRewardToRoom();
                } else {
                    addToBot(new ApplyPowerAction(p, p, new ShineRewardPower()));
                }
            }
        }));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }
}
