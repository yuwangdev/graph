# graph
2015_07_22_mktplace_shop_web_log_sample.log MapPartitionsRDD[151] at textFile at <console>:88
2015-07-22T09:00:28.019143Z marketpalce-shop 123.242.248.130:54635 10.0.6.158:80 0.000022 0.026109 0.00002 200 200 0 699 "GET https://paytm.com:443/shop/authresponse?code=f2405b05-e2ee-4b0d-8f6a-9fed0fcfe2e0&state=null HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2
2015-07-22T09:00:27.894580Z marketpalce-shop 203.91.211.44:51402 10.0.4.150:80 0.000024 0.15334 0.000026 200 200 0 1497 "GET https://paytm.com:443/shop/wallet/txnhistory?page_size=10&page_number=0&channel=web&version=2 HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1; rv:39.0) Gecko/20100101 Firefox/39.0" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2
2015-07-22T09:00:27.885745Z marketpalce-shop 1.39.32.179:56419 10.0.4.244:80 0.000024 0.164958 0.000017 200 200 0 157 "GET https://paytm.com:443/shop/wallet/txnhistory?page_size=10&page_number=0&channel=web&version=2 HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2
2015-07-22T09:00:28.048369Z marketpalce-shop 180.179.213.94:48725 10.0.6.108:80 0.00002 0.002333 0.000021 200 200 0 35734 "GET https://paytm.com:443/shop/p/micromax-yu-yureka-moonstone-grey-MOBMICROMAX-YU-DUMM141CD60AF7C_34315 HTTP/1.0" "-" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2
2015-07-22T09:00:28.036251Z marketpalce-shop 120.59.192.208:13527 10.0.4.217:80 0.000024 0.015091 0.000016 200 200 68 640 "POST https://paytm.com:443/papi/v1/expresscart/verify HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.89 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2
2015-07-22T09:00:28.033793Z marketpalce-shop 117.239.195.66:50524 10.0.6.195:80 0.000024 0.02157 0.000021 200 200 0 60 "GET https://paytm.com:443/api/user/favourite?channel=web&version=2 HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2
2015-07-22T09:00:28.055029Z marketpalce-shop 101.60.186.26:33177 10.0.4.244:80 0.00002 0.001098 0.000022 200 200 0 1150 "GET https://paytm.com:443/favicon.ico HTTP/1.1" "Mozilla/5.0 (Windows NT 6.3; rv:27.0) Gecko/20100101 Firefox/27.0" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2
2015-07-22T09:00:28.050298Z marketpalce-shop 59.183.41.47:62014 10.0.4.227:80 0.000021 0.008161 0.000021 200 200 0 72 "GET https://paytm.com:443/papi/rr/products/6937770/statistics?channel=web&version=2 HTTP/1.1" "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2
2015-07-22T09:00:28.059081Z marketpalce-shop 117.239.195.66:50538 10.0.4.227:80 0.000019 0.001035 0.000021 200 200 0 396 "GET https://paytm.com:443/images/greyStar.png HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2
2015-07-22T09:00:28.054939Z marketpalce-shop 183.83.237.83:49687 10.0.6.108:80 0.000023 0.008762 0.000021 200 200 0 214 "GET https://paytm.com:443/shop/cart?channel=web&version=2 HTTP/1.1" "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2



println("WeblogMain")
val logFile = "2015_07_22_mktplace_shop_web_log_sample.log"
println(sc.appName)
val logData = sc.textFile(logFile, 2).cache()
//val splitRdd = logData.map( line => line.split("\t") )

// val numAs = logData.filter(line => line.contains("a")).count()
 // val numBs = logData.filter(line => line.contains("b")).count()
 //  println("Lines with a: %s, Lines with b: %s".format(numAs, numBs))
 logData.take(10).foreach(println)
import sqlContext.implicits._
val logDf  =logData.toDF()
logDf.take(10).foreach(println)
logDf.count
logDf.take(1).length
