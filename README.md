# Url shorten
by Ivan Di Paola

The project take as input a long URL that the user want to shorten and a desired short URL.
If you are running the application in your local enviroment you can reach it at: http://localhost:8080/ (if you plan to change it, modify the .properties file)

The URL to shorten must be active and it must work over https (just to be secure... :D). 
You can pass the link with the protocol: https://www.facebook.com or without: www.facebook.com in this case https protocol will be automatically added to the beginning of the link.

If the desired short url it's already present, an error will be prompted to the user.
If the long url to shorten is already present, the already existing short url will be returned to the user.

Developed with Spring Boot & Thymeleaf