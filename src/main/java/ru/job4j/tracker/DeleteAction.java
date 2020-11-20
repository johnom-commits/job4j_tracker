package ru.job4j.tracker;

import javax.persistence.criteria.CriteriaBuilder;

public class DeleteAction extends BaseAction {

    protected DeleteAction(int key, String name) {
        super(key, name);
    }

    @Override
    public String name() {
        return "Delete item";
    }

    @Override
    public boolean execute(Input input, ITracker tracker) {
        Integer id = Integer.valueOf(input.askStr("Enter Id:"));
        boolean result = tracker.delete(id);
        if (result) {
            System.out.println("Item delete.");
        } else {
            System.out.println("Attempt failed");
        }
        return true;
    }
}
