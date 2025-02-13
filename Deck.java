import java.util.ArrayList;
import java.util.Random;

public class Deck{
    private ArrayList<Card> cardDeck;
    
    public Deck(){
        cardDeck = new ArrayList<>();
        String[] suits = {"Diamonds", "Hearts", "Spades", "Clubs"};
        String[] ranks = {"Ace", "King", "Queen", "Jack", "10", "9", 
                            "8", "7", "6", "5", "4", "3", "2"};
                            
        //populate
        int value = 0;
        for(String suit : suits){
            for(String rank : ranks){
                cardDeck.add(new Card(suit,rank,value));
                value++;
            }
            //value = 0;
        }
        /* Testing cards in deck
        for(Card c : cardDeck){
            System.out.println(c);
        }
        */
    }
    
    public int findCardLinear(String suit, String rank){
        int index = 0;
        for(Card c : this.cardDeck){
            if(c.getSuit().equals(suit) && c.getRank().equals(rank)){
                return index;
            }
            index++;
        }
        return -1;
    }
    
    public int findOGLocation(String suit,String rank){
        int multiplier = 0;
        if(suit.equals("Diamonds")){
            multiplier = 0;
        }
        else if(suit.equals("Hearts")){
            multiplier = 1;
        }
        else if(suit.equals("Spades")){
            multiplier = 2;
        }
        else if(suit.equals("Clubs")){
            multiplier = 3;
        }
        
        int value = 0;
        
        if(rank.equals("Ace"))
            value = 0;
        else if(rank.equals("King"))
            value = 1;
        else if(rank.equals("Queen"))
            value = 2;
        else if(rank.equals("Jack"))
            value = 3;
        else{
            value = 13 - Integer.valueOf(rank) + 1; 
            System.out.println(value);
        }
        value = value + (multiplier * 13);
        return value;
    }
    
    public int findCardBinary(String suit, String rank){
        int low = 0;
        int high = cardDeck.size()-1;
        int targetValue = findOGLocation(suit,rank);
        
        while(low <= high){
            int mid = low + (high-low)/2;
            Card midCard = this.getCard(mid);
            
            if(midCard.getValue() == targetValue){
                return mid;
            }
            
            if(midCard.getValue() < targetValue){
                low = mid+1;
            }
            else{
                high = mid-1;
            }
        }
        return -1;
    }
    
    public Card getCard(int index){
        return this.cardDeck.get(index);
    }
    
    public void shuffleCards(){
        //create new arrLs
        ArrayList<Card> shufDeck = new ArrayList<>();
        int randIdx = 0;//loop & randomly select vals from og Deck
        for(int i = 0; i < this.cardDeck.size(); i++){
            randIdx = new Random().nextInt(this.cardDeck.size());
            if(randIdx < cardDeck.size()){
                shufDeck.add(cardDeck.get(randIdx));
                cardDeck.remove(randIdx);
                i--;
            }
        }
        for(Card card : shufDeck){
            this.cardDeck.add(card);
        }
        shufDeck.clear();
        //add them to new arrLs
    }
    
    public void displayDeck(){
        for(Card card : this.cardDeck){
            System.out.println(this.cardDeck.indexOf(card) + "\t-\t" + card.toString() +"\t Value -\t"+ card.getValue());
        }
    }
    
    public void resetDeck(){
        this.cardDeck.clear();
        String[] suits = {"Diamonds", "Hearts", "Spades", "Clubs"};
        String[] ranks = {"Ace", "King", "Queen", "Jack", "10", "9", 
                            "8", "7", "6", "5", "4", "3", "2"};
                            
        //populate
        int value = 0;
        for(String suit : suits){
            for(String rank : ranks){
                cardDeck.add(new Card(suit,rank,value));
                value++;
            }
            //value = 0;
        }
    }
    
    public Card drawTop(){
        if(this.cardDeck.size() > 0){
            Card topCard = this.cardDeck.get(0);
            cardDeck.remove(0);
            return topCard;
        }
        else
            return null;
    }
    
    public void selectionSortByVal(){
        for(int i = 0; i < this.cardDeck.size(); i++){
            int minIdx = i;
            for(int k = i+1; k < this.cardDeck.size(); k++){
                if(this.getCard(k).getValue() < this.getCard(minIdx).getValue()){
                    minIdx = k;
                }
            }   
            Card temp = this.getCard(i);
            this.cardDeck.set(i, this.getCard(minIdx));
            this.cardDeck.set(minIdx, temp);
        }
    }
    
    public void insertionSortByVal(){
        for(int i = 0; i < this.cardDeck.size(); i++){
            Card key = this.getCard(i);
            int leftIdx = i-1;
            
            while(leftIdx >= 0 && this.getCard(leftIdx).getValue() > key.getValue()){
                this.cardDeck.set(leftIdx + 1, this.getCard(leftIdx));
                leftIdx--;
            }
            this.cardDeck.set(leftIdx+1, key);
        }
    }
}

