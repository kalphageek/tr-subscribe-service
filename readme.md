### Tr Consumer 서비스
> Kafka의 Topic을 Sub해서, TR Mart로 저장 한다.
>
### pk를 이미 가지고 있는 entity저장하기
1. Persistable Implement
2. Entity 수정
  * isNew() method overload
  ```java
  public Boolean isNew() {
    return createDate == null;
  }  
  ```
3. ModelMapper에 TypeMap 추가
  * createDate는 null 매핑
  ```java
  eqp1TrMapper.addMappings(new PropertyMap<Eqp1Tr, Eqp1Tr>() {
     @Override
     protected void configure() {
        map().setCreatedDate(null);
     }
  });
  ```
4. DataSourceBuilder 를 이용해 생성할 때
   * url -> jdbcUrl 