import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Grid grid = new Grid(9, 9);
        grid.setup();
        boolean isManual = false;
        while (true) {
            if (!isManual) {
                System.out.println("------- Menu -------");
                System.out.println("(1) Print Board");
                System.out.println("(2) Auto Solve");
                System.out.println("(3) Manual Input");
                System.out.println("(4) New Board");
                System.out.println("(0) Exit");
                System.out.print("Enter your choice: ");
                Scanner sc = new Scanner(System.in);

                String term = sc.nextLine();
                switch (term) {
                    case "1" -> grid.printBoard();
                    case "2" -> {
                        System.out.println("Before:");
                        grid.printBoard();
                        grid.solve();
                        System.out.println("After:");
                        grid.printBoard();
                        System.out.println("Input '4' to create a new board");
                    }
                    case "3" -> {
                        isManual = true;
                    }
                    case "4" -> {
                        grid.setup();
                        System.out.println("New board setup.");
                    }
                    case "0" -> System.exit(0);
                    default -> System.out.println("Invalid input.");
                }
            } else {
                System.out.println("------- Manual Menu -------");
                System.out.println("(1) Print Board");
                System.out.println("(2) Input Value");
                System.out.println("(3) New Board");
                System.out.println("(0) Return to Menu");
                System.out.println("Strikes: " + grid.getStrikes() + "/" + grid.getMaxStrikes());
                System.out.print("Enter your choice: ");
                Scanner sc = new Scanner(System.in);

                String term = sc.nextLine();
                switch (term) {
                    case "1" -> grid.printBoard();
                    case "2" -> {
                        System.out.print("Enter row: ");
                        int row = sc.nextInt();
                        System.out.print("Enter column: ");
                        int col = sc.nextInt();
                        if (grid.get(col, row) != 0) {
                            System.out.println("You must choose a blank cell.");
                            break;
                        }
                        System.out.print("Enter value: ");
                        int val = sc.nextInt();
                        if (val < 1 || val > 9) {
                            System.out.println("Invalid value. Must be 1-9.");
                            break;
                        }
                        if (grid.valid(col, row, val)) {
                            grid.set(col, row, val);
                            System.out.println("Success! You have placed " + val + " at (" + row + ", " + col + ")");
                            grid.printBoard();
                            if (grid.isSolved()) {
                                System.out.println("You have solved the board!");
                                System.out.println("You had " + grid.getStrikes() + " strikes.");
                                System.out.println("Enter '3' to create a new board and start over!");
                                break;
                            }
                        } else {
                            System.out.println("You can't place that value there! +1 Strike!");
                            grid.incrementStrikes();
                            System.out.println("You now have " + grid.getStrikes() + "/" + grid.getMaxStrikes() + " strikes.");
                            if (grid.getStrikes() == grid.getMaxStrikes()) {
                                System.out.println("You have lost the game.");
                                System.out.println("Here is the solved board:");
                                grid.printSolved();
                                System.exit(0);
                            }
                        }
                    }
                    case "3" -> {
                        grid.setup();
                        System.out.println("New board setup and strikes reset.");
                        grid.setStrikes(0);
                    }
                    case "0" -> {
                        isManual = false;
                    }
                }
            }
        }
    }
}
