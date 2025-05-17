# 🧪 Authentication with Passkeys Lab

## 🎯 Objective

Learn how authentication with Passkeys works and is implemented using Spring Security.

## 🧰 Lab Prerequisites

Check if your operating system supports Passkeys. If not, you can use a cross-device secret store like 1Password to register and use Passkeys.

![Passkeys on different OS](image/how_passkeys_work_on_different_os.png "Passkeys Support on different OS")

Note: Passkeys are supported to run on localhost without any TLS certificate. However, this is not recommended for production use. For production use, you need a valid TLS certificate for your domain.

![Passkeys on different domains](image/passkeys_on_localhost.png "Passkeys Support on different Domains")

---

## 🔹 Lab 1: Passkeys (on localhost)

### Step 1: Get to know the provided application

The `passkeys` directory contains a simple Spring Boot application that demonstrates how to use Passkeys for authentication. The application is configured with a main index.html page containing all important links for the demonstration scenario.

Start the application using your IDE or using the maven spring boot plugin with `./mvnw springboot:run`.
Then navigate to [localhost:8080](http://localhost:8080). You should see a welcome page with the following links:

- **Register**: Click on this link to register your user for a Passkey (using Keychain on Mac, Browser, 1Password, etc.)
- **Call Hello API**: This calls a testing API that welcomes you as a user
- **Log Out**: Force a logout, i.e., to test logging in using a passkey

![Passkey Demo](image/password_demo_app.png "Passkeys Demo")

Before continuing to the next step, stop the application using `Ctrl+C` in the terminal or using your IDE.

---

### Step 2: Extend the application for Passkeys

Now it is time to get **Passkeys** working in the application. 

The application already contains the necessary dependencies for Passkeys. You can find the configuration in the `pom.xm` file.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>com.webauthn4j</groupId>
    <artifactId>webauthn4j-core</artifactId>
    <version>0.29.0.RELEASE</version>
</dependency>
```

Next up is the configuration of the `WebSecurityConfiguration` class. This class is responsible for configuring the security filter chain and thus enabling Passkeys.

So let's add the corresponding code snippet to the `WebSecurityConfiguration` class:

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        authorizeRequests -> {
                            authorizeRequests.requestMatchers("/login/**", "/message", "/error").permitAll();
                            authorizeRequests.anyRequest().authenticated();
                        }
                )
                .headers(headers -> headers.httpStrictTransportSecurity(HstsConfig::disable))
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                // add this snippet for webAuthn support to enable Passkeys
                .webAuthn((webAuthn) -> webAuthn
                        .rpName("Spring Security Relying Party")
                        .rpId("localhost")
                        .allowedOrigins("http://localhost:8080")
                );
        return http.build();
    }
    //... other beans and configuration
}
```

---

### Step 3: Run the application (with Passkeys)

Start the application in your IDE or using the maven spring boot plugin with `./mvnw springboot:run`.

Now navigate your browser to [localhost:8080](http://localhost:8080). First, you need to log in with the user credentials user/password. Now you should see a welcome page with the following links:

- **Register**: Click on this link to register your user for a Passkey (using Keychain on Mac, Browser, 1Password, etc.)
- **Hello**: This calls a testing API that welcomes you as a user
- **Log Out**: Force a logout, i.e., to test logging in using a passkey


## 🔹 Lab 2: Passkeys with a secure local domain (optional lab)

### 🧰 Lab Prerequisites

- Java 21 or higher
- A Java IDE (IntelliJ, Eclipse, etc.)
- Access to a modern web browser (Chrome, Firefox, Safari, etc.)
- Operating System compatible with Passkeys (macOS, Windows 11) and/or a cross-device secret store like 1Password
- A working local domain (e.g., `server.test`) with a valid TLS certificate
- [mkcert](https://github.com/FiloSottile/mkcert) installed on your machine to create a local CA and trusted certificates

### 🛠️ Setup

#### mkcert

To quickly set up a TLS configuration required for Passkeys, we need to install the tool [mkcert](https://github.com/FiloSottile/mkcert). [mkcert](https://github.com/FiloSottile/mkcert) is a simple tool for making locally-trusted development certificates.

On **MacOS**, you can install [mkcert](https://github.com/FiloSottile/mkcert) using Homebrew:

```bash
brew install mkcert
brew install nss # if you use Firefox
```

On **Windows**, you can install [mkcert](https://github.com/FiloSottile/mkcert) using Chocolatey:

```bash
choco install mkcert
```

or just install the binaries from the [mkcert releases page](https://github.com/FiloSottile/mkcert/releases)

On **Linux**, you can install [mkcert](https://github.com/FiloSottile/mkcert) using the pre-built binaries:

```bash
curl -JLO "https://dl.filippo.io/mkcert/latest?for=linux/amd64"
chmod +x mkcert-v*-linux-amd64
sudo cp mkcert-v*-linux-amd64 /usr/local/bin/mkcert
```

### Step 1: Create a local CA

Using Passkeys with most providers requires secure HTTPS connections and does not work with the localhost domain.
Therefore, this sample is configured for https://server.test:8433 address.

[RFC 2606](https://www.rfc-editor.org/rfc/rfc2606.html) officially reserves the following domains for testing and development:

- `.test`
- `.example`
- `.invalid`
- `.localhost`

To enable this local domain on your machine, on **MacOS** and **Linux** you need to add the following entry to your `/etc/hosts` file:

```shell
127.0.0.1 server.test
```

On **Windows 11**, you can add the entry to the `C:\Windows\System32\drivers\etc\hosts` file.

```shell
127.0.0.1 server.test
```

After installing [mkcert](https://github.com/FiloSottile/mkcert), you need to create a local CA (Certificate Authority) that will be used to sign the certificates for your local domain. This will also import the root certificate into your system's trust store, Java and browsers:

```bash
mkcert -install
```

After installing the local CA with the root certificate, it is time to create a keystore containing the required certificate:

```shell
mkcert -p12-file server-keystore.p12 -pkcs12 127.0.0.1 localhost server.test
```

Finally, copy the generated `server-keystore.p12` file to the `src/main/resources` directory of the `passkeys` module.

### Step 2: Extend the application for Passkeys

The `passkeys-demo` module contains a simple Spring Boot application that demonstrates how to use Passkeys for authentication. The application is configured to use the `server-keystore.p12` file for TLS.

The application already contains the necessary dependencies for Passkeys. You can find the configuration in the `pom.xm` file.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>com.webauthn4j</groupId>
    <artifactId>webauthn4j-core</artifactId>
    <version>0.29.0.RELEASE</version>
</dependency>
```

```java
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        authorizeRequests -> {
                            authorizeRequests.requestMatchers("/login/**", "/message", "/error").permitAll();
                            authorizeRequests.anyRequest().authenticated();
                        }
                )
                .headers(headers -> headers.httpStrictTransportSecurity(HstsConfig::disable))
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
                // add this snippet for webAuthn support to enable Passkeys
                .webAuthn((webAuthn) -> webAuthn
                        .rpName("Spring Security Relying Party")
                        .rpId("server.test")
                        .allowedOrigins("https://server.test:8443")
                );
        return http.build();
    }
    //... other beans and configuration
}
```

### Step 3: Run the application

Start the application in your IDE or using the maven spring boot plugin with `./mvnw springboot:run`.

Now navigate your browser to https://server.test:8443. First, you need to log in with the user credentials user/password. Now you should see a welcome page with the following links:

- **Register**: Click on this link to register your user for a Passkey (using Keychain on Mac, Browser, 1Password, etc.)
- **Hello**: This calls a testing API that welcomes you as a user
- **Log Out**: Force a logout, i.e., to test logging in using a passkey
