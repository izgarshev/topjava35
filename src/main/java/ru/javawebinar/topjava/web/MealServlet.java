package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository mealRepository;

    @Override
    public void init() throws ServletException {
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Integer id = getId(req);
        log.debug("id: " + id);
        LocalDateTime ldt = LocalDateTime.parse(req.getParameter("dateTime"));
        log.debug("ldt: " + ldt);
        int calories = Integer.parseInt(req.getParameter("calories"));
        log.debug("calories: " + calories);
        String description = req.getParameter("description");
        log.debug("description: " + description);
        Meal meal = new Meal(id, ldt, description, calories);
        mealRepository.save(meal);
        resp.sendRedirect(req.getContextPath() + "/meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "all";
        log.debug("action: {}", action);

        switch (action) {
            case "create":
            case "update":
                log.debug("action: {}, id: {}", action, getId(req));
                req.setAttribute("meal", getId(req) == null ? new Meal(null, LocalDateTime.now(), "default description", 99) : mealRepository.getById(getId(req)));
                req.getRequestDispatcher("mealForm.jsp").forward(req, resp);
                break;
            case "delete":
                log.debug("delete: {}", getId(req));
                mealRepository.delete(getId(req));
                resp.sendRedirect(req.getContextPath() + "/meals");
                break;
            case "all":
            default:
                //get all
                log.debug("redirect to meals");
                req.setAttribute("meals", MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDay));
                req.getRequestDispatcher("meals.jsp").forward(req, resp);
                break;
        }
    }

    private Integer getId(HttpServletRequest req) {
        try {
            return Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
