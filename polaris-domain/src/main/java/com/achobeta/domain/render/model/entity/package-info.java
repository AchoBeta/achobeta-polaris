/**
 * 实体对象；
 * 1. 一般和数据库持久化对象1v1的关系，但因各自开发系统的不同，也有1vn的可能。
 * 2. 如果是老系统改造，那么旧的库表冗余了太多的字段，可能会有nv1的情况
 * 3. 对象名称 XxxEntity
 * 4. 与 ORM 映射实体相比不能带上主键 ID 与实现本领域功能无关的字段
 */
package com.achobeta.domain.render.model.entity;