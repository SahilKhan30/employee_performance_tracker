-- ==========================================================
-- REVIEW CYCLES
-- ==========================================================

INSERT INTO review_cycles (
    uuid,
    name,
    start_date,
    end_date,
    created_at,
    updated_at
)
VALUES
(
    '550e8400-e29b-41d4-a716-446655440000',
    'Q1 2025',
    '2025-01-01',
    '2025-03-31',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    '550e8400-e29b-41d4-a716-446655440001',
    'Q2 2025',
    '2025-04-01',
    '2025-06-30',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ==========================================================
-- EMPLOYEES
-- ==========================================================

INSERT INTO employees (
    uuid,
    name,
    department,
    role,
    joining_date,
    created_at,
    updated_at
)
VALUES
(
    'e1111111-1111-1111-1111-111111111111',
    'Alice Smith',
    'Engineering',
    'Software Engineer',
    '2023-01-15',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'e2222222-2222-2222-2222-222222222222',
    'Bob Jones',
    'Marketing',
    'Marketing Coordinator',
    '2024-03-10',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'e3333333-3333-3333-3333-333333333333',
    'Charlie Brown',
    'Sales',
    'Sales Associate',
    '2024-08-01',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'e4444444-4444-4444-4444-444444444444',
    'Diana Prince',
    'Engineering',
    'Tech Lead',
    '2022-05-20',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'e5555555-5555-5555-5555-555555555555',
    'Emma Wilson',
    'Engineering',
    'Senior Software Engineer',
    '2021-07-10',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'e6666666-6666-6666-6666-666666666666',
    'Frank Miller',
    'Engineering',
    'DevOps Engineer',
    '2024-02-05',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'e7777777-7777-7777-7777-777777777777',
    'Grace Hopper',
    'Engineering',
    'Principal Engineer',
    '2020-01-01',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'e8888888-8888-8888-8888-888888888888',
    'Henry Ford',
    'HR',
    'HR Manager',
    '2023-04-01',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'e9999999-9999-9999-9999-999999999999',
    'Ivy Chen',
    'Marketing',
    'Marketing Manager',
    '2022-09-15',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ==========================================================
-- PERFORMANCE REVIEWS
-- ==========================================================

INSERT INTO performance_reviews (
    uuid,
    employee_id,
    review_cycle_id,
    rating,
    reviewer_notes,
    submitted_at,
    created_at,
    updated_at
)
VALUES

-- Alice Q1
(
    'f1111111-1111-1111-1111-111111111111',
    (SELECT id FROM employees WHERE uuid='e1111111-1111-1111-1111-111111111111'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
    5,
    'Excellent project leadership and delivery.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Alice second review same cycle (edge case)
(
    'fccccccc-cccc-cccc-cccc-cccccccccccc',
    (SELECT id FROM employees WHERE uuid='e1111111-1111-1111-1111-111111111111'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
    4,
    'Strong peer feedback and collaboration.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Alice Q2
(
    'faaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    (SELECT id FROM employees WHERE uuid='e1111111-1111-1111-1111-111111111111'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440001'),
    5,
    'Continued exceptional leadership.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Bob
(
    'f2222222-2222-2222-2222-222222222222',
    (SELECT id FROM employees WHERE uuid='e2222222-2222-2222-2222-222222222222'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
    3,
    'Met expectations but missed some campaign goals.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Charlie Q1
(
    'f3333333-3333-3333-3333-333333333333',
    (SELECT id FROM employees WHERE uuid='e3333333-3333-3333-3333-333333333333'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
    5,
    'Closed record number of enterprise deals.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Charlie Q2
(
    'f3333333-aaaa-bbbb-cccc-333333333333',
    (SELECT id FROM employees WHERE uuid='e3333333-3333-3333-3333-333333333333'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440001'),
    5,
    'Exceeded quarterly sales targets.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Diana Q1
(
    'f4444444-4444-4444-4444-444444444444',
    (SELECT id FROM employees WHERE uuid='e4444444-4444-4444-4444-444444444444'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
    4,
    'Great database optimization work.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Diana Q2
(
    'fbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
    (SELECT id FROM employees WHERE uuid='e4444444-4444-4444-4444-444444444444'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440001'),
    4,
    'Strong technical mentoring.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Emma
(
    'f1111111-aaaa-bbbb-cccc-111111111111',
    (SELECT id FROM employees WHERE uuid='e5555555-5555-5555-5555-555555555555'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
    5,
    'Delivered critical modernization initiatives.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Frank
(
    'fddddddd-dddd-dddd-dddd-dddddddddddd',
    (SELECT id FROM employees WHERE uuid='e6666666-6666-6666-6666-666666666666'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
    2,
    'Failed to complete infrastructure migration.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Grace
(
    'feeeeeee-eeee-eeee-eeee-eeeeeeeeeeee',
    (SELECT id FROM employees WHERE uuid='e7777777-7777-7777-7777-777777777777'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
    5,
    'Outstanding architectural leadership.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),

-- Ivy
(
    'f2222222-aaaa-bbbb-cccc-222222222222',
    (SELECT id FROM employees WHERE uuid='e9999999-9999-9999-9999-999999999999'),
    (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
    4,
    'Successfully launched major campaigns.',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- ==========================================================
-- GOALS
-- ==========================================================

INSERT INTO goals (
    uuid,
    employee_id,
    review_cycle_id,
    title,
    status,
    created_at,
    updated_at
)
VALUES

('b1111111-1111-1111-1111-111111111111',
 (SELECT id FROM employees WHERE uuid='e1111111-1111-1111-1111-111111111111'),
 (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
 'Deliver project architecture design',
 'COMPLETED',
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('b2222222-2222-2222-2222-222222222222',
 (SELECT id FROM employees WHERE uuid='e1111111-1111-1111-1111-111111111111'),
 (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
 'Improve API response times',
 'COMPLETED',
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('b3333333-3333-3333-3333-333333333333',
 (SELECT id FROM employees WHERE uuid='e2222222-2222-2222-2222-222222222222'),
 (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
 'Launch ad campaign',
 'MISSED',
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('b4444444-4444-4444-4444-444444444444',
 (SELECT id FROM employees WHERE uuid='e3333333-3333-3333-3333-333333333333'),
 (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
 'Close five enterprise accounts',
 'COMPLETED',
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('b5555555-5555-5555-5555-555555555555',
 (SELECT id FROM employees WHERE uuid='e4444444-4444-4444-4444-444444444444'),
 (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
 'Optimize analytical queries',
 'PENDING',
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('b6666666-6666-6666-6666-666666666666',
 (SELECT id FROM employees WHERE uuid='e5555555-5555-5555-5555-555555555555'),
 (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
 'Reduce API latency by 30%',
 'COMPLETED',
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('b7777777-7777-7777-7777-777777777777',
 (SELECT id FROM employees WHERE uuid='e6666666-6666-6666-6666-666666666666'),
 (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
 'Automate infrastructure provisioning',
 'MISSED',
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('b8888888-8888-8888-8888-888888888888',
 (SELECT id FROM employees WHERE uuid='e7777777-7777-7777-7777-777777777777'),
 (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
 'Design next-generation platform architecture',
 'COMPLETED',
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP),

('b9999999-9999-9999-9999-999999999999',
 (SELECT id FROM employees WHERE uuid='e9999999-9999-9999-9999-999999999999'),
 (SELECT id FROM review_cycles WHERE uuid='550e8400-e29b-41d4-a716-446655440000'),
 'Increase lead conversion rate by 20%',
 'PENDING',
 CURRENT_TIMESTAMP,
 CURRENT_TIMESTAMP);