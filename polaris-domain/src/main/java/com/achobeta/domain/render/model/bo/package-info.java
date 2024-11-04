/**
 * 数据流转包，整合或拆分 Entity
 * 与聚合不同，虽然 bo 也整合了 Entity，但它的职责是数据传输与流转
 * 而聚合请专职做事务，其他时候请不要用聚合来做业务，否则聚合随着时间的推移会越来越大
 */
package com.achobeta.domain.render.model.bo;