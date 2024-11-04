/**
 * 聚合对象；
 * 1. 聚合实体和值对象
 * 2. 聚合是聚合的对象，和提供基础处理对象的方法。但不建议在聚合中引入仓储和接口来做过大的逻辑。而这些复杂的操作应该放到service中处理
 * 3. 对象名称 XxxAggregate
 * 4. 聚合对象是做事务的基础，当业务需求要做事务时，请创建聚合对象，并将需要做事务的所有入参都封装到 Aggregate 聚合对象里
 */
package com.achobeta.domain.render.model.aggregate;