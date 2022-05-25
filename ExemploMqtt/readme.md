# Mqtt sample app 

This app is a sample of an use case for Mqtt. 

### Use case
An industrial environment is monitored by a set of temperature sensors that sends the temperature every 60 seconds to a MQTT broker. Two services are linked to the broker: the **Compute Average Temperature (CAT)** service, which calculates the average of monitored temperatures, and the **Alarms** service, which triggers messages of various types of alarms (fire, high temperature, water flow) for human system operators.

### Running 
1. clone the repo 
2. run `yarn` to install dependencies
3. navigate to project folder using your terminal 
4. run `yarn sensor` to start the temperature sensor simulator
5. run `yarn cat` to start the Compute Average Temperature (CAT) service 
6. run `yarn alarms`  to see the log of the alarms been fires