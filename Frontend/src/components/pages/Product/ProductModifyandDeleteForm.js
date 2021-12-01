import React, {useEffect, useState} from 'react';
import ProductModifyandDeleteView from './ProductModifyandDeleteView';
import { Link } from 'react-router-dom';

export default function OrderListForm() {

    const [cartDatas, setCartDatas] = useState([]);

    let process = require('../../../db/myProcess.json');

    //orders 테이블로 바꿔야 함
    useEffect(()=>{
        fetch(`/catalog-service/catalogs`)
        .then(res => {
            return res.json();
        })
        .then(data => {
            setCartDatas(data);
        });
    },[]);

    return(
        <div className="cart-main-area pt-90 pb-100">
            <div className="container">
                <h3 className="cart-page-title">상품에 대해 수정 및 삭제를 할 수 있습니다.</h3>
                <div className="row">
                    <div className="col-12">
                        <div className="table-content table-responsive cart-table-content">
                            <table>
                                <thead>
                                    <tr>
                                        <th>사진</th>
                                        <th>책 제목</th>
                                        <th>작가</th>
                                        <th>가격</th>
                                        <th>갯수</th>
                                        <th>총 가격</th>
                                        <th>등록 일자</th>
                                        <th>삭제하기</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        cartDatas.length > 0 && cartDatas.map(item => (
                                            <ProductModifyandDeleteView
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