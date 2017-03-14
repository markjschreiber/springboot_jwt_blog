# Demo of using JWT to secure a Spring Boot webservice

Creates a very basic demo service that deploys to `localhost:8080`. There is a unsecured 'Hello world' endpoint at `localhost:8080/`.

In addition, there is a `/users` endpoint that will return JSON but only if presented with a valid JWT in the header.

To get the JWT you can make `POST` call to `/login` with a JSON body `{"username":"admin","password":"password"}`. The username and password is configured in code
in the `WebConfig.java`. Obviously a real application would use a more sophisticated login. The `POST` returns a Authorization header with a Bearer token. The value of that
token is used in the call to `/users`.

`curl -X POST -d '{"username":"admin","password":"password"}' "http://localhost:8080/login"`

returns in the header (among other things):

`Authorization Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTQ5MDM4MjQ1M30.z3vU8eV85Hw9THDpkDQokppXCev-MmtCzSASkL_7UVGsG-6GCNGv1c7z38mwslBFIOt9bb3COa4qRrV0337zQw`

To call the `/users` service you make a `GET` call with the Authorization header set:

`curl -X GET -H "Authorization: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTQ5MDM4MjQ1M30.z3vU8eV85Hw9THDpkDQokppXCev-MmtCzSASkL_7UVGsG-6GCNGv1c7z38mwslBFIOt9bb3COa4qRrV0337zQw" "http://localhost:8080/users/"`

The token is usable for 10 hours although it will change with each call to login.
