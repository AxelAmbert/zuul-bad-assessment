public class Go implements Functionality {
    public Go() {
        super();
        System.out.print("coucou " );
    }
    public void run(Game game, Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = game.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            game.setCurrentRoom(nextRoom);
            System.out.println("You are " + game.getCurrentRoom().getDescription());
            System.out.print("Exits: ");
            game.showARoomExits(game.getCurrentRoom());
            System.out.println();
            System.out.print("Items: ");
            if (game.getCurrentRoom().itemDescription != null) {
                System.out.print(game.getCurrentRoom().itemDescription
                        + '(' + game.getCurrentRoom().itemWeight + ')');
            }
            System.out.println();
        }
    }
}
