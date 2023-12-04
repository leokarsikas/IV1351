-- Query 1 

--View 
CREATE VIEW lessons_per_month AS
SELECT
  EXTRACT(YEAR FROM time) AS Year,
  TO_CHAR(time, 'Mon') AS Month,
  COUNT(*) AS Total,
  COUNT(*) FILTER (WHERE t.value = 'Individual') AS Individual,
  COUNT(*) FILTER (WHERE t.value = 'Group') AS Group,
  COUNT(*) FILTER (WHERE t.value = 'Ensemble') AS Ensemble
FROM
  music_lesson ml
JOIN
  type_of_lesson t ON ml.type_of_lesson_id = t.type_of_lesson_id
GROUP BY
  EXTRACT(YEAR FROM time), TO_CHAR(time, 'Mon')
ORDER BY
  EXTRACT(YEAR FROM time), MIN(time);

--Query 2

--View

CREATE VIEW sibling_relation_count AS
SELECT
  COALESCE(sibling_count, 0) AS "No of Siblings",
  COUNT(DISTINCT s.student_id) AS "No of Students"
FROM
  student s
LEFT JOIN (
  SELECT
    student_id1, COUNT(*) as sibling_count
  FROM
    sibling
  GROUP BY
    student_id1
  UNION ALL
  SELECT
    student_id2, COUNT(*)
  FROM
    sibling
  GROUP BY
    student_id2
) AS sibling_data ON s.student_id = sibling_data.student_id1
GROUP BY
  sibling_count
ORDER BY
  "No of Siblings";

--Query 3

--View
CREATE VIEW instructor_lessons_summary AS
SELECT
  i.instructor_id,
  p.first_name,
  p.last_name,
  EXTRACT(MONTH FROM ml.time) AS lesson_month,
  EXTRACT(YEAR FROM ml.time) AS lesson_year,
  COUNT(ml.lesson_id) AS lessons_given
FROM
  instructor i
JOIN
  person p ON i.instructor_person_id = p.person_id
JOIN
  music_lesson ml ON i.instructor_id = ml.instructor_id
GROUP BY
  i.instructor_id, p.first_name, p.last_name, EXTRACT(MONTH FROM ml.time), EXTRACT(YEAR FROM ml.time);

--Explain Analyse
EXPLAIN ANALYZE 
SELECT
  i.instructor_id,
  p.first_name,
  p.last_name,
  COUNT(ml.lesson_id) AS lessons_given
FROM
  instructor i
JOIN
  person p ON i.instructor_person_id = p.person_id
JOIN
  music_lesson ml ON i.instructor_id = ml.instructor_id
WHERE
  EXTRACT(MONTH FROM ml.time) = 1
  AND EXTRACT(YEAR FROM ml.time) = 2023
GROUP BY
  i.instructor_id, p.first_name, p.last_name
HAVING
  COUNT(ml.lesson_id) > 2;


--Query 4

--View

CREATE VIEW ensemble_lessons_next_week AS
SELECT
  TO_CHAR(ml.time, 'Dy') AS Day,
  ml.genre,
  CASE
    WHEN COUNT(lp.student_id) >= ml.max_students THEN 'No Seats'
    WHEN ml.max_students - COUNT(lp.student_id) BETWEEN 1 AND 2 THEN '1 or 2 Seats'
    ELSE 'Many Seats'
  END AS "No of Free Seats"
FROM
  music_lesson ml
LEFT JOIN
  lesson_participant lp ON ml.lesson_id = lp.lesson_id
JOIN
  type_of_lesson tol ON ml.type_of_lesson_id = tol.type_of_lesson_id
WHERE
  tol.value = 'Ensemble'
  AND EXTRACT(WEEK FROM ml.time) = EXTRACT(WEEK FROM CURRENT_DATE) + 1
  AND EXTRACT(YEAR FROM ml.time) = CASE
  WHEN EXTRACT(WEEK FROM CURRENT_DATE) = 52 THEN EXTRACT(YEAR FROM CURRENT_DATE) + 1
  ELSE EXTRACT(YEAR FROM CURRENT_DATE)
END
GROUP BY
  ml.lesson_id, ml.genre, ml.time, ml.max_students;


