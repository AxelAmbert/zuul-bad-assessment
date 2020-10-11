public class Take implements Functionality{

    @Override
    public void run(Game game, Command command) {
        Room currentRoom = game.getCurrentRoom();

        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }

        String item = command.getSecondWord();
        int w = currentRoom.containsItem(item);
        if (w == 0) {
            // The item is not in the room
            System.out.println("No " + item + " in the room");
            return;
        }
        if (game.getTotalWeight() + w <= game.getMaxWeight()) {
            // The player is carrying too much
            System.out.println(item + " is too heavy");
            return;
        }
        // OK we can pick it up
        currentRoom.removeItem(item);
        items.add(item);
        weights.add(w);
        totalWeight += w;
    }
}
