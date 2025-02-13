import java.util.*;

public abstract class Player{       //abstract player class because we have bots and users
    protected ArrayList<Card> hand;
    protected String name;
    
    public Player(String name){
        this.name = name;
        this.hand = new ArrayList<>();
    }
    
    public String viewHand(){    //go through each card in player's hand and print it in one string
        String handStr = "";
        int index = 0;
        for(Card card : hand){
            handStr += card.toString()+" - Index: "+index+" | ";
            index++;
        }
        return handStr;
    }

    public String getName(){
        return this.name;
    }
    public ArrayList<Card> getHand(){
        return this.hand;
    }

 
    public abstract void discNDraw(Deck deck);
    
    public void clearHand(){     
        hand.clear();
    }
}