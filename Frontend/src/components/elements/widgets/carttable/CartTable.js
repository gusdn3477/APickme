import React, {useEffect, useState} from 'react';
import CartListView from '../../ui/CartListView';
import CartTableFooter from './CartTableFooter';

export default function CartTable() {

    const [cartDatas, setCartDatas] = useState([]);
    let process = require('../../../../db/myProcess.json');

    useEffect(()=>{
        fetch(`/cart-service/${localStorage.getItem('userId')}/carts`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            setCartDatas(data);
            console.log(data);
        });
    },[]);

    return(
        <div className="cart-main-area pt-90 pb-100">
            <div className="container">
                <h3 className="cart-page-title">장바구니</h3>
                <div className="row">
                    <div className="col-12">
                        <div className="table-content table-responsive cart-table-content">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Image</th>
                                        <th>상품 이름</th>
                                        <th>가격</th>
                                        <th>갯수</th>
                                        <th>총 가격</th>
                                        <th>담은 날짜</th>
                                        <th>구매하기</th>
                                        <th>삭제하기</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        cartDatas.length > 0 && cartDatas.map(item => (
                                            <CartListView
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