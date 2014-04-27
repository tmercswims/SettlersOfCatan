package edu.brown.cs032.tmercuri.catan.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DevCardDeck {
	
	/**
	 * knight = 0;
	 * roadbuilder = 1;
	 * year of plenty = 2;
	 * monopoly = 3;
	 * vp = 4;
	 */
	private final List<Integer> _cards;
	
	public DevCardDeck(){
		_cards = new ArrayList<>(25);
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
        Collections.shuffle(_cards);
	}
	
	public int getCard(){
		if (!_cards.isEmpty()) return _cards.remove(0);
        return -1;
	}

}
