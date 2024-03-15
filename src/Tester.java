public class Tester {
    public static void main(String[] args) {
        Player human = new HumanPlayer("Human");
//
//        Player comp = new ComputerPlayer("Computer");
//
//        Battleship b = new Battleship();
//        b.addPlayer(human);
//        b.addPlayer(comp);
//
//        System.out.println("Game over? " + b.gameOver());
//        System.out.println("Winner: " + b.getWinner());
//
//        System.out.println("ATTACKING");
//        human.attack(comp, new Location(4, 2));
//        human.attack(comp, new Location(4, 3));
//        System.out.println("FINISHED ATTACKING");
//
//        System.out.println("UPKEEP");
//        b.upkeep();
//        System.out.println("FINISH UPKEEP");
//
//        System.out.println("Game over? " + b.gameOver());
//        System.out.println("Winner: " + b.getWinner());

        OldExpertComputerPlayer ecp = new OldExpertComputerPlayer("Expert");
        ecp.printHeatmap();
        ecp.attack(human, new Location(1, 1));
        ecp.printHeatmap();
        System.out.println(ecp.getNextAttack());
    }
}
