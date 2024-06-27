# IBM PRODUCER
## Iniciando IBM MQ
Para iniciar este projeto corretamente, execute o container da mensageria IBM MQ, com os comandos abaixo.

### 1. Baixe a imagem do IBM MQ:
```shell script
docker pull ibmcom/mq:latest
```
1.2 Rode o container docker para iniciar o IBM MQ:
```shell script
docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --volume qm1data:/mnt/mqm --publish 1414:1414 --publish 9443:9443 --detach --env MQ_APP_PASSWORD=passw0rd ibmcom/mq:latest
```
> **_NOTA:_** Estas variáveis são inseridas por padrão pela documentação IBM MQ, seus valores podem ser alterados, se desejar.

### 2. Verifique se o gerenciador do IBM MQ iniciou corretamente.

2.1 Abra seu navegador e acesse: https://localhost:9443/ibmmq/console/login.html

2.2 Ira aparecer um alerta, vá em avançado e depois, aceitar o risco e continuar.

2.3 utilize as credenciais default para acessar o gerenciador IBM MQ:
```
Login: admin
Senha: passw0rd
```
Site Documentação IBM MQ: https://www.ibm.com/docs/pt-br/ibm-mq

### 3. Utilizando esta aplicação.
3.1 Execute o comando abaixo, e aguarde sua inicialização:
```shell script
./mvnw compile quarkus:dev
```
3.2 Utilize o API Client de sua preferência e acesse os endpoints:

Endpoint JSON: https://localhost:8080/send/user
```
{
"name":"Juarez",
"age":"30",
"country":"Brazil"
}
```
Endpont STRING: https://localhost:8080/send/string
```
"Escreva sua frase"
```

3.3 Acesse o gerenciador e verifique a mensagem publicada na fila, que neste caso e: DEV.QUEUE.1
