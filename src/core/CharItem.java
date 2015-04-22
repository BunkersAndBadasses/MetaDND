package core;

import entity.ItemEntity;

public class CharItem {
	private ItemEntity item;
	private int count;
	
	public CharItem(ItemEntity i) {
		item = i;
		count = 1;
	}
	
	public CharItem(ItemEntity i, int c) {
		item = i;
		count = c;
	}
	
	public void incCount() {
		count++;
	}
	
	public void incCountBy(int inc) {
		count += inc;
	}
	
	public void setCount(int i) {
	    count = i;
	}
	
	public boolean decCount() {
		if (count == 1)
			return false;
		count--;
		return true;
	}
	
	public String getName() {
		return item.getName();
	}
	
	public ItemEntity getItem() {
		return item;
	}
	
	public int getCount() {
		return count;
	}
	
}
