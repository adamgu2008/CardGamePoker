public class PlayerBotGame extends Game{
    public PlayerBotGame(){     //subclass main game class
        super();
    }

    @Override
    public void initPlayers() { //add 1 user player and multiple bots
        players.add(new HumanPlayer("Player"));

        for(int i = 1; i<=3;i++){
            players.add(new BotPlayer("Bot"+i));
        }
    }
    
    @Override
    public void playRound(){
        System.out.println("Press ENTER to deal cards...");
        inputScn.nextLine();
        dealInitCards(5);
        for(Player player : players){
            if(player instanceof BotPlayer){    //if bot; operate its logic
                player.discNDraw(deck);
            }
            else if(player instanceof HumanPlayer){ //if player, let decide
                System.out.println(player.viewHand()); 
                System.out.println("Press ENTER to discard and draw...");
                inputScn.nextLine();
                player.discNDraw(deck);
            }
        }
        Player bestPlayer = evalHands();
        System.out.println("\nAll players have made their moves.");
        System.out.println("Press ENTER to reveal the winner...");
        inputScn.nextLine();
        declareWinner(bestPlayer);
        for(Player player : players){   //reset cards after every round
            player.clearHand();
        }
    }
}