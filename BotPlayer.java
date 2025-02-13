import java.util.*;
public class BotPlayer extends Player{
    public BotPlayer(String name){
        super(name);
    }
    
    //This is where stuff gets complicated: bot logic
    
    @Override
    public void discNDraw(Deck deck) {
        System.out.println("Player "+name+" is thinking...");
        //hashmap format mentioned in HandEval Class
        HashMap<String, Integer> rankCount = countRanks();  //create hashmaps to keep count of amount of each rank in hand 
        HashMap<String, Integer> suitCount = countSuits();  // same for suits

        ArrayList<Integer> idxsToDisc = new ArrayList<>();
        
        //order of priority
        if(has3OfAKind(rankCount))      
            idxsToDisc = discEx3ofKind(rankCount);
        else if(hasPair(rankCount))
            idxsToDisc = discExPair(rankCount);
        else if(flushPotential(suitCount))
            idxsToDisc = discNonFlush(suitCount);
        else
            idxsToDisc = discLowCards();

        Collections.sort(idxsToDisc);   //sort to avoid shift errors

        for(int i = idxsToDisc.size()-1; i >= 0; i--){      //remove cards
            hand.remove((int)idxsToDisc.get(i));
        }

        for(int i = 0; i < idxsToDisc.size(); i++){     //add new cards in place of the removed
            hand.add(deck.drawTop());
        }

        System.out.println(getName()+" discarded "+idxsToDisc.size()+" cards.");
    }

    private HashMap<String, Integer> countRanks(){
        HashMap<String, Integer> rankCount = new HashMap<>();
        for (Card card : hand){     //for each card in hand
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);   
        }   //insert new key (rank), and value (1 by default)
        return rankCount;   //return all the keys (ranks) and their corresponding values
    }
    private HashMap<String, Integer> countSuits(){  //same as above  but for the suits
        HashMap<String, Integer> suitCount = new HashMap<>();
        for (Card card : hand){
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }
        return suitCount;
    }
    private boolean has3OfAKind(HashMap<String, Integer> rankCount){    
        for (String rank : rankCount.keySet()){     //for each rank in the keys of the count
            if (rankCount.get(rank) >= 3){  //if a rank appears 3+ times, return true
                return true;
            }
        }
        return false;
    }
    private boolean hasPair(HashMap<String, Integer> rankCount){
        for (String rank : rankCount.keySet()){
            if (rankCount.get(rank) >= 2){  //same as before but 2+
                return true;
            }
        }
        return false;
    }
    private boolean flushPotential(HashMap<String, Integer> suitCount){
        for(int count : suitCount.values()){    //for each value of each suit
            if(count >= 3)      //return true if there are 3+ of the same suit
                return true;
        }
        return false;
    }
    private ArrayList<Integer> discEx3ofKind(HashMap<String,Integer> rankCount){
        String rank3OfKind = getRankWCount(rankCount,3);    // check if there is 3 of any rank in hand
        return getDiscIdxsExRank(rank3OfKind);  // get the indeces (list) of the ranks needed to be discarded if previous function satisfied
    }
    private ArrayList<Integer> discExPair(HashMap<String,Integer> rankCount){
        String rankPair = getRankWCount(rankCount,2);   //same as above but for 2 of any rank
        return getDiscIdxsExRank(rankPair);
    }
    private ArrayList<Integer> discNonFlush(HashMap<String,Integer> suitCount){
        String flushSuit = getSuitWCount(suitCount, 3);     //check if there is 3 cards with same suit in hand
        ArrayList<Integer> idxs = new ArrayList<>();
        for(int i = 0; i < hand.size(); i++){
            if(!hand.get(i).getSuit().equals(flushSuit))    //add indeces that aren't 3+ with the same suit to discard
                idxs.add(i);
        }
        return idxs;    //return indeces to discard
    }
    private ArrayList<Integer> discLowCards(){
        ArrayList<Integer> idxs = new ArrayList<>();
        for(int i = 0; i < hand.size(); i++){
            String rank = hand.get(i).getRank();
            if (!rank.equals("Ace") && !rank.equals("King") && !rank.equals("Queen")) {
                idxs.add(i);    //for each card in hand, discard if its not of ranks above
        }
    }
    return idxs;
    }
    private String getRankWCount(HashMap<String,Integer> rankCount, int goalCount){ //helper function
        for(String rank : rankCount.keySet()){  //goes through each rank in hand, return the rank if there are "goalCount" amount of them
            if(rankCount.get(rank) == goalCount)    return rank;
        }
        return null;
    }
    private String getSuitWCount(HashMap<String,Integer> suitCount, int goalCount){ //helper
        for(String suit : suitCount.keySet()){  //same as above but with suits
            if(suitCount.get(suit) == goalCount)    return suit;
        }
        return null;
    }
    private ArrayList<Integer> getDiscIdxsExRank(String keepRank){  //helper, stands for get indeces to discard except the selected rank
        ArrayList<Integer> idxs = new ArrayList<>();
        for(int i = 0; i< hand.size(); i++){
            if(!hand.get(i).getRank().equals(keepRank)) //gets all the indeces to discard except the parameter rank cards
                idxs.add(i);
        }
        return idxs;    //returns the list of indeces to disc.
    }
}
