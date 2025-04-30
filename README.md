# RESTful API for Recipe Management System using Spring Boot 3
- [Tech Stack](#tech-stack)
- [Run Project](#run-project)
- [Containerized application with Docker](#containerized-application-with-docker)
- [Prometheus for Monitoring](#prometheus-for-monitoring)
- [Spring Boot Jwt Based Authentication and Role-based access control](#spring-boot-jwt-based-authentication)
  - [UserDetailsService](#userdetailsservice)
  - [Add SecurityFilterChain](#add-securityfilterchain)
  - [Generate Jwt Token](#generate-jwt-token)
  - [Implement JwtAuthenticationFilter](#implement-jwtauthenticationfilter)

## Tech Stack:
* Spring Boot 3.x
* Java 17+
* Spring Data JPA
* jOOQ Object Oriented Querying
* Spring Security - JWT Token Authentication
* PostgreSQL - Neon tech
* Gradle for dependency management




## Run Project

```
./gradlew bootRun
```

## Containerized application with Docker

```
<!-- Build docker image -->
docker build -t recipe-management .

<!-- Run container from image-->
docker run --name recipe-management -p 8080:8080 recipe-management
```

## Prometheus for Monitoring

Add prometheus dependencies

```
  implementation 'io.micrometer:micrometer-registry-prometheus'
```

Add prometheus.yaml

```
scrape_configs:
  - job_name: 'RecipeManagementMetrics'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 3s
    static_configs:
      - targets: ['localhost:8080']
        labels:
          application: 'Recipe Management System'

```

Monitoring through API calls
```
localhost:8080/actuator/prometheus
```

## Spring Boot Jwt Based Authentication

### UserDetailsService

By default Spring Boot has a default Basic Authentication implemented. Spring boot will provide the username and password in the command line when application is booted. Therefore, process of registering user and authenticate the user required to Override the existing default Authentication. 

Add CustomUserDetailsService which implements the UserDetailsService from Spring. Implement the function loadByUsername and getting the UserDetails information from Database.

```
@Service
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return usersRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
  }
}
```

### Add SecurityFilterChain

Main responsibility of a SecurityFilterChain is to provide flexibiltiy and extensible way to secure the applications such as only allows ADMIN to access admin level modules and USER to access the main functionality of the application. 

Example of SecurityFilterChain

```
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // CSRF will block unauthenticated POST request, therefore here will disable it
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers("/actuator/**")
                    .permitAll()
                    .requestMatchers("/auth/**")
                    .permitAll()
                    .requestMatchers("/api/v1/recipe/{recipeId}/attachments/{attachmentId}")
                    .permitAll()
                    // Allow Swagger to bypass authentication
                    .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/v3/api-docs",
                        "/swagger-resources/**",
                        "/webjars/**")
                    .permitAll()
                    // Only allow admin
                    .requestMatchers("/api/v1/admin/**")
                    .hasRole("ADMIN")
                    // // Only allow user
                    // .requestMatchers("/user")
                    // .hasRole("USER")
                    .anyRequest()
                    .authenticated());

    // Add JWT filter before UsernamePasswordAuthenticationFilter
    // If JwtAuthenticationFilter passes it will proceed to UsernamePasswordAuthenticationFilter
    // Else it will return 401
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Set the DaoAuthenticationProvider to use UserDetailsServices instead of the default one
  @Bean
  public AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(daoAuthenticationProvider);
  }
```

### Generate Jwt Token

Create a JwtUtil to generate Jwt Token after logged in successfully. By using Jwt token for authentication, every api calls would not required to do log in repeatly as Jwt token will consists of information to authenticate before the token expired.

Configure your own secret key.

```
 // Generate token and adding all the required details
  public final String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();

    // Add roles
    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    claims.put("roles", roles);

    SecretKey keys = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.builder()
        .claims(claims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
        .signWith(keys, SIG.HS256)
        .compact();
  }

  // Extracting username from the token
  public String extractUsername(String token) {
    Claims body = extractClaims(token);
    return body.getSubject();
  }

  // Extract details from the token
  private Claims extractClaims(String token) {
    SecretKey keys = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.parser().verifyWith(keys).build().parseSignedClaims(token).getPayload();
  }

  // Validate token to ensure token is not expired and username is same between token and
  // database
  public Boolean validateToken(String username, UserDetails userDetails, String token) {
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  // Extract details from token and check if expiration time is before current time
  private Boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().before(new Date());
  }

  // Extract the Roles from the token
  public List<String> extractRoles(String token) {
    Claims claims = extractClaims(token);
    List<String> roles = claims.get("roles", List.class);
    return roles;
  }

```

### Implement JwtAuthenticationFilter 

JwtAuthenticationFilter is needed for authenticating incoming HTTP requests. It intercepts requests and extracts the JWT from the Authorization header, validates the token, and sets the security context with the authenticated user's details if the token is valid.


```
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // Get header from bearer token
    String header = request.getHeader("Authorization");
    String token = null;
    String username = null;
    if (header != null && header.startsWith("Bearer ")) {
      // Get token
      token = header.substring(7);

      // Extract username from token
      username = jwtUtil.extractUsername(token);
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

      // Validate token to ensure token is not expired and username is same between token and
      // database
      if (jwtUtil.validateToken(username, userDetails, token)) {
        // Get Roles from token
        List<String> roles = jwtUtil.extractRoles(token);

        List<GrantedAuthority> authorities =
            roles.stream()
                .filter(role -> role != null && !role.trim().isEmpty())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        // Set the userDetails and the authorities of the user
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

        // Set all the request details into authentication
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }
```

