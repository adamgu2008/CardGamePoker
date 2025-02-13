

public class PlayerInputGame extends Game{
    public PlayerInputGame(){
        super();

    }

    @Override
    public void initPlayers(){      //populate players List with custom name characters
        System.out.println("how many players?");
        try{    //catch exceptions like non int input
            int amount = inputScn.nextInt();
            if(amount < 2){
                System.out.println("Minimum of 2 players required.");
                System.exit(0);
            }
            for(int i = 1; i <= amount; i++){
                System.out.print("Enter name for Player " + i + ": ");
                String name = inputScn.next();
                players.add(new HumanPlayer(name));
            }
        }
        catch(Exception e){
            System.out.println("Invalid input. Exiting...");
            System.exit(0);
        }
    }

    @Override
    public void playRound(){   
        System.out.println("Press ENTER to deal cards...");
        inputScn.nextLine();
        dealInitCards(5);       //populate players hands
        for(Player player : players){   //play round making sure all players are users
            if(player instanceof HumanPlayer){
                System.out.println("\n" + player.getName() + ", it's your turn!");
                System.out.println("Press ENTER to view your hand...");
                inputScn.nextLine();
                System.out.println(player.viewHand());  // show the player their hand when ready
                
                player.discNDraw(deck);     //player makes their moves
                System.out.println("Press ENTER to continue...");
                inputScn.nextLine();
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");   //clear screen for next player
            }
        }
        Player bestPlayer = evalHands();    //evaluate all hands and determine winner
        System.out.println("\nAll players have made their moves.");
        System.out.println("Press ENTER to reveal the winner...");
        inputScn.nextLine();
        declareWinner(bestPlayer);
        for(Player player : players){   //reset cards after every round
            player.clearHand();
        }
    }
    
}