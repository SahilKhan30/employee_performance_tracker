Project Assumptions

1. One Performance Review Per Employee Per Cycle  
An employee can have only one performance review within a review cycle. Attempts to submit a duplicate review for the same employee and review cycle combination will be rejected.

2. Top Performer Tie-Breaker  
When multiple employees have the same average rating in a review cycle, the tie-breaker is determined by the employee's joining date (oldest joining date wins).

3. Employee Filtering API  
When calling the employee filtering API, if null values are passed for both the department and the minimum rating filters, the system will fetch all employees.

4. Performance Review Rating Scale  
The performance review rating is stored as a whole integer from 1 to 5 (e.g., 1, 2, 3, 4, or 5, typically represented by a radio button input in a UI). Fractional ratings (such as 3.5 or 4.2) are not supported.

5. Filtering Employees with No Reviews  
When filtering employees by a minimum rating, any employee who has not received any reviews in the system yet will be treated as having an average rating of 0.

6. Returning Average Rating in Employee Response  
When filtering and retrieving employees, the average rating is calculated across all their historical reviews and returned back in the response object. This rating is calculated on-the-go from the database to maintain consistency instead of being persisted.

7. Active Development Profile  
The application is configured to run with a development profile (`dev`) by default to enable SQL query logging and detailed trace logs for testing convenience.

8. Entity CRUD Scope and Seed Data  
Since the task details do not specify CRUD endpoints for managing `ReviewCycle` or `Goal` entities, it is assumed that these entities are pre-populated and managed via seed data (such as `mock-data.sql`) and do not require additional REST API endpoints.
