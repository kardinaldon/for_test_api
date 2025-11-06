Для использования необходимо(тестовый пример): 

- _создать объект класса, задав лимит вызова в секунду:_

```
CrptApi crptApi = new CrptApi(TimeUnit.SECONDS, requestLimit);
```

- _указать адресс api:_

```
String url = "https://markirovka.demo.crpt.tech/api/v3/lk/documents/create?pg=milk";
```

- _вести необходимые параметры тела запроса в модель(пример модели):_

```
public class TestApiModel{
            public static String buildApiTestModel() throws JsonProcessingException {
                ObjectMapper mapper = new ObjectMapper();

                ObjectNode apiModel = mapper.createObjectNode();
                apiModel.put("document_format", "MANUAL");
                apiModel.put("product_document",
                        Base64.getEncoder().encodeToString(("product_document").getBytes()));
                apiModel.put("product_group", "1 clothes");
                apiModel.put("type", "LP_INTRODUCE_GOODS");

                return mapper.writeValueAsString(apiModel);
            }
        }
```

- _вопользоваться его методом:_

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

  
