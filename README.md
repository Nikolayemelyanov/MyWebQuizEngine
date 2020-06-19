
# MyWebQuizEngine
 Backend SpringBoot MicroService with Authenication 
***
## About
In the Internet, you can often find sites where you need to answer some questions. It can be educational sites, sites with psychological tests, job search services, or just entertaining sites like web quests. The common thing for them is the ability to answer questions (or quizzes) and then see some results. This project is a multi-users web service for creating and solving quizzes.

## Registration and Authentication

First of all, client should register its personal account to use MyWebQuizEngine features.
Client needs to send a JSON with email and password via `POST` request to `/api/register`:

```yaml
{
  "email": "test@gmail.com",
  "password": "secret"
}
```
The microservice uses HTTP Basic Auth for each request, i.e. create, solve, get one, get all, delete quiz.
The only operation that does not require authorization is the registration.
A user can delete their quiz by sending the `DELETE` request to `/api/quizzes/{id}`. If the specified user is not the author of this quiz, the response is the `403 (Forbidden)` status code.

## Creating a Quiz

To create a new quiz, the client needs to send a JSON as the request's body via POST to `/api/quizzes`. The JSON should contain the four fields:

`title`: a string, required;  
`text`: a string, required;  
`options`: an array of strings, required, should contain at least 2 items;  
`answer`: an array of indexes of correct options, optional, since all options can be wrong.

Here is an example:
```yaml
{
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"],
  "answer": [0,2]
}
```
The server response of the example is here:
```yaml
{
  "id": 1,
  "title": "Coffee drinks",
  "text": "Select only coffee drinks.",
  "options": ["Americano","Tea","Cappuccino","Sprite"]
}
```
The `id` field is a generated unique integer identifier for the quiz. Also, the response doesn't include the answer field.

If the request JSON does not contain title or text, or they are empty strings (`""`), then the server should respond with the  `400 (Bad request)` status code. If the number of options in the quiz is less than 2, the server returns the same status code.

## Get a Quiz by Id

To get a quiz by `id`, the client sends the `GET` request to `/api/quizzes/{id}`.

Here is a response example:
```yaml
{
  "id": 1,
  "title": "The Java Logo",
  "text": "What is depicted on the Java logo?",
  "options": ["Robot","Tea leaf","Cup of coffee","Bug"]
}
```
## Get all Quizzes

To get all existing quizzes in the service, the client sends the `GET` request to `/api/quizzes`.
The response contains a JSON with quizzes (inside `content`) and some additional metadata:
```yaml
{"content":[
{"id":1,"title":"Coffee drinks","text":"Select only coffee drinks.","options":["Americano","Tea","Cappuccino","Sprite"]},
{"id":2,"title":"Coffee drinks","text":"Select only coffee drinks.","options":["Americano","Tea","Cappuccino","Sprite"]},
{"id":3,"title":"Coffee drinks","text":"Select only coffee drinks.","options":["Americano","Tea","Cappuccino","Sprite"]},
{"id":4,"title":"Coffee drinks","text":"Select only coffee drinks.","options":["Americano","Tea","Cappuccino","Sprite"]}],
"pageable":{"sort":{"sorted":true,"unsorted":false,"empty":false},
"pageNumber":0,"pageSize":10,"offset":0,"paged":true,"unpaged":false},
"totalPages":1,
"totalElements":4,
"last":true,
"numberOfElements":4,
"first":true,
"number":0,
"size":10,
"sort":{"sorted":true,"unsorted":false,"empty":false},"empty":false}
```
API supports the navigation through pages by passing the page parameter ( `/api/quizzes?page=1`). If there are no quizzes, content is empty `[]`.

## Solving a Quiz

To solve a quiz, the client sends the `POST` request to `/api/quizzes/{id}/solve` with a JSON that contains the indexes of all chosen options as the answer.
Here is an example:
```yaml
{"answer": [0,2]} 
```
It is also possible to send an empty array `[]` since some quizzes may not have correct options.

The service returns a JSON with two fields: `success` (`true` or `false`) and `feedback` (just a string). There are three possible responses.

If the passed answer is correct:
```yaml
{"success":true,"feedback":"Congratulations, you're right!"}
```
If the answer is incorrect:
```yaml
{"success":false,"feedback":"Wrong answer! Please, try again."}
```
If the specified quiz does not exist, the server returns the 404 (Not found) status code.


## Get all completions of Quizzes with paging 

Microservice provides an operation for getting all completions of quizzes for a specified user by sending the `GET` request to `/api/quizzes/completed` together with the user auth data. All the completions are sorted from the most recent to the oldest.

A response is separated by pages since the service may return a lot of data. 
It contains a JSON with quizzes (inside content) and some additional metadata as in the previous operation.

Here is a response example:
```yaml
{"content":[
{"id":1,"completedAt":"2020-06-01T19:38:52.795504"},
{"id":1,"completedAt":"2020-06-01T19:38:51.145409600"}],
"pageable":{"sort":{"sorted":true,"unsorted":false,"empty":false},
"pageNumber":0,
"pageSize":10,
"offset":0,
"paged":true,
"unpaged":false},
"totalPages":1,
"totalElements":2,
"last":true,"numberOfElements":2,
"first":true,"number":0,"size":10,
"sort":{"sorted":true,"unsorted":false,"empty":false},"empty":false}
If there are no quizzes, content is empty [].
```
All data is stored in embedded H2 database. 
## Acknowledgment
The project was developed with the support from [JetBrainsAcademy](https://hyperskill.org).
