# Database Management Course Project

## Project Description
This program is a CLI tool to create and manage Exams and Questions in a Postgres database.

## Project Structure
- `src` - Contains the source code of the program
- `sql` - Contains the sql scripts to create the database tables and triggers

## How to Run
1. Clone the repository
2. Create a postgres database, make sure you have installed a Postgres java driver.
   if you don't have one you can download it from [here](https://jdbc.postgresql.org/download/)
3. run the sql script in the `sql` folder in the following order:
    - `finalproject_tables.sql`
    - `trig finalproject.sql`
    - `questions finalproject.sql`
    - `query_add_question_answer_finalproject.sql`
4. Go to the `src` folder and open Main.java
5. At the top put your db credentials in the `User` and `Password` variables
6. Run the program

## Authors
- [Daniel Jerbi](https://github.com/danielgerbi7)
- [Yarden Perets](https://github.com/lordYorden)
- [Netanel Michel](https://github.com/natim1997)