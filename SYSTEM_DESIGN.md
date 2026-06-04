# System Design Considerations

## 1. Scaling for 500 Concurrent Managers

To support 500 concurrent managers running reports, I would first look at the two main bottlenecks: the application and the database.

For the application, I would run multiple Spring Boot instances behind a load balancer. Since the application is stateless, scaling horizontally is straightforward and helps distribute incoming traffic.

For the database, I would first ensure proper indexing on frequently queried columns and configure HikariCP connection pooling appropriately. This would help the database handle concurrent requests efficiently.

If the database still becomes a bottleneck as reporting traffic grows, I would introduce read replicas and route report summaries and search queries to them while keeping write operations on the primary database. This helps reduce load on the primary database and keeps write performance stable.



## 2. Handling Slow Cycle Summary Queries (100k+ Reviews)

If the `GET /cycles/{id}/summary` endpoint becomes slow, first I would check the query execution plan and verify that the required indexes are in place.

The average rating, top performer, and goal counts are already handled using database aggregation queries, so I would continue keeping those calculations in the database instead of loading all the data into application memory.

The current implementation uses multiple focused aggregation queries. If this endpoint becomes a bottleneck at larger scales, I would look at combining some of these queries to reduce database calls.

For completed review cycles, I would consider storing the summary data since it is not going to change anymore. This avoids recalculating the same aggregates every time the endpoint is called.

For active review cycles, I would still need to recalculate the summary as new reviews and goals are added, so I would use caching to reduce database load and improve response times.


## 3. Caching Strategy

I would add caching for the `GET /cycles/{id}/summary` endpoint since it involves aggregation queries and is likely to be requested frequently during review periods.

For completed review cycles, assuming that the summary data is not going to change anymore, I would cache it for a longer duration. Client-side caching can also be used here since the response is effectively static.
For active review cycles, I would use a shorter cache duration or clear the cache whenever a new review or goal is added.

I would also consider caching the employee filtering results from `GET /employees?department={dept}&minRating={x}` if those queries become expensive and are requested frequently.