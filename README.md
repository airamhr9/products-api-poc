# Java Products API POC

API to query a product catalog.

Contains two endpoints, one to get the products as a list and one to get 
them in pages.

The internal structure follows a Ports&Adapters architecture with DDD. It's like follows:

```
- application (core of the application, independent of implementation details)
    - entity (one of our domain entities)
        - model (package for our domain classes)
            Entity.java (Our aggreggate entity. Should handle its own state)
            Subentity.java (Dependant object. It cannot exist without Entity)
        - ports (the port interfaces that will be implemented by our adapters)
            - in (input ports for interacting with the application, for REST, GraphQL, gRPC or even CLI adapters. Organized by usecases)
                EntityListUseCase.java
                EntityCreateUseCase.java
            - out (output ports for our application to interact with, mainly persistence)
                EntityRepository.java
        - service (Holds the service that will implement the input ports and inject the output ports)
            EntityService.java (Should be package private)
    - common (Common code for all entities)
        - exception 
        - search 
- adapters (contains the adapters that provide input and output of the application)
    - entity
      - persistence (provides an implementation for EntityRepository.java) 
        ...
      - rest (injects the use cases and exposes them via REST API)
        ...
```

With this architecture decisions, we can build our application focusing mainly on 
the application domain and problems, relegating the implementation specifics like
DB interaction or type of API to the adapters. We even have the possibility to offer
several API styles without modifying the core. 
Even with this architecture choice, we still have clear if not better division between the api,
business logic and persistence layers than with a traditional Spring application, in which the
services, repository and controller packages tend to grow disproportionately.

If we check the Product class we can see that discount calculations and discount selection
is done inside of it. That's because Product is an aggregate root, following a DDD approach.
This means that Product is the owner of its own state, and all modifications and selections
are its responsibility, not of other classes like the service.

Another thing to take note is that the use cases contain all the necessary request and response
objects that they can need (except for entities themselves or common objects). That avoids the mistake
of making the core receive implementation specific requests like a gRPC generated class.

### What is not done

- REST request logging
- Category endpoints
- Product CUD