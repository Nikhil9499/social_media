# Social Media Nested Comments

## Requirements

1. Design a comments service for a social media website which can support scalable levels of nesting.
2. The service should return N(>100) first level comments.
3. On clicking on view replies, the next level of comments should be fetched.
4. Make reasonable assumptions with reasonable options for extensibility open.
5. All the comments have associated likes and dislikes.
6. One clicking on the likes or dislikes the list of the users participating in the like/dislike shall be displayed




## Deliverables:

1. Design a basic service which can add comments, likes, dislikes to a social media post, replies to a comment, replies to replies and associated likes or dislikes.
2. There should be a get api to satisfy the requirements. The API shall be scalable for a service where there can be 1000s of comments and each of the comment having 100s of levels.
3. API design, Database design, relationship between entities, class design, pagination concepts
4. If a craft demo is given a day in advance, brownie points for a test suite, and a working API.
5. Basic UI is not mandatory yet would be nice to have in view of UX such as a hardcoded post content.
6. REST API’s endpoints and integration to connect back end system
7. Tests Cases