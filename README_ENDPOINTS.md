#### Security info
Access to particular routes is provided according to JWT claims ("permission", "exp", ...).\
See:
- `se.fastdev.portal.motivator.bonuses.face.extras.auth.jwt.JwtHandle`
- `se.fastdev.portal.motivator.bonuses.face.config.SecurityConfig`

---
#### Admin routes

`POST /admin/persons`\
create new person from attributes\
_body example_
```
{
    "portalId": "it_is_email@by_convention_with_portal.core",
    "firstName": "Zinoviy",
    "lastName": "Yogurtov",
    "location": "Tallin"
}
```

`GET /admin/persons`\
read array of all persons brief descriptions

`GET /admin/persons/{uuid}`\
read detailed information about a person by UUID

`POST /admin/persons/{uuid}/expense-profiles`\
create and start new expense profile for one person (with auto closing previous expense profile)\
_body example_
```
{
    "limit": 30100,
    "startAt": "2020-06-03T01:02:03.11",
    "finishAt": "2021-09-03T01:02:03.11"
}
```

`POST /admin/persons/{uuid}/expenses`\
add expense to current expense profile\
_body example_
```
{
   "type": "HEALTHCARE",
   "amount": 2000.02,
   "description": "third expense",
   "spentAt": "2021-01-05T01:02:03.11"
}
```

`POST /admin/support/refresh-attributes`\
refresh all persons attributes based on the given array 
(request body is the same as response from `GET %portal-root%/api/employees`)\
_body example_
```
[{ 
    "name": "Zinoviy Yogurtov",
    "email": "zyx@fastdev.se",
    "location":"Ekaterinburg"
}]
```
  __TODO: this action is to be automated
        (i.e. make this request internally once a certain period
         to sync employees with the portal)__

`POST /admin/support/refresh-profiles`\
actualize expense profiles for all persons (close expired, create new when needed)\
_body example_
```
{
    "limit": 30100,
    "startAt": "2020-06-03T01:02:03.11",
    "finishAt": "2021-09-03T01:02:03.11"
}
```
  __TODO: this action is to be automated
        (i.e. make this request internally once a certain period
         to create profiles for new employees and actualize profiles for existing employees)__

---
#### Anyuser routes
`GET /anyuser/i`\
  read current authorization info from JWT (this could help figuring out reasons of possible issues with auth)

`GET /anyuser/persons`\
  read full description of the person owned by current authorized user
  (represented as array with single item for consistency with other `GET .../persons` endpoints)
