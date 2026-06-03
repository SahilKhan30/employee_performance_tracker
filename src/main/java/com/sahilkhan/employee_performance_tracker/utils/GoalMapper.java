package com.sahilkhan.employee_performance_tracker.utils;

import com.sahilkhan.employee_performance_tracker.entity.Employee;
import com.sahilkhan.employee_performance_tracker.entity.Goal;
import com.sahilkhan.employee_performance_tracker.entity.ReviewCycle;
import com.sahilkhan.employee_performance_tracker.enums.GoalStatus;

public final class GoalMapper {

    private GoalMapper() {}

    public static Goal toCreateEntity(String title, GoalStatus status, Employee employee, ReviewCycle cycle) {
        Goal goal = new Goal();
        goal.setTitle(title);
        goal.setStatus(status);
        goal.setEmployee(employee);
        goal.setReviewCycle(cycle);
        return goal;
    }
}
