RODAR O BANCO EM CONTAINER.

sudo docker run --name some-postgres -e POSTGRES_PASSWORD=mypassword -e POSTGRES_USER=myuser -e POSTGRES_DB=blog_db -d -p 5432:5432 postgres

REPOSITORIO DE ONDE PEGUEI O CODIGO

https://github.com/tericcabrel/blog-tutorials/tree/main