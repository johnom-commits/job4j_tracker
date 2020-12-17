package ru.job4j.tracker;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HbmTrackerTest {

    @Test
    public void add() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item(1, "item1");
        tracker.add(item);
        List<Item> list = tracker.findAll();
        assertEquals(item, list.get(0));
    }

    @Test
    public void replace() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item(1, "item1");
        tracker.add(item);
        Item item2 = new Item(2, "item2");
        tracker.replace(1, item2);
        assertEquals("item2", tracker.findById(1).getName());
    }

    @Test
    public void delete() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item(1, "item1");
        Item item2 = new Item(2, "item2");
        tracker.add(item);
        tracker.add(item2);
        tracker.delete(1);
        List<Item> list = tracker.findAll();
        assertEquals(item2, list.get(0));
    }

    @Test
    public void findAll() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item(1, "item1");
        Item item2 = new Item(2, "item2");
        Item item3 = new Item(3, "item3");
        tracker.add(item);
        tracker.add(item2);
        tracker.add(item3);
        List<Item> list = tracker.findAll();
        assertEquals(3, list.size());
    }

    @Test
    public void findByName() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item(1, "item1");
        Item item2 = new Item(2, "item2");
        tracker.add(item);
        tracker.add(item2);
        List<Item> list = tracker.findByName("item2");
        assertEquals(item2, list.get(0));
        assertEquals(1, list.size());
    }

    @Test
    public void findById() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item(1, "item1");
        Item item2 = new Item(2, "item2");
        tracker.add(item);
        tracker.add(item2);
        Item item1 = tracker.findById(2);
        assertEquals(Integer.valueOf(2), item1.getId());
    }
}