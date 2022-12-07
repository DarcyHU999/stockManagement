import React, {useState,useEffect} from 'react';
import {Col, Row, Button, Descriptions, Table,} from 'antd';
import { Statistic } from 'antd';
import { Typography } from 'antd';
import { AudioOutlined } from '@ant-design/icons';
import { List, Input, Space } from 'antd';
import { InputNumber } from 'antd';

import axios from "axios";
import Card from "antd/es/card/Card";

const { Search } = Input;


const Home = () => {
    const [balance,setBalance]=useState(0);
    const [querySym,setQuerySym]=useState('-');
    const [queryPrice,setQueryPrice]=useState('-');
    const [buyCount,setBuyCount]=useState(1);
    const [buySym,setBuySym]=useState("-");
    const [sellCount,setSellCount]=useState(1);
    const [sellSym,setSellSym]=useState("-");
    const [stockHold,setStockHold]=useState([]);
    const columns = [
        {
            title: 'Sym',
            dataIndex: 'sym',
            sorter: (a, b) => a.sym.length - b.sym.length,
            sortDirections: ['descend','ascend'],
        },
        {
            title: 'Count',
            dataIndex: 'count',
            sorter: (a, b) => a.count - b.count,
            sortDirections: ['descend','ascend'],
        },
        {
            title: 'Avg',
            dataIndex: 'avg',
            sorter: (a, b) => a.avg - b.avg,
            sortDirections: ['descend','ascend'],
        },
    ];

    const onStockHold = (pagination, sorter, extra) => {
        console.log('params', pagination, sorter, extra);
        let url = 'http://localhost:8080/stock/stockhold';
        axios.get(url)
            .then(function (response) {

                if(response.data.data !== "-1"){
                    console.log("-----");
                    console.log(response.data);
                    setStockHold(JSON.parse(response.data.data));


                }else{
                    console.log("fuck");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    useEffect(()=>{
        let url = 'http://localhost:8080/balance/query';
        axios.get(url)
            .then(function (response) {
                let data=response.data.data;
                if(data !== "-1"){
                    console.log(data);
                    setBalance(data);

                }else{
                    console.log("fuck");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
        onStockHold();
    },[])

    const onBuyCount = (value) => {
        console.log('changed', value);
        setBuyCount(value);
    };

    const onSellCount = (value) => {
        console.log('changed', value);
        setSellCount(value);
    };

    const onSellSym = (value) => {
        console.log("changed", value.target.value);
        setSellSym(value.target.value);
    };

    const onBuySym = (value) => {
        console.log("changed", value.target.value);
        setBuySym(value.target.value);
    };

    const onSell = () => {
        let bodyFormData = new FormData();
        bodyFormData.append('sym','' + sellSym);
        bodyFormData.append('amount','' + sellCount);
        let url = 'http://localhost:8080/stock/sell';
        axios.post(url,bodyFormData)
            .then(function (response) {
                console.log(response.data.data);
                if(response.data.data === "-1"){
                    alert(response.data.msg);
                }else {
                    setBalance(response.data.data);
                    onStockHold();
                }
            })
            .catch(function (error) {
                console.log(error);
            });

    };

    const onBuy = () => {
        let bodyFormData = new FormData();
        bodyFormData.append('sym','' + buySym);
        bodyFormData.append('amount', '' + buyCount);
        console.log("=========",buySym);
        let url = 'http://localhost:8080/stock/buy';
        axios.post(url,bodyFormData)
            .then(function (response) {
                console.log(response.data.data);
                if(response.data.data === "-1"){
                    alert(response.data.msg);
                }
                else {
                    setBalance(response.data.data);
                    onStockHold();
                }
            })
            .catch(function (error) {
                console.log(error);
            });


    };

    const onSearch = (value) => {
        console.log(value);
        let url = 'http://localhost:8080/stock/queryprice/' + value;
        axios.get(url)
            .then(function (response) {
                let data=response.data.data;
                if(data !== "-1"){
                    console.log(data);
                    setQuerySym(value);
                    setQueryPrice(data);
                }else{
                    console.log("unknown error");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    const onDeposit = (value) => {
        console.log(value);
        let bodyFormData = new FormData();
        bodyFormData.append('cash',value);
        let url = 'http://localhost:8080/balance/deposit';
        axios.post(url,bodyFormData)
            .then(function (response) {
                console.log(response.data.data);
                setBalance(response.data.data);
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    const onWithdraw = (value) => {
        console.log(value);
        if(balance < value){
            alert("You do not have enough money")
        }else {
            let bodyFormData = new FormData();
            bodyFormData.append('cash', value);
            let url = 'http://localhost:8080/balance/withdraw';
            axios.post(url, bodyFormData)
                .then(function (response) {
                    console.log(response.data.data);
                    setBalance(response.data.data);
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    };

    return (
        <div>
            <div>
                <h1>STOCK MANAGEMENT</h1>
            </div>
            <div>
                <Row>
                    <Col span={8}></Col>
                    <Col span={8}>
                        <Statistic title="Account Balance (AUD)" value={balance} precision={2} />
                    </Col>
                    <Col span={8}></Col>
                </Row>

            </div>
            <div style = {{height:"10px"}}></div>
            <div>
                <Row>
                    <Col span = {8}></Col>
                    <Col span = {8}>
                        <Space>
                            <Search
                                placeholder="amounts of cash"
                                enterButton="+"
                                size="large"
                                onSearch={onDeposit}
                            />
                        </Space>
                    </Col>
                    <Col span = {8}></Col>
                </Row>
            </div>
            <div style = {{height:"10px"}}></div>
            <div>
                <Row>
                    <Col span = {8}></Col>
                    <Col span ={8}>
                        <Space>
                            <Search
                                placeholder="amounts of cash"
                                enterButton="-"
                                size="large"
                                onSearch={onWithdraw}
                            />
                        </Space>
                    </Col>
                    <Col span = {8}></Col>
                </Row>
            </div>
            <div style = {{height:"10px"}}></div>
            <div className="site-card-wrapper">
                <Row gutter={16}>
                    <Col span={8}></Col>
                    <Col span={8}>
                        <Statistic title={`Current Price (${querySym})`} value={queryPrice} precision={2} />
                    </Col>
                    <Col span={8}></Col>
                </Row>
            </div>
            <div style = {{height:"20px"}}></div>
            <div>
                <Row>
                    <Col span ={8}></Col>
                    <Col span ={8}>
                        <Space>
                            <Search
                                placeholder="Input stock sym"
                                enterButton="Query"
                                size="large"
                                onSearch={onSearch}
                            />
                        </Space>
                    </Col>
                    <Col span ={8}></Col>
                </Row>
            </div>
            <div style = {{height:"20px"}}></div>
            <div>
                <Row>
                    <Col span = {6}></Col>
                    <Col span = {12}>
                        <Space>
                            <Input placeholder="input stock sym to buy" size="large" onChange={onBuySym}/>
                        </Space>
                        <Space>
                            <InputNumber size="large" min={1} max={100000} defaultValue={1} onChange={onBuyCount} />
                        </Space>
                        <Button type="primary" size={"large"} onClick={onBuy}>
                            Buy
                        </Button>
                    </Col>
                    <Col span = {6}>
                    </Col>
                </Row>
            </div>
            <div style = {{height:"20px"}}></div>
            <div>
                <Row>
                    <Col span = {6}></Col>
                    <Col span = {12}>
                        <Space>
                            <Input placeholder="input stock sym to sell" size="large" onChange={onSellSym}/>
                        </Space>
                        <Space>
                            <InputNumber size="large" min={1} max={100000} defaultValue={1} onChange={onSellCount} />
                        </Space>
                        <Button type="primary" size={"large"} onClick={onSell}>
                            Sell
                        </Button>

                    </Col>
                    <Col span = {6}>

                    </Col>
                </Row>
            </div>
            <div style = {{height:"20px"}}></div>
            <div>
                <Row>
                    <Col span={8}></Col>
                    <Col span={8}>
                        <Table columns={columns} dataSource={stockHold} onChange={onStockHold} />
                    </Col>
                    <Col span={8}></Col>
                </Row>
            </div>


        </div>
    );
};

export default Home;