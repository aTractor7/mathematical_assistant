# Math Teacher Assistant
This application is a prototype designed to assist mathematics teachers in handling equations and roots efficiently. This is a test project for a vacancy.

## Features
**Equation Input**: Users can input mathematical equations containing numbers (integers or decimals), as well as mathematical operations +, -, *, /, and parentheses with arbitrary nesting levels. The variable in all equations is denoted by the English letter x.

**Parentheses Validation**: The application validates the correctness of parentheses placement in the entered equations.

**Expression Validation**: The application checks the correctness of the entered expressions. There shouldn't be two consecutive mathematical operation signs. For example, "3+4" is not allowed, while "4-7" is valid.

**Equation Database (DB) Management**:

-If an equation is valid, it is stored in the database.
-Users can input roots of equations. The application verifies if the entered number is a root of any equation and stores it in the database if it is.
-Functions for searching equations in the database based on their roots are provided. For instance, users can find all equations having one of the specified roots or find equations having exactly one root stored in the database.

**Technology Stack**:

-Implemented using Java 17.
-Utilizes Maven as the build automation tool.
-Uses MySQL.
-Includes modular tests using JUnit.
