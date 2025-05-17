# Setup for the Labs

This document describes how to set up the environment for the labs.

## Step 1: Clone the repository

Clone the repository to your local machine using the following command:

```bash
git clone https://github.com/andifalk/springio25-security-workshop.git
```

---

## Step 2: Import the project into your IDE

Open your IDE and import the cloned project as a **Maven** project. Make sure to select the correct JDK version (**Java 21** or higher) for the project.

## Step 3: Update Maven dependencies in your IDE

Depending on your IDE, you may need to update the Maven dependencies. In IntelliJ IDEA, you can do this by right-clicking on the project and selecting **Maven** > **Reload Project**.

## Step 4: Get to know the project structure

The project is structured into several modules, each representing a different lab or demo. The main modules are:

- `labs/enhanced-authorization`: Contains the lab to demonstrate new authorization features, i.e., domain object authz.
- `labs/passkeys`: Contains the lab to demonstrate the Passkeys authentication.
- `labs/token-exchange`: Contains the lab and all corresponding application parts to demonstrate the OAuth2 Token Exchange feature:
  - `labs/token-exchange/spring-authorization-server`: Contains the pre-configured Spring Authorization Server.
  - `labs/token-exchange/token-echange-client`: Contains the OAuth2 client that triggers the whole scenario for OAuth2 token exchange.
  - `labs/token-exchange/token-exchange-resource-server`: Contains the resource server that performs the token exchange and calls the target resource server.
  - `labs/token-exchange/target-resource-server`: Contains the target resource server that is called with the exchanged token.