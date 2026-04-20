RODAR O BANCO EM CONTAINER.

sudo docker run --name some-postgres -e POSTGRES_PASSWORD=mypassword -e POSTGRES_USER=myuser -e POSTGRES_DB=blog_db -d -p 5432:5432 postgres

REPOSITORIO DE ONDE PEGUEI O CODIGO:

https://github.com/tericcabrel/blog-tutorials/tree/main

CRIAR CHAVE 256 -> 32 usando BASE64URL ENCODE (diferente do base64 normal):

https://encode64.com/en/security-token-tools/hmac-key-generator

TESTAR SE O TOKEN E A SECRET ESTÁ OK:

https://www.jwt.io/


CONFIGURAR AS ROLES 

https://medium.com/@victoronu/implementing-role-and-permission-based-authorization-in-spring-boot-with-jwt-359901206b6a