import java.util.*;

abstract class Game{
    protected ArrayList<Player> players;
    protected Deck deck;
    protected Scanner inputScn = new Scanner(System.in);
    
    public Game(){
        players = new ArrayList<>();
        deck = new Deck();
        deck.shuffleCards();
    }
    
    public abstract void initPlayers();
    
    public void dealInitCards(int amount){
        for(Player player : players){
            for(int i = 0; i < amount; i++){
                player.hand.add(deck.drawTop());
            }
        }
    }
    
    public abstract void playRound();

    public Player evalHands(){
        HandEval evaluator = new HandEval();
        Player bestPlayer = null;
        int bestScore = 0;
        for(Player player : players){
            int score = evaluator.evaluate(player.getHand());
            if(score > bestScore){
                bestScore = score;
                bestPlayer = player;
            }
        }
        return bestPlayer;
    }
    
    public void declareWinner(Player winner){
        System.out.println("The winner is: " + winner.getName());
        System.out.println("Winning hand: " + winner.viewHand());
    }
}