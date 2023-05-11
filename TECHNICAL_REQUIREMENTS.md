![](logo.png)

**<div style="text-align: right">AnyMind Group Limited (Thailand)</div>**
*<div style="text-align: right">689 BhirajTower at Emquartier, 34th Floor, Sukhumvit Road,</div>*
*<div style="text-align: right">Khlongtan Nuea,</div>*
*<div style="text-align: right">Wattana, Bangkok 10110</div>*


## Project forBack-end Engineer(Kotlin) at AnyMind Group

**Deadline:**

- 7 days

**Project details:**

Let's imagine a lot of people send Bitcoins to your wallet every second from different countries and different time zones. The amount of BTC and the time is represented by an abstract record that has the following structure:

```
    json
    {
        "datetime": "2019-10-05T14:48:01+01:00",
        "amount": 1.1
    }
```

You don't want to withdraw it and you don’t have any plan to use it in the near future because you really believe that BTC will keep growing. Recently you’ve decided to keep track of and show a history of your wallet’s wealth to everyone.

At this time you’ve already collected `1000 BTC`. You want to show the history of your wallet balance at the end of each `hour` between the DateTime range.

(**Warning:not a history of transaction amount at that time range**)

The data you want to provide contains an array of data with the following structure:

```
    json
    [
        {
            "datetime": "2019-10-05T13:00:00+00:00",
            "amount": 1000
        },
        {
            "datetime": "2019-10-05T14:00:00+00:00",
            "amount": 1001.1
        },
        {
            "datetime": "2019-10-05T15:00:00+00:00",
            "amount": 1001.1
        },
        {
            "datetime": "2019-10-05T16:00:00+00:00",
            "amount": 1001.1
        }
    ]
```

You decided to create a web API(`Graphql`, `REST` or `GRPC`) and use any frameworks/technologies/libraries that might help you.

Your server should have 2 features:

1) Save Record

Input example:
```
    json
    {
        "datetime": "2019-10-05T14:45:05+07:00",
        "amount": 1.1
    }
```

Output:

```
    {
        // Any output what can be recognized as successful 
    }
```

2) Get history of your wallet balance at the end of each `hour` between two dates

Input example:

```
    json
    {
        "startDatetime": "2019-10-05T12:48:01+00:00",
        "endDatetime": "2019-10-05T17:48:02+00:00"
    }
```

Output example:

```
    json
    [
        {
            "datetime": "2019-10-05T12:00:00+00:00",
            "amount": 1000
        },
        {
            "datetime": "2019-10-05T13:00:00+00:00",
            "amount": 1000
        },
        {
            "datetime": "2019-10-05T14:00:00+00:00",
            "amount": 1001.1
        },
        {
            "datetime": "2019-10-05T15:00:00+00:00",
            "amount": 1001.1
        },
        {
            "datetime": "2019-10-05T16:00:00+00:00",
            "amount": 1001.1
        },
        {
            "datetime": "2019-10-05T17:00:00+00:00",
            "amount": 1001.1
        }
    ]
```

You’re on the road to become super rich and famous!More and more people want to send you BTC and check your balance history. It means that your server should be ready to accept many requests and to respond in an acceptable time. You also want to show your friends that you are an awesome BE developer. It means that:

- You have to use Git
- Your code has to be clear
- You know good coding practices and patterns
- Your APIcan handle incorrect data
- Your architecture is extendable/testable
- You can consider multi-thread,many servers

it’s optional to implement,but we’ll ask in an interview.

- L4: Apply CQRS / Reporting Database pattern to read and write operations
- L4: Can apply DDDand sub-modules
- L4: Can publish changes in DBto message queue reliably
- L4: Can explain how to deploy a new version without loss of request/data.

**Create a repository and publish an instruction foryourfellow BEdev friends on how to launch your server.**

**Good luck!**
