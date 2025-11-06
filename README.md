Для использования необходимо(тестовый пример): 

- _создать объект класса, задав лимит вызова в секунду:_

```
CrptApi crptApi = new CrptApi(TimeUnit.SECONDS, requestLimit);
```

- _указать адресс api(указан базовый для создания документов) и получить тело запроса(базовые параметры заданы):_

```
String url = "https://ismp.crpt.ru/api/v3/api/v3/lk/documents/create?pg=milk";
String requestBody = CrptApi.ApiModel.getInstance().getJsonApiModel();
```

- _вопользоваться методом "createDocument" (пример использования):_

```
Optional<HttpResponse<String>> response = crptApi.createDocument(url, "POST", requestBody);
                if (!response.isEmpty()){
                    System.out.println(response.get().statusCode());
                    System.out.println(response.get().body());
                }
                else {
                    System.out.println("api sender is busy");
                }
```

  
