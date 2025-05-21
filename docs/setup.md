# Setup for the Labs

This document describes how to set up the environment for the labs.

## Step 1: Install Prerequisites

Before you start, ensure you have the following prerequisites installed on your machine:
- **Java Development Kit (JDK)**: Ensure you have JDK 21 or higher installed. You can download the latest version from the [Oracle website](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or use a package manager like Homebrew (for macOS) or SDKMAN! (for Linux or macOS).
- **Java IDE**: You can use any Java IDE of your choice, such as IntelliJ IDEA, Eclipse, or Visual Studio Code. Make sure to install the necessary plugins for Maven support.
- **Passkeys**: If you want to run the Passkeys demo, you will require a compatible OS and browser. The Passkeys demo is designed and tested to work with macOS and Chrome, but it may also work with other operating systems, browsers and 3rd party apps that support Passkeys (i.e., 1Password).
- **Http Client**: You can use any HTTP client of your choice to test the APIs. Some popular options include Postman, IntelliJ Http Client, Httpie, or cURL.

### IntelliJ Http Client

If you are using IntelliJ IDEA, you can use the built-in HTTP client to test the APIs. To do this, create a new file with the `.http` extension and write your HTTP requests in it. You find ready to use Http Client files in the `labs/.../requests` folder of each module. You can run the requests directly from the IDE.

### Httpie

If you prefer using the command line, you can use [Httpie](https://httpie.io/) to test the APIs. Httpie is a user-friendly command-line HTTP client that makes it easy to send HTTP requests and view responses. You can install Httpie using Homebrew on macOS:

```bash
brew install httpie
```

### cURL + jq

cURL is part of most operating systems, so you may already have it installed. If not, you can install it using Homebrew on macOS:

```bash
brew install curl
```

You can also use `jq` to format the JSON responses from cURL. You can install `jq` using Homebrew on macOS:

```bash
brew install jq
```
### Postman

If you prefer using a GUI tool, you can use [Postman](https://www.postman.com/) to test the APIs. Postman is a popular API development and testing tool that allows you to send HTTP requests and view responses in a user-friendly interface. You can download Postman from the official website and install it on your machine. 

> 💡 **Note**: Postman poses some risk when storing collections in the cloud. If you are using Postman, make sure to use it in a private workspace (requires a commercial license) and do not share your collections with others. One alternative would be to use [Bruno](https://www.usebruno.com) which is an open-source alternative to Postman.

## Step 2: Clone the repository

Clone the repository to your local machine using the following command:

```bash
git clone https://github.com/andifalk/springio25-security-workshop.git
```

---

## Step 3: Import the project into your IDE

Open your IDE and import the cloned project as a **Maven** project. Make sure to select the correct JDK version (**Java 21** or higher) for the project.

## Step 4: Update Maven dependencies in your IDE

Depending on your IDE, you may need to update the Maven dependencies. In IntelliJ IDEA, you can do this by right-clicking on the project and selecting **Maven** > **Reload Project**.

## Step 5: Get to know the project structure

The project is structured into several modules, each representing a different lab or demo. The main modules are:

- `labs/enhanced-authorization`: Contains the lab to demonstrate new authorization features, i.e., domain object authz.
- `labs/passkeys`: Contains the lab to demonstrate the Passkeys authentication.
- `labs/token-exchange`: Contains the lab and all corresponding application parts to demonstrate the OAuth2 Token Exchange feature:
  - `labs/token-exchange/spring-authorization-server`: Contains the pre-configured Spring Authorization Server.
  - `labs/token-exchange/token-echange-client`: Contains the OAuth2 client that triggers the whole scenario for OAuth2 token exchange.
  - `labs/token-exchange/token-exchange-resource-server`: Contains the resource server that performs the token exchange and calls the target resource server.
  - `labs/token-exchange/target-resource-server`: Contains the target resource server that is called with the exchanged token.