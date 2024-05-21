# CS307 Principles of Database Systems - Spring 2024
## Project 2 Report 

### Group Members: 
- **ZERHOUNI KHAL Jaouhara (12211456)**
- **HOK Layheng (12210736)**
- **Lab Session**: Tuesday (5-6)
- **Instructor**: Dr. MA Yuxin

---

## CONTENT
- Contribution
- Basic Requirements (65%)
  - API Specification
  - Functional Requirements
- Advanced Requirements (30%) 
- Conclusion

---

### CONTRIBUTION

| Members                    | Tasks                                                                                                                                                                                                                                                                                                                                                                                                                        | Ratio |
|----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------|
| **ZERHOUNI KHAL Jaouhara** | - Import new data (price)<br>- CRUD on station data<br>- CRUD on the line detail data (relationship between stations and lines)<br>- Search for the n-th station’s details that come before or after a specified station on a line<br>- Design a comprehensive system for stations buses and landmarks integration<br>- UI/UX design<br>- Report                                              | 50%   |
| **HOK Layheng**            | - Set up project and dependencies with Maven<br>- Set up triggers and procedures<br>- Build API with Spring Boot<br>- CRUD on the line data<br>- Boarding and exiting functionalities<br>- View information about ongoing rides passengers and cards<br>- Utilize the status of stations and different ride classes<br>- Filter ride records with multi-parameter inputs and utilize pagination to handle large ride results | 50%   |

---

### BASIC REQUIREMENTS

#### API Specification

1. **Add, Modify, Delete a Station**
   - **Purpose**: Manage individual station records.
   - **Use**: Create new stations, update existing station details, or remove stations from the database.

2. **Add, Modify, Delete a New Line**
   - **Purpose**: Manage subway line records.
   - **Use**: Create new subway lines, update existing line details, or remove lines from the database.

3. **Station and Line Management**
   - **Purpose**: Manage the placement and removal of stations on subway lines.
   - **Use**: Place one or more stations at a specified location on a line or remove a station from a line.

4. **Search Stations**
   - **Purpose**: Retrieve station names based on their position relative to a specific station.
   - **Use**: Find the names of the stations that are the n-th station ahead or behind a specific station on a line.

5. **Boarding Functionality**
   - **Purpose**: Record boarding information for passengers or cards.
   - **Use**: Record the starting station, boarding time, and passenger or card details when they board the subway.

6. **Exit Functionality**
   - **Purpose**: Record exit information for passengers or cards and calculate trip cost.
   - **Use**: Record the destination station, exit time, and calculate the price based on `Price.xlsx` when passengers or cards exit the subway.

7. **View Current Boarded Passengers/Cards**
   - **Purpose**: Display information about passengers or cards currently on board.
   - **Use**: View all information about passengers or cards who have boarded but have not yet exited at the current time.

---

### Functional Requirements

- **Database Implementation**: 
  - **Requirement**: Complete the project using OpenGauss or MySQL database.
  - **Implementation**: The project utilizes MySQL database to manage and store all necessary data including stations, lines, passenger details, and pricing information.

- **Station Status Management**: 
  - **Requirement**: Add and appropriately utilize the status of stations (e.g., under construction, operational, closed).
  - **Implementation**: Extended the station management system to include status indicators. Stations can now have statuses like "under construction", "operational", and "closed." These statuses are stored in the database and can be updated via API endpoints.

- **Business Carriage in the Subway**: 
  - **Requirement**: Include functionality for business carriages in the subway.
  - **Implementation**: Added a new feature that designates certain carriages as business carriages alongside a price adjustment (up to 30%) of the original price. Passengers can book seats in these carriages.

- **Integration of Buses and Subways**: 
  - **Requirement**: Establish a comprehensive system to integrate buses and subways.
  - **Implementation**: Developed an integrated transport management system that allows for combined queries and ticketing for both buses and subways. Users can view bus details associated with specific subway stations, manage these associations, and plan routes that include both modes of transport.

- **Integration of Landmarks**: 
  - **Requirement**: Establish a comprehensive system to integrate landmarks and subways.
  - **Implementation**: Users can view landmark details associated with specific subway stations.

- **Multi-Parameter Search for Ride Records**: 
  - **Requirement**: Enable searching ride records based on multiple parameters.
  - **Implementation**: Created a robust search functionality in the API that allows users to search ride records based on various parameters such as subway stations, passengers, periods, and more.

- **View User Details**:
  - **Using the developed system users can view detailed information about passengers and cards.**
    - **Passenger Details**:
      - If a user clicks on a passenger's ID number, they can view detailed passenger information including:
        - National ID
        - Name
        - Gender
        - Phone Number
        - Origin
    - **Card Details**:
      - If a user clicks on a card number, they can view detailed card information including:
        - Code
        - Balance
        - Created Time

- **Package Management**: 
  - **Tool Used**: Maven
  - **Implementation**: Maven was used for managing project dependencies, ensuring all required libraries and tools were included and properly configured in the project.

- **Using Sockets or HTTP/RESTful Web**: 
  - **Tool Used**: Spring Boot
  - **Implementation**: Developed a backend server using Spring Boot to handle HTTP/RESTful requests. This allows the application to support various API endpoints for managing stations, lines, passenger details, and more.

- **Using Backend Frameworks or ORM Mapping**: 
  - **Tool Used**: JPA (Jakarta Persistence API) with Spring Boot
  - **Implementation**: Used JPA for Object-Relational Mapping (ORM) to interact with the MySQL database. This simplifies database operations by allowing developers to work with Java objects instead of raw SQL queries.

- **Big Data Management**:
  - **Requirement**: Implement big data management to efficiently handle large datasets.
  - **Implementation**: Utilized pagination to display large data sets in manageable pages. Specifically in the ride table, large datasets are broken into pages with each page fetching 100 rows of data. This approach ensures that the system remains responsive and the data is easily navigable for users.

- **Effective Presentation and Communication**:
  - **Requirement**: Ensure effective presentation and communication of data and functionalities.
  - **Implementation**: Developed a simple, interactive, and responsive web page UI. The design is optimized to work seamlessly across all forms of devices, including mobile, tablet, and desktop. The interface features smooth scrolling and animations, providing a user-friendly and engaging experience for users.
