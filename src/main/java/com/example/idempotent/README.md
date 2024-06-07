idempotent:幂等性

测试地址：
curl -X POST "http://119.119.118.119:8080/api/transfers?requestId=unique-req-12345&fromAccountId=1&toAccountId=2&amount=100.00"