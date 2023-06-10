# account-statment

`This a POC of a RESTful api in the project folder resources/static you will find two files one for SonarQubeReport and the other is for test coverage report file I tried hard to do a filteration on DB level but MSAccess is not friendly for this, finally I extracted the postman collection you will find it under this folder test/postman/ just import it in postman and try to send requests, thank you .`
## 1. Get statement for admin

**URL** : `http://localhost:8080/account/admin/statement/3?fromDate=24.10.2020&toDate=24.01.2021&fromAmount=350.793682741483&toAmount=564.982890505824`

**Method** : `GET`

**first you should LOGING throught postman auth by admin credentials**

### 1.1. Request

```js
{
   
}
```
### 1.2. Response

```json
[
    {
        "accountId": 3,
        "accountType": "current",
        "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
        "date": "2021-01-24",
        "amount": 564.982890505824
    },
    {
        "accountId": 3,
        "accountType": "current",
        "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
        "date": "2020-11-29",
        "amount": 350.793682741483
    }
]
```

## 2. Get statement for admin using filter for amount

**URL** : `http://localhost:8080/account/admin/statement/3?fromAmount=350.793682741483&toAmount=564.982890505824`

**Method** : `GET`

**first you should LOGING throught postman auth by admin credentials**

### 2.1. Request

```js
{
   
}
```
### 2.2. Response

```json
[
  {
    "accountId": 3,
    "accountType": "current",
    "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
    "date": "2020-08-09",
    "amount": 535.197875027054
  },
  {
    "accountId": 3,
    "accountType": "current",
    "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
    "date": "2021-01-24",
    "amount": 564.982890505824
  },
  {
    "accountId": 3,
    "accountType": "current",
    "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
    "date": "2020-11-29",
    "amount": 350.793682741483
  },
  {
    "accountId": 3,
    "accountType": "current",
    "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
    "date": "2016-12-22",
    "amount": 369.407670060882
  },
  {
    "accountId": 3,
    "accountType": "current",
    "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
    "date": "2012-03-23",
    "amount": 557.533882076657
  },
  {
    "accountId": 3,
    "accountType": "current",
    "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
    "date": "2019-08-08",
    "amount": 518.420620441823
  }
]
```

## 3. Get statement for user

**URL** : `http://localhost:8080/account/user/statement/3`

**Method** : `GET`

**This endpoint will get statements from the last 3 months based on the provided data as it is old data I sorted it desc and then get the last 3 month3 from the provided data**

### 3.1. Request

```js
{
  
}
```
### 3.2. Response

```json
[
    {
        "accountId": 3,
        "accountType": "current",
        "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
        "date": "2021-01-24",
        "amount": 564.982890505824
    },
    {
        "accountId": 3,
        "accountType": "current",
        "accountNumber": "b67b64ab19f2c023f460876e84dd917ed8f04798c06e3012092c633e0e9818e7",
        "date": "2020-11-29",
        "amount": 350.793682741483
    }
]
```


## 4. any Unauthorized access 

**URL** : `http://localhost:8080/account/user/statement/3?fromAmount=350.793682741483&toAmount=564.982890505824`

**Method** : `GET`

**in case of any unauthorized access status 401 will be returned**

### 4.1. Request

```js
{
  
}
```

### 4.2. Response

`Unauthorized`

## 5. in case of invalid params

**URL** : `http://localhost:8080/account/user/statement/d?fromDate=24.10.2020&toDate=24.01.2021&fromAmount=350.793682741483&toAmount=564.982890505824`

**Method** : `GET`

**In case of invalid params sent to server then an exception will be thrown and returned to user**

### 5.1. Request

```js
{
   
}
```

### 5.2. Response

```json
{
  "errorCode": "CS_400",
  "errorMessage": "invalid parameter try again with valid one"
}
```

## NOTE
`There are there users to test admin, user and test`

**ADMIN** : `username: admin,  password: admin`

**USER** : `username: user,  password: user`

**TEST** : `username: test,  password: test`

