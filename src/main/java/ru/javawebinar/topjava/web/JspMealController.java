package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@RequestMapping("/meals")
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService mealService;

    @GetMapping("")
    public String getAll(Model model) {
        log.info("get all meals");
        model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/filter")
    public String getAllFiltered(Model model) {
        log.info("get filtered meals");
        return "meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        log.info("create meal");
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable int id) {
        log.info("update meal with id {}", id);
        model.addAttribute("meal", mealService.get(id, SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        log.info("delete meal with id {}", id);
        mealService.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("")
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        log.info("save meal");
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.hasLength(request.getParameter("id"))) {
            assureIdConsistent(meal, getId(request));
            mealService.update(meal, SecurityUtil.authUserId());
        } else {
            mealService.create(meal, SecurityUtil.authUserId());
        }
        return "redirect:meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
