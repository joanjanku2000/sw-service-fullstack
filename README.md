# Services REPO - Back End + Front-End

## Tech Stack
 ### Back End 
    Java 21
    Spring Boot 3
    MongoDB 
    Redis (for prod/dev)
Exposes CRUD operations on AppService Entity.

Features:
 - Profiling (local and dev)
 - Caching (spring boot's default cache for local profile
and redis for dev profile)
 - Thread Safe - achieved through optimistic locking mechanism in update operation, also tested for this behaviour in integration tests
 ### Front End
    Angular 18

The service-app folder contains the back-end repo, 
while the service-app-fe contains angular project.

## Project Deployment

The project is containerized using Docker. Back-end, front-end, mongo
and redis are all run through docker.
After cloning the repository and making sure docker is installed
in the machine the project can be ran using the command
``docker compose up --build``
The default profile when the back-end is run through docker is dev.

Inside the repo can be found also a "script.sh" which can be
used as a standalone script (without cloning the repo yourself)
and is used in Hostinger VPS to trigger the deployment. The script does everything itself as follows:

- Checks for the repository folder in the folder it is run, if found deletes it - to ensure code is uddated
- clones the repository and cd inside it
- checks for docker and docker compose , and installs them if they are not present
- runs docker compose up --build which setsup mongo, redis, back-end and front-end from the provided dockerfiles in each

In a linux enviromment the script can be made executable using  `chmod +x setup.sh`.
(in a real world VM the chmod should specify the user for which the script is executable)

Github Actions is used to trigger a workflow, which is defined inside .github/workflows/deploy/yml.
The workflow is triggered on each push and using the HOST, USER and SSH_KEY inside
the configured VM (they are stored as secrets in github secrets section), it executes
the script.sh which is found in the host machine. 

Through this step, with each commit we get a mini simulated simple version of CI/CD.


## Logging
Back End Application logs are written in a file inside the repo in the filesystem (but not pushed in git)
They can be accessed by running the following <br>

<pre>
docker exec -it sw-service-app sh
tail -f logs/app-service.log
</pre>
Note: The application should've been started using `docker compose up --build`

## Local Project Setup
After cloning the repository the local_setup.sh script can be ran
like this  `./local_setup.sh`
Just make sure it's executable. To stop running containers:
`docker stop $(docker ps -aq`

## Production Deployment
Project is deployed and can be accessed through the URL:
``http://62.72.33.67:4200``.
Each push in master, activates the Github workflow, which
triggers the setup.sh script in the virtual machine to get the new code
, run the tests , re-build and re-start the containers.

Thank you :)
