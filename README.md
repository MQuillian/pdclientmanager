# PDClientManager

PDClientManager is a webapp for efficiently managing the clients and caseload of a public defender's office. By streamlining processes for creating and controlling the various records, PDClientManager seeks to enable those providing indigent legal services the support and tools necessary to achieve the optimal results for clients.

PDClientManager tracks and displays various metrics, such as aging reports, that often have to be calculated manually. Automating the calculation of this kind of essential information relieves the burden on office staff of managing this responsibility. This encourages continuous awareness of the overall health and functioning of the office, thereby shortening the time required to address any issues and improving outcomes for clients.

## Technologies used

![Java](https://img.shields.io/badge/-Java-brightgreen) ![Spring](https://img.shields.io/badge/-Spring_Framework--Data--Security--AOP-blue) ![JavaScript](https://img.shields.io/badge/-JavaScript-lightgrey) ![Hibernate](https://img.shields.io/badge/-Hibernate-9cf) ![JUnit](https://img.shields.io/badge/-JUnit-red) ![Docker](https://img.shields.io/badge/-Docker-0fffa3) ![Kubernetes](https://img.shields.io/badge/-Kubernetes-071942) ![Maven](https://img.shields.io/badge/-Maven-yellow) ![MySQL](https://img.shields.io/badge/-MySQL-blueviolet) ![GCalendar](https://img.shields.io/badge/-Google_Calendar_API-orange) ![Log4J2](https://img.shields.io/badge/-Log4J2-yellowgreen)

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

## Getting Started

1. To run on local kubernetes cluster, (install minikube)[https://minikube.sigs.k8s.io/docs/start/]
2. Copy pdcm-app.yaml and pdcm-db.yaml files into desired application directory
3. If you do not yet have a Google service account, [create one](https://cloud.google.com/iam/docs/creating-managing-service-accounts#iam-service-accounts-create-console)
4. If you do not yet have a Google service account key, [create one as a .json file](https://cloud.google.com/iam/docs/creating-managing-service-account-keys#iam-service-account-keys-create-console)
5. Name your service account key calendar-key.json and place it in your desired application directory
6. Navigate to your desired directory via command line and execute the following commands:
    * minikube start
    * kubectl create secret generic mysql-root-pass --from-literal=password=YOUR_DESIRED_ROOT_PASSWORD
    * kubectl create secret generic mysql-user-pass --from-literal=username=YOUR_DESIRED_USERNAME --from-literal=password=YOUR_DESIRED_PASSWORD
    * kubectl create secret generic mysql-db-url --from-literal=database=pdcm_db --from-literal=url='jdbc:mysql://pdcm-mysql:3306/pdcm_db'
    * kubectl create secret generic calendar-key --from-file=key.json=calendar-credentials.json
    * kubectl apply -f pdcm-db.yaml; kubectl apply -f pdcm-app.yaml
7. Wait for pods to deploy and application context to initialize (you can monitor progress by finding the pdcm-app pod with 'kubectl get pods' and then 'kubectl logs -f pdcm-app-POD_ID')
8. Once complete, you may use 'minikube service pdcm-app' command to open in the browser. Append '/pdclientmanager' to URL to be brought to login page.
9. Default login credentials are username: admin, password: adminpass
10. You may create your own users with different roles for future logins by navigating to System Management -> Add User


## Roadmap

* Expand available metrics and provide greater insight to legal practice data and trends
  * Sentencing results and trends to provide insight for negotiations and client advisement
  * Changes in caseload movement over time to guarantee efficient management of caseloads in various courtrooms
  * Caseload comparisons for attorneys and investigators to enable optimal distribution of casework


## License
[Apache 2.0](https://choosealicense.com/licenses/apache-2.0/)
