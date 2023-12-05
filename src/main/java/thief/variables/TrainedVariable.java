package thief.variables;

import static thief.ModInfo.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;

public class TrainedVariable extends DynamicVariable {

	@Override
	public int baseValue(AbstractCard card) {
		return card.baseDamage;
	}

	

	@Override
	public String key() {
        return makeID("Trained");
	}

	
	@Override
	public boolean isModified(AbstractCard arg0) {
		return false;
	}

	@Override
	public int value(AbstractCard arg0) {
		return 0;
	}



	@Override
	public boolean upgraded(AbstractCard var1) {
		// TODO Auto-generated method stub
		return false;
	}

}
