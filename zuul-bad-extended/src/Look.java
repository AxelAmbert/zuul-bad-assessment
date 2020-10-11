public class Look implements Functionality {
    @Override
    public void run(Game game, Command command) {
        Room currentRoom = game.getCurrentRoom();

        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if (currentRoom.northExit != null) {
            System.out.print("north ");
        }
        if (currentRoom.eastExit != null) {
            System.out.print("east ");
        }
        if (currentRoom.southExit != null) {
            System.out.print("south ");
        }
        if (currentRoom.westExit != null) {
            System.out.print("west ");
        }
        System.out.println();
        System.out.print("Items: ");
        if (currentRoom.itemDescription != null) {
            System.out.print(currentRoom.itemDescription
                    + '(' + currentRoom.itemWeight + ')');
        }
        System.out.println();
    }
}
