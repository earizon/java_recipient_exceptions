# java_recipient_exceptions
 Keep-It-Simple-And-Stupid java micro-library to promote error-handling best practices

Note: This library  supersede the first version @ https://github.com/earizon/recipientexceptions , simplifying the (already simple) code and API,
while providing an extra extension point through the linkedInfo property.

# Summary
 This library is based on the next simple algorithm:
"""
   If something can be fixed by the running code, then it is NOT really an exception but a contemplated flow of code even if it is fixed in the "catch" block. 

   If it can't be fixed and the running code can NOT continue normal -non error- flow of code then the application aborts any process or transaction in course and forward the exception to an *EXTERNAL INTELIGENCE* (human being, deployment system,...) to fix it."

3 categories of EXTENAL INTELIGENCE are defined: 
* User: Human or client system that uses the application. This "matches" the 4xx Client Error in HTTP, but is not restricted to the HTTP protocol and is not restricted to client/server network requests but to any sort of (local/remote) user operating the application.

* Administrator: Human or running system in charge of the running environement (server, network, disk and other physical/virtual resources needed by the application. This matches the 5xx Server Error, but is more general to include any error that must be fixed by an Administrator (the external inteligence)

* Developer: Human that wrote the application code. The is no equivalent in HTTP. They will most probably be triggered by assert conditions.

There is not any mandated work-flow to process the exceptions but most probably next flow or similar will be used by most applications:
- If a developer exception is triggered:
  - developers will receive a detailed report to allow them to fix the error as soon as possible.
  - administrators will receive as much as information as possible to let them take a sensible decision.
  - users will receive a generic error indicating an internal error.

- If an administration exception is triggered:
  - administrators will receive as much as information as possible to let them take a sensible decision.
  - users will receive a generic error indicating an internal (most probably temporal) error requesting them to try later or use an
    alternative path.

- If a user exception is triggered:
  - users will receive a detailed error indicating them how to fix the error.

# Ussage
Since error handling can be critical to any application in production environment this library has been designed to be as simple as possible. In fact the core is comprised of a single class with different constructors and an optional setter used as a hook for libraries who want to extend the code. Check TestRecipientException for further info.
 
# Extending the library
The core library doesn't mandate any way to encode the exceptions to send them through the wire. plain text, binary, json, ... can be used. The one-class library just try to create a common pattern processing for exception handling.
