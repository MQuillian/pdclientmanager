# PDClientManager

PDClientManager is a webapp for efficiently managing the clients and caseload of a public defender's office. By streamlining processes for creating and controlling the various records, PDClientManager can offer the support and tools necessary to achieve the optimal results for clients.

PDClientManager tracks and displays various metrics, such as aging reports, that often have to be calculated manually. Automating the calculation of this kind of essential information relieves the burden on office staff of managing this responsibility. This encourages continuous awareness of the overall health and functioning of the office, thereby shortening the time required to address any issues and improving outcomes for clients.

## Technologies used

![Java](https://img.shields.io/badge/-Java-brightgreen) ![Spring](https://img.shields.io/badge/-Spring_Framework--Data--Security--AOP-blue) ![JavaScript](https://img.shields.io/badge/-JavaScript-lightgrey) ![Hibernate](https://img.shields.io/badge/-Hibernate-9cf) ![JUnit](https://img.shields.io/badge/-JUnit-red) ![Docker](https://img.shields.io/badge/-Docker-0fffa3) ![AWS](https://img.shields.io/badge/-AWS-071942) ![Maven](https://img.shields.io/badge/-Maven-yellow) ![MySQL](https://img.shields.io/badge/-MySQL-blueviolet) ![GCalendar](https://img.shields.io/badge/-Google_Calendar_API-orange) ![Log4J2](https://img.shields.io/badge/-Log4J2-yellowgreen)

## Features

* Create/manage accounts, employees, clients, and cases via Spring Data
* Integration with Google Calendar to create/manage case events
* Efficiently calculate statistics for office-wide or individual caseloads using MySQL stored procedures
* Secured login credentials and endpoints via Spring Security
* Form validation using JavaScript and Spring validators
* AOP exception handlers to intercept and handle exceptions
* Deployable to Kubernetes cluster to support high availability and and fault tolerance
* JUnit unit tests and MockMVC integration tests  to identify bugs and protect application integrity

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

The application has been deployed on an AWS EC2 instance and may be accessed [here](http://PDCM-ALB-407053983.us-west-2.elb.amazonaws.com/pdclientmanager).

## Potential Roadmap

* Expand available metrics and provide greater insight to legal practice data and trends
  * Sentencing results and trends to provide insight for negotiations and client advisement
  * Changes in caseload movement over time to guarantee efficient management of caseloads in various courtrooms
  * Caseload comparisons for attorneys and investigators to enable optimal distribution of casework


## License
[Apache 2.0](https://choosealicense.com/licenses/apache-2.0/)
