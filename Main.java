import java.util.Scanner;
public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many rounds will you play?");
        int rounds = scanner.nextInt();
        System.out.println("Which game would you like to play?");
        System.out.println("1. multiplayer game");
        System.out.println("2. bot game");
        int choice = scanner.nextInt();
        if(choice == 1){
            playerGame(rounds);
        }
        else if(choice == 2){
            botGame(rounds);
        }
        else{
            System.out.println("Invalid choice");
        }
        scanner.close();    //close the scanner to prevent resource leaks
    }

    public static void playerGame(int rounds){  //all needed to initialize a player input game
        PlayerInputGame game = new PlayerInputGame();
        game.initPlayers();
        for(int i = 0; i < rounds; i++){
           game.playRound(); 
        }
        
    }
    public static void botGame(int rounds){     //all needed to initialize player vs bots game
        PlayerBotGame game = new PlayerBotGame();
        game.initPlayers();
        for(int i = 0; i < rounds; i++){
           game.playRound(); 
        }
    }
}