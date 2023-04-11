-- 1.参数列表
-- 1.1.装扮物id
local adornmentId = ARGV[1]
-- 1.2.用户id
local userId = ARGV[2]

-- 2.数据key
-- 2.1.库存key
local stockKey = 'seckill:stock:' .. adornmentId
-- 2.2.订单key
local orderKey = 'seckill:order:' .. adornmentId

-- 3.脚本业务
-- 3.4.解锁库存 incrby stockKey 1
redis.call('incrby', stockKey, 1)
-- 3.5.（删除用户）srem orderKey userId
redis.call('SREM', orderKey, userId)
return 0