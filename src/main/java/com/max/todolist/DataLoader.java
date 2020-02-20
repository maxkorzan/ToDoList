package com.max.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    ItemRepository repository;

    @Override
    public void run(String ... strings) throws Exception{
        Item item;

        item = new Item("Nick","Laundry","Urgent!!","Wednesday","N", "https://res.cloudinary.com/dptc7fbot/image/upload/v1582217435/tgyc462xaqext1eyvntk.png");
        repository.save(item);

        item = new Item("George","Wash Dishes","Moderate","Afternoon","N", "https://res.cloudinary.com/dptc7fbot/image/upload/v1582218213/chores/00_dishes_wpiqnk.png");
        repository.save(item);

        item = new Item("Emily","Paint Fence","Low","Spring","Y", "https://res.cloudinary.com/dptc7fbot/image/upload/v1582218213/chores/00_paintfence_ndbriw.jpg");
        repository.save(item);

        item = new Item("Derek","Mow Lawn","Low","Sunday 2/23","Y", "https://res.cloudinary.com/dptc7fbot/image/upload/v1582218213/chores/00_mowlawn_ghwbfk.jpg");
        repository.save(item);



    }
}
