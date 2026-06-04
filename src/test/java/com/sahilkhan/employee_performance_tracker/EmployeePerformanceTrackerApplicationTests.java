package com.sahilkhan.employee_performance_tracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahilkhan.employee_performance_tracker.dto.request.EmployeeRequest;
import com.sahilkhan.employee_performance_tracker.dto.request.PerformanceReviewRequest;
import com.sahilkhan.employee_performance_tracker.dto.request.ReviewCycleRequest;
import com.sahilkhan.employee_performance_tracker.entity.Employee;
import com.sahilkhan.employee_performance_tracker.entity.Goal;
import com.sahilkhan.employee_performance_tracker.entity.ReviewCycle;
import com.sahilkhan.employee_performance_tracker.enums.GoalStatus;
import com.sahilkhan.employee_performance_tracker.repository.EmployeeRepository;
import com.sahilkhan.employee_performance_tracker.repository.GoalRepository;
import com.sahilkhan.employee_performance_tracker.repository.PerformanceReviewRepository;
import com.sahilkhan.employee_performance_tracker.repository.ReviewCycleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeePerformanceTrackerApplicationTests {

	@TestConfiguration
	static class FlywayTestConfig {
		@Bean
		public FlywayMigrationStrategy flywayMigrationStrategy() {
			return flyway -> {
				flyway.repair();
				flyway.migrate();
			};
		}
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ReviewCycleRepository reviewCycleRepository;

	@Autowired
	private PerformanceReviewRepository performanceReviewRepository;

	@Autowired
	private GoalRepository goalRepository;

	private Employee employee1;
	private Employee employee2;
	private ReviewCycle reviewCycle;

	@BeforeEach
	void setUp() {
		performanceReviewRepository.deleteAllInBatch();
		goalRepository.deleteAllInBatch();
		employeeRepository.deleteAllInBatch();
		reviewCycleRepository.deleteAllInBatch();

		employee1 = new Employee();
		employee1.setName("Alice Smith");
		employee1.setDepartment("Engineering");
		employee1.setRole("Software Engineer");
		employee1.setJoiningDate(LocalDate.of(2023, 1, 15));
		employee1 = employeeRepository.save(employee1);

		employee2 = new Employee();
		employee2.setName("Bob Jones");
		employee2.setDepartment("Marketing");
		employee2.setRole("Marketing Coordinator");
		employee2.setJoiningDate(LocalDate.of(2024, 3, 10));
		employee2 = employeeRepository.save(employee2);

		reviewCycle = new ReviewCycle();
		reviewCycle.setName("Q1 2025");
		reviewCycle.setStartDate(LocalDate.of(2025, 1, 1));
		reviewCycle.setEndDate(LocalDate.of(2025, 3, 31));
		reviewCycle = reviewCycleRepository.save(reviewCycle);
	}

	@Test
	void testCreateEmployee_Success() throws Exception {
		EmployeeRequest request = new EmployeeRequest(
				"Charlie Brown",
				"Sales",
				"Sales Associate",
				LocalDate.of(2025, 5, 20));

		mockMvc.perform(post("/api/employees")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.name", is("Charlie Brown")))
				.andExpect(jsonPath("$.department", is("Sales")))
				.andExpect(jsonPath("$.role", is("Sales Associate")))
				.andExpect(jsonPath("$.joiningDate", is("2025-05-20")));
	}

	@Test
	void testCreateEmployee_ValidationError() throws Exception {
		EmployeeRequest request = new EmployeeRequest(
				"", // Blank
				"Sales",
				"Sales Associate",
				LocalDate.now().plusDays(1)); // Future date (invalid)

		mockMvc.perform(post("/api/employees")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.name", notNullValue()))
				.andExpect(jsonPath("$.errors.joiningDate", notNullValue()));
	}

	@Test
	void testSubmitReview_Success() throws Exception {
		PerformanceReviewRequest request = new PerformanceReviewRequest(
				employee1.getUuid(),
				reviewCycle.getUuid(),
				4,
				"Excellent work on the project.");

		mockMvc.perform(post("/api/reviews")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.rating", is(4)))
				.andExpect(jsonPath("$.reviewerNotes", is("Excellent work on the project.")))
				.andExpect(jsonPath("$.submittedAt", notNullValue()))
				.andExpect(jsonPath("$.reviewCycle.id", is(reviewCycle.getUuid().toString())))
				.andExpect(jsonPath("$.reviewCycle.name", is("Q1 2025")));
	}

	@Test
	void testSubmitReview_ValidationError() throws Exception {
		PerformanceReviewRequest request = new PerformanceReviewRequest(
				employee1.getUuid(),
				reviewCycle.getUuid(),
				6, // Rating > 5 is invalid
				"Incorrect rating");

		mockMvc.perform(post("/api/reviews")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors.rating", notNullValue()));
	}

	@Test
	void testSubmitReview_DuplicateError() throws Exception {
		PerformanceReviewRequest request = new PerformanceReviewRequest(
				employee1.getUuid(),
				reviewCycle.getUuid(),
				4,
				"First review");

		mockMvc.perform(post("/api/reviews")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated());

		// Submit the second one (duplicate)
		mockMvc.perform(post("/api/reviews")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString("already been reviewed")));
	}

	@Test
	void testGetEmployeeReviews_Success() throws Exception {
		PerformanceReviewRequest request1 = new PerformanceReviewRequest(
				employee1.getUuid(),
				reviewCycle.getUuid(),
				4,
				"Notes 1");
		mockMvc.perform(post("/api/reviews")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request1)))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/api/employees/" + employee1.getUuid() + "/reviews"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].rating", is(4)))
				.andExpect(jsonPath("$[0].reviewerNotes", is("Notes 1")))
				.andExpect(jsonPath("$[0].reviewCycle.name", is("Q1 2025")));
	}

	@Test
	void testGetCycleSummary_Success() throws Exception {
		PerformanceReviewRequest request1 = new PerformanceReviewRequest(
				employee1.getUuid(),
				reviewCycle.getUuid(),
				4,
				"Good");
		mockMvc.perform(post("/api/reviews")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request1)))
				.andExpect(status().isCreated());

		PerformanceReviewRequest request2 = new PerformanceReviewRequest(
				employee2.getUuid(),
				reviewCycle.getUuid(),
				5,
				"Excellent");
		mockMvc.perform(post("/api/reviews")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request2)))
				.andExpect(status().isCreated());

		Goal goal1 = new Goal();
		goal1.setEmployee(employee1);
		goal1.setReviewCycle(reviewCycle);
		goal1.setTitle("Goal 1");
		goal1.setStatus(GoalStatus.COMPLETED);
		goalRepository.save(goal1);

		Goal goal2 = new Goal();
		goal2.setEmployee(employee2);
		goal2.setReviewCycle(reviewCycle);
		goal2.setTitle("Goal 2");
		goal2.setStatus(GoalStatus.MISSED);
		goalRepository.save(goal2);

		Goal goal3 = new Goal();
		goal3.setEmployee(employee2);
		goal3.setReviewCycle(reviewCycle);
		goal3.setTitle("Goal 3");
		goal3.setStatus(GoalStatus.COMPLETED);
		goalRepository.save(goal3);

		mockMvc.perform(get("/api/cycles/" + reviewCycle.getUuid() + "/summary"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.averageRating", closeTo(4.5, 0.01)))
				.andExpect(jsonPath("$.topPerformer.id", is(employee2.getUuid().toString())))
				.andExpect(jsonPath("$.topPerformer.name", is("Bob Jones")))
				.andExpect(jsonPath("$.topPerformer.averageRating", closeTo(5.0, 0.01)))
				.andExpect(jsonPath("$.completedGoalsCount", is(2)))
				.andExpect(jsonPath("$.missedGoalsCount", is(1)));
	}

	@Test
	void testFilterEmployees_Success() throws Exception {
		PerformanceReviewRequest request1 = new PerformanceReviewRequest(
				employee1.getUuid(),
				reviewCycle.getUuid(),
				4,
				"Notes 1");
		mockMvc.perform(post("/api/reviews")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request1)))
				.andExpect(status().isCreated());

		PerformanceReviewRequest request2 = new PerformanceReviewRequest(
				employee2.getUuid(),
				reviewCycle.getUuid(),
				3,
				"Notes 2");
		mockMvc.perform(post("/api/reviews")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request2)))
				.andExpect(status().isCreated());

		mockMvc.perform(get("/api/employees"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));

		mockMvc.perform(get("/api/employees?department=Engineering"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is("Alice Smith")));

		mockMvc.perform(get("/api/employees?minRating=4.0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is("Alice Smith")));

		mockMvc.perform(get("/api/employees?department=Engineering&minRating=3.5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is("Alice Smith")));

		mockMvc.perform(get("/api/employees?department=Marketing&minRating=4.0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void testFilterEmployees_InvalidRating() throws Exception {
		mockMvc.perform(get("/api/employees?minRating=6.0"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString("Minimum rating must be at most 5.0")));

		mockMvc.perform(get("/api/employees?minRating=-0.5"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString("Minimum rating must be at least 0.0")));
	}


	@Test
	void testCreateReviewCycle_Success() throws Exception {
		ReviewCycleRequest request = new ReviewCycleRequest(
				"Q2 2025",
				LocalDate.of(2025, 4, 1),
				LocalDate.of(2025, 6, 30));

		mockMvc.perform(post("/api/cycles")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", notNullValue()))
				.andExpect(jsonPath("$.name", is("Q2 2025")))
				.andExpect(jsonPath("$.startDate", is("2025-04-01")))
				.andExpect(jsonPath("$.endDate", is("2025-06-30")));
	}

	@Test
	void testCreateReviewCycle_ValidationError_DateOrder() throws Exception {
		ReviewCycleRequest request = new ReviewCycleRequest(
				"Invalid Cycle",
				LocalDate.of(2025, 4, 1),
				LocalDate.of(2025, 3, 31)); // End before start

		mockMvc.perform(post("/api/cycles")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString("End date cannot be before start date")));
	}

	@Test
	void testCreateReviewCycle_ValidationError_DuplicateName() throws Exception {
		ReviewCycleRequest request = new ReviewCycleRequest(
				"Q1 2025", // Already created in setUp
				LocalDate.of(2025, 4, 1),
				LocalDate.of(2025, 6, 30));

		mockMvc.perform(post("/api/cycles")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString("already exists")));
	}
}
