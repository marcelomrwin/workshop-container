### Palestra de desenvolvimento ágil com containers

1. Logar no OCP
1. Criar projeto Banco de Dados usando web console com storage.
  - Inserir parâmetros de nome do banco, usuário e senha como __people__.
1. Criar nova aplicação a partir da linha de comando.
```
oc new-app quay.io/quarkus/ubi-quarkus-native-s2i:19.2.1~https://github.com/marcelomrwin/workshop-container.git --context-dir=workshop/workshop-quarkus --name=workshop-quarkus-native
```
1. Criar uma rota para a aplicação pela web console
1. Acessar a aplicação via swagger
[Link](http://workshop-quarkus-native-ocp-workshop.apps.belem-a207.open.redhat.com/swagger-ui)
  - __*(Verifique o link para garantir que corresponde a URL configurada na rota do passo acima)*__
1. Realize alguns testes no swagger
1. Fazer deploy da app vue.js people-view através da web console selecionando a imagem _marcelodsales/people-view:belem_
1. Cria uma rota http e https _sem precisar redirecionar https_
1. Acessa a app via https
1. Cria regra de redirecionamento para https
1. Cotas e limites
