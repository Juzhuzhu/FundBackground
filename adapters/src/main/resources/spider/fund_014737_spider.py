# !/usr/bin/env python
# -*-coding:utf-8 -*-

"""
# File       : fund_014737_spider.py
# Time       ：2023/5/9 22:50
# Author     ：罗康明
# version    ：python 3.9.7
# Description：
"""
import re
import requests
from bs4 import BeautifulSoup
import pymysql.cursors

"""
type：lsjz表示历史净值
code：表示基金代码
page：表示获取的数据的页码
per：表示获取的数据每页显示的条数
sdate：表示开始时间
edate：表示结束时间
"""
id = 20


# 创建链接，获取源码
def create_request(fund_code, page, per, sdate, edate):
    # https://fundf10.eastmoney.com/F10DataApi.aspx?type=lsjz&code=050026&page=1&sdate=2020-01-01&edate=2020-03-01&per=20

    url = "https://fundf10.eastmoney.com/F10DataApi.aspx?"
    data = {
        "type": "lsjz",
        "code": fund_code,
        "page": page,
        "per": per,
        "sdate": sdate,
        "edate": edate
    }
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36"
    }
    # response = requests.get(url=url, params=data, headers=headers)
    try:
        response = requests.get(url=url, params=data, headers=headers)
    except requests.exceptions.ConnectionError:
        print("Connection refused")
    return response


# 解析源码
def parse_html(response):
    soup = BeautifulSoup(response.text, "lxml")
    # 先解析获取所有tr标签包含的内容，是一个列表
    trs = soup.find_all("tr")
    # 最终解析完毕后的结果，初始是一个空列表，后续解析逐渐加入
    result = []
    for tr in trs[1:]:
        fund_date = tr.find_all("td")[0].text  # 净值日期
        fund_nav = tr.find_all("td")[1].text  # 单位净值
        fund_accnav = tr.find_all("td")[2].text  # 累计净值
        fund_dgr = tr.find_all("td")[3].text  # 日增长率
        subsribe_state = tr.find_all("td")[4].text  # 申购状态
        redeem_state = tr.find_all("td")[5].text  # 赎回状态
        result.append([fund_date, fund_nav, fund_accnav, fund_dgr, subsribe_state, redeem_state])
    return result


# 插入数据
def input_data(result):
    try:
        # 获取游标并开启事物
        cursor = connection.cursor()
        connection.begin()
        # 将当页的内容一条条插入进数据库
        for j in result:
            sql = "insert into fund_014737(fund_id,fund_date,fund_nav,fund_accnav,fund_dgr,subsribe_state,redeem_state) values ('%s','%s','%s','%s','%s','%s','%s');"
            cursor.execute(sql % (id, j[0], j[1], j[2], j[3], j[4], j[5]))
        # 提交事务
        connection.commit()
        print("插入数据成功----------------")
    except Exception as input_exception:
        connection.rollback()
        connection.close()
        print(input_exception)


# 插入前清空数据
def clear_data():
    try:
        cursor = connection.cursor()
        connection.begin()
        cursor.execute("truncate table fund_014737")
        connection.commit()
        print("清空数据表fund_014737")
    except Exception as input_exception:
        connection.rollback()
        connection.close()
        print(input_exception)


# 更新fund_list
def update_fund_list():
    try:
        cursor = connection.cursor()
        connection.begin()
        # 查询最新的记录
        sql = "select fund_id,fund_nav,fund_accnav,fund_date,fund_dgr,subsribe_state,redeem_state from fund_014737 order by fund_date desc limit 1"
        cursor.execute(sql)
        result = cursor.fetchone()
        fund_id = result.get("fund_id")
        fund_nav = result.get("fund_nav")
        fund_accnav = result.get("fund_accnav")
        fund_date = result.get("fund_date")
        fund_dgr = result.get("fund_dgr")
        subsribe_state = result.get("subsribe_state")
        redeem_state = result.get("redeem_state")
        print("最新数据是==》" + str(result))
        # 更新fund_list
        update_sql = "update fund_list set fund_nav='%s',fund_accnav='%s',fund_date='%s',fund_dgr='%s',subsribe_state='%s',redeem_state='%s',fund_fee='%s' where id = '%s' "
        cursor.execute(
            update_sql % (fund_nav, fund_accnav, fund_date, fund_dgr, subsribe_state, redeem_state, "0.15%", fund_id))
        print("更新成功fund_list,对应fund_id为：" + str(fund_id))
        connection.commit()
    except Exception as input_exception:
        connection.rollback()
        connection.close()
        print(input_exception)


if __name__ == '__main__':
    # 数据库连接
    try:
        connection = pymysql.connect(host="localhost",
                                     user="root",
                                     password="123456",
                                     db="fund_db",
                                     charset='utf8mb4',
                                     cursorclass=pymysql.cursors.DictCursor)
        print("数据库连接成功")
    except Exception as e:
        print(e)
    # 创建连接获取源码
    response = create_request("014737", 1, 20, "2012-01-01", "2040-02-04")
    response.encoding = "utf-8"
    # 获取页码最大值
    max_page = re.findall(r'pages:(.*),', response.text)[0]
    response.close()
    # 爬取前清空数据
    clear_data()
    # 循环爬取多页内容
    for i in range(1, int(max_page) + 1):
        print("目前正在爬取第" + str(i) + "页----")
        response = create_request("014737", i, 20, "2012-01-01", "2040-02-04")
        response.encoding = "utf-8"
        # 解析获取当页结果
        result = parse_html(response)
        # 入库
        print("插入第" + str(i) + "页数据")
        input_data(result)
        response.close()
    # 更新fund_list
    update_fund_list()
    # 最后关闭数据库连接
    connection.close()
    print("数据库连接关闭")