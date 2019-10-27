# TCP Pinging application built in Java

## Usage
1. Clone the project
2. Compile the code using ```gradlew build```

## Parmeters
```python
-p #Run the app in Pitcher mode

-c #Run the app in Catcher mode

-port <port> # [Pitcher] TCP socket port for connection
             # [Catcher] TCP socket port for listening
             
-bind <ip_address> [Pitcher] TCP server socket address

-mps <rate> [Pitcher] # messages per second

-size <size> [Pitcher]  # message size in bytes

<hostname> [Pitcher]  # name of the machine the server is running on
```
