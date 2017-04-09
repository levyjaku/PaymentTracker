# Payment Tracker

Simple console application for processing payment entries.

User can enter the data (payment entry) in format:
```
CURRENCY_CODE AMOUNT
```

Currency code must be three letters capital (according ISO 4217 + extra RMB).

Examples:
USD 100<br/>
CZK +200.5<br/>
RMB 0.5
 
When user enter invalid entry application print to console message about this error.

Every one minute application print to console actual payment balance - sum of amount of each currency ordered by currency code.

When user write to 'quit' to console, application will exit.

## Prerequisites
<ul>
    <li> Java 8 </li>
    <li> Apache Maven </li>
</ul>


## Running from command line
1. Compile Application
```mvn clean package```

2. Go to  ```target/``` folder

3. Run application using java -jar ```payment-tracker-1.0.jar``` or ```payment-tracker-1.0.jar FILEPATH``` where FILEPATH is path to file containing initial data for application
 

## License
MIT
