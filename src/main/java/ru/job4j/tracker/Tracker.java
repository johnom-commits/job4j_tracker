package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @version $Id$
 * @since 0.1
 */
public class Tracker implements ITracker {
    /** Массив для хранение заявок. */
    private final List<Item> items = new ArrayList<>(100);
    /**Указатель ячейки для новой заявки. */
    private int position = 0;
    /**
     * Метод реализаущий добавление заявки в хранилище
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        items.add(item);
        position++;
        return item;
    }

    public Item findById(Integer id) {
        Item tmp = null;
        for (int i = 0; i < position; i++) {
            if (items.get(i) != null && items.get(i).getId().equals(id)) {
                tmp = items.get(i);
                break;
            }
        }
        return tmp;
    }

    public List<Item> findByName(String key) {
        List<Item> array = new ArrayList<>(position);
        int index = 0;
        for (int i = 0; i < position; i++) {
            if (items.get(i) != null && items.get(i).getName().equals(key)) {
                array.add(items.get(i));
                index++;
            }
        }
        return array.subList(0, index);
    }

    public List<Item> findAll() {
        return items;
    }

    public boolean replace(Integer id, Item item) {
        boolean flag = false;
        for (int i = 0; i < position; i++) {
            if (items.get(i) != null && items.get(i).getId().equals(id)) {
                flag = true;
                items.set(i, item);
                break;
            }
        }
        return flag;
    }

    public boolean delete(Integer id) {
        int index = 0;
        boolean find = false;
        for (int i = 0; i < position; i++) {
            if (items.get(i) != null && items.get(i).getId().equals(id)) {
                items.remove(i);
                position--;
                find = true;
                break;
            }
        }
        return find;
    }
    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     * @return Уникальный ключ.
     */
    private Integer generateId() {
        Random rm = new Random();
        return rm.nextInt();
    }

    @Override
    public void close() throws Exception {

    }
}
