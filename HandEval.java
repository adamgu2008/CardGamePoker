import java.util.*;
public class HandEval {
    
    //@param hand ArrayList of cards to evaluate
    //@return int value of the hand
    public int evaluate(ArrayList<Card> hand) {
        HashMap<String, Integer> rankCount = countRanks(hand);      //HashMap format: <Key, Value>, like python dictionary sort of
        HashMap<String, Integer> suitCount = countSuits(hand);

        //order of priorities: (could have used switch statement)
        if (isRoyalFlush(hand)) return 10;
        if (isStraightFlush(hand, rankCount, suitCount)) return 9;
        if (isFourOfAKind(rankCount)) return 8;
        if (isFullHouse(rankCount)) return 7;
        if (isFlush(suitCount)) return 6;
        if (isStraight(rankCount)) return 5;
        if (isThreeOfAKind(rankCount)) return 4;
        if (isTwoPair(rankCount)) return 3;
        if (isOnePair(rankCount)) return 2;


        return 1;
    }
    //helper functions
    private static HashMap<String, Integer> countRanks(ArrayList<Card> hand){   //counts how many of each rank are in a deck
        HashMap<String, Integer> rankCount = new HashMap<>();
        for (Card card : hand){
            rankCount.put(card.getRank(), rankCount.getOrDefault(card.getRank(), 0) + 1);
        }
        return rankCount;
    }
    private static HashMap<String, Integer> countSuits(ArrayList<Card> hand){   //same as earlier with suits
        HashMap<String, Integer> suitCount = new HashMap<>();
        for (Card card : hand){
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }
        return suitCount;
    }
    private static boolean isRoyalFlush(ArrayList<Card> hand){  //check for RF
        int totalWorth = 0;
        for(Card card : hand){
            totalWorth += rankToVal(card.getRank());
        }
        if(totalWorth == 60)    //royal flush must be 60 pts, as I counted it 14+13+12+11+10, as per my conversion in func. rankToVal()
            return true;
        else
            return false;
    }
    private static boolean isStraightFlush(ArrayList<Card> hand, HashMap<String, Integer> rankCount, HashMap<String, Integer> suitCount){
        return isFlush(suitCount) && isStraight(rankCount);     //SF is if flush && straight
    }
    private static boolean isFourOfAKind(HashMap<String, Integer> rankCount){
        return rankCount.containsValue(4);      //if the count function counted 4 of the same rank
    }
    private static boolean isFullHouse(HashMap<String, Integer> rankCount){
        return rankCount.containsValue(3) && rankCount.containsValue(2);        //count func. counted 3 & 2 of same ranks
    }
    private static boolean isFlush(HashMap<String, Integer> suitCount){     
        return suitCount.containsValue(5);     //if 5 of the same suit
    }
    private static boolean isThreeOfAKind(HashMap<String, Integer> rankCount){
        return rankCount.containsValue(3);      //if 3 of same rank
    }
    private static boolean isTwoPair(HashMap<String, Integer> rankCount){
        int pairCount = 0;
        for (int count : rankCount.values()){   // for each value (count) of the rankCount hashmap
            if (count == 2){    //if there are 2 pairs
                pairCount++;    
            }
        }
        return pairCount == 2;
    }
    private static boolean isOnePair(HashMap<String, Integer> rankCount){
        return rankCount.containsValue(2);  //same as before: if 2 of same rank
    }
    private static boolean isStraight(HashMap<String,Integer> rankCount){
        ArrayList<Integer> rankValues = rankToSortedVals(rankCount);    //first sort ranks by value (descending order)
        return isConsecutive(rankValues,0);     //check if the ranks are consecutive ex(6,5,4,3,2) using recursive helper function
    }
    private static boolean isConsecutive(ArrayList<Integer> values, int index){     //isConsecutive utilizes RECURSION!!
        if(index == values.size()-1)
            return true;    //base case: consecutive
        if(values.get(index) + 1 != values.get(index+1))
            return false;       //base case: not consecutive
        return isConsecutive(values, index+1);       //recursive case: if previous one is consecutive
    }

    private static ArrayList<Integer> rankToSortedVals(HashMap<String,Integer> rankCount){
        ArrayList<Integer> rankVals = new ArrayList<>();    //List of values of each rank
        for(String rank : rankCount.keySet()){      //for each rank
            rankVals.add(rankToVal(rank));  //add the value of that rank to list rankVals
        }
        //insertion sort
        for(int i = 1; i < rankVals.size(); i++){
            int key = rankVals.get(i);
            int j = i - 1;
            while(j >= 0 && rankVals.get(j) < key){
                rankVals.set(j+1, rankVals.get(j));
                j--;
            }
            rankVals.set(j+1, key);
        }
        return rankVals;
    }
    private static int rankToVal(String rank){
        //Switch statement: instead of large chain of if statements, establish base case, and other cases with conditions and outputs
        switch (rank) {                     //I wish I had known about switch statements earlier
            case "Ace": return 14;
            case "King": return 13;
            case "Queen": return 12;
            case "Jack": return 11;
            default: return Integer.parseInt(rank); //default case return the rank value as an int (non face card)
        }
    }
}
