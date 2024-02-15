Excited to share my recent achievement – a comprehensive University Data Management System! Over two intense weeks, I've honed my skills in SQL, PLSQL, Advanced PLSQL, Red Hat, Bash scripting, Java SE, and OOP principles, leading to the successful completion of key project milestones. Allow me to walk you through the essential stages of this transformative project:

1. Database Design:
Crafted a normalized relational database schema ensuring data integrity. Documented the design to pave the way for a strong foundation.

Department Tables: Manage department details, including ID, name, required credits, and their locations.

Students Tables: Store student information such as ID, names, gender, department affiliation, date of birth, address, and Emails.

Registration: Tracks student enrollment in courses, including student ID, course ID, department ID, and schedule.

Courses: Hold information on courses, including course ID, course name, credit hours, and success grade.

Grades: Record student grades for courses, capturing student ID, student grade, success grade, grade point, and result status.

GPA: Store student GPA details, including student ID, department ID, required credits, student GPA, registered hours, and final result.

2. SQL Implementation:
Developed SQL scripts to construct the database schema, populated it with sample data, tested, and validated its correctness.

3. PLSQL Implementation:
Implemented a series of triggers and procedures to maintain data integrity and automate various processes within the system:

Update Registration Schedule Trigger: Ensures that the schedule is updated in the Registration table when changes are made in the Departments courses table.

Courses Update Trigger: Updates the courses ID in the Registration table when changes are made in the courses table.

Departments Update Trigger: Updates the Department ID in the Registration table when changes occur in the UNI Departments table.

Students Update Trigger: Updates the STUDENT ID in the Registration, Grades, and GPA tables when changes are made in the students table.

Departments Num Trigger: Dynamically updates the students’ number in the UNI Departments table based on the count of students in each department.

Update StudDEP Trigger: Updates the DEPARTMENT ID in the students table after changes in the UNI Departments table.

Update Students Grades Trigger: Updates the STUDENT ID in the Grades table after changes in the students’ table.

Update Students GPA Trigger: Updates the STUDENT ID in the GPA table after changes in the students’ table.

Departments Trigger GP: Updates the Department ID in the GPA table after changes in the UNI Departments table.

Students Trigger GP: Ensures the GPA table is updated after insert or update in the students’ table.

GPA Insert Trigger: Manages the insertion of records into the GPA table based on Registration data.

Students Final result Procedure: Determines the final result ('Pass' or 'Fail') for a student based on the count of 'Fail' or 'Not Complete' grades at every course that is registered to them.

Students total registered hours Procedure: Calculates the total registered credit hours for a student in a specific department.

GPA calculator Procedure: Computes the student's GPA based on the weighted sum of grade points and credit hours for all their completed courses.

These procedures collectively contribute to the dynamic and efficient functioning of the data management system, providing valuable insights into students' academic performance and progress. 

4. Automation Scripts:
Crafted Bash scripts for essential tasks: a database backup script and a disk space monitoring script with alerts. Scheduled a script to detect anomalies and send timely notifications, ensuring proactive management.

5. Java Application Development:
Embarked on the Java Application Development phase by organizing distinct folders for Students Data, Courses Data, Departments Data, Registrations, Grades, and Reports, while adhering to Object-Oriented Programming (OOP) principles. Brought the core CRUD operations (Create, Read, Update, Delete) to life within the Java application, fostering a seamless connection with the SQL database. Tested the application across various scenarios to ensure robust functionality and user satisfaction.

6. Integration and Reporting:
Elevated the project by introducing a sophisticated reporting feature within the Java application. Users now possess the capability to generate comprehensive reports that unveil information, including All Students, courses and departments Data , enrolled students at courses, and average GPA for each department. This enhancement not only provides valuable insights into academic metrics but also positions the application as a powerful tool for decision-makers.
This meticulous integration guarantees a user-friendly experience, where data retrieval and reporting occur seamlessly. 

