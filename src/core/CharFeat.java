package core;

import entity.FeatEntity;

public class CharFeat {

	private FeatEntity feat; 
	private int count = 1;
	private String special;
	
	public CharFeat(FeatEntity feat) {
		setFeat(feat);
	}
	
	public CharFeat(FeatEntity feat, int count) {
		setFeat(feat);
		setCount(count);
	}
	
	public CharFeat(FeatEntity feat, String special) {
		setFeat(feat);
		setSpecial(special);
	}
	
	public CharFeat(FeatEntity feat, String special, int count) {
		setFeat(feat);
		setSpecial(special);
		setCount(count);
	}

	public FeatEntity getFeat() {
		return feat;
	}

	public void setFeat(FeatEntity feat) {
		this.feat = feat;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void incCount() {
		this.count++;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}
}
