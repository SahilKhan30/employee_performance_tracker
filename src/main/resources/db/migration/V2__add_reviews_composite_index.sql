-- Migration to add composite index on performance_reviews(employee_id, review_cycle_id)
DROP INDEX IF EXISTS idx_reviews_employee;
CREATE UNIQUE INDEX IF NOT EXISTS idx_reviews_employee_cycle ON performance_reviews(employee_id, review_cycle_id);
