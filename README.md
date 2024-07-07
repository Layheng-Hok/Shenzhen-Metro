<div align=center>

# Shenzhen Metro

SUSTech 2024 Spring's Projects of Course `CS307 - Principles of Database System`

Divided into two parts as shown below</a>
</div>

### Simplified Directory Path Diagram
```
Shenzhen-Metro
├── Project1                                                            # part 1 of project (database design and data import)
│   ├── ShenzhenMetroDatabaseDesign
│   │   ├── src
│   │   │   ├── main
│   │   │   │   ├── java                                                # import script in java
│   │   │   │   ├── resources                                           # data in json
│   │   │   │   └── sql                                                 # ddl
│   └── ShenzhenMetroDatabaseDesignPython
│       ├── import_script.py                                            # import script in python
│       └── resources                                                   # data in json
├── Project2                                                            # part 2 of project (building an api)
│   ├── DataImport                                                      # updated data import       
│   │   ├── src                                 
│   │   │   ├── main    
│   │   │   │   ├── java                                                # updated import script in java  
│   │   │   │   ├── resources                                           # updated data
│   │   │   │   └── sql                                                 # updated ddl
│   └── ShenzhenMetro                                                   # api built with spring boot
│       ├── src
│       │   ├── main
│       │   │   ├── java                                                # backend logic
│       │   │   │   └── com/sustech/cs307/project2/shenzhenmetro        
│       │   │   │       ├── ShenzhenMetroApplication.java               # main application driver
│       │   │   │       ├── controller                                  # api route mapping
│       │   │   │       ├── dto                                         # dto between client and server
│       │   │   │       ├── object                                      # orm between database tables and application code
│       │   │   │       ├── repository                                  # interfaces for data access
│       │   │   │       └── service                                     # service layer containing business logic
│       │   │   └── resources                                           # frontend logic
│       │   │       ├── application.properties                          # configuration file for spring boot application
│       │   │       ├── static
│       │   │       │   ├── assets
│       │   │       │   │   ├── css                                     
│       │   │       │   │   ├── img
│       │   │       │   │   ├── js
│       │   │       │   │   └── vendor
│       │   │       │   │       ├── aos
│       │   │       │   │       ├── bootstrap
│       │   │       │   │       ├── bootstrap-icons
│       │   │       │   │       ├── glightbox
│       │   │       │   │       └── swiper
│       │   │       │   └── index.html                                  # main html
│       │   │       └── templates                                   
│       │   │           ├── buses
│       │   │           │   ├── create_bus.html
│       │   │           │   ├── index.html
│       │   │           │   └── update_bus.html
│       │   │           ├── landmarks
│       │   │           │   ├── create_landmark.html
│       │   │           │   ├── index.html
│       │   │           │   └── update_landmark.html
│       │   │           ├── lineDetails
│       │   │           │   ├── create_line_detail.html
│       │   │           │   ├── index.html
│       │   │           │   ├── navigate_routes.html
│       │   │           │   └── search_line_detail.html
│       │   │           ├── lines
│       │   │           │   ├── create_line.html
│       │   │           │   ├── index.html
│       │   │           │   └── update_line.html
│       │   │           ├── ongoingRides
│       │   │           │   └── index.html
│       │   │           ├── rides
│       │   │           │   ├── create_ride.html
│       │   │           │   ├── filter_ride.html
│       │   │           │   ├── index.html
│       │   │           │   └── update_ride.html
│       │   │           ├── stations
│       │   │           │   ├── create_station.html
│       │   │           │   ├── index.html
│       │   │           │   └── update_station.html
│       │   │           └── users
│       │   │               ├── card.html
│       │   │               └── passenger.html
└── README.md
```

### Tool Used
#### Part 1
<p align="left">
    <a href="https://www.java.com" target="_blank" rel="noreferrer">
        <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/>
    </a>
    <a href="https://www.python.org" target="_blank" rel="noreferrer">
        <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/python/python-original.svg" alt="python" width="40" height="40"/>
    </a>
    <a href="https://www.mysql.com/" target="_blank" rel="noreferrer">
        <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/>
    </a>
    <a href="https://www.postgresql.org" target="_blank" rel="noreferrer">
        <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/>
    </a>
</p>


#### Part 2
<p align="left">
    <a href="https://en.wikipedia.org/wiki/HTML" target="_blank" rel="noreferrer">
        <img src="https://upload.wikimedia.org/wikipedia/commons/3/38/HTML5_Badge.svg" alt="html5" width="40" height="40"/>
    </a>
    <a href="https://en.wikipedia.org/wiki/CSS" target="_blank" rel="noreferrer">
        <img src="https://upload.wikimedia.org/wikipedia/commons/6/62/CSS3_logo.svg" alt="css3" width="40" height="40"/>
    </a>
    <a href="https://getbootstrap.com" target="_blank" rel="noreferrer">
        <img src="https://upload.wikimedia.org/wikipedia/commons/b/b2/Bootstrap_logo.svg" alt="bootstrap" height="40"/>
    </a>
    <a href="https://www.java.com" target="_blank" rel="noreferrer">
        <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/>
    </a>
    <a href="https://spring.io/" target="_blank" rel="noreferrer">
        <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/>
    </a>
    <a href="https://www.mysql.com/" target="_blank" rel="noreferrer">
        <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/>
    </a>
</p>

## Part 1: Database Design and Data Import
### Requirements
The first part of the project is mainly about designing a database schema that satisfies the principles of relation databases based on the background of the provided data. Once the design phase is complete, we wrote scripts to import those large datasets. Then we ran some experiments with data to gain some insights, as shown later.

[*[Read the detailed requirements]*]()