import java.util.*;

public class HumanPlayer extends Player{
    protected Scanner scan1 = new Scanner(System.in);
    public HumanPlayer(String name){
        super(name);
    }

    @Override
    public void discNDraw(Deck deck){
        System.out.println("Choose card indeces to discard (Separated by spaces, 0-based): ");
        
        ArrayList<Integer> indecesToDisc = new ArrayList<>();
        
        try{
            String input = scan1.nextLine().trim();     //trim clears out all the spaces before and after a string
            

            if(!input.isEmpty()){
                String[] choiceIndx = input.split("\\s+"); //epic regex, splits string on every space character, forming an array
                for(String idx : choiceIndx){
                    int index = Integer.parseInt(idx);  //try to convert array elements to int if possible
                    if(index >= 0 && index < hand.size()){      //if index in range
                        if(!indecesToDisc.contains(index)){     //if index is not already in the list, add it
                            indecesToDisc.add(index);
                        }
                    }
                    else{
                        System.out.println("Invalid index: "+idx+", Ignoring it");
                    }
                }
            }
        }
        catch(Exception e){     //catching exceptions, most notably not being able to split or parse to int
            System.out.println("Invalid input. Exiting...");
            System.exit(0);
        }
        
        ArrayList<Card> discCards = new ArrayList<>();
        //selection sort of indeces to discard to avoid shifting errors
        for(int i = 0; i < indecesToDisc.size(); i++){
            int minIdx = i;
            for(int j = i+1; j < indecesToDisc.size(); j++){
                if(indecesToDisc.get(j) < indecesToDisc.get(minIdx)){
                    minIdx = j;
                }
            }
            int temp = indecesToDisc.get(i);
            indecesToDisc.set(i, indecesToDisc.get(minIdx));
            indecesToDisc.set(minIdx, temp);
        }

        for(int i = indecesToDisc.size()-1; i >= 0; i--){   //reverse loop to sort in descending order for discarding the cards
            discCards.add(hand.get(indecesToDisc.get(i)));
            hand.remove((int)indecesToDisc.get(i));
        }
        
        for(int i = 0; i<discCards.size(); i++){    //for each card discarded add 1 to hand
            hand.add(deck.drawTop());
        }
        
        System.out.println("Your new hand: ");
        System.out.println(viewHand());
    }
}
