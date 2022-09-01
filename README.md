# [PDClientManager](http://pdcm-alb-1-1934426545.us-west-2.elb.amazonaws.com/pdclientmanager)
NOTE: The deployed application is only available from 16:00 - 23:00 UTC (9am - 4pm PDT) in order to save on costs.

PDClientManager is a webapp for efficiently managing the clients and caseload of a public defender's office. By streamlining processes for creating and controlling the various records, PDClientManager offers the support and tools necessary to achieve the optimal results for clients.

PDClientManager tracks and displays various metrics, such as aging reports, that often have to be calculated manually. Automating the calculation of this kind of essential information relieves the burden on office staff of managing this responsibility. This encourages continuous awareness of the overall health and functioning of the office, thereby shortening the time required to address any issues and improving outcomes for clients.

## Technologies used

![Java](https://img.shields.io/badge/-Java-brightgreen) ![Spring](https://img.shields.io/badge/-Spring_Framework_[Data,_Security,_AOP]-blue) ![JavaScript](https://img.shields.io/badge/-JavaScript-lightgrey) ![Hibernate](https://img.shields.io/badge/-Hibernate-9cf) ![JUnit](https://img.shields.io/badge/-JUnit-red) ![Docker](https://img.shields.io/badge/-Docker-0fffa3) ![AWS](https://img.shields.io/badge/-AWS_[EC2,_S3]-071942) ![Maven](https://img.shields.io/badge/-Maven-yellow) ![MySQL](https://img.shields.io/badge/-MySQL-blueviolet) ![GCalendar](https://img.shields.io/badge/-Google_Calendar_API-orange) ![Log4J2](https://img.shields.io/badge/-Log4J2-yellowgreen)

## Features

* Create/manage user accounts, employees, clients, and cases via Spring Data
* Integration with Google Calendar to create/manage case events
* Upload and view case documents using AWS S3
* Efficiently calculate statistics for office-wide or individual caseloads using MySQL stored procedures
* Secured login credentials and endpoints via Spring Security
* Form validation using JavaScript and Spring validators
* AOP exception handlers to intercept and handle exceptions
* Dockerized deployment to AWS EC2 using an Auto Scaling Group and Application Load Balancer to enable high availability and fault tolerance
* JUnit unit tests and MockMVC integration tests  to provide immediate development feedback, identify bugs, and protect application integrity

## Project Packages Description

* **calendar** - Controller, service, and entity classes for Google Calendar integration
* **config** - Config classes for Hibernate and Spring Web MVC/Data/Security
* **controller** - Spring WebMVC controller classes
* **documents** - Controller, service, and entity classes for AWS S3 integration
* **model.form** - Classes for form-backing objects used in controllers
* **model.projection** - Projections/DTOs used with Spring Data repositories
* **repository** - Spring Data repository classes
* **repository.entity** - Entity classes used with Hibernate/Spring Data repositories
* **service** - Services between controllers and repositories
* **util** - Utility classes for controllers and date/time formatting
* **util.error** - Classes for AOP exception handling and error page
* **util.json** - Custom JSON serializers for entity projections
* **util.mapper** - MapStruct interfaces and resolvers for mapping between entities, projections, and forms
* **util.validator** - Classes for form and data validation

## Usage

The application has been deployed on AWS and may be accessed [here](http://pdcm-alb-1-1934426545.us-west-2.elb.amazonaws.com/pdclientmanager).
NOTE: The deployed application is only available from 16:00 - 23:00 UTC (9am - 4pm PDT) in order to save on costs.

![pdcm-home](https://user-images.githubusercontent.com/38482544/167959086-437e9d8a-3c6f-4030-86e2-682fefae6999.PNG)

#### Logging In

You may use either of the following accounts to log in:

| Username       | Password           | Privileges  |
| ------------- |-------------| -----|
| admin      | adminpass | Access to all functionality (including creating/managing employees and users) |
| attorney    | attypass      |   Access to case management ONLY (including creating/managing events and documents) |

#### Navigation Overview

* System Management
    * Add users, manage existing users
    * Manage judge/charge databases
    * Access office statistics (e.g. total caseload, average age of all in-custody cases
* Case Management
    * View list of all cases
        * On individual case page, upload documents and manage events
    * Add new cases
    * Reassign caseloads (e.g. in case of attorney retirement)
* Client Management
    * Add new clients
* Calendar Management
    * Batch add events for a court date
    * Add individual events for a single case
* Employee Mangement
    * Create/manage attorneys and investigators
* Individual Statistics
    * Generate caseload statistics and aging reports for individual attorneys

#### Sample Cases

The following is a sample of existing cases that can be used when searching for cases, creating events, etc.:

| Case Number        | Client           | Attorney  |
| ------------- |-------------| -----|
| 21J1614     | Eric Hoefle | Matt Quillian |
| 18J4761      | Franklin Fish  |   John Doe |
| 20J1417 | Jason Baddorf      |   John Doe |

Note: a full list of existing cases may be found via the Case Management page if desired

## Potential Roadmap

* Expand available metrics and provide greater insight to legal practice data and trends
  * Sentencing results and trends to provide insight for negotiations and client advisement
  * Changes in caseload movement over time to guarantee efficient management of caseloads in various courtrooms
  * Caseload comparisons for attorneys and investigators to enable optimal distribution of casework


## License
[Apache 2.0](https://choosealicense.com/licenses/apache-2.0/)
