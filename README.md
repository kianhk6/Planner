# Course Planner

## Introduction

Course Planner is a Spring Boot server application designed to assist students in planning their courses at SFU. It provides insights into past course offerings to predict future availabilities. This server interfaces with a web-based UI through a REST API, processing and organizing data from a CSV file containing historical course data.

## Getting Started

### Prerequisites
- Java 8 or later
- Spring Boot
- Maven (for dependency management)

### Installation
1. Clone the repository or download the source code.
2. Ensure that Java and Maven are correctly installed on your system.
3. Navigate to the project directory and run the application using Maven:
   ```
   mvn spring-boot:run
   ```

## Project Structure

### Data Input
- Place the `course_data_2018.csv` file in the `data/` folder at the root of the project.
- The CSV file includes columns for semester, subject, catalog number, location, enrollment capacity, enrollment total, instructors, and component code.

### Phase 1: Initial Model Implementation
- Model reads data from the CSV file.
- A Spring Boot controller to print the model structure to the local terminal.

### Phase 2: Enhancements and REST API
- Implementation of REST API for integration with the provided web front-end.
- Generation of department-specific data regarding student enrollment per semester.
- Ability to add more class offerings and record changes to specific courses.

## REST API Endpoints

- `GET /api/dump-model`: Dumps a summary of the model to the server’s terminal.

## Usage

1. Run the server using the command mentioned in the Installation section.
2. Access the web-based UI at `http://localhost:8080`.
3. Use the application to view past course offerings and predict future availability.


