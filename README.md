# VaccinateMe
Determine which COVID vaccine Phase you are eligible for in Ontario.

## Inspiration
There are spikes in the COVID curve all the time because of human activity. While changing your habits can help, it relies on controlling a large population.  Vaccines do not require control and are the most effective tool against the COVID curve if enough people are Vaccinated. 

## Questions it answers
_Do you know when you can get Vaccinated?_

_When can your elderly grandparents, or sibling with autoimmune conditions get Vaccinated?_

_If you are a stressed worker, will our program be difficult to use and understand?_

_How can we best allocate and distribute vaccines?_

## What it does
_See project images for examples_
- **Simple to Use:** Takes user data in simple yes/no questions.
- **Purpose:** Analyzes for each of the three Phases in Ontario.
- **Informative:** Returns the Phase the user is eligible for with the reasons why they are eligible.
- **Resourceful:** OPTIONAL: formats the data into a .txt file that will be sent to Public Health.
         - .txt file is simple and easily readable by Public Health computers through any code.
- **Warning:** Returns COVID risk factors the user faces.
- **Customizable** Special messages for pregnant women and healthcare workers, more can be added.
- **Educational** Important vaccine message for all users about before vaccine and after the vaccine.


## Features
- Determine which phase you are eligible for
- Determine your COVID physical risk level
- Special notes for Healthcare workers and for Pregnant women
- Save your data as a .txt file that can be exported to Public Health
- Simple GUI for stressed-out users, simple input (YES) or (NO) buttons
- Pictures to guide understanding

## How we built it
Built in the eclipse JDE. The panels that pop up for a better GUI are done through java Swing. File writing is done through java io.  I am new to programming but I am very happy that I was able to move my projects **out of the console, and into proper and user-friendly mediums**.

## Challenges we ran into
This took a lot of time to do it right. Ended up sleeping at 3 AM but it was very worth it because the program runs very smoothly and we achieved all the features we wanted to implement. 

## Accomplishments that we're proud of
Full completion of the project, but more importantly the code is readable and fully modular. I am happy that if someone stumbled upon my GitHub, they could modify some conditionals and apply the VaccinateMe app for their local or provincial rules. Currently, the conditions are for COVID vaccine Phases in **Ontario**.

## What we learned
Creating a user interface with java swing, adding images through ImageIcons. Modular programming helped break down this complicated task into many smaller parts. Made myself a very useful helper method to get input that also simplified things.

## What's next for VaccinateMe
Potential mobile port and website port. This would allow it to be deployed to the millions of Ontarians who are unsure of when they can get vaccinated. While this is useful, even more important would be creating a **secure pipeline to Public Health**. The data is incredibly valuable to help the different Public Health regions allocate vaccines and identify the areas where there are eligible people. While this information might be available through census data, it is difficult and much is missing. VaccinateMe **only forwards the most relevant information to Public Health**. The Federal Government could also consider implementing this into their COVID-19 Alert App which would be even better.



