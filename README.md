gae-rest-skeleton
=============================

This is a simple skeleton JAX-RS application for Google App Engine.  This application provides
two REST calls

```GET http://localhost:8181/hello```
Returns the string "REST API is available."  This is used to verify that REST is responding.


```GET http://localhost:8181/version```
Returns the Server build information.  This relies on the ServerVersion class contained 
in [Protocols Resources](https://github.com/deege/protocols-resources)
