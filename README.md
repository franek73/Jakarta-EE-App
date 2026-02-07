# Recipe Sharing Web Application

This repository contains a web application developed as part of the **Narzedzia i Aplikacje Jakarta EE** course for computer science students.

The application allows users to add, browse, and share recipes. It leverages Jakarta EE technologies including Jakarta Faces for the front end, CDI, JPA, and exposes RESTful services. The application also supports internationalization.

## Features

- User-friendly website interface
- Recipes and categories management
- RESTful API for integration
- Security API for user authentication
- AJAX for dynamic content updates
- Internationalization support

## Tech Stack

- Jakarta EE
- CDI (Contexts and Dependency Injection)
- JSF (Jakarta Faces)
- JAX-RS (Jakarta RESTful Web Services)
- JPA (Jakarta Persistence API)
- EJB (Enterprise JavaBeans)

## Requirements

To build and run the project, the following tools are required:

- OpenJDK 17
- Apache Maven 3.8
- Node.js 18
- npm 9.5
- Angular CLI 16

## Building

To build the project, run:

```bash
mvn clean package
```

## Running

To build the project, run:

```bash
mvn -P liberty liberty:dev
```

The website will be available at: http://localhost:9080/jakarta-ee-project
