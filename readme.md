RODAR O BANCO EM CONTAINER.

sudo docker run --name some-postgres -e POSTGRES_PASSWORD=mypassword -e POSTGRES_USER=myuser -e POSTGRES_DB=blog_db -d -p 5432:5432 postgres

JWT TUTORIAL

https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac

https://medium.com/@srivastp/securing-jwt-authentication-in-spring-boot-with-rsa-keys-666b5c467378


GERAR CHAVES RSA PUBLICA E PRIVADA

https://www.c-sharpcorner.com/article/generate-rsa-public-and-private-keys-using-openssl/