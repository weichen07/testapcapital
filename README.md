先使用createTable.sql base in mysql8

api補充庫存
127.0.0.1:8650/products/inventory/0000-0-2
post content-type:application/x-www-form-urlencoded
Body

merchantId:2
quantity:1

api用戶充值
127.0.0.1:8650/users/recharge/1
post content-type:application/x-www-form-urlencoded
Body

amount:1000

api玩家購買商品
127.0.0.1:8650/orders/purchase
post content-type:application/x-www-form-urlencoded
Body

userId:2
productSku:0000-0-2
quantity:5