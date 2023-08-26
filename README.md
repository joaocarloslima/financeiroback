# Gestanca

API da aplicação de controle de gastos pessoais.

## Endpoints

- Despesas
    - [Cadastrar despesa](#cadastrar-despesa)
    - Apagar despesa
    - Alterar despesa
    - Listar todas as despesas
    - [Detalhar uma despesa](#detalhar-despesa)
- Receitas
- Transferências 
- Contas
- Categorias

---

### Cadastrar Despesa

`POST` /gestanca/api/despesa

| campo | tipo | obrigatório | descrição
|-------|------|:-------------:|----
| valor | decimal | sim | o valor da despesa, deve ser maior do que zero
| data | data | sim | a data em que a despesa ocorreu
| categoria_id | inteiro | sim | o id da catrgoria da despesa previamente cadastrada
| conta_id | inteiro | sim | o id da conta previamente cadastrada
| descricao | texto | não | um texto com detalhes sobre a despesa
  
  **Exemplo de corpo de requisição**

```js 
{
    "valor": 100.00,
    "data": "2023-01-27",   
    "categoria_id": 1,
    "conta_id": 1,
    "descricao": "cinema com os amigos"
}
```

**Respostas**

| código | descrição
|-|-
|201| despesa cadastrada com sucesso
|400| a validação dos campos falhou

---

### Detalhar despesa

`GET` /gestanca/api/despesa/{id}

**Respostas**

| código | descrição
|-|-
|200| os dados da despesa foram retornados no corpo da resposta
|404| não existe despesa com o id informado

**Exemplo de corpo da resposta**
```js 
{
    valor: 100.00,
    data: '2023-01-27',
    categoria: {
        categoria_id: 1,
        nome: 'lazer',
    }
    conta: {
        conta_id: 1,
        nome: 'itaú',
    },
    descricao: 'cinema com os amigos'
}
```