Для использования необходимо(тестовый пример): 

- _создать объект класса, задав лимит вызова в секунду:_

```
CrptApi crptApi = new CrptApi(TimeUnit.SECONDS, requestLimit);
```

- _указать адресс api(базовый пример для создания документов) и получить тело запроса(указаны базовые параметры):_

```
String url = "https://ismp.crpt.ru/api/v3/api/v3/lk/documents/create?pg=milk";
String requestBody = CrptApi.ApiModel.getInstance().getJsonApiModel();
```

- _вопользоваться методом "sendApiCall":_

```
Optional<HttpResponse<String>> response = crptApi.sendApiCall(url, "POST", requestBody);
                if (!response.isEmpty()){
                    System.out.println(response.get().statusCode());
                    System.out.println(response.get().body());
                }
                else {
                    System.out.println("api sender is busy");
                }
```

  
