# java_recipient_exceptions
 Keep-It-Simple-And-Stupid java micro-library to promote error-handling best practices

Note: This library  supersede the first version @ https://github.com/earizon/recipientexceptions , simplifying the (already simple) code and API,
while providing an extra extension point through the linkedInfo property.

# Summary
 This library is based on the next simple algorithm:
"""
   If something can be fixed by the running code, then it is NOT really an exception but a contemplated flow of code even if it is fixed in the "catch" block. 

   If it can't be fixed and the running code can NOT continue normal -non error- flow of code then the application aborts any process or transaction in course and forward the exception to an *EXTERNAL INTELIGENCE* (human being, deployment system,...) to fix it.
"""

3 categories of EXTENAL INTELIGENCE are defined: 
* User: Human or client system that uses the application. This "matches" the 4xx Client Error in HTTP, but is not restricted to the HTTP protocol and is not restricted to client/server network request/response but to any sort of (local/remote) user operating the application.

* Administrator: Human or computer system (resource orchestator like kubernetes, ...) in charge of the running environement (server, network, disk and other physical/virtual resources needed by the application). This matches the 5xx Server Error, but is more general to include any error that must be fixed by an Administrator (the external inteligence). An application using recipient exceptions, in case of external error will limit itself to abort and forward the error. Again is not restricted to client/server network requests/response schema.

* Developer: Human that wrote the application code. The is no equivalent in HTTP. They will most probably be triggered by assert conditions.

# Workflow
There is not any mandated work-flow to process the exceptions but most probably next flow or similar will be used by most applications:
- If a developer exception is triggered:
  - developers will receive a detailed report to allow them to fix the error as soon as possible.
  - administrators will receive as much as information as possible to let them take a sensible decision.
  - users will receive a generic error indicating an internal error.

- If an administration exception is triggered:
  - developers will ignore the error.
  - administrators will receive as much as information as possible to let them take a sensible decision. Administrators can decide that the error could have been mitigated by (a better) code and forward it to developers.
  - users will receive a generic error indicating an internal (most probably temporal) error requesting them to try later or use an
    alternative path.

- If a user exception is triggered:
  - developers will ignore the error.
  - administrators will ignore the error.
  - users will receive a detailed error indicating them how to fix the error.

(The workflow can be detailed as to include logging or automatic recovery)

# Ussage
Since error handling can be critical to any application in production environment this library has been designed to be as simple as possible. In fact the core is comprised of a single class with different constructors and an optional setter used as a hook for libraries who want to extend the code. Check TestRecipientException for further info.
 
# Extending the library
The core library doesn't mandate any way to encode the exceptions to send them through the wire. plain text, binary, json, ... can be used. The one-class library just try to create a common pattern processing for exception handling.

The core class RecipientException has an extra (Object) linkedInfo member to allow linking any type of extra java information to the exception. This could be used to add errcodes, timestamps,... From the point of view of the library this information is optional since it doesn't provide any information about the recipient (external inteligence) that must fix the problem, just information that such recipient could use.
