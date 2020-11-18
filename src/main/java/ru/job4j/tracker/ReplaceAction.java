package ru.job4j.tracker;

public class ReplaceAction extends BaseAction {

    protected ReplaceAction(int key, String name) {
        super(key, name);
    }

    @Override
    public String name() {
        return "Edit item";
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        Integer id = input.askInt("Enter Id:", 7);
        String name = input.askStr("Enter new name:");
        Item item = new Item(name);
        item.setId(id);
        boolean result = tracker.replace(id, item);
        if (result) {
            System.out.println("Done!");
        } else {
            System.out.println("Item does not find");
        }
        return true;
    }
}
