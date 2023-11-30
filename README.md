# Pickles
A high performance cucumber library that adds generic data generation and monitoring services. 
Cucumber Steps are available to configure test data generators in a variety of ways.
Data can be injected into files, databases and message systems
Steps are also available to check received data in files, databases and message systems
Performance is measured in tests.

Philosophy
This library provides generators for low level attributes like integers, floating point values, strings, UUIDs and also JSON data. Features of the generators work in the same way accross attributes. For example the way to configure random values, sequences and looping is similar for each attribute. Generators can be combined into a string or JSON value that has a mix of generated values. The comnbined result is also a generator and can be combined further into other generators. This allows sophisticated data generation of objects like People, Products and any other data that can be represented as a string or JSON.

Performance is a key benefit of the library
The tests show code generating attributes single threaded at a rate of 70 million values per second on a Mac M1 Pro.
