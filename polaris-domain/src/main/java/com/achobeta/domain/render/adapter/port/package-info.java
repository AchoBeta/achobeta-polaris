/**
 * 外部接口适配器层；当需要调用外部接口时，则创建出这一层，并定义接口，之后由基础设施层的 adapter 层具体实现
 * 对于调用外部接口，它的返回值由于是查了数据库的，因此属于实体对象而不是值对象
 * 区分实体对象还是值对象就是看这个对象是否含有业务 ID，如 userId
 */
package com.achobeta.domain.render.adapter.port;