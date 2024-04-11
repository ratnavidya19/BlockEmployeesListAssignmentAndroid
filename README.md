# Block Employees List

## Build tools & versions used
Android gradle plugins used = 7.4.2  build tools version used = 32.0.0

## Steps to run the app
First clean and build the app, then run the app(I tested the app on emulator) 

## What areas of the app did you focus on?
ViewModel: I focused on designing a robust ViewModel to handle the app logic for fetching and processing employee data. 
This involved implementing methods to fetch data from the repository, handle network errors, and manage loading states.

Network Connectivity: I paid attention to managing network connectivity in the ViewModel to ensure that the app responds
appropriately to changes in network status. This involved using Android's connectivity manager to monitor network 
availability and updating the UI accordingly.

UI Components: I designed the UI components such as RecyclerView, SwipeRefreshLayout, and ProgressBar to provide a smooth
user experience when viewing and refreshing the employee list. I also implemented error handling and empty state views 
to gracefully handle different scenarios.

Unit Testing: I wrote comprehensive unit tests to verify the behavior of the ViewModel under different conditions, 
including successful data fetching, error handling, and handling of empty responses. These tests ensure the stability 
and correctness of the app's core functionality.

Overall, my focus was on building a robust and reliable employee list feature that provides a seamless experience for 
users while handling various edge cases and maintaining code quality through thorough testing.

## What was the reason for your focus? What problems were you trying to solve?
My focus on the employee list project was for reliability, user experience, scalability, data integrity and testing.
Reliability: I aimed to ensure that the app reliably fetches and displays employee data, regardless of network conditions
or other external factors. This meant implementing robust error handling and network connectivity checks to prevent 
crashes or unexpected behavior.
User Experience: My goal was to create a seamless and intuitive user experience for viewing and interacting with the 
employee list. This involved optimizing UI components for performance, providing informative loading and error messages, 
and designing intuitive navigation.
Data Integrity: I prioritized data integrity by implementing checks to validate the correctness and completeness of the 
fetched employee data. This helped to prevent issues such as missing or malformed data from impacting the user experience.
Scalability: I designed the architecture with scalability in mind, ensuring that the app could easily accommodate future 
enhancements or changes without significant refactoring. This included adhering to best practices in software design and 
implementing modular, reusable components.
Testing: A key focus was on testing the app thoroughly to identify and fix bugs early in the development process. 
This involved writing unit tests to cover all aspects of the app's functionality.

## How long did you spend on this project?
For this employee list project, I dedicated around 4 to 5 hours. During this time, I focused on developing the core 
features of the app, including fetching and displaying employee data, implementing network connectivity checks, and 
handling different states such as loading, empty data, and errors. I also spent time writing unit tests to ensure the 
reliability and robustness of the codebase.

## Did you make any trade-offs for this project? What would you have done differently with more time?
Yes, I made some trade-offs to prioritize essential features and stay within the time constraints. One trade-off was 
focusing more on core functionality rather than implementing additional features or optimizations. For example, 
I might have simplified the UI or skipped certain edge cases to ensure timely completion.
With more time, I would have addressed the following areas:
User Interface: I would have spent more time refining the user interface to enhance the overall user experience. This could include adding animations, polishing the layout, and improving the visual presentation of employee data.
Error Handling: While I implemented basic error handling, I would have expanded error handling to cover more scenarios and provide better feedback to users in case of failures.
Unit Test Coverage: Although I wrote unit tests for critical components, I would have increased test coverage to ensure comprehensive testing of all features and edge cases.
Caching Mechanism: With more time, I would have implemented a caching mechanism to store employee data locally, reducing the need for frequent network requests and improving app responsiveness.

## What do you think is the weakest part of your project?
The weakest part of my project might be the lack of extensive testing and handling of edge cases. While I implemented 
basic error handling and unit tests for critical components, there could be scenarios or corner cases that are not 
adequately covered. Additionally, the UI could be further refined to provide a more intuitive user experience.
The project might lack certain optimizations, such as caching mechanisms or network request optimizations,
which could enhance performance and reduce data usage.

## Did you copy any code or dependencies? Please make sure to attribute them here!
Yes, I used several libraries and code snippets to facilitate the development of the project:
Retrofit: Used for making network requests and interacting with the API.
Gson Converter: Integrated with Retrofit for converting JSON responses to Kotlin data classes.
Coroutines: Employed for managing asynchronous operations, such as network requests, in a more concise and readable manner.
LiveData and ViewModel: Utilized from the Android Architecture Components to implement the MVVM architecture and facilitate data observation and lifecycle management.
RecyclerView: Employed to display the list of employees in the app's UI.
Google Search: I also referred to Google and various online resources for some basic code functionality and problem-solving during the development process.

## Is there any other information you’d like us to know?
Yes! I'd like to highlight that while I focused on implementing the core functionality and robustness of the Employee List
project, there may be additional features or optimizations that could enhance its overall performance and user experience.
With more time, I would explore implementing features such as:
Search Functionality: Adding a search bar to allow users to search for specific employees by name or other criteria.
Caching Mechanism: Implementing a caching mechanism to store fetched data locally, reducing the need for frequent network requests and improving app responsiveness.
Error Handling and Logging: Enhancing error handling mechanisms to provide more informative error messages to users and logging detailed information for debugging purposes.
UI/UX Improvements: Refining the user interface design and interaction patterns to ensure a more intuitive and visually appealing experience.