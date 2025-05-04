package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        log.debug("init constructor");
        for (Meal meal: MealsUtil.meals) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        }
    }

    @Override
    public List<Meal> getAll() {
        log.debug("get all meals");
        return repository.values().stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public Meal getById(int id) {
        log.debug("getting meal with id {}", id);
        return repository.getOrDefault(id, null);
    }

    @Override
    public Meal save(Meal meal) {
        log.debug("Saving meal: {}", meal);
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
            return repository.putIfAbsent(meal.getId(), meal);
        } else {
            log.debug("Updating meal: {}", meal);
            return repository.put(meal.getId(), meal);
        }
    }

    @Override
    public void delete(Integer id) {
        log.debug("Deleting {}", id);
        repository.remove(id);
    }
}
