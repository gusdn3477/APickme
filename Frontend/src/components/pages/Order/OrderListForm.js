import React, {useEffect, useState} from 'react';
import OrderListView from './OrderListView';

export default function OrderListForm() {

    const [cartDatas, setCartDatas] = useState([]);

    let process = require('../../../db/myProcess.json');

    //추후에 orders 테이블로 바꿔야 함
    useEffect(()=>{
        fetch(`/order-service/orders`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            setCartDatas(data);
        });
    },[process.IP, process.PORT]);
    return(
        <div className="cart-main-area pt-90 pb-100">
            <div className="container">
                <h3 className="cart-page-title">전체 주문내역 입니다.</h3>
                <div className="row">
                    <div className="col-12">
                        <div className="table-content table-responsive cart-table-content">
                            <table>
                                <thead>
                                    <tr>
                                        <th>사진</th>
                                        <th>주문번호</th>
                                        <th>가격</th>
                                        <th>갯수</th>
                                        <th>총 가격</th>
                                        <th>주문 일자</th>
                                    </tr>
                                </thead>
                                <tbody>
                                {
                                    cartDatas.length > 0 && cartDatas.map(item => (
                                        <OrderListView
                                            key = {item.id}
                                            data = {item}
                                            setCartDatas = {setCartDatas}
                                        />
                                    ))
                                }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}