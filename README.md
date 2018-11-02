# gateway-zuul

Edge proxy to provide basic security, external to internal routing, circuit breaker

### Create new routes

1. Add a new entry to zuul.routes

`zuul:
  host:
    max-total-connections: 2000
    max-per-route-connections: 2000
    # set some aggressive timeouts to see if it improves connectivity
    connect-timeout-millis: 2000
    socket-timeout-millis: 9000
  routes:
    kalah-service:
      path: /kalah/v1/**
      url: ${internalkalahurl}
    new-route:
      path: /new/v1/**
      url: ${internalkalahurl}
  SendErrorFilter:
    error:
      disable: true`

2. Add entry to hystrix 

`hystrix:
   command:
     default:
       execution:
         isolation:
           strategy: SEMAPHORE
           semaphore:
             maxConcurrentRequests: 300
     kalah-service:
       execution:
         isolation:
           thread:
             timeoutInMilliseconds: 4000
     new-route:
       execution:
         isolation:
           thread:
             timeoutInMilliseconds: 4000`
             
3. Add entry for ribbon connection timeout values
 
`kalah-service:
   ribbon:
     ConnectTimeout: 2000
     ReadTimeout: 3000l
 new-route:
    ribbon:
      ConnectTimeout: 2000
      ReadTimeout: 3000`
     
### Run locally

mvn spring-boot:run

The service binds to port 8251 by default