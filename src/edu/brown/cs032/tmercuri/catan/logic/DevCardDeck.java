package edu.brown.cs032.tmercuri.catan.logic;

import java.util.*;

public class DevCardDeck {
	
	/**
	 * knight = 0;
	 * roadbuilder = 1;
	 * year of plenty = 2;
	 * monopoly = 3;
	 * vp = 4;
	 */
	private List<Integer> _cards;
	
	public DevCardDeck(){
		_cards = new ArrayList<Integer>(25);
		for(int i = 0; i < 25; i++){
			if(i < 14){
				_cards.add(0);
			}
			else if(i < 19){
				_cards.add(4);
			}
			else if(i < 21){
				_cards.add(1);
			}
			else if(i < 23){
				_cards.add(2);
			}
			else{
				_cards.add(3);
			}
		}
	}
	
	public int getCard(){
		return _cards.remove((int)Math.random()*_cards.size());
	}

}
